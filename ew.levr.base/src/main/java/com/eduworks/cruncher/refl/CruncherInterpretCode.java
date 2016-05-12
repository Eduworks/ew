package com.eduworks.cruncher.refl;

import java.io.InputStream;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.lang.EwMap;
import com.eduworks.lang.util.EwJson;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.resolver.Resolvable;
import com.eduworks.resolver.ResolverFactory;

public class CruncherInterpretCode extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		final EwMap<String, String[]> newParams = new EwMap<String, String[]>(parameters);
		JSONObject jo = getAsJsonObject("params", c, parameters, dataStreams);
		if (jo != null)
			for (String s : EwJson.getKeys(jo))
				newParams.put(s, new String[]{jo.get(s).toString()});
		String codeAsString = getObjAsString(c, parameters, dataStreams);
		Resolvable r = (Resolvable) ResolverFactory.create(new JSONObject(codeAsString));
		return r.resolve(c, newParams, dataStreams);
	}

	@Override
	public String getDescription()
	{
		return "Interprets and executes code in obj with parameters defined by params.";
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
		return jo("obj","JSONObject","params","JSONObject");
	}

}
