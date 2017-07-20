package com.eduworks.levr.rdf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.ontoware.rdf2go.vocabulary.XSD;

import com.github.jsonldjava.core.JsonLdConsts;
import com.github.jsonldjava.core.JsonLdError;
import com.github.jsonldjava.core.JsonLdUtils;
import com.github.jsonldjava.core.RDFDataset;
import com.github.jsonldjava.core.RDFDatasetUtils;
import com.github.jsonldjava.core.RDFParser;

public class RdfXmlParser implements RDFParser {

	public RdfXmlParser(){
		this(false);
	}
	
	public RdfXmlParser(boolean ignoreLanguage){
		this.ignoreLanguage = ignoreLanguage;
	}
	
	private boolean ignoreLanguage;
	
	
	@Override
	public RDFDataset parse(Object input) throws JsonLdError {
		if(input instanceof String){
			JSONObject obj;
			try {
				obj = XML.toJSONObject((String) input);
				return parseXmlJson(obj);
			} catch (JSONException e) {
				
				
				 throw new JsonLdError(JsonLdError.Error.INVALID_INPUT,
		                    "XML Parse Error: "+e.getMessage());
			}
			
		} else {
			 throw new JsonLdError(JsonLdError.Error.INVALID_INPUT,
	                    "RDF/XML Parser expected string input.");
		}
	}

	public RDFDataset parseXmlJson(JSONObject input) throws JSONException {
		RDFDataset dataset = new RDFDataset();

		if(!input.has("rdf:RDF")){
			
		}else{
			JSONObject rdf = input.getJSONObject("rdf:RDF");
			
			HashMap<String, String> namespaces = new HashMap<>();
			
			Iterator<String> keys = rdf.keys();
			while(keys.hasNext()){
				String key = keys.next();
				if(key.contains("xmlns:")){
					namespaces.put(key.replace("xmlns:", ""), rdf.getString(key));
					dataset.setNamespace(key.replace("xmlns:", ""), rdf.getString(key));
				}
			}
			
			for(String ns : namespaces.keySet()){
				rdf.remove("xmlns:"+ns);
			}
			
			//System.out.println(rdf.toString(2));
			
			parseInner(rdf, dataset, namespaces);
		}
		
		return dataset;
	}
	
	public void parseInner(JSONObject rdf, RDFDataset dataset, HashMap<String,String> globalNS) throws JSONException {
		if(!rdf.has("rdf:about")){
			Iterator<String> keys = rdf.keys();
			while(keys.hasNext()){
				String key = keys.next();
				try {
					if(rdf.getJSONObject(key).has("rdf:about")){
						String type = key;
						String[] keysplit = key.split(":");

						
						if(globalNS.get(keysplit[0]) != null){
							type = type.replace(keysplit[0]+":", globalNS.get(keysplit[0]));
						}
						
						String id = RDFDatasetUtils.unescape(rdf.getJSONObject(key).getString("rdf:about"));
						
						addTypeTripleToDataset(dataset, id, type);
					}
					
					parseInner(rdf.getJSONObject(key), dataset, globalNS);
				} catch (JSONException e) {
					JSONArray arr = rdf.getJSONArray(key);
					
					String type = key;
					String[] keysplit = key.split(":");
					if(globalNS.get(keysplit[0]) != null){
						type = type.replace(keysplit[0]+":", globalNS.get(keysplit[0]));
					}
					
					parseObjectArray(arr, dataset, globalNS, type);
				}
			}
			return;
		}
		
		RDFDataset.Node subject;
		
		String value = RDFDatasetUtils.unescape(rdf.getString("rdf:about"));
		if(JsonLdUtils.isAbsoluteIri(value)){
			subject = new RDFDataset.IRI(value);
		}else{
			subject = new RDFDataset.BlankNode(value);
		}
		
		
		
		JSONObject duplicate = new JSONObject(rdf.toString());
		duplicate.remove("rdf:about");
		
		// Find Namespaces
		HashMap<String, String> namespaces = (HashMap<String, String>) globalNS.clone();
		Iterator<String> keys = rdf.keys();
		while(keys.hasNext()){
			String key = keys.next();
			if(key.contains("xmlns:")){
				namespaces.put(key.replace("xmlns:", ""), rdf.getString(key));
				duplicate.remove(key);
			}else if(key.equals("xmlns")){
				namespaces.put("@default", rdf.getString(key));
				duplicate.remove(key);
			}
		}
		
		
		keys = duplicate.keys();
		while(keys.hasNext()){
			String sPredicate, sObject;
			
			String key = keys.next();
			if(!key.equals("rdf:about") && !key.contains("xmlns:")){
				sPredicate = key;
				String[] keysplit = key.split(":");
				if(keysplit.length <= 1){
					if(namespaces.get("@default") != null){
						sPredicate = namespaces.get("@default") + sPredicate;
					}
				}
				if(namespaces.get(keysplit[0]) != null){
					sPredicate = sPredicate.replace(keysplit[0]+":", namespaces.get(keysplit[0]));
				}
				
				RDFDataset.Node predicate = new RDFDataset.IRI(RDFDatasetUtils.unescape(sPredicate));
				
				RDFDataset.Node object = null;
				
				try {
					JSONArray arr = duplicate.getJSONArray(key);
					
					parseArray(arr, dataset, namespaces, subject, predicate);
				} catch (JSONException e2) {
					try {
						JSONObject obj = duplicate.getJSONObject(key);
						
						if(obj.has("content")){
							String language = obj.optString("xml:lang");
							if(language.isEmpty() || ignoreLanguage)
								language = null;
							String datatype = obj.optString("rdf:datatype");
							if(datatype.isEmpty())
								datatype = null;
							String unescaped = RDFDatasetUtils.unescape(obj.getString("content"));
							
							object = new RDFDataset.Literal(unescaped, datatype, language);
						}else if(obj.length() == 1 && obj.has("rdf:resource")){
							sObject = obj.optString("rdf:resource");
							
							if(JsonLdUtils.isAbsoluteIri(sObject)){
								object = new RDFDataset.IRI(sObject);
							}else{
								object = new RDFDataset.BlankNode(sObject);
							}
						}else{
							parseInner(obj, dataset, namespaces);
						}
					} catch (JSONException e) {
						sObject = duplicate.getString(key);
						
						object = new RDFDataset.BlankNode(RDFDatasetUtils.unescape(sObject));
					}
					
				}
				
				if(object != null){
					addTripleToDataset(dataset, subject, predicate, object);
				}
				
			}
		}
	}
	
	private void parseObjectArray(JSONArray arr, RDFDataset dataset, HashMap<String, String> namespaces, String type) throws JSONException{
		for(int i = 0; i < arr.length(); i++){
			JSONObject obj = arr.getJSONObject(i);
			
			if(obj.has("rdf:about")){
				addTypeTripleToDataset(dataset, obj.getString("rdf:about"), type);
				
				parseInner(obj, dataset, namespaces);
			}
		}
	}
	

	private void parseArray(JSONArray arr, RDFDataset dataset, HashMap<String,String> namespaces, 
			RDFDataset.Node subject, RDFDataset.Node predicate) throws JSONException{
		
		for(int i = 0; i < arr.length(); i++){
			String sObject;
			RDFDataset.Node object = null;
			try {
				JSONObject obj = arr.getJSONObject(i);
				
				if(obj.has("content")){
					String language = obj.optString("xml:lang");
					String datatype = obj.optString("rdf:datatype");
					String unescaped = RDFDatasetUtils.unescape(obj.getString("content"));
					
					object = new RDFDataset.Literal(unescaped, datatype, language);
				}else if(obj.length() == 1 && obj.has("rdf:resource")){
					sObject = obj.optString("rdf:resource");
					
					if(JsonLdUtils.isAbsoluteIri(sObject)){
						object = new RDFDataset.IRI(sObject);
					}else{
						object = new RDFDataset.BlankNode(sObject);
					}
				}else if(obj.has("rdf:about")){
					String value = RDFDatasetUtils.unescape(obj.getString("rdf:about"));
					if(JsonLdUtils.isAbsoluteIri(value)){
						object = new RDFDataset.IRI(value);
					}else{
						object = new RDFDataset.BlankNode(value);
					}
					
					parseInner(obj, dataset, namespaces);
				}
			} catch (JSONException e) {
				sObject = arr.getString(i);
				
				object = new RDFDataset.BlankNode(RDFDatasetUtils.unescape(sObject));
			}
			
			if(subject != null && predicate != null && object != null){
				addTripleToDataset(dataset, subject, predicate, object);
			}
		}
	}
	
	private void addTripleToDataset(RDFDataset dataset, RDFDataset.Node subject, RDFDataset.Node predicate, RDFDataset.Node object){
		String name = "@default";
		
		RDFDataset.Quad triple = new RDFDataset.Quad(subject, predicate, object, name);
		
		// initialise graph in dataset
        if (!dataset.containsKey(name)) {
            final List<RDFDataset.Quad> tmp = new ArrayList<RDFDataset.Quad>();
            tmp.add(triple);
            dataset.put(name, tmp);
        }
        // add triple if unique to its graph
        else {
            final List<RDFDataset.Quad> triples = (List<RDFDataset.Quad>) dataset.get(name);
            if (!triples.contains(triple)) {
                triples.add(triple);
            }
        }
	}
	
	private void addTypeTripleToDataset(RDFDataset dataset, String id, String type) {
		String name = "@default";
		
		RDFDataset.Quad triple = new RDFDataset.Quad(id, JsonLdConsts.RDF_TYPE, type, name);
		
		// initialise graph in dataset
        if (!dataset.containsKey(name)) {
            final List<RDFDataset.Quad> tmp = new ArrayList<RDFDataset.Quad>();
            tmp.add(triple);
            dataset.put(name, tmp);
        }
        // add triple if unique to its graph
        else {
            final List<RDFDataset.Quad> triples = (List<RDFDataset.Quad>) dataset.get(name);
            if (!triples.contains(triple)) {
                triples.add(triple);
            }
        }
	}
}
