package com.eduworks.resolver;

import com.eduworks.resolver.lang.LevrJsParser;
import java.io.InputStream;
import java.util.Map;
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
            JSONObject jo = new JSONObject();
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
            LevrJsParser.engine.put("context", c);
            LevrJsParser.engine.put("params", jo);
            LevrJsParser.engine.put("parameters", parameters);
            LevrJsParser.engine.put("dataStreams", dataStreams);
            if (get("obj") instanceof ScriptObjectMirror)
                return ((ScriptObjectMirror) get("obj")).call(null);
            return ((ScriptObjectMirror) LevrJsParser.engine.get(getAsString("function", c, parameters, dataStreams))).call(jo.opt("obj"));
    }

    @Override
    public String getDescription()
    {
        return "Executes a javascript binding with an object constructed from the parameters.";
    }

    @Override
    public String getReturn()
    {
        return "Any";
    }

    @Override
    public String getAttribution()
    {
        return ATTRIB_NONE;
    }

    @Override
    public JSONObject getParameters() throws JSONException
    {
        return jo("obj", "Any");
    }

}
