package com.eduworks.cruncher.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
		
		ProcessBuilder builder = new ProcessBuilder(args);

        Process process;
        ArrayList<String> output = new ArrayList<String>();
		try {
			process = builder.start();
			
			InputStream outp = process.getInputStream();
	        InputStreamReader isr = new InputStreamReader(outp);
	        BufferedReader br = new BufferedReader(isr);
	        
	        String line;
	        process.waitFor();
	        while ((line = br.readLine()) != null) {
	        	output.add(line);
	        }
	        
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
		return jo("args", "String[]");
	};
}
