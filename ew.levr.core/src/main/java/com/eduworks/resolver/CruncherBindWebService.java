/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eduworks.resolver;

import com.eduworks.levr.servlet.impl.LevrResolverServlet;
import java.io.InputStream;
import java.util.Map;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author fray
 */
public class CruncherBindWebService extends Cruncher
{

    @Override
    public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
    {
        final Object get = get("obj");
        if (get instanceof Resolvable)
        {
            LevrResolverServlet.resolvableWebServices.put(getAsString("endpoint", c, parameters, dataStreams), (Resolvable) get);
            return null;
        }
        if (get instanceof ScriptObjectMirror)
        {
            ScriptObjectMirror m = (ScriptObjectMirror) get;
            if (m.isFunction())
            {
                CruncherJavascriptBinder b = new CruncherJavascriptBinder();
                b.build("obj", m);

                LevrResolverServlet.resolvableWebServices.put(getAsString("endpoint", c, parameters, dataStreams), b);
                return null;
            }
        }
        return null;
    }

    @Override
    public String getDescription()
    {
        return "Creates a binding of a web service endpoint, specified by endpoint, to the function chain defined by obj.";
    }

    @Override
    public String getReturn()
    {
        return null;
    }

    @Override
    public String getAttribution()
    {
        return ATTRIB_NONE;
    }

    @Override
    public JSONObject getParameters() throws JSONException
    {
        return jo("endpoint", "String", "obj", "Resolvable");
    }

}
