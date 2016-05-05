package com.eduworks.resolver.manip;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.lang.util.EwJson;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherFlatten extends Cruncher
{
	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		StringBuilder	sb;
		String	delimiter;
		Boolean	trim;
		boolean	trimLeadingPunctuation;

		delimiter = optAsString("_delimiter", "",c, parameters, dataStreams);
		trim = optAsBoolean("_trim", false, c, parameters, dataStreams);
		trimLeadingPunctuation = optAsBoolean("_trimLeadingPunctuation", false, c, parameters, dataStreams);
		sb = new StringBuilder();
		Object o = get("obj", c, parameters, dataStreams);
		flatten(o,sb,delimiter,trim);
		String tostr = sb.toString();
		if (trimLeadingPunctuation)
			while (tostr.startsWith(",") || tostr.startsWith(".") || tostr.startsWith(";") || tostr.startsWith(":")
					|| tostr.startsWith("?") || tostr.startsWith("!"))
				tostr = tostr.substring(1);
		return tostr;
	}

	private void flatten(Object o, StringBuilder sb, String delimiter, Boolean trim) throws JSONException
	{
		if (o == null)
			return;
		if (o instanceof JSONArray)
		{
			JSONArray jsonArray = (JSONArray) o;
			for (int i = 0; i < jsonArray.length(); i++)
				try
				{
					flatten(jsonArray.get(i),sb,delimiter,trim);
				}
				catch (JSONException ex)
				{
				}
		}
		else if (o instanceof JSONObject)
		{
			JSONObject jsonObject = (JSONObject) o;
			List<String> keys = EwJson.getKeysUnsorted(jsonObject);
			for (int i = 0; i < keys.size(); i++)
				flatten(jsonObject.get(keys.get(i)),sb,delimiter,trim);
		}
		else
		{
			if (sb.length() > 0)
				if (delimiter != null)
					sb.append(delimiter);
			String tostr = o.toString();
			if (trim)
				tostr = tostr.trim();
			sb.append(tostr);
		}
	}

	@Override
	public String getDescription()
	{
		return "Collapse any objects or arrays and append values together." +
				"\n(Optional) delimiter - The delimiter to separate values by." +
				"\n(Optional) trim - Whether to trim strings of extreneous whitespace before appending." +
				"\n(Optional) trimLeadingPunctuation - Remove any leading punctuation.";
	}

	@Override
	public String getReturn()
	{
		return "String";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("obj","JSONArray|JSONObject|String","?_delimiter","Boolean","?_trim","Boolean","?_trimLeadingPunctuation","Boolean");
	}

}
