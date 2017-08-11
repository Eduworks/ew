package com.eduworks.cruncher.refl;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;

public class CruncherHeaders extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		JSONObject result = new JSONObject();
		if (c.request == null) return null;
		Enumeration<String> headerNames = c.request.getHeaderNames();
		while (headerNames.hasMoreElements())
		{
			String s = headerNames.nextElement();
			result.put(s,c.request.getHeader(s));
		}
		return result;
	}

	@Override
	public String getDescription()
	{
		return "Returns HTTP headers in a JSON Object";
	}

	@Override
	public String getReturn()
	{
		return "JSONObject";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo();
	}

}
