package com.eduworks.cruncher.net;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.lang.threading.EwThreading;
import com.eduworks.lang.util.EwJson;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.resolver.exception.SoftException;
import com.eduworks.util.io.InMemoryFile;

public class CruncherHttpPut extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		final Object o = getObj(c, parameters, dataStreams);
		String url = getAsString("url", c, parameters, dataStreams);
		String contentType = getAsString("contentType", c, parameters, dataStreams);
		boolean reliable = optAsBoolean("reliable", false, c, parameters, dataStreams);
		String authToken = getAsString("authToken", c, parameters, dataStreams);
		HttpPut put = new HttpPut(url);

		HttpEntity entity = null;
			try
			{
				if (o instanceof File)
					entity = new FileEntity((File) o);
				else if (o instanceof InMemoryFile)
					entity = new InputStreamEntity(((InMemoryFile) o).getInputStream(), ((InMemoryFile) o).data.length, ContentType.create(contentType));
				else
				{
					byte[] bytes = o.toString().getBytes("UTF-8");
					entity = new InputStreamEntity(new ByteArrayInputStream(bytes), bytes.length, ContentType.create(contentType));
				}
				put.setHeader("Content-Type", contentType);
			}
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}

		if (authToken != null && !authToken.trim().isEmpty())
		{
			put.setHeader("Authorization", "Basic " + authToken);
		}
		for (String key : keySet())
		{
			if (key.equals("url"))
				continue;
			if (key.equals("obj"))
				continue;
			if (key.equals("authToken"))
				continue;
			if (key.equals("multipart"))
				continue;
			if (key.equals("name"))
				continue;
			if (key.equals("contentType"))
				continue;
			put.setHeader(key, getAsString(key, c, parameters, dataStreams));
		}

		put.setEntity(entity);

		CloseableHttpClient hc = HttpClients.createDefault();

		CloseableHttpResponse execute = null;
		try
		{
			do
				try
				{
					execute = hc.execute(put);
				}
				catch (ClientProtocolException e)
				{
					if (reliable)
						EwThreading.sleep(500);
					else
						e.printStackTrace();
				}
				catch (SocketException e)
				{
					if (reliable)
						EwThreading.sleep(500);
					else
						throw new SoftException(e.getMessage());
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
			String string = null;
			try
			{
				string = EntityUtils.toString(execute.getEntity());
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			if (string == null)
				return null;

			if (EwJson.isJson(string))
				return EwJson.tryParseJson(string, false);
			return string;
		}
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
		return "Performs an HTTP Put. The payload is provided by obj.\n"
				+ "Will attach one file as a payload\n"
				+ "Results will come back as JSON or a string.";
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
		return jo("obj", "String", "contentType", "String", "?name", "String", "?authToken", "String");
	}

}
