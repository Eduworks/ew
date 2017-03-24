package com.eduworks.resolver;

import com.eduworks.resolver.lang.LevrJsParser;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author fray
 */
public class CruncherJavascriptBinder extends Cruncher
{

    @Override
    public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
    {
        ScriptObjectMirror jo = null;
        ScriptEngine e = LevrJsParser.engine;
        try
        {
            jo = (ScriptObjectMirror) e.eval("new Object()");
//            Invocable i = (Invocable)e;
//            i.invokeMethod(e.eval("Object"), "bindProperties",jo, e.eval("this"));
//            jo = (ScriptObjectMirror) e.eval("new Object()");
        } catch (ScriptException ex)
        {
            Logger.getLogger(CruncherJavascriptBinder.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (String s : parameters.keySet())
        {
            if (parameters.get(s) != null)
            {
                if (parameters.get(s).length != 0)
                {
                    jo.put(s, parameters.get(s)[0]);
                }
            }
        }
        final ScriptObjectMirror som;
        if (get("obj") instanceof ScriptObjectMirror)
        {
            som = (ScriptObjectMirror) get("obj");
        } else
        {
            som = (ScriptObjectMirror) LevrJsParser.engine.get(getAsString("function", c, parameters, dataStreams));
        }
        ScriptObjectMirror newThis = null;
        try
        {
            newThis = (ScriptObjectMirror) LevrJsParser.engine.eval("new Object()");
        } catch (ScriptException ex)
        {
            Logger.getLogger(CruncherJavascriptBinder.class.getName()).log(Level.SEVERE, null, ex);
        }
        newThis.put("ctx", c);
        newThis.put("params", jo);
        newThis.put("parameters", parameters);
        newThis.put("dataStreams", dataStreams);
        return LevrJsParser.jsToJava(som.call(newThis));
    }

    @Override
    public String getDescription()
    {
        return "Executes a javascript binding with an object constructed from the parameters.";
    }

    @Override
    public String getReturn()
    {
        return "Object";
    }

    @Override
    public String getAttribution()
    {
        return ATTRIB_NONE;
    }

    @Override
    public JSONObject getParameters() throws JSONException
    {
        return jo("obj", "Object");
    }

}
