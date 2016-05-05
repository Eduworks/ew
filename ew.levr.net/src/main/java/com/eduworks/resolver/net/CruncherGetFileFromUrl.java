package com.eduworks.resolver.net;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.util.io.EwFileSystem;

public class CruncherGetFileFromUrl extends Cruncher
{
	public static File operate(Context c, String url, int timeout) throws IOException
	{
		try
		{
			File f = EwFileSystem.downloadFile(url, timeout);
			return f;
		}
		finally
		{
		}
	}

	@Override
	public Object resolve(final Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		final int timeout = optAsInteger("timeout", 30000, c, parameters, dataStreams);
		String url = getAsString("url", c, parameters, dataStreams);
		File operate = null;
		try
		{
			operate = operate(c, url, timeout);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return operate;
	}

	@Override
	public String getDescription()
	{
		return "Retreives a file from a target url using an HTTP GET";
	}

	@Override
	public String getReturn()
	{
		return "File";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("url", "String", "?timeout", "Number");
	}
}
