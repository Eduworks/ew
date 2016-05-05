package com.eduworks.resolver.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONML;
import org.json.JSONObject;
import org.json.XML;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.resolver.net.CruncherGetFileFromUrl;
import com.eduworks.util.io.EwFileSystem;

public class CruncherGetXmlFromUrlAsJson extends Cruncher
{
	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		int timeout = optAsInteger("timeout", 30000, c, parameters, dataStreams);
		boolean optAsBoolean2 = optAsBoolean("array", false, c, parameters, dataStreams);
		String url = getAsString("url", c, parameters, dataStreams);
		JSONObject result = new JSONObject();
		try
		{
			url = url.trim();
			if (url.isEmpty())
				return null;
			Object text;
			if (optAsBoolean2)
				text = operateArray(c, url, timeout);
			else
				text = operate(c, url, timeout);
			result.put(url, text);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	public static Object operate(Context c, String url, int timeout)
	{
		FileReader fileReader = null;
		try
		{
			File f = CruncherGetFileFromUrl.operate(c, url, timeout);

			fileReader = new FileReader(f);
			String string = IOUtils.toString(fileReader);
			JSONObject result = XML.toJSONObject(string);
			return result;
		}
		catch (JSONException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally
		{
			com.eduworks.util.io.EwFileSystem.closeIt(fileReader);
		}
	}

	public static JSONArray operateArray(Context c, String url, int timeout)
	{
		FileReader fileReader = null;
		try
		{
			File f = CruncherGetFileFromUrl.operate(c, url, timeout);
			fileReader = new FileReader(f);
			return JSONML.toJSONArray(IOUtils.toString(fileReader));
		}
		catch (JSONException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally
		{
			EwFileSystem.closeIt(fileReader);
		}
	}

	@Override
	public String getDescription()
	{
		return "Retreives an XML document from the web and decodes it to JSON. Deprecated.";
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
		return jo("url", "String");
	}

}
