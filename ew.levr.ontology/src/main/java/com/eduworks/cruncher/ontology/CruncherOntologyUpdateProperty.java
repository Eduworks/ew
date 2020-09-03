package com.eduworks.cruncher.ontology;

import com.eduworks.ontology.Ontology;
import com.eduworks.ontology.OntologyProperty;
import com.eduworks.resolver.Context;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.shared.ClosedException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Map;

public class CruncherOntologyUpdateProperty extends CruncherOntology
{

	@SuppressWarnings("unused")
	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{

		String directory = decodeValue(optAsString("directory", "", c, parameters, dataStreams));

		String ontologyId = decodeValue(optAsString("ontologyId", "", c, parameters, dataStreams));

		String propertyId = decodeValue(optAsString("propertyId", "", c, parameters, dataStreams));

		JSONObject vals = new JSONObject(decodeValue(optAsString("vals", "{}", c, parameters, dataStreams)));

		Ontology o = null;
		OntologyProperty prop = null;
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
				return resolve(c, parameters, dataStreams);
			}

			prop = o.getProperty(propertyId);

			prop.update(vals);

			if (prop != null)
			{
				jsonRepresentation = prop.getJSONRepresentation();
			}

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
		return "Updates the property specified by propertyId with the values passed in the vals parameter. "
				+ "Vals could contain domain, range, subclassof, or property flags such as reflexive, tranistive etc.";
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
		return jo("ontologyId", "String", "directory", "String", "propertyId", "String", "vals", "Object");
	}

}
