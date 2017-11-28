package com.eduworks.cruncher.ws;

import com.eduworks.levr.websocket.LevrResolverWebSocket;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import java.io.InputStream;
import java.util.Map;
import javax.websocket.Session;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Broadcasts a message to all websockets connected to this machine.
 * 
 * rs2: obj.wsBroadcast(to="@wsId");<br>
 * LevrJS: wsBroadcast.call(this,params.wsId,obj);
 *
 * @class wsEmit
 * @module ew.levr.base
 * @author fritz.ray@eduworks.com
 */
/**
 * @method wsEmit
 * @param to {String} ID to emit to, set by the WebSocket Session. (comes in on "@wsId")
 * @param obj {Cruncher|Function|Object} Thing to emit. Will be converted to a string.
 * @return {Object} Returns obj.
 */
public class CruncherWsEmit extends Cruncher
{

    @Override
    public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
    {
        String to = getAsString("to", c, parameters, dataStreams);
        Object obj = getObj(c, parameters, dataStreams);
        if (obj == null) return null;
        for (Session s : LevrResolverWebSocket.sessions)
        {
            if (s.getId().equals(to))
                CruncherWsBroadcast.resolve(obj,s);
        }
        return obj;
    }

    @Override
    public String getDescription()
    {
        return "Sends a message to the session identified by 'to', connected to this machine.";
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
        return jo("to","String","obj","Object");
    }
    
}
