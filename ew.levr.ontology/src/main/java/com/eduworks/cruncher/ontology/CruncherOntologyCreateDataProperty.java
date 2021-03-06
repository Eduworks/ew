package com.eduworks.cruncher.ontology;

import com.eduworks.ontology.Ontology;
import com.eduworks.resolver.Context;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.shared.ClosedException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Map;

public class CruncherOntologyCreateDataProperty extends CruncherOntology
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{

		String directory = decodeValue(optAsString("directory", "", c, parameters, dataStreams));

		String ontologyId = decodeValue(optAsString("ontologyId", "", c, parameters, dataStreams));

		String propertyId = decodeValue(optAsString("propertyId", "", c, parameters, dataStreams));

		JSONObject vals = new JSONObject(decodeValue(optAsString("vals", "{}", c, parameters, dataStreams)));

		Ontology o = null;
		JSONObject jsonRepresentation = null;
		Dataset tdbDataset = getDataSet(directory,ReadWrite.WRITE,c);
		
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

			jsonRepresentation = o.createDataProperty(propertyId, vals).getJSONRepresentation();

		}
		catch (RuntimeException e)
		{

			throw e;
		}
		finally
		{
			if (o != null)
				o.close(false);

		}
		
		return jsonRepresentation;
	}

	@Override
	public String getDescription()
	{
		return "Defines a new data property in the ontology specified. Vals defines any superproperties, domain, and range as well as if the data property is functional. "
				+ "Data properties relate instances to pieces of data such as a string or integer";
	}

	@Override
	public String getReturn()
	{
		return "JSONObject";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("ontologyId", "String", "directory", "String", "propertyId", "String", "vals", "Object");
	}

}
