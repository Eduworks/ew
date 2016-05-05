package com.eduworks.cruncher.manip;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.lang.EwList;
import com.eduworks.lang.util.EwJson;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherUnion extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		Collection<Object> ja;
		boolean unique = optAsBoolean("unique",true,c,parameters,dataStreams);
		if (unique)
			ja = new HashSet<Object>();
		else
			ja = new ArrayList<Object>();
		JSONArray obj = getObjAsJsonArray(c, parameters, dataStreams);
		
		for (int i = 0;i < obj.length();i++)
			ja.add(obj.get(i));
		
			
		Boolean accumulate = optAsBoolean("accumulate", true, c, parameters, dataStreams);
		JSONObject jo = new JSONObject();
		int keys = 0;
		for (String key : keySet())
		{
			if (key.equals("accumulate")) continue;
			if (key.equals("unique")) continue;
			keys++;
		}
		if (keys == 1)
		{
			Collection<Object> results;
			if (unique)
				results = new HashSet<Object>();
			else
				results = new ArrayList<Object>();
			for (Object o : ja)
			{
				if (o instanceof JSONArray)
					results.addAll(new EwList<Object>(o));
				else if (o instanceof JSONObject)
					for (String key : EwJson.getKeys((JSONObject)o))
						if (accumulate)
						jo.accumulate(key, ((JSONObject)o).get(key));
						else
							jo.put(key, ((JSONObject)o).get(key));
				else
					results.add(o);
			}
			ja = results;
		}
		else
		{
			for (String key : keySet())
			{
				if (key.equals("obj"))
					continue;
				EwList<Object> organizations = new EwList<Object>(getAsJsonArray(key, c, parameters, dataStreams));
					ja.addAll(organizations);
			}
		}
		if (jo.length() != 0)
			return jo;
		//if (ja.size() == 0)  removed these two lines because it was returning null on the union of two empty arrays
			//return null;     this caused problems when combining lists if the first two lists were empty
		return new JSONArray(ja);
	}

	@Override
	public String getDescription()
	{
		return "Creates the discrete union of all objects (or arrays), or arrays in the obj.";
	}

	@Override
	public String getReturn()
	{
		return "JSONObject|JSONArray";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("<any>","JSONObject|JSONArray","?obj","JSONObject|JSONArray");
	}

}
