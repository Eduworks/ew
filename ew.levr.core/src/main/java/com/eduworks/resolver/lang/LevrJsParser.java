package com.eduworks.resolver.lang;

import com.eduworks.lang.json.impl.EwJsonObject;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.resolver.CruncherJavascriptBinder;
import com.eduworks.resolver.Resolvable;
import com.eduworks.resolver.ResolverFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Level;
import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author fray
 */
public class LevrJsParser
{

    public static Logger log = Logger.getLogger(LevrJsParser.class);
    public static ScriptEngineManager factory;
    public static ScriptEngine engine;

    static
    {
        factory = new ScriptEngineManager();
        engine = factory.getEngineByName("nashorn");
        log.info("Nashorn Javascript Engine Loaded.");
        String allCruncherBindings = "";
        for (String cruncherName : ResolverFactory.cruncherSpecs.keySet())
        {
            String cruncherBinding = generateCruncherBinding(cruncherName);
            if (cruncherBinding != null)
            {
                allCruncherBindings += cruncherBinding;
            }
        }
        try
        {
            engine.eval(allCruncherBindings);
        } catch (ScriptException ex)
        {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        log.info("Cruncher Javascript Bindings Loaded.");
    }

    public static Bindings decodeStreams(File fileToDecode)
    {
        Invocable inv = (Invocable) engine;
        FileReader fr;
        try
        {
            fr = new FileReader(fileToDecode);
            LevrJsParser.engine.put("context", new Context());
            LevrJsParser.engine.put("parameters", new HashMap<String,String[]>());
            LevrJsParser.engine.put("dataStreams", new HashMap<String, InputStream>());
            engine.eval(fr);
            Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
            fr.close();
            if (bindings.size() > 0)
            {
                final TreeSet<String> bindingTreeset = new TreeSet<String>(bindings.keySet());
                bindingTreeset.removeAll(ResolverFactory.cruncherSpecs.keySet());
                log.debug(bindingTreeset);
            }
            return bindings;
        } catch (FileNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ScriptException ex)
        {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static String generateCruncherBinding(String cruncherName)
    {
        try
        {
            if (cruncherName.equals("try") || cruncherName.equals("if") || cruncherName.equals("catch") || cruncherName.equals("while") || cruncherName.equals("true") || cruncherName.equals("false"))
            {
                return null;
            }
            Cruncher c = ResolverFactory.cruncherSpecs.get(cruncherName).newInstance();
            String paramList = "";
            if (c.getParameters() == null)
            {
                log.warn("No parameter list found for " + cruncherName + ". Not creating JS Binding.");
                return null;
            }
            for (String key : ((EwJsonObject) c.getParameters()).keySetUnsorted())
            {
                key = key.trim();
                if (key.contains("<any>"))
                {
                    key = "any";
                }
                if (key.startsWith("?"))
                {
                    key = key.replace("?", "");
                }
                if (!paramList.isEmpty())
                {
                    paramList += ",";
                }
                paramList += "v" + key.replace("-", "");
                //String value = c.getParameters().getString(key);
            }
            String jsTemplate;
            if (paramList.isEmpty())
            {
                jsTemplate = "function {functionName}(){\n";
            } else
            {
                jsTemplate = "function {functionName}({paramList}){\n";
            }
            jsTemplate += "\tvar cru = new " + ResolverFactory.cruncherSpecs.get(cruncherName).getName() + "();\n";
            for (String key : ((EwJsonObject) c.getParameters()).keySetUnsorted())
            {
                key = key.trim();
                if (key.contains("<any>"))
                {
                    jsTemplate += "\tfor(var k in vany) cru.build(k,com.eduworks.resolver.lang.LevrJsParser.jsToJava(vany[k]));\n";
                } else if (key.startsWith("?"))
                {
                    key = key.replace("?", "");
                    jsTemplate += "\tif (v" + key.replace("-", "") + " != null) cru.build('" + key + "',com.eduworks.resolver.lang.LevrJsParser.jsToJava(v" + key.replace("-", "") + "));\n";
                } else
                {
                    jsTemplate += "\tcru.build('" + key + "',com.eduworks.resolver.lang.LevrJsParser.jsToJava(v" + key.replace("-", "") + "));\n";
                }
            }
            jsTemplate += "\treturn cru.resolve(this.context,this.parameters,this.dataStreams);\n"
                    + "}";
            jsTemplate = jsTemplate.replace("{paramList}", paramList).replace("{functionName}", cruncherName);
            //System.out.println(jsTemplate);
            return jsTemplate;
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex)
        {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void createJavascriptFunctionBinding(String cruncherName, Resolvable c)
    {
        try
        {
            String jsTemplate;
            jsTemplate = "function {functionName}(vany){\n";
            jsTemplate += "\tvar cru = com.eduworks.levr.servlet.impl.LevrResolverServlet.resolvableFunctions.get('" + cruncherName + "');\n";
            jsTemplate += "\tif (vany != null) for(var k in vany) cru.build(k,com.eduworks.resolver.lang.LevrJsParser.jsToJava(vany[k]));\n";
            jsTemplate += "\treturn cru.resolve(this.context,this.parameters,this.dataStreams);\n"
                    + "}";
            jsTemplate = jsTemplate.replace("{functionName}", cruncherName);
//            System.out.println(jsTemplate);
            engine.eval(jsTemplate);
        } catch (ScriptException ex)
        {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Object jsToJava(Object o)
    {
        if (o instanceof ScriptObjectMirror)
        {
        ScriptObjectMirror m = (ScriptObjectMirror) o;
        if (m.getClassName().toLowerCase().equals("object"))
        {
            return new JSONObject(m);
        }
        else if (m.isArray())
        {
            return new JSONArray(m.values());
        }
        else if (m.isFunction())
        {
            CruncherJavascriptBinder b = new CruncherJavascriptBinder();
            b.build("obj", m);
            return b;
        }
        }
        return o;
    }
}
