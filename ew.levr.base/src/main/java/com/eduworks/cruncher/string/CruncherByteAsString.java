package com.eduworks.cruncher.string;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherByteAsString extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		byte b = Byte.parseByte(getObjAsString(c, parameters, dataStreams),16);
		return new String(new byte[]{b},Charset.forName("ASCII"));
	}

	@Override
	public String getDescription()
	{
		return "Returns the byte as a string.";
	}

	@Override
	public String getReturn()
	{
		return "String";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("obj","String");
	}

}
