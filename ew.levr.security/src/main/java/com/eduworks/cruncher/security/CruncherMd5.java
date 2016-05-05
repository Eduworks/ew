package com.eduworks.cruncher.security;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.util.io.InMemoryFile;

public class CruncherMd5 extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		try
		{
				Object obj = getObj(c, parameters, dataStreams);
				if (obj instanceof InMemoryFile)
					return MessageDigest.getInstance("MD5").digest(((InMemoryFile) obj).data);
				return MessageDigest.getInstance("MD5").digest(obj.toString().getBytes());
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getDescription()
	{
		return "Returns MD5 Hash of String.";
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
