package com.eduworks.cruncher.rdf;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.github.jsonldjava.core.*;
import com.github.jsonldjava.utils.JsonUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.impl.client.CloseableHttpClient;
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
    static JsonLdOptions options = new JsonLdOptions();
    static {
        options.setDocumentLoader(new DocumentLoader(){
            private Map<String, Object> injectedDocs = new HashMap();
            public static final String DISALLOW_REMOTE_CONTEXT_LOADING = "com.github.jsonldjava.disallowRemoteContextLoading";
            /** @deprecated */
            @Deprecated
            public static final String ACCEPT_HEADER = "application/ld+json, application/json;q=0.9, application/javascript;q=0.5, text/javascript;q=0.5, text/plain;q=0.2, */*;q=0.1";
            private volatile CloseableHttpClient httpClient;

            @Override
            public DocumentLoader addInjectedDoc(String url, String doc) throws JsonLdError {
                try {
                    this.injectedDocs.put(url, JsonUtils.fromString(doc));
                    return this;
                } catch (Exception var4) {
                    throw new JsonLdError(JsonLdError.Error.LOADING_INJECTED_CONTEXT_FAILED, url, var4);
                }
            }

            @Override
            public RemoteDocument loadDocument(String url) throws JsonLdError {
                RemoteDocument doc = new RemoteDocument(url, (Object)null);
                if (this.injectedDocs.containsKey(url)) {
                    try {
                        doc.setDocument(this.injectedDocs.get(url));
                        return doc;
                    } catch (Exception var5) {
                        throw new JsonLdError(JsonLdError.Error.LOADING_INJECTED_CONTEXT_FAILED, url, var5);
                    }
                } else {
                    String disallowRemote = System.getProperty("com.github.jsonldjava.disallowRemoteContextLoading");
                    if ("true".equalsIgnoreCase(disallowRemote)) {
                        throw new JsonLdError(JsonLdError.Error.LOADING_REMOTE_CONTEXT_FAILED, "Remote context loading has been disallowed (url was " + url + ")");
                    } else {
                        try {
                            Object result = JsonUtils.fromURL(new URL(url), this.getHttpClient());
                            this.injectedDocs.put(url,result);
                            doc.setDocument(result);
                            return doc;
                        } catch (Exception var6) {
                            throw new JsonLdError(JsonLdError.Error.LOADING_REMOTE_CONTEXT_FAILED, url, var6);
                        }
                    }
                }
            }
        });
    }
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
