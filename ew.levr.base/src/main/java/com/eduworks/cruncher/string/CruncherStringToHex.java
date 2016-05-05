package com.eduworks.cruncher.string;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherStringToHex extends Cruncher{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		String obj = getObjAsString(c, parameters, dataStreams);
		if (obj == null) return null;
	    return Hex.encodeHexString(obj.getBytes());
	}

	@Override
	public String getDescription()
	{
		return "Converts a string to a hexadecimal encoding of a string.";
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
		return jo("obj","String");
	}

}