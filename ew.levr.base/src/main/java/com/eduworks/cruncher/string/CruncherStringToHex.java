package com.eduworks.cruncher.string;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Map;

public class CruncherStringToHex extends Cruncher{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		Object obj = getObj(c, parameters, dataStreams);
		if (obj == null) return null;
		if (obj instanceof String)
			return Hex.encodeHexString(((String)obj).getBytes());
		if (obj instanceof byte[])
			return Hex.encodeHexString((byte[])obj);
		return Hex.encodeHexString(obj.toString().getBytes());
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