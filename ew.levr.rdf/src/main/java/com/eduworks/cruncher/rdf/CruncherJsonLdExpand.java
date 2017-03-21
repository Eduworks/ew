package com.eduworks.cruncher.rdf;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.github.jsonldjava.core.JsonLdError;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Converts a JSON object to JSON-LD and performs a Expand operation.
 *
 * rs2: result = obj.jsonLdExpand();<br>
 * LevrJS: result = jsonLdExpand.call(this,obj);
 *
 * @class jsonLdExpand
 * @module ew.levr.rdf
 * @author fritz.ray@eduworks.com
 */
/**
 * @method jsonLdExpand
 * @param obj {JSONObject} JSONObject to convert into a JSON-LD object.
 * @return {JSON-LD} Cached value if in cache, computed value if not in cache.
 */
public class CruncherJsonLdExpand extends Cruncher
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
// Create an instance of JsonLdOptions with the standard JSON-LD options
            JsonLdOptions options = new JsonLdOptions();
// Call whichever JSONLD function you want! (e.g. compact)
            
            Object compact = JsonLdProcessor.expand(jsonObject,options);
// Print out the result (or don't, it's your call!)
            return new JSONArray(JsonUtils.toString(compact));
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
