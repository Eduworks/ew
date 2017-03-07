package com.eduworks.cruncher.lang;

import java.io.InputStream;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherWhile extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		Object result = null;
		Boolean accm = optAsBoolean("accumulate", false, c, parameters, dataStreams);
		if (accm)
			result = new JSONArray();
		if (optAsString("do", "false", c, parameters, dataStreams).equals("true"))
			if (accm)
				((JSONArray) result).put(getObj(c, parameters, dataStreams));
			else
				result = getObj(c, parameters, dataStreams);
		Object o = get("condition", c, parameters, dataStreams);
		while (o != null && !o.equals("false"))
		{
			if (accm)
				((JSONArray) result).put(getObj(c, parameters, dataStreams));
			else
				result = getObj(c, parameters, dataStreams);
			o = get("condition", c, parameters, dataStreams);
		}
		return result;
	}

	@Override
	public String getDescription()
	{
		return "Will perform some 'obj' Resolvable until a condition resolves to 'false'. Set 'do' to true if you wish to make it a do-while.";
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
		return jo("obj", "Resolvable", "condition", "Boolean", "?do", "Boolean");
	}

}
