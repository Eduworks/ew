package com.eduworks.resolver.math;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherDivide extends Cruncher
{
	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		boolean ignoreZeros = optAsBoolean("_ignoreZeros", false, c, parameters, dataStreams);
		Double result = getAsDouble("numerator", c, parameters, dataStreams);
		if (result == null)
			result = getAsDouble("obj", c, parameters, dataStreams);
		Iterator<String> i = keySet().iterator();
		while (i.hasNext())
		{
			String key = i.next();
			if (isSetting(key))
				continue;
			if (key.equals("numerator"))
				continue;
			if (key.equals("obj"))
				continue;
			Double value = getAsDouble(key, c, parameters, dataStreams);
			if (value == 0 && ignoreZeros)
				continue;
			result /= value;
			if ((Double.isNaN(result) || Double.isInfinite(result)))
				if (!has("NaNresult"))
					return "NaN";
				else
					result = getAsDouble("NaNresult", c, parameters, dataStreams);
		}
		return result;
	}

	@Override
	public String getDescription()
	{
		return "Divides the number in 'numerator' or 'obj' by all other numbers provided." + "\nIf '_NaNresult' is provided, will use that result if NaN is achieved.";
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
		return jo("numerator", "Number", "<any>", "Number", "?_NaNresult", "Number");
	}
}
