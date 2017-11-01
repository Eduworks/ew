package com.eduworks.cruncher.uuid;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

/**
 * Created by fray on 10/30/17.
 */
public class CruncherUuidFromString extends Cruncher {

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException {
		String obj = getObjAsString(c,parameters,dataStreams);
		if (obj == null) return null;
		return UUID.nameUUIDFromBytes(obj.getBytes()).toString();
	}

	@Override
	public String getDescription() {
		return "Computes a deterministic UUID from a string.";
	}

	@Override
	public String getReturn() {
		return "String";
	}

	@Override
	public String getAttribution() {
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException {
		return jo("obj","String");
	}

}