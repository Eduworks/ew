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


public class CruncherOntologyGetShortestPath extends CruncherOntology {

	@Override
	public Object resolve(Context c,
			Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException {
		
		String directory = decodeValue(optAsString("directory","", c,parameters, dataStreams));
		
		String ontologyId = decodeValue(optAsString("ontologyId","", c,parameters, dataStreams));
		
		String start = decodeValue(optAsString("startId","", c,parameters, dataStreams));
		
		String end = decodeValue(optAsString("endId","", c,parameters, dataStreams));
		
		JSONArray pathRelationships = getAsJsonArray("path", c, parameters, dataStreams);
		
		Ontology o = null;
		JSONArray pathRepresentation = null;
		Dataset tdbDataset = getDataSet(directory,ReadWrite.READ,c);
		
		tdbDataset.begin(ReadWrite.READ);
		try
		{
			try{
				o = getOntology(ontologyId, tdbDataset, c);
			}catch(ClosedException e){
				clearContextData(c);
				return resolve(c,parameters, dataStreams);
			}
			
			pathRepresentation = o.findShortestPath(start, end, pathRelationships);
		}
		finally
		{
			if (o != null)
				o.close(true);
			
		}
			
		return pathRepresentation;
	}

	@Override
	public String getDescription() {
		return "Returns the object representation of the class specified";
	}

	@Override
	public String getReturn() {
		return "Object";
	}

	@Override
	public String getAttribution() {
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException {
		return jo("ontologyId", "String", "directory", "String", "classId", "String");
	}

}
