package com.eduworks.cruncher.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.lang.threading.EwThreading;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherRunProcess extends Cruncher
{
	public Object resolve(Context c, java.util.Map<String, String[]> parameters, java.util.Map<String, java.io.InputStream> dataStreams)
			throws org.json.JSONException
	{
		
		JSONArray argsArray = getAsJsonArray("args", c, parameters, dataStreams);
		
		String[] args = new String[argsArray.length()];
		for(int i = 0; i < argsArray.length(); i++){
			args[i] = argsArray.getString(i);
		}
		
		ArrayList<String> output = new ArrayList<String>();
		
		boolean background = optAsBoolean("background", false, c, parameters, dataStreams);
		
		try {
			ProcessBuilder builder = new ProcessBuilder(args);
			String wd = optAsString("workingDirectory",null,c,parameters,dataStreams);
			if (wd != null)
				builder.directory(new File(wd));
	        final Process process;
	        
			process = builder.start();

			if(background){
				EwThreading.fork(new EwThreading.MyRunnable()
				{
					@Override
					public void run()
					{
						try{
							process.waitFor();
						}catch(InterruptedException e){

						}
					}
				});
				return null;
			}

			
			InputStream outp = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(outp);
	        BufferedReader br = new BufferedReader(isr);
			InputStream eoutp = process.getErrorStream();
			InputStreamReader eisr = new InputStreamReader(eoutp);
	        BufferedReader ebr = new BufferedReader(eisr);
	        
	        String line;
	        int timeout = optAsInteger("timeout", -1, c, parameters, dataStreams);
	        if (timeout < 0)
	        	process.waitFor();
	        else
	        	for (int i = 0;i < 10000;i++)
	        	{
	        		EwThreading.sleep(timeout/10000);
	    	        while ((line = br.readLine()) != null) {
	    	        	output.add(line);
	    	        }
	    	        while ((line = ebr.readLine()) != null) {
	    	        	output.add(line);
	    	        }
	        		try
	        		{
	        			process.exitValue();
	        			break;
	        		}
	        		catch (IllegalThreadStateException ex)
	        		{
	        			
	        		}
	        	}
	        while ((line = br.readLine()) != null) {
    	        	output.add(line);
    	        }
    	        while ((line = ebr.readLine()) != null) {
    	        	output.add(line);
    	        }
	        process.destroy();
	        
		} catch (IOException e) {
			e.printStackTrace();
			throw new JSONException(e.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new JSONException(e.getMessage());
		}
        
		return new JSONArray(output);
	}

	@Override
	public String getDescription()
	{
		return "Exececutes a process on the machine with the arguments given";
	}

	@Override
	public String getReturn()
	{
		return "String[]";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("args", "String[]", "?workingDirectory", "String", "?background", "boolean");
	};
}
