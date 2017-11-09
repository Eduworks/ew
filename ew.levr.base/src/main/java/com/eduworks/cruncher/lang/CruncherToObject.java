package com.eduworks.cruncher.lang;

import com.eduworks.lang.json.impl.EwJsonObject;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Map;

public class CruncherToObject extends Cruncher {

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException {
		Object obj = getObj(c, parameters, dataStreams);
		if (obj instanceof JSONObject)
			return obj;
		if (obj == null)
			return null;
		String asString = obj.toString();
		if (asString == null) return null;
		if (asString.isEmpty()) return null;
		Object object = c.get(asString);
		JSONObject result = null;
		if (object instanceof JSONObject)
			result = (JSONObject) object;
		if (result == null)
			try {
				result = new EwJsonObject(asString);
			} catch (JSONException ex) {
				if (optAsBoolean("soft", false, c, parameters, dataStreams))
					return null;
				throw ex;
			}
		return result;
	}

	@Override
	public String getDescription() {
		return "Converts a string into a JSONObject.";
	}

	@Override
	public String getReturn() {
		return "JSONObject";
	}

	@Override
	public String getAttribution() {
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException {
		return jo("obj", "String");
	}

}
