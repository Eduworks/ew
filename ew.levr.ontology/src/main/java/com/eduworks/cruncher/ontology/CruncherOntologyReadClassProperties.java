package com.eduworks.cruncher.ontology;

import com.eduworks.ontology.Ontology;
import com.eduworks.ontology.OntologyClass;
import com.eduworks.ontology.OntologyProperty;
import com.eduworks.resolver.Context;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.shared.ClosedException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;

public class CruncherOntologyReadClassProperties extends CruncherOntology
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{

		String directory = decodeValue(optAsString("directory", "", c, parameters, dataStreams));

		String ontologyId = decodeValue(optAsString("ontologyId", "", c, parameters, dataStreams));

		String classId = decodeValue(optAsString("classId", "", c, parameters, dataStreams));

		String rangeId = decodeValue(optAsString("rangeId", "", c, parameters, dataStreams));

		Boolean direct = optAsBoolean("direct", false, c, parameters, dataStreams);

		Ontology o = null;
		JSONArray arr = new JSONArray();
		Dataset tdbDataset = getDataSet(directory, ReadWrite.READ, c);

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

			OntologyClass cls = o.getClass(classId);

			Set<OntologyProperty> props = cls.getPropertiesWithDomain(direct);

			for (OntologyProperty prop : props)
			{
				if (!rangeId.isEmpty())
				{

					JSONArray range = prop.getRange();

					for (int i = 0; i < range.length(); i++)
					{
						String rangeItem = range.get(i).toString();

						if (rangeItem.equals(rangeId))
						{
							arr.put(prop.getId());
						}
					}
				}
				else
				{
					arr.put(prop.getId());
				}
			}
		}
		finally
		{
			if (o != null)
				o.close(true);
		}

		return arr;
	}

	@Override
	public String getDescription()
	{
		return "Returns the object representation of the class specified";
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
		return jo("ontologyId", "String", "directory", "String", "classId", "String");
	}

}
