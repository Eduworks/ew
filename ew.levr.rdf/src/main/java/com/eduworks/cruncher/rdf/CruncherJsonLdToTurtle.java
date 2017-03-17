package com.eduworks.cruncher.rdf;

import com.eduworks.lang.util.EwJson;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.github.jsonldjava.core.JsonLdConsts;
import com.github.jsonldjava.core.JsonLdError;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author fray
 */
public class CruncherJsonLdToTurtle extends Cruncher
{
    
    @Override
    public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
    {
        try
        {
            Object obj = getObj(c, parameters, dataStreams);
            if (obj == null) return null;
            Object jsonObject = JsonUtils.fromInputStream(new ByteArrayInputStream(obj.toString().getBytes()));

            Object context;
            String ctxString = optAsString("context", null, c, parameters, dataStreams);
            JSONObject ctxObj;
            if (ctxString != null){
            	try{
            		ctxObj = new JSONObject(ctxString);
            		context = new HashMap();
                    for (String s : EwJson.getKeys(ctxObj))
                    {
                        if (ctxObj.isNull(s))continue;
                        
                        ((Map)context).put(s, ctxObj.get(s));
                    }
            	}catch(JSONException e){
            		try{
            			JSONArray arr = new JSONArray(ctxString);
            			
            			context = new ArrayList();
            			for(int i = 0; i < arr.length(); i++){
            				((ArrayList)context).add(arr.get(i));
            			}
            		}catch(JSONException e2){
            			context = ctxString;
            		}
            	}
            } else {
                context = jsonObject;
        	}
            
            JsonLdOptions options = new JsonLdOptions();
            options.setExpandContext(context);
            options.format = JsonLdConsts.TEXT_TURTLE;
            String rdfString = (String)JsonLdProcessor.toRDF(jsonObject, options);
            return rdfString;
        }
        catch (IOException ex)
        {
            Logger.getLogger(CruncherJsonLdExpand.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (JsonLdError ex)
        {
            Logger.getLogger(CruncherJsonLdExpand.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public String getDescription()
    {
        return "Performs a JSON-LD Expand algorithm on the obj.";
    }
    
    @Override
    public String getReturn()
    {
        return "JSON-LD";
    }
    
    @Override
    public String getAttribution()
    {
        return ATTRIB_NONE;
    }
    
    @Override
    public JSONObject getParameters() throws JSONException
    {
        return jo("obj", "JSONObject|JSONArray");
    }
    
}
