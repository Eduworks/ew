package com.eduworks.cruncher.string;

import java.io.InputStream;
import java.util.Map;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class CruncherHexToString extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		String obj = getObjAsString(c, parameters, dataStreams);
		if (obj == null) return null;
		
		try
		{
			return new String(Hex.decodeHex(obj.toCharArray()));
		}
		catch (DecoderException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getDescription()
	{
		return "Converts Hex String back to normal string.";
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
