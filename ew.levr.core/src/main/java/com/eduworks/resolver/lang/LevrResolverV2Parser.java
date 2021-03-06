package com.eduworks.resolver.lang;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.lang.util.EwJson;
import com.eduworks.resolver.lang.output.resolverv2Lexer;
import com.eduworks.resolver.lang.output.resolverv2Parser;
import com.eduworks.util.Tuple;

public class LevrResolverV2Parser
{
	public static Logger	log	= Logger.getLogger(LevrResolverParser.class);

	public static Tuple<Map<String, JSONObject>,Map<String, JSONObject>> decodeStreams(File fileToDecode) throws JSONException
	{
		FileInputStream input = null;
		try
		{
			input = new FileInputStream(fileToDecode);
			resolverv2Lexer rl = new resolverv2Lexer(new ANTLRStringStream(IOUtils.toString(input)));
			CommonTokenStream tokens = new CommonTokenStream(rl);
			resolverv2Parser rp = new resolverv2Parser(tokens);
			try
			{
				rp.parse();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			for (Entry<String,JSONObject> o : rp.servlets.entrySet())
			{
				setFile(o.getValue(),fileToDecode.getName());
				setMethod(o.getValue(),o.getKey());
			}
			for (Entry<String,JSONObject> o : rp.functions.entrySet())
			{
				setFile(o.getValue(),fileToDecode.getName());
				setMethod(o.getValue(),o.getKey());
			}
			if (rp.servlets.size() > 0)
				log.debug(new TreeSet<String>(rp.servlets.keySet()));
			if (rp.functions.size() > 0)
				log.debug(new TreeSet<String>(rp.functions.keySet()));
				
			return new Tuple<Map<String, JSONObject>,Map<String, JSONObject>>(rp.servlets,rp.functions);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
		catch (Throwable e)
		{
			System.out.println("Overflowed on " + fileToDecode.getPath());
			return null;
		}
		finally
		{
			IOUtils.closeQuietly(input);
		}
	}

	private static void setFile(JSONObject o,String file) throws JSONException
	{
		o.put("_codeFile",file);
		for (String key : EwJson.getKeys(o))
			if (o.get(key) instanceof JSONObject)
				setFile(o.getJSONObject(key),file);
	}
	private static void setMethod(JSONObject o,String method) throws JSONException
	{
		o.put("_codeMethod",method);
		for (String key : EwJson.getKeys(o))
			if (o.get(key) instanceof JSONObject)
				setMethod(o.getJSONObject(key),method);
	}
}
