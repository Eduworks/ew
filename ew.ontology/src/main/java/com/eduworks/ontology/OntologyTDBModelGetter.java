package com.eduworks.ontology;


import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ModelGetter;
import org.apache.jena.rdf.model.ModelReader;
import org.apache.jena.tdb.TDBException;
import org.apache.jena.tdb.TDBLoader;


public class OntologyTDBModelGetter implements ModelGetter {
	 private Dataset ds;
	 
     public OntologyTDBModelGetter( Dataset dataset ) {
         ds = dataset;    
     }
     
     @Override
     public Model getModel( String uri ) {
         if(ds.containsNamedModel(uri)){
        	 return ds.getNamedModel(uri);
         }else{
        	 return null;
         }
     }

     @Override
     public Model getModel( String uri, ModelReader loadIfAbsent ) {
         Model m = getModel(uri);

         // Creates the model if it cannot be found in the dataset
         if (m == null) {

        	 // First try to load from the URI
        	 try 
        	 { 
        		 m = ModelFactory.createOntologyModel();
        		 
        		 TDBLoader.loadModel(m, uri);
        	 }
        	 // If that fails, create empty model
        	 catch (Exception e) 
        	 {
        		 m = ModelFactory.createDefaultModel();
        	 }
             
        	 // Try to add to TDB datastore if possible
        	 try
        	 {
        		 ds.addNamedModel( uri, m );
        	 }
        	 catch (TDBException e)
        	 {
        		// Fails if in a READ-ONLY transaction 
        	 }
         }

         return m;
     }
}
