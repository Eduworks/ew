package com.eduworks.cruncher.trig;

import java.io.InputStream;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherToRadians extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		String sdeg = optAsString("deg","",c,parameters, dataStreams);
		
		if(sdeg.isEmpty()){
			return Math.toRadians(Double.parseDouble(sdeg));
		}else{
			double obj = getAsDouble("obj", c, parameters, dataStreams);
			return Math.toRadians(obj);
		}
	}

	@Override
	public String getDescription()
	{
		return "Takes a degree angle and returns in radians";
	}

	@Override
	public String getReturn()
	{
		return "Double";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("rad", "Number", "obj","Number");
	}

}
