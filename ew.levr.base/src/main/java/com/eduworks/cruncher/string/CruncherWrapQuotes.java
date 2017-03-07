package com.eduworks.cruncher.string;

import java.io.InputStream;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherWrapQuotes extends Cruncher
{
	  @Override
	   public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	   {
	      String str = getAsString("str",c,parameters, dataStreams);
          if (str == null)
              str = getObjAsString(c, parameters, dataStreams);
	      
	      return "\"" + str + "\"";
	   }

	   @Override
	   public String getDescription()
	   {
	      return "Wraps the string in quotes.";
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
