package com.eduworks.resolver.db.couchdb;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.ace.product.levr.adapter.DocumentDbInterface;
import com.eduworks.lang.EwList;
import com.eduworks.lang.json.impl.EwJsonArray;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.util.Tuple;

public class CruncherGetKeysInTable extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		Logger.getLogger(getClass()).info("This class has recently been converted to a Cruncher by Fritz.");
		int count = optAsInteger("count", Integer.MAX_VALUE, c,parameters,dataStreams);
		String start = optAsString("start", null,c,parameters,dataStreams);
		String end = optAsString("end", null,c,parameters,dataStreams);
		if (start == null)
			start = "";
		if (end == null || end.isEmpty())
			end = "zzzzzzzzzzzzzzzzzzzzz";
		Tuple<Integer, List<String>> allDocumentIds;
		try
		{
			allDocumentIds = DocumentDbInterface.getAllDocumentIds(this, URLEncoder.encode(start,"UTF-8"), URLEncoder.encode(end,"UTF-8"),
					Math.min(count, 10000), c,parameters,dataStreams);
		}
		catch (UnsupportedEncodingException e)
		{
			allDocumentIds = null;
			e.printStackTrace();
		}
		int max = allDocumentIds.getSecond().size();
		EwList<String> results = new EwList<String>();
		int i = 0;
		while (i < count && i < max)
		{
			List<String> ids = allDocumentIds.getSecond();
			results.addAll(ids);
			String lastId = ids.get(ids.size() - 1);
			start += ids.size();
			i += ids.size();
			allDocumentIds = DocumentDbInterface.getAllDocumentIds(this, lastId, end, Math.min(count - i, 10000),
					c,parameters,dataStreams);
		}
		EwList.sort(results);
		return new EwJsonArray(results);
	}

	@Override
	public String getDescription()
	{
		return "Retreives the keys of documents from a NoSQL CouchDb table.\n" +
				"The endpoint is defined by _serverHostname, _serverPort, _serverLogin, _serverPassword, _databasePrefix and _databaseName\n"
				;
	}
	@Override
	public String getReturn()
	{
		return "JSONArray";
	}
	@Override
	public String getAttribution()
	{
		return ATTRIB_UCASTER;
	}
	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("_serverHostname","String","_serverPort","Number","_serverLogin","String","_serverPassword","String","?_databasePrefix","String","_databaseName","String");
	}

}
