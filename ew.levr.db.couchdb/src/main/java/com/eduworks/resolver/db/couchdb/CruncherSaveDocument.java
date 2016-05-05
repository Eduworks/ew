package com.eduworks.resolver.db.couchdb;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.ace.product.levr.adapter.DocumentDbInterface;
import com.eduworks.interfaces.EwJsonSerializable;
import com.eduworks.lang.EwList;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.fourspaces.couchdb.Document;

public class CruncherSaveDocument extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		Logger.getLogger(getClass()).info("This class has recently been converted to a Cruncher by Fritz.");
		JSONObject result = new JSONObject();
		try
		{
			for (String key : keySet())
			{
				result.put(key, serializeTree(get(key, c,parameters,dataStreams)));
				result.put(key, getAsString(key, c,parameters,dataStreams));
			}
			Document d = new Document(result);
			DocumentDbInterface.stripDatabaseSettings(d);
			DocumentDbInterface.saveDocument(this, d, c,parameters,dataStreams);
		}
		catch (Exception e)
		{
			result.put("error", e.toString());
			e.printStackTrace();
		}
		return result;
	}

	public static Object serializeTree(Object o) throws JSONException
	{
		if (o instanceof EwList)
		{
			EwList ja = (EwList) o;
			for (int i = 0; i < ja.size(); i++)
				ja.set(i,serializeTree(ja.get(i)));
		}
		if (o instanceof JSONArray)
		{
			JSONArray ja = (JSONArray) o;
			for (int i = 0; i < ja.length(); i++)
				ja.put(i,serializeTree(ja.get(i)));
		}
		else if (o instanceof JSONObject)
		{
			JSONObject ja = (JSONObject) o;
			Iterator<String> keys = ja.keys();
			while (keys.hasNext())
			{
				String key = keys.next();
				ja.put(key, serializeTree(ja.get(key)));
			}
		}
		else if (o instanceof EwJsonSerializable)
		{
			return ((EwJsonSerializable) o).toJsonObject();
		}
		return o;
	}

	@Override
	public String getDescription()
	{
		return "Creates a document in a NoSQL CouchDb table.\n" +
				"The endpoint is defined by _serverHostname, _serverPort, _serverLogin, _serverPassword, _databasePrefix and _databaseName\n" +
				"The NoSQL Document is defined by _id"+
				"The set variables in the NoSQL Document are defined by any other parameters.";
	}

	@Override
	public String getReturn()
	{
		return "null";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_UCASTER;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("_serverHostname","String","_serverPort","Number","_serverLogin","String","_serverPassword","String","?_databasePrefix","String","_databaseName","String","_id","String","<any>","Object");
	}
}
