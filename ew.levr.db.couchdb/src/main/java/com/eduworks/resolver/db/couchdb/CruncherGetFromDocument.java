package com.eduworks.resolver.db.couchdb;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.ace.product.levr.adapter.DocumentDbInterface;
import com.eduworks.lang.json.impl.EwJsonObject;
import com.eduworks.lang.util.EwJson;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.exception.SoftException;
import com.fourspaces.couchdb.Document;

public class CruncherGetFromDocument extends CruncherDocument
{
	boolean toldem = false;

	public String encodeValue2(String value)
	{
		try
		{
			return URLEncoder.encode(value, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		if (!toldem)
			Logger.getLogger(getClass()).info("This class has recently been converted to a Cruncher by Fritz.");
		toldem = true;

		JSONObject result = new JSONObject();
		final boolean doNotShorten = optAsBoolean("_doNotShorten", false, c, parameters, dataStreams);
		final boolean nullify = optAsBoolean("_canNull", false, c, parameters, dataStreams);
		final boolean soft = optAsBoolean("_soft", false, c, parameters, dataStreams);
		final String id = getAsString("id", c, parameters, dataStreams);

		Document d = null;
		try
		{
			d = DocumentDbInterface.getDocument(this, id, c, parameters, dataStreams);
			if (d == null && decodeValue(id).equals(id) == false)
				d = DocumentDbInterface.getDocument(this, decodeValue(id), c, parameters, dataStreams);
			if (d == null && encodeValue(id).equals(id) == false)
				d = DocumentDbInterface.getDocument(this, encodeValue(id), c, parameters, dataStreams);
			if (d == null && encodeValue2(id).equals(id) == false)
				d = DocumentDbInterface.getDocument(this, encodeValue2(id), c, parameters, dataStreams);
		}
		catch (IOException e)
		{
		}
		catch (IllegalArgumentException e)
		{
			throw new SoftException(e.getMessage());
		}

		if (d == null)
		{
			if (soft)
				return null;
			throw new RuntimeException("Document not found: " + id);
		}

		security(d, c, parameters, dataStreams);

		for (String key : keySet())
		{
			if (isSetting(key)) continue;
			final EwJsonObject jsonObject = (EwJsonObject) d.getJSONObject();
			final String existing = getAsString(key, c, parameters, dataStreams);

			if (key.equals("id"))
				result.put(key, getId(jsonObject));
			else if (jsonObject.hasComplex(key))
			{
				Object complex = jsonObject.get(key);
				if (complex instanceof String)
					complex = decodeValue((String) complex);
				if (existing.isEmpty())
					result.put(key, complex);
				else if (existing.equals(complex))
					result.put(key, complex);
				else
					return null;
			}
			else if (!existing.isEmpty())
				return null;
		}

		if (!doNotShorten && result.length() == 1)
		{
			Object object = EwJson.tryParseJson(result.get(result.names().getString(0)), false);
			;

			if (object instanceof JSONArray)
				object = EwJson.reduce((JSONArray) object);

			return object;
		}

		return (nullify && result.length() == 0) ? null : result;
	}

	@Override
	public String getDescription()
	{
		return "Retreives a document from a NoSQL CouchDb table.\n"
				+ "The endpoint is defined by _serverHostname, _serverPort, _serverLogin, _serverPassword, _databasePrefix and _databaseName\n"
				+ "The NoSQL Document is defined by _id\n" + "The retreived variables from the NoSQL Document are defined by any other parameters.";
	}

	@Override
	public String getReturn()
	{
		return "JSONObject";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_UCASTER;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("_serverHostname", "String", "_serverPort", "Number", "_serverLogin", "String", "_serverPassword", "String", "?_databasePrefix", "String",
				"_databaseName", "String", "_id", "String", "<any>", "Empty");
	}

}
