package com.eduworks.cruncher.lang;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherToBracketNotationObject extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		JSONObject obj = getObjAsJsonObject(c, parameters, dataStreams);
		String stopAtx = optAsString("stopAt", null, c, parameters, dataStreams);
		String[] stopAt = stopAtx == null ? null : stopAtx.split(",");
		String removex = optAsString("remove", null, c, parameters, dataStreams);
		String[] remove = removex == null ? null : removex.split(",");
		JSONObject result = new JSONObject();
		recurse("", result, obj, stopAt, remove);
		return result;
	}

	private void recurse(String prefix, JSONObject result, JSONObject obj, String[] stopAt, String[] remove) throws JSONException
	{
		Iterator it = obj.keys();
		while (it.hasNext())
		{
			String key = it.next().toString();
			String expandedKey = prefix;
			if (!expandedKey.endsWith("]"+key))
			{
				if (prefix.isEmpty() == false)
				{
					expandedKey += "[";
					expandedKey += key;
					expandedKey += "]";
				}
				else
				{
					expandedKey += key;
				}
			}
			Object o = obj.get(key);

			put(result, expandedKey, o, stopAt, remove);
		}
	}

	private void put(JSONObject result, String expandedKey, Object o, String[] stopAt, String[] remove) throws JSONException
	{
		if (o instanceof JSONObject)
			recurse(expandedKey, result, (JSONObject) o, stopAt, remove);
		else if (o instanceof JSONArray)
		{
			result.put(expandedKey, o);
		}
		else
		{
			if (stopAt != null)
			{
				String shortest = null;
				for (String stop : stopAt)
				{
					int indexOf = expandedKey.indexOf(stop);
					if (indexOf != -1)
					{
						String substring = expandedKey.substring(0, indexOf);
						if (shortest == null || substring.length() < shortest.length())
							shortest = substring;
					}
					else if (shortest == null || expandedKey.length() < shortest.length())
						shortest = expandedKey;
				}
				if (remove != null)
					for (String s : remove)
						shortest = shortest.replace(s, "");
				result.accumulate(shortest, o);
			}
			else
			{
				String myDot2 = expandedKey;
				if (remove != null)
					for (String s : remove)
						myDot2 = myDot2.replace(s, "");
				result.accumulate(myDot2, o);
			}
		}
	}

	@Override
	public String getDescription()
	{
		return null;
	}

	@Override
	public String getReturn()
	{
		return null;
	}

	@Override
	public String getAttribution()
	{
		return null;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return null;
	}

}
