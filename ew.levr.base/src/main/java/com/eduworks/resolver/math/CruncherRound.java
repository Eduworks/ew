package com.eduworks.resolver.math;

import java.io.InputStream;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherRound extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		int places = optAsInteger("_places", 0, c, parameters, dataStreams);
		JSONObject results = new JSONObject();
		for (String key : keySet())
		{
			if (isSetting(key))
				continue;
			results.put(key, Math.pow(10, -places) * Math.round(Math.pow(10, places) * getAsDouble(key, c, parameters, dataStreams)));
		}
		if (results.length() == 0)
			return null;
		if (results.length() == 1){
			return results.get(results.keys().next().toString());
			//return get(keySet().iterator().next());
		}
		return results;
	}

	@Override
	public String getDescription()
	{
		return "Rounds all provided numbers. Will return all numbers in a JSON Object if provided, one number if only one is provided."
				+ "\nRounds to _places (default: 0 places, or whole numbers).";
	}

	@Override
	public String getReturn()
	{
		return "Number";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("<any>", "Number", "?_places", "Number");
	}

}
