package com.eduworks.cruncher.ws;

import com.eduworks.levr.websocket.LevrResolverWebSocket;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import org.json.JSONException;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.InputStream;
import java.util.Map;

/**
 * Broadcasts a message to all websockets connected to this machine.
 * <p>
 * rs2: obj.wsBroadcast();<br>
 * LevrJS: wsBroadcast.call(this,obj);
 *
 * @class wsBroadcast
 * @module ew.levr.base
 * @author fritz.ray@eduworks.com
 */

/**
 * @param obj {Cruncher|Function|Object} Thing to broadcast. Will be converted to a string.
 * @method wsBroadcast
 * @return {Object} Returns obj.
 */
public class CruncherWsBroadcast extends Cruncher {

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException {
		Object obj = getObj(c, parameters, dataStreams);
		if (obj == null) return null;
		for (Session s : LevrResolverWebSocket.sessions) {
			resolve(obj, s);
		}
		return obj;
	}

	public static void resolve(Object obj, Session s) throws JSONException {
		if (!s.isOpen()) {
			LevrResolverWebSocket.sessions.remove(s);
			return;
		}
		try {
			s.getAsyncRemote().sendText(obj.toString());
		} catch (Exception ex) {
			try {
				resolve(obj,s);
			} catch (StackOverflowError error) {
			}
		}
	}

	@Override
	public String getDescription() {
		return "Broadcasts a message to all websockets connected to this machine.";
	}

	@Override
	public String getReturn() {
		return "Object";
	}

	@Override
	public String getAttribution() {
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException {
		return jo("obj", "Object");
	}

}
