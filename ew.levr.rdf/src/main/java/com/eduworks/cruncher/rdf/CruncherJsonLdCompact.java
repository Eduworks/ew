package com.eduworks.cruncher.rdf;

import com.eduworks.lang.util.EwJson;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
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
import org.json.JSONTokener;

/**
 * Converts a JSON object to JSON-LD and performs a Compact operation.
 *
 * rs2: result = obj.jsonLdCompact();<br>
 * LevrJS: result = jsonLdCompact.call(this,obj);
 *
 * @class jsonLdCompact
 * @module ew.levr.rdf
 * @author fritz.ray@eduworks.com
 */
/**
 * @method jsonLdCompact
 * @param obj {JSONObject} JSONObject to convert into a JSON-LD object and compact.
 * @param [context] {JSONObject} Context to use to compact. Will use context from obj if missing.
 * @return {JSONObject} Compacted object.
 */
public class CruncherJsonLdCompact extends Cruncher
{
    @Override
    public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
    {
        try
        {
            Object obj = getObj(c, parameters, dataStreams);
            if (obj == null) return null;
// Read the file into an Object (The type of this object will be a List, Map, String, Boolean,
// Number or null depending on the root object in the file).
            Object jsonObject = JsonUtils.fromInputStream(new ByteArrayInputStream(obj.toString().getBytes()));
// Create a context JSON map containing prefixes and definitions
            
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
// Create an instance of JsonLdOptions with the standard JSON-LD options
            JsonLdOptions options = new JsonLdOptions();
// Call whichever JSONLD function you want! (e.g. compact)
            
            Object compact = JsonLdProcessor.compact(jsonObject,context,options);
            
            ((Map)compact).put("@context", context);
// Print out the result (or don't, it's your call!)
            return new JSONObject(JsonUtils.toString(compact));
        }
        catch (IOException ex)
        {
            Logger.getLogger(CruncherJsonLdCompact.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (JsonLdError ex)
        {
            Logger.getLogger(CruncherJsonLdCompact.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public String getDescription()
    {
        return "Performs a JSON-LD Compact algorithm on the obj.";
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
