package com.eduworks.cruncher.trig;

import java.io.InputStream;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherToDegrees extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		String srad = optAsString("rad","",c,parameters, dataStreams);
		
		if(srad.isEmpty()){
			return Math.toDegrees(Double.parseDouble(srad));
		}else{
			double obj = getAsDouble("obj", c, parameters, dataStreams);
			return Math.toDegrees(obj);
		}
	}

	@Override
	public String getDescription()
	{
		return "Takes a radian angle and returns in degrees";
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
		return jo("deg", "Number", "obj","Number");
	}

}
