package com.eduworks.cruncher.lang;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.lang.EwList;
import com.eduworks.lang.util.EwJson;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherFromBracketNotationObject extends Cruncher
{

	public static void main(String[] args){
		
	}
	
	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		JSONObject jo = getObjAsJsonObject(c, parameters, dataStreams);
		JSONObject result = new JSONObject();
		if (jo == null) return null;
		for (String key : EwJson.getKeys(jo))
		{
			parseInto(result,key,jo.get(key));
		}
		return result;
	}

	private void parseInto(JSONObject result, String key, Object object) throws JSONException
	{
		List<String> parts = new EwList<String>(key.split("(?<=[\\[\\]\\.])|(?=[\\[\\]\\.])"));
		Object o = null;
		JSONObject jo = result;
		JSONArray ja = null;
		
		String parent = null;
		String finalKey = null;
		
		boolean inBrackets = false;
		
		while (parts.size() > 0)
		{
			String s = parts.get(0);
			
			if (s.contains("["))
			{
				inBrackets = true;
				
				if (o instanceof JSONObject)
					jo = (JSONObject) o;
				else if (jo != null)
				{
					if(jo.opt(parent) instanceof JSONObject){
						jo = jo.getJSONObject(parent);
					}else{
						jo.put(parent,jo = new JSONObject());
					}
				}

				o = null;
			}
			else if (s.contains("]"))
			{
				inBrackets = false;
			}
			else
			{
				if(inBrackets)
				{
					finalKey = parent = s;
				}else{
					parent = s;
				}
			}
			
			parts.remove(0);
			
			if(parts.size() == 0 && finalKey == null)
				finalKey = s;
		}
		
		if (jo != null)
			jo.put(finalKey,object);
		//if (ja != null)
			//ja.put(finalKey,object);
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
