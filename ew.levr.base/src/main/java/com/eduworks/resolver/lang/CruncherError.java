package com.eduworks.resolver.lang;

import java.io.InputStream;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.levr.servlet.HttpErrorException;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherError extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String,String[]> parameters, Map<String,InputStream> dataStreams) throws JSONException
	{
		short httpStatus = (short) optAsInteger("code", 500, c, parameters, dataStreams);
		Object object = get("msg", c,parameters,dataStreams).toString();
		String message = "";
		if (object != null)
			message = object.toString();

		HttpErrorException ex = new HttpErrorException(message);
		ex.httpStatus = httpStatus;
		throw ex;
	}

	@Override
	public String getDescription()
	{
		return "Throws an error";
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
		return jo("msg","String","code","Number");
	}
}
