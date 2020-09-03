package com.eduworks.cruncher.ontology;

import com.eduworks.ontology.Ontology;
import com.eduworks.resolver.Context;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Map;

public class CruncherOntologyTDBExport extends CruncherOntology
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		String exportDirectory = decodeValue(optAsString("exportDirectory", "", c, parameters, dataStreams));

		String tdbDirectory = decodeValue(optAsString("tdbDirectory", "", c, parameters, dataStreams));

		String identifier = decodeValue(optAsString("identifier", "", c, parameters, dataStreams));
		
		String extension = decodeValue(optAsString("extension", "", c, parameters, dataStreams));

		Dataset tdbDataset = getDataSet(tdbDirectory,ReadWrite.READ,c);

		try{
			Ontology.exportFromTDB(tdbDataset,exportDirectory, identifier, extension);
		}finally{
		}
		
		return true;
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
		return jo("identifier", "String", "extension", "String", "tdbDirectory", "String", "exportDirectory", "String");
	}

}
