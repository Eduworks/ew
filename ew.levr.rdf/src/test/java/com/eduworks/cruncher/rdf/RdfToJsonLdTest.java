package com.eduworks.cruncher.rdf;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.eduworks.resolver.Context;
import com.eduworks.util.io.EwFileSystem;

public class RdfToJsonLdTest {

	 @Test
	 public void RdfXmlToJsonLdTest() throws JSONException, IOException{
		 String asnString = FileUtils.readFileToString(EwFileSystem.findFile("asn/D10003FB-commoncore.rdf", RdfToJsonLdTest.class, false, false), Charset.forName("UTF-8")); 
		 
		 CruncherRdfToJsonLd cr = new CruncherRdfToJsonLd();
	     cr.build("obj", asnString);
	     //cr.build("context", unexpanded.get("@context"));
	     JSONObject json = (JSONObject) cr.resolve(new Context(), new HashMap<>(), null);
	     
	     System.out.println(json.toString(2));
	 }
	 
	 @Test
	 public void LargeRdfXmlToJsonLdTest() throws JSONException, IOException{
		 String asnString = FileUtils.readFileToString(EwFileSystem.findFile("asn/D10003FB.xml", RdfToJsonLdTest.class, false, false), Charset.forName("UTF-8")); 
		 
		 CruncherRdfToJsonLd cr = new CruncherRdfToJsonLd();
	     cr.build("obj", asnString);
	     //cr.build("context", unexpanded.get("@context"));
	     JSONObject json = (JSONObject) cr.resolve(new Context(), new HashMap<>(), null);
	     
	     System.out.println(json.toString(2));
	 }
	 
	 @Test
	 public void RdfXmlToExpandedJsonLdTest() throws JSONException, IOException{
		 String asnString = FileUtils.readFileToString(EwFileSystem.findFile("asn/D10003FB-commoncore.rdf", RdfToJsonLdTest.class, false, false), Charset.forName("UTF-8")); 
		 
		 CruncherRdfToJsonLd cr = new CruncherRdfToJsonLd();
	     cr.build("obj", asnString);
	     //cr.build("context", unexpanded.get("@context"));
	     JSONObject json = (JSONObject) cr.resolve(new Context(), new HashMap<>(), null);
	     
	     CruncherJsonLdExpand cr2 = new CruncherJsonLdExpand();
	     cr2.build("obj", json.toString());
	     JSONArray expanded = (JSONArray) cr2.resolve(new Context(), new HashMap<>(), null);
	     
	     System.out.println(expanded.toString(2));
	 }
	 
	 @Test
	 public void RdfXmlToJsonLdAndBackTest() throws JSONException, IOException{
		 String asnString = FileUtils.readFileToString(EwFileSystem.findFile("asn/D10003FB-commoncore.rdf", RdfToJsonLdTest.class, false, false), Charset.forName("UTF-8")); 
		 
		 CruncherRdfToJsonLd cr = new CruncherRdfToJsonLd();
	     cr.build("obj", asnString);
	     //cr.build("context", unexpanded.get("@context"));
	     JSONObject json = (JSONObject) cr.resolve(new Context(), new HashMap<>(), null);
	     
	     CruncherJsonLdExpand cr2 = new CruncherJsonLdExpand();
	     cr2.build("obj", json.toString());
	     JSONArray expanded = (JSONArray) cr2.resolve(new Context(), new HashMap<>(), null);
	     
	     System.out.println(expanded.toString(2));
	     
	     CruncherJsonLdToRdfXml cr3 =  new CruncherJsonLdToRdfXml();
	     cr3.build("obj", json);
	     
	     String output = (String) cr3.resolve(new Context(), new HashMap<>(), null);
	     
	     System.out.println(output);
	 }
}
