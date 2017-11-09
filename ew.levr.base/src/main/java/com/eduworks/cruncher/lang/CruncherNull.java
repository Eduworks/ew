package com.eduworks.cruncher.lang;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Map;


/**
 * Returns null.
 *
 * rs2: #toObject(obj="@param").catch(any=#null()); levrJs: f = null;
 *
 * @class null
 * @module ew.levr.base
 * @author fritz.ray@eduworks.com
 */
public class CruncherNull extends Cruncher{

	/**
	 * @method null
	 * @return (Object) null.
	 */
	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException {
		return null;
	}

	@Override
	public String getDescription() {
		return "Returns null.";
	}

	@Override
	public String getReturn() {
		return null;
	}

	@Override
	public String getAttribution() {
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException {
		return jo();
	}
}
