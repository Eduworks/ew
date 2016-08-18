package com.eduworks.cruncher.io;

import java.io.InputStream;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherSetHeader extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		String value = getObjAsString(c, parameters, dataStreams);
		String name = getAsString("name", c, parameters, dataStreams);
		c.response.setHeader(name, value);
		return null;
	}

	@Override
	public String getDescription()
	{
		return "Set a response header.";
	}

	@Override
	public String getReturn()
	{
		return null;
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("obj","String","name","String");
	}

}
