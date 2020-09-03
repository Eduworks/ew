package com.eduworks.cruncher.ontology;

import com.eduworks.ontology.Ontology;
import com.eduworks.resolver.Context;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Map;

public class CruncherOntologyListOntologies extends CruncherOntology
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		String directory = decodeValue(optAsString("directory", "", c, parameters, dataStreams));

		JSONArray ret = new JSONArray();

		Dataset tdbDataset = getDataSet(directory, ReadWrite.READ,c);
		for (String id : Ontology.listModelIdentifiers(tdbDataset))
		{
			ret.put(id);
		}

		return ret;
	}

	@Override
	public String getDescription()
	{
		return "adds an import statement to the ontology specified by ontologyId to import the ontology specified with importId";
	}

	@Override
	public String getReturn()
	{
		return "Object";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("ontologyId", "String", "directory", "String", "importId", "String");
	}

}
