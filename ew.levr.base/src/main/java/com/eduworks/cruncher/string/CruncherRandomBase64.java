package com.eduworks.cruncher.string;

import java.io.InputStream;
import java.util.Map;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherRandomBase64 extends Cruncher
{
	Random r = new Random();

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		int length = getAsInteger("length", c, parameters, dataStreams);
		byte[] bytes = new byte[length];
		r.nextBytes(bytes);
		return Base64.encodeBase64String(bytes);
	}

	@Override
	public String getDescription()
	{
		return "Creates a random base64 encoded number of bytes.";
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
		return jo("length", "Number");
	}

}
