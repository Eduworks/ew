package com.eduworks.resolver.math;

import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherMultiply extends Cruncher
{
	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		boolean fancyNumber = optAsBoolean("_fancyNumber",false,c,parameters,dataStreams);
		
		Double result = null;

		for (String key : keySet())
		{
			if (isSetting(key))
				continue;

			if (result == null)
				result = getAsDouble(key,c, parameters,dataStreams);
			else
				result *= getAsDouble(key,c, parameters,dataStreams);
		}
		if (fancyNumber)
			return NumberFormat.getNumberInstance(java.util.Locale.US).format(result);
		
		return result;
	}

	@Override
	public String getDescription()
	{
		return "Multiplies together all numbers provided.";
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
		return jo("<any>","Number");
	}
}
