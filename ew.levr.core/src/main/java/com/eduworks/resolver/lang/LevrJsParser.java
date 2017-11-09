package com.eduworks.resolver.lang;

import com.eduworks.lang.json.impl.EwJsonObject;
import com.eduworks.lang.util.EwJson;
import com.eduworks.resolver.*;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.runtime.Undefined;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.script.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;
import java.util.logging.Level;

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
        reinitialize();
        String allCruncherBindings = "";
        for (String cruncherName : ResolverFactory.cruncherSpecs.keySet())
        {
            if (cruncherName.equals("toString")) continue; 
            String cruncherBinding = generateCruncherBinding(cruncherName);
            if (cruncherBinding != null)
            {
                allCruncherBindings += cruncherBinding;
            }
        }
        try
        {
            engine.eval(allCruncherBindings);
        }
        catch (ScriptException ex)
        {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        log.info("Cruncher Javascript Bindings Loaded.");
    }

    public static void reinitialize()
    {
        factory = new ScriptEngineManager();
        engine = factory.getEngineByName("nashorn");
        engine.put("ctx", new Context());
        engine.put("parameters", null);
        engine.put("dataStreams", null);
    }

    public static Bindings decodeStreams(File fileToDecode)
    {
        Invocable inv = (Invocable) engine;
        FileReader fr;
        try
        {
            fr = new FileReader(fileToDecode);
            engine.eval(fr);
            Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
            fr.close();
            if (bindings.size() > 0)
            {
                final TreeSet<String> bindingTreeset = new TreeSet<>();
                bindingTreeset.addAll(bindings.keySet());
                bindingTreeset.removeAll(ResolverFactory.cruncherSpecs.keySet());
                log.debug(bindingTreeset);
            }
            return bindings;
        }
        catch (FileNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (ScriptException ex)
        {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static String generateCruncherBinding(String cruncherName)
    {
        try
        {
            if (cruncherName.equals("try") || cruncherName.equals("if") || cruncherName.equals("catch") || cruncherName.equals("null") || cruncherName.equals("while") || cruncherName.equals("true") || cruncherName.equals("false"))
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
            }
            else
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
                }
                else if (key.startsWith("?"))
                {
                    key = key.replace("?", "");
                    jsTemplate += "\tif (v" + key.replace("-", "") + " != null) cru.build('" + key + "',com.eduworks.resolver.lang.LevrJsParser.jsToJava(v" + key.replace("-", "") + "));\n";
                }
                else
                {
                    jsTemplate += "\tcru.build('" + key + "',com.eduworks.resolver.lang.LevrJsParser.jsToJava(v" + key.replace("-", "") + "));\n";
                }
            }

            jsTemplate += "\tif (this.parameters === undefined)\n\t\tthrow new java.lang.NullPointerException(\"Cruncher invoked without appropriate 'this'. Please use fun.call to pass 'this' from invocation source to the current function.\");\n";

            jsTemplate += "\treturn com.eduworks.resolver.lang.LevrJsParser.javaToJs(cru.resolve("
                    + "this === undefined ? new com.eduworks.resolver.Context() : this.ctx,"
                    + "this === undefined ? null : this.parameters,"
                    + "this === undefined ? null : this.dataStreams"
                    + "));\n"
                    + "}";
            jsTemplate = jsTemplate.replace("{paramList}", paramList).replace("{functionName}", cruncherName);
            //System.out.println(jsTemplate);
            return jsTemplate;
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (JSONException ex)
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
            jsTemplate += "\tvar cru = new com.eduworks.cruncher.refl.CruncherExecute();\n";
            jsTemplate += "\tcru.build('service','" + cruncherName + "');\n";
            jsTemplate += "\tif (vany != null) for(var k in vany) cru.build(k,com.eduworks.resolver.lang.LevrJsParser.jsToJava(vany[k]));\n";

            jsTemplate += "\tif (this.parameters === undefined)\n\t\tthrow new java.lang.NullPointerException(\"Levr Function invoked without appropriate 'this'. Please use fun.call to pass 'this' from invocation source to the current function.\");\n";

            jsTemplate += "\treturn com.eduworks.resolver.lang.LevrJsParser.javaToJs(cru.resolve("
                    + "this === undefined ? new com.eduworks.resolver.Context() : this.ctx,"
                    + "this === undefined ? null : this.parameters,"
                    + "this === undefined ? null : this.dataStreams"
                    + "));\n";
            jsTemplate += "}";
            jsTemplate = jsTemplate.replace("{functionName}", cruncherName);
//            System.out.println(jsTemplate);
            engine.eval(jsTemplate);
        }
        catch (ScriptException ex)
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
        else if (o instanceof Boolean)
            return o.toString();
        else if (o instanceof Undefined)
            return null;
        return o;
    }

    public static Object javaToJs(Object o)
    {
        if (o instanceof JSONArray || o instanceof JSONObject)
        {
            if (o instanceof JSONArray)
            {
                JSONArray ary = (JSONArray) o;
                for (int i = 0; i < ary.length(); i++)
                {
                    try
                    {
                        if (ary.isNull(i))
                            continue;
                        Object test = ary.get(i);
                        if (test.getClass().isPrimitive())
                        {
                            continue;
                        }
                        if (test instanceof JSONArray)
                        {
                            continue;
                        }
                        if (test instanceof JSONObject)
                        {
                            continue;
                        }
                        if (test instanceof String || test instanceof Boolean || test instanceof Number)
                        {
                            continue;
                        }
                        return o;
                    }
                    catch (JSONException ex)
                    {
                        java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if (o instanceof JSONObject)
            {
                JSONObject obj = (JSONObject) o;
                for (String key : EwJson.getKeys(obj))
                {
                    try
                    {
                        if (obj.isNull(key))
                            continue;
                        Object test = obj.get(key);
                        if (test.getClass().isPrimitive())
                        {
                            continue;
                        }
                        if (test instanceof JSONArray)
                        {
                            continue;
                        }
                        if (test instanceof JSONObject)
                        {
                            continue;
                        }
                        if (test instanceof String || test instanceof Boolean || test instanceof Number)
                        {
                            continue;
                        }
                        return o;
                    }
                    catch (JSONException ex)
                    {
                        java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            try
            {
                ScriptObjectMirror som = (ScriptObjectMirror) engine.eval("JSON.parse");
                o = som.call(null, o.toString());
            }
            catch (ScriptException ex)
            {
                java.util.logging.Logger.getLogger(LevrJsParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return o;
    }
}
