package com.eduworks.cruncher.ontology;

import com.eduworks.ontology.Ontology;
import com.eduworks.ontology.OntologyInstance;
import com.eduworks.resolver.Context;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.shared.ClosedException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Map;


public class CruncherOntologyDeleteInstance extends CruncherOntology {

	@SuppressWarnings("unused")
	@Override
	public Object resolve(Context c,
			Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException {
		
		String ontologyId = decodeValue(optAsString("ontologyId","", c,parameters, dataStreams));
		
		String instanceId = decodeValue(optAsString("instanceId","", c,parameters, dataStreams));
		
		String directory = decodeValue(optAsString("directory","", c,parameters, dataStreams));
		
		Ontology o = null;
		OntologyInstance ins = null;
		Dataset tdbDataset = getDataSet(directory,ReadWrite.WRITE,c);

		try
		{
			try{
				o = getOntology(ontologyId, tdbDataset, c);
			}catch(ClosedException e){
				clearContextData(c);
				return resolve(c,parameters, dataStreams);
			}
			
			ins = o.getInstance(instanceId, true);
			
			ins.delete();
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
		
		return true;
	}

	@Override
	public String getDescription() {
		return "Deletes the instance specified by the instanceId parameter and removes any reference to it in it's own ontology";
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
		return jo("ontologyId", "String", "directory", "String", "instanceId", "String");
	}

}
