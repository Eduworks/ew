package com.eduworks.cruncher.rdf;

import com.eduworks.lang.util.EwJson;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.github.jsonldjava.core.JsonLdError;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.rdf2go.RDF2GoTripleCallback;
import com.github.jsonldjava.utils.JsonUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ontoware.rdf2go.model.Syntax;
import org.openrdf.rdf2go.RepositoryModelSet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fray
 */
public class CruncherJsonLdToRdfJson extends Cruncher
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
			final RDF2GoTripleCallback cb = new RDF2GoTripleCallback();
			final RepositoryModelSet model = (RepositoryModelSet) JsonLdProcessor.toRDF(jsonObject, cb, options);

			StringWriter writer = null;
			model.writeTo(writer = new StringWriter(), Syntax.RdfJson);
			return writer.getBuffer().toString();
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
		return "JSON-LD to RDF JSON";
	}

	@Override
	public String getReturn()
	{
		return "RDF/JSON";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("obj", "JSONObject|JSONArray","context","JSONObject");
	}

}
