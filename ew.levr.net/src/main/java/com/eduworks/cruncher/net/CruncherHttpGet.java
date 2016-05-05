package com.eduworks.cruncher.net;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.mail.internet.ContentDisposition;
import javax.mail.internet.ParseException;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.lang.threading.EwThreading;
import com.eduworks.lang.util.EwJson;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.util.io.InMemoryFile;

public class CruncherHttpGet extends Cruncher
{
	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		final String url = getObjAsString(c, parameters, dataStreams);
		HttpGet get = new HttpGet(url);
		boolean reliable = optAsBoolean("reliable", false, c, parameters, dataStreams);
				
		CloseableHttpClient hc = HttpClients.createDefault();

		for (String key : keySet())
		{
			if (key.equals("url"))
				continue;
			if (key.equals("obj"))
				continue;
			if (key.equals("reliable"))
				continue;
			if (key.equals("file"))
				continue;
			get.setHeader(key, getAsString(key, c, parameters, dataStreams));
		}
		
		CloseableHttpResponse execute = null;
		try
		{
			do
				try
				{
					execute = hc.execute(get);
				}
				catch (ClientProtocolException e)
				{
					if (reliable)
						EwThreading.sleep(500);
					else
						e.printStackTrace();
				}
				catch (IOException e)
				{
					if (reliable)
						EwThreading.sleep(500);
					else
						e.printStackTrace();
				}
			while (execute == null && reliable);
		
		if (execute == null)
			return null;
		try
		{
			if (optAsBoolean("file", false, c, parameters, dataStreams))
			{
				InMemoryFile imf = new InMemoryFile();
				imf.data = EntityUtils.toByteArray(execute.getEntity());
				imf.mime = EntityUtils.getContentMimeType(execute.getEntity());
				Header header = execute.getFirstHeader("Content-Disposition");
				if (header != null)
					try
					{
						imf.name = new ContentDisposition(header.getValue()).getParameter("filename");
					}
					catch (ParseException e)
					{
						e.printStackTrace();
					}
				return imf;
			}
			else
			{
				String string = EntityUtils.toString(execute.getEntity());
				if (EwJson.isJson(string))
					return EwJson.tryParseJson(string, false);
				return string;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}}
		finally
		{
			try
			{
				if (execute != null)
					execute.close();
				if (hc != null)
					hc.close();
			}
			catch (IOException e)
			{
			}
		}
	}

	@Override
	public String getDescription()
	{
		return "Fetches a web page using an HTTP Get. URL is to be placed in 'obj'. Will auto convert result to JSON if possible.";
	}

	@Override
	public String getReturn()
	{
		return "JSONObject|JSONArray|String";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("obj", "String");
	}

}
