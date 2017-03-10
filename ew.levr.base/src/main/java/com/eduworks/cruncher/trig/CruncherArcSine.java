package com.eduworks.cruncher.trig;

import java.io.InputStream;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherArcSine extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		String sDeg = optAsString("deg","",c,parameters, dataStreams);
		String sRad = optAsString("rad","",c,parameters, dataStreams);
		
		
		if(!sDeg.isEmpty()){
			return Math.asin(Math.toRadians(Double.parseDouble(sDeg)));
		}else if(!sRad.isEmpty()){
			return Math.asin(Double.parseDouble(sRad));
		}else{
			double obj = getAsDouble("obj", c, parameters, dataStreams);
			if(obj < 2 * Math.PI && obj > -2 * Math.PI){
				return Math.asin(obj);
			}else{
				return Math.asin(Math.toRadians(obj));
			}
		}
	}

	@Override
	public String getDescription()
	{
		return "Retrieves the ArcSine of the angle passed in. If angle passed in via 'obj' will assume values < 2*pi are radians";
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
		return jo("rad","Number", "deg", "Number", "obj","Number");
	}

}
