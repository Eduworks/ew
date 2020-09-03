package com.eduworks.cruncher.ontology;

import com.eduworks.ontology.Ontology;
import com.eduworks.resolver.Context;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.shared.ClosedException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Map;

public class CruncherOntologyQuery extends CruncherOntology
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{

		String ontologyId = decodeValue(optAsString("ontologyId", "", c, parameters, dataStreams));

		String directory = decodeValue(optAsString("directory", "", c, parameters, dataStreams));

		String query = decodeValue(optAsString("query", "", c, parameters, dataStreams));

		boolean local = optAsBoolean("local", false, c, parameters, dataStreams);

		Ontology o = null;
		JSONArray results = new JSONArray();
		Dataset tdbDataset = getDataSet(directory,ReadWrite.READ,c);

		try
		{
			try
			{
				o = getOntology(ontologyId, tdbDataset, c);
			}
			catch (ClosedException e)
			{
				clearContextData(c);
				return resolve(c,parameters, dataStreams);
			}

			JSONObject queryReturn = o.query(query, local);

			if (queryReturn.has("result"))
			{
				results = queryReturn.getJSONArray("result");
			}
		}
		finally
		{
			if (o != null)
				o.close(true);

		}
		
		return results;

	}

	@Override
	public String getDescription()
	{
		return "Runs the SPARQL query given on the ontology specified. Returns Array of objects, each a result to the query with values that match the variables in the query";
	}

	@Override
	public String getReturn()
	{
		return "JSONArray";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("ontologyId", "String", "directory", "String", "query", "String");
	}

}
