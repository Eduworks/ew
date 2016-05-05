package com.eduworks.resolver.io;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.lang.EwList;
import com.eduworks.lang.util.EwJson;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.util.io.InMemoryFile;

public class CruncherDisplayCsv extends Cruncher
{
	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		StringBuilder accmData = new StringBuilder();

		Object o = get("obj", c,parameters,dataStreams);
		if (o instanceof JSONArray)
		{
			JSONArray a = (JSONArray) o;
			List<String> keys = EwJson.getKeysUnsorted((JSONObject) a.get(0));
			if (optAsString("fields", null,c,parameters,dataStreams) != null)
				keys = new EwList<String>(optAsString("fields", null,c,parameters,dataStreams).split(","));
			for (int i = 0; i < a.length(); i++)
			{
				JSONObject jo = (JSONObject) a.get(i);
				for (String s : keys)
				{
					if (!s.equals(keys.get(0)))
						accmData.append(",");
					if (jo.has(s))
						accmData.append(encodeCsv(jo.opt(s).toString()));
				}
				accmData.append("\n");
			}
		}
		if (o instanceof JSONObject)
		{
			JSONObject a = (JSONObject) o;
			if (optAsBoolean("valueList", false, c,parameters,dataStreams))
				listCentricAccumulate(parameters, accmData, a);
			else if (optAsBoolean("array2d", false, c,parameters,dataStreams))
				list2dCentricAccumulate(c,parameters,dataStreams, accmData, a);
			else if (optAsBoolean("simple",false,c,parameters,dataStreams))
				simpleCentricAccumulate(c,parameters,dataStreams, accmData, a);
			else
				objectCentricAccumulate(c,parameters,dataStreams, accmData, a);
		}

		InMemoryFile f = new InMemoryFile();
		f.data = accmData.toString().getBytes();
		f.mime = "text/csv";
		f.name = getAsString("name", c,parameters,dataStreams);
		return f;
	}

	private void listCentricAccumulate(Map<String, String[]> parameters, StringBuilder accmData, JSONObject a)
			throws JSONException
	{
		List<String> indexKeys = EwJson.getKeys(a);
		for (String key : indexKeys)
		{
			JSONArray ja = null;
			ja = a.getJSONArray(key);
			accmData.append(encodeCsv(key));
			for (int i = 0; i < ja.length(); i++)
			{
				if (ja.isNull(i))
					continue;
				accmData.append(",");
				if (ja.get(i) instanceof Number)
					accmData.append(ja.get(i));
				else
					accmData.append(encodeCsv(ja.get(i).toString()));
			}
			accmData.append("\n");
		}
	}

	private void list2dCentricAccumulate(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams, StringBuilder accmData, JSONObject a)
			throws JSONException
	{
		// output first row (first order keys)

		List<String> indexKeys = EwJson.getKeysUnsorted(a);
		for (String key1 : indexKeys)
		{
			accmData.append(",");
			accmData.append(encodeCsv(key1.toString()));
		}
		List<String> rowKeys = new ArrayList<String>();
		for (String key : indexKeys)
		{
			for (String rowName : EwJson.getKeysUnsorted(a.getJSONObject(key)))
				if (!rowKeys.contains(rowName))
					rowKeys.add(rowName);
		}
		for (String row : rowKeys)
		{
			accmData.append("\n");
			accmData.append(encodeCsv(row));
			for (String col : indexKeys)
			{
				accmData.append(",");
				if (a.getJSONObject(col).has(row))
				{
					Object object = a.getJSONObject(col).get(row);
					if (object instanceof Number)
						accmData.append(object);
					else
						accmData.append(encodeCsv(object.toString()));
				}
				else
				{
					accmData.append(optAsString("default", null,c,parameters,dataStreams));
				}
			}
		}

	}

	public void objectCentricAccumulate(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams, StringBuilder accmData, JSONObject a)
			throws JSONException
	{
		List<String> indexKeys = EwJson.getKeysUnsorted(a);
		List<String> keys = EwJson.getKeysUnsorted((JSONObject) a.get(indexKeys.get(0)));
		if (optAsString("fields", null,c,parameters,dataStreams) != null)
			keys = new EwList<String>(optAsString("fields", null,c,parameters,dataStreams).split(","));
		if (optAsString("outputFields",null,c,parameters,dataStreams).equals("true"))
		{
			for (String s : keys)
			{
				if (!s.equals(keys.get(0)))
					accmData.append(",");
				accmData.append(encodeCsv(s));
			}
			accmData.append("\n");
		}
		for (String key : indexKeys)
		{
			JSONObject jo = (JSONObject) a.get(key);
			for (String s : keys)
			{
				if (!s.equals(keys.get(0)))
					accmData.append(",");
				if (jo.has(s))
					accmData.append(encodeCsv(jo.opt(s).toString()));
			}
			accmData.append("\n");
		}
	}
	public void simpleCentricAccumulate(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams, StringBuilder accmData, JSONObject a)
			throws JSONException
	{
		List<String> indexKeys = EwJson.getKeysUnsorted(a);
		for (String key : indexKeys)
		{
			String jo = a.get(key).toString();
			accmData.append(encodeCsv(key)+",");
			accmData.append(encodeCsv(jo));
			accmData.append("\n");
		}
	}

	public static String encodeCsv(String string)
	{
		return "\"" + string.replace("\"", "\"\"") + "\"";
	}

	@Override
	public String getDescription()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getReturn()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAttribution()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		// TODO Auto-generated method stub
		return null;
	}
}
