package com.eduworks.cruncher.lang;

import com.eduworks.lang.EwMap;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Map;

public class CruncherCatch extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{		
		Object result;
		boolean printStackTrace = optAsBoolean("printStackTrace", false, c, parameters, dataStreams);
		try{
			if (has("try"))
				result = resolveAChild("try", c,parameters, dataStreams);
			else
				result = resolveAChild("obj", c,parameters, dataStreams);
		}catch(NullPointerException e){
			if(has("null")){
				final EwMap<String, String[]> newParams = new EwMap<String, String[]>(parameters);
				newParams.put("message", new String[] { e.getMessage() });
				if (printStackTrace)
					e.printStackTrace();
				result = resolveAChild("null", c,newParams, dataStreams);
			}else
			if(has("any")){
				final EwMap<String, String[]> newParams = new EwMap<String, String[]>(parameters);
				newParams.put("message", new String[] { e.getMessage() });
				if (printStackTrace)
					e.printStackTrace();
				result = resolveAChild("any", c,newParams, dataStreams);
			}else{
				throw e;
			}
		}catch(RuntimeException e){
			if(has("runtime")){
				final EwMap<String, String[]> newParams = new EwMap<String, String[]>(parameters);
				newParams.put("message", new String[] { e.getMessage() });
				if (printStackTrace)
					e.printStackTrace();
				result = resolveAChild("runtime", c,newParams, dataStreams);
			}else
			if(has("any")){
				final EwMap<String, String[]> newParams = new EwMap<String, String[]>(parameters);
				newParams.put("message", new String[] { e.getMessage() });
				if (printStackTrace)
					e.printStackTrace();
				result = resolveAChild("any", c,newParams, dataStreams);
			}else{
				throw new RuntimeException(e);
			}
		}catch(Exception e){
			if(has("any")){
				final EwMap<String, String[]> newParams = new EwMap<String, String[]>(parameters);
				newParams.put("message", new String[] { e.getMessage() });
				if (printStackTrace)
					e.printStackTrace();
				result = resolveAChild("any", c,newParams, dataStreams);
			}else{
				throw new RuntimeException(e);
			}
		}finally{
			if(has("finally")){
				result = resolveAChild("finally", c,parameters, dataStreams);
			}
		}
		return result;
	}

	@Override
	public String getDescription()
	{
		return "Runs the operation identified by 'try' and catches an Exception if catch ('null', 'runtime' or 'any' params) clause is specified, can also run a finally clause (result of finally is returned)";
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
		return jo("try","Resolvable","runtime","Resolvable","any","Resolvable", "finally", "Resolvable");
	}
}
