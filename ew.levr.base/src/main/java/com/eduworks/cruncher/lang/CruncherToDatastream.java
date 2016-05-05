package com.eduworks.cruncher.lang;

import java.io.InputStream;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.lang.EwMap;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.util.io.InMemoryFile;

public class CruncherToDatastream extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		String paramName=getAsString("paramName",c,parameters, dataStreams);
		Object obj = getObj(c,parameters, dataStreams);
		InMemoryFile o = (InMemoryFile) obj;

		final EwMap<String, InputStream> newData = new EwMap<String, InputStream>(dataStreams);
        newData.put(paramName, o.getInputStream());
        Object result = resolveAChild("op",c, parameters, newData);
        return result;
	}
    
	@Override
	public String getDescription()
	{
		return "Converts obj into a named datastream and runs op.";
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
		return jo("obj","Object","paramName","String","op","Resolvable");
	}
}
