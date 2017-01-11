package com.eduworks.resolver.lang;

import com.eduworks.lang.json.impl.EwJsonObject;
import com.eduworks.resolver.Cruncher;
import com.eduworks.resolver.ResolverFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;
import java.util.logging.Level;
import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.apache.log4j.Logger;
import org.json.JSONException;

/**
 *
 * @author fray
 */
public class LevrJsParser {

    public static Logger log = Logger.getLogger(LevrJsParser.class);
    public static ScriptEngineManager factory;
    public static ScriptEngine engine;

    static {
        factory = new ScriptEngineManager();
        engine = factory.getEngineByName("nashorn");
        log.info("Nashorn Javascript Engine Loaded.");
        for (String cruncherName : ResolverFactory.cruncherSpecs.keySet()) {
            generateCruncherBinding(cruncherName);
        }
        log.info("Cruncher Javascript Bindings Loaded.");
    }

    public static Bindings decodeStreams(File fileToDecode) {
        Invocable inv = (Invocable) engine;
        FileReader fr;
        try {
            fr = new FileReader(fileToDecode);
            engine.eval(fr);
            Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
            fr.close();
            if (bindings.size() > 0) {
                log.debug(new TreeSet<String>(bindings.keySet()));
            }
            return bindings;
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ScriptException ex) {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static void generateCruncherBinding(String cruncherName) {
        try {
            if (cruncherName.equals("try")||cruncherName.equals("if")||cruncherName.equals("catch")||cruncherName.equals("while")||cruncherName.equals("true")||cruncherName.equals("false")) {
                return;
            }
            Cruncher c = ResolverFactory.cruncherSpecs.get(cruncherName).newInstance();
            String paramList = "";
            if (c.getParameters() == null) {
                log.warn("No parameter list found for " + cruncherName + ". Not creating JS Stub.");
                return;
            }
            for (String key : ((EwJsonObject) c.getParameters()).keySetUnsorted()) {
                key = key.trim();
                if (key.contains("<any>")) {
                    key = "any";
                }
                if (key.startsWith("?")) {
                    key = key.replace("?", "");
                }
                if (!paramList.isEmpty()) {
                    paramList += ",";
                }
                paramList += "v" + key.replace("-","");
                //String value = c.getParameters().getString(key);
            }
            String jsTemplate;
            if (paramList.isEmpty()) {
                jsTemplate = "function {functionName}(){\n";
            } else {
                jsTemplate = "function {functionName}({paramList}){\n";
            }
            jsTemplate += "var cru = new " + ResolverFactory.cruncherSpecs.get(cruncherName).getName() + "();\n";
            for (String key : ((EwJsonObject) c.getParameters()).keySetUnsorted()) {
                key = key.trim();
                if (key.contains("<any>")) {
                    jsTemplate += "for(var k in vany) cru.build(k,vany[k]);\n";
                } else if (key.startsWith("?")) {
                    key = key.replace("?", "");
                    jsTemplate += "if (key != null) cru.build('" + key + "',v" + key.replace("-","") + ");\n";
                } else {
                    jsTemplate += "cru.build('" + key + "',v" + key.replace("-","") + ");\n";
                }
            }
            jsTemplate += "return cru.resolve(context,parameters,dataStreams);\n"
                    + "}";
            jsTemplate = jsTemplate.replace("{paramList}", paramList).replace("{functionName}", cruncherName);
            //System.out.println(jsTemplate);
            engine.eval(jsTemplate);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ScriptException ex) {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
