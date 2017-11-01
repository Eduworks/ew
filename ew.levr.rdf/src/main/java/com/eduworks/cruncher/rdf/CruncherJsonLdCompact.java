package com.eduworks.cruncher.rdf;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.github.jsonldjava.core.JsonLdError;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Converts a JSON object to JSON-LD and performs a Compact operation.
 * <p>
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
public class CruncherJsonLdCompact extends Cruncher {
	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException {
		try {
			Object obj = getObj(c, parameters, dataStreams);
			if (obj == null) return null;
			Object jsonObject = JsonUtils.fromInputStream(new ByteArrayInputStream(obj.toString().getBytes()));

			Object context;

			String ctxString = optAsString("context", null, c, parameters, dataStreams);
			JSONObject ctxObj;
			if (ctxString != null) {
				try {
					context = jsonToJava(new JSONObject(ctxString));
				} catch (JSONException e) {
					try {
						context = jsonToJava(new JSONArray(ctxString));
					} catch (JSONException e2) {
						context = ctxString;
					}
				}
			} else {
				context = jsonObject;
			}
			JsonLdOptions options = new JsonLdOptions();

			Object compact = JsonLdProcessor.compact(jsonObject, context, options);

			((Map) compact).put("@context", context);
			return new JSONObject(JsonUtils.toString(compact));
		} catch (IOException ex) {
			Logger.getLogger(CruncherJsonLdCompact.class.getName()).log(Level.SEVERE, null, ex);
		} catch (JsonLdError ex) {
			Logger.getLogger(CruncherJsonLdCompact.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public Object jsonToJava(Object o) throws JSONException {
		if (o instanceof JSONObject)
		{
			Map m = new HashMap();
			Iterator<String> it = ((JSONObject)o).keys();
			while (it.hasNext())
			{
				String next = it.next();
				m.put(next,jsonToJava(((JSONObject)o).get(next)));
			}
			return m;
		}

		if (o instanceof JSONArray)
		{
			List l = new ArrayList();
			for (int i = 0;i < ((JSONArray)o).length();i++)
				l.add(jsonToJava(((JSONArray)o).get(i)));
			return l;
		}

		return o;
	}

	@Override
	public String getDescription() {
		return "Performs a JSON-LD Compact algorithm on the obj.";
	}

	@Override
	public String getReturn() {
		return "JSON-LD";
	}

	@Override
	public String getAttribution() {
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException {
		return jo("obj", "JSONObject|JSONArray", "?context", "String");
	}

}
