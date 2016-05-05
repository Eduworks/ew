package com.eduworks.resolver.manip;

import java.io.InputStream;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherReduce extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		Object obj = getObj(c, parameters, dataStreams);
		if (obj == null) return null;

		if (obj instanceof JSONArray)
		{
			JSONArray a = (JSONArray) obj;
			if (a.length() == 0)
				return null;
			if (a.length() == 1)
				return a.get(0);
			return a;
		}
		else if (obj instanceof JSONObject)
		{
			JSONObject o = (JSONObject) obj;
			if (o == null)
				return null;
			else if (o.length() == 0)
				return null;
			else if (o.length() == 1)
				return o.get((String) o.keys().next());
			else
				return o;
		}
		return obj;
	}

	@Override
	public String getDescription()
	{
		return "Will reduce a collection down to its sole item if it contains only one item.";
	}

	@Override
	public String getReturn()
	{
		return "JSONObject|JSONArray|Object";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("obj", "JSONObject|JSONArray");
	}

}
