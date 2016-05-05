package com.eduworks.cruncher.lang;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.lang.EwRandom;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherGetIndex extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		if (optAsString("soft", "false", c, parameters, dataStreams).equals("true") && !(getObj(c, parameters, dataStreams) instanceof JSONArray))
			return null;
		Object objx = getObj(c, parameters, dataStreams);
		Integer index = optAsInteger("index", 0,c, parameters, dataStreams);
		boolean random = false;
		Integer sampleSize=optAsInteger("samplesize",-1,c,parameters,dataStreams);
				
		if (optAsBoolean("random",false,c,parameters,dataStreams))
			random = true;
		if (objx instanceof List)
		{
			List obj = (List) objx;
			if (obj == null)
				return null;
			if (random){
				if (sampleSize >= 0){
					
					// If you try to get too many values, you get the full original list instead.
					if (sampleSize >= obj.size())
						return obj;
					TreeSet<Integer> selectedIndecesSoFar=new TreeSet<Integer>();
					JSONArray selectedSoFar = new JSONArray();
					for (int i = 0; i < sampleSize; i++){
						int selectedIndex=EwRandom.r(obj.size()-i);
						Iterator<Integer> iter = selectedIndecesSoFar.iterator();
						while (iter.hasNext() && iter.next() <= selectedIndex)
							selectedIndex++;
						selectedIndecesSoFar.add(selectedIndex);
						selectedSoFar.put(obj.get(selectedIndex));
						
					}
					return selectedSoFar;				
				
				}
				else{
					return obj.get(EwRandom.r(obj.size()));
				}
			}	
				
			if (obj.size() > index && index >= 0)
				return obj.get(index);
		}
		// if the object was a list and did not return, a cast exception would
		// be thrown...TB 10/30/2014
		else
		{
			JSONArray obj = (JSONArray) objx;
			if (obj == null)
				return null;
			if (random){
				if (sampleSize >= 0){
					
					// If you try to get too many values, you the original list instead.
					if (sampleSize >= obj.length())
						return obj;
					TreeSet<Integer> selectedIndecesSoFar=new TreeSet<Integer>();
					JSONArray selectedSoFar = new JSONArray();
					for (int i = 0; i < sampleSize; i++){
						int selectedIndex=EwRandom.r(obj.length()-i);
						Iterator<Integer> iter = selectedIndecesSoFar.iterator();
						while (iter.hasNext() && iter.next() <= selectedIndex)
							selectedIndex++;
						selectedIndecesSoFar.add(selectedIndex);
						selectedSoFar.put(obj.get(selectedIndex));
					}
					return selectedSoFar;				
				
				}
				else{
					return obj.get(EwRandom.r(obj.length()));
				}
			}
			if (obj.length() > index && index >= 0)
				return obj.get(index);
		}
		return null;
	}

	@Override
	public String getDescription()
	{
		return "Indexes into an array and retreives the item at the index designated by 'index'.\nSoft will make it not error out if obj is not a JSONArray.\nIf random and samplesize is not given, a random element from the array is returned. If random and samplesize = n, an array containing n distinct random item from the array is returned (or the original list if n is too large).";
	}

	@Override
	public String getReturn()
	{
		return "Object";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("obj", "JSONArray", "index", "Number", "?soft", "Boolean", "?random", "Boolean", "?samplesize","Number");
	}
}
