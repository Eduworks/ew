package com.eduworks.resolver.math;

import java.io.InputStream;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherFloor extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		JSONObject result = new JSONObject();
		for (String key : keySet())
		{
			result.put(key, (int) Math.floor(getAsDouble(key, c, parameters, dataStreams)));
		}
		if (result.length() == 0)
			return null;
		if (result.length() == 1)
			return result.get(result.keys().next().toString());
		return this;
	}

	@Override
	public String getDescription()
	{
		return "Rounds down all numbers provided. Will return just the number if there is only one result.";
	}

	@Override
	public String getReturn()
	{
		return "JSONObject|Number";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("<any>", "Number");
	}

}
