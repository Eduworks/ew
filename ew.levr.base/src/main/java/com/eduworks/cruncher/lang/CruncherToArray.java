package com.eduworks.cruncher.lang;

import com.eduworks.lang.EwList;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class CruncherToArray extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		Object obj = getObj(c, parameters, dataStreams);
		if (obj == null)
			return null;
		if (obj instanceof String[])
			return new JSONArray(new EwList<String>((String[]) obj));
		if (obj instanceof JSONArray)
			return obj;
		if (obj instanceof List)
			return new JSONArray((List) obj);
		if (obj instanceof String)
		{
			try
			{
				String asString = obj.toString();
				Object result = c.get(asString);
				if (result == null)
					if (asString.startsWith("[") || !optAsBoolean("wrapString", true, c, parameters, dataStreams))
						return new JSONArray(asString);
					else
					{
						result = new JSONArray();
						((JSONArray) result).put(obj);
					}
				if (!(result instanceof JSONArray))
				{
					if (result.toString().startsWith("[") || !optAsBoolean("wrapString", true, c, parameters, dataStreams))
						return new JSONArray(result.toString());
					result = new JSONArray();
					((JSONArray) result).put(obj);
				}
				return result;
			} catch (Exception ex)
			{
				if (!optAsBoolean("wrapString", true, c, parameters, dataStreams))
					throw ex;
				JSONArray ary = new JSONArray();
				ary.put(obj);
				return ary;
			}
		}
		if (optAsBoolean("wrap", true, c, parameters, dataStreams))
		{
			JSONArray ja = new JSONArray();
			ja.put(obj);
			return ja;
		}
		return null;
	}

	@Override
	public String getDescription()
	{
		return "Converts many things into an array. If wrap = true, will wrap obj.";
	}

	@Override
	public String getReturn()
	{
		return "JSONArray";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("obj", "String|Object|List|JSONArray|Number|String[]|JSONObject", "?wrap", "Boolean");
	}

}
