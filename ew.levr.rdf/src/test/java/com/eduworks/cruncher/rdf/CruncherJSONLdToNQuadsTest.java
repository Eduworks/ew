package com.eduworks.cruncher.rdf;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.eduworks.resolver.Context;
import com.eduworks.util.io.EwFileSystem;

public class CruncherJSONLdToNQuadsTest {
	
	@Test
    public void NQuadFramework() throws IOException, JSONException {
		 String jsonString = FileUtils.readFileToString(EwFileSystem.findFile("012.framework.json", CruncherJsonLdExpandTest.class, false, false), Charset.forName("UTF-8"));
		 CruncherJsonLdToNQuads c = new CruncherJsonLdToNQuads();
         JSONObject unexpanded;
         try{
        	 unexpanded = new JSONObject(jsonString);
         }catch(JSONException e){
        	 unexpanded = new JSONArray(jsonString).getJSONObject(0);
         }
         
         System.out.println(unexpanded.toString(2));
         
         c.build("context", unexpanded.get("@context"));
         c.build("obj", unexpanded);
         String nquads = (String)c.resolve(new Context(), new HashMap<>(), null);
         
         System.out.println(nquads);
         
         CruncherNQuadsToJsonLd cr = new CruncherNQuadsToJsonLd();
         cr.build("obj", nquads);
         cr.build("context", unexpanded.get("@context"));
         JSONObject json = (JSONObject) cr.resolve(new Context(), new HashMap<>(), null);
         
         System.out.println(json.toString(2));
         
         Assert.assertTrue("JSON-LD from NQuads does not match original", TestHelper.compareJSON(json, unexpanded));
	}
	
	@Test
    public void NQuadCompetency() throws IOException, JSONException {
		String jsonString = FileUtils.readFileToString(EwFileSystem.findFile("94c.competency.json", CruncherJsonLdExpandTest.class, false, false), Charset.forName("UTF-8"));
		 CruncherJsonLdToNQuads c = new CruncherJsonLdToNQuads();
        JSONObject unexpanded;
        try{
       	 unexpanded = new JSONObject(jsonString);
        }catch(JSONException e){
       	 unexpanded = new JSONArray(jsonString).getJSONObject(0);
        }
        
        System.out.println(unexpanded.toString(2));
        
        c.build("context", unexpanded.get("@context"));
        c.build("obj", unexpanded);
        String nquads = (String)c.resolve(new Context(), new HashMap<>(), null);
        
        System.out.println(nquads);
        
        CruncherNQuadsToJsonLd cr = new CruncherNQuadsToJsonLd();
        cr.build("obj", nquads);
        cr.build("context", unexpanded.get("@context"));
        JSONObject json = (JSONObject) cr.resolve(new Context(), new HashMap<>(), null);
        
        System.out.println(json.toString(2));
        
        Assert.assertTrue("JSON-LD from NQuads does not match original", TestHelper.compareJSON(json, unexpanded));
	}
	
	@Test
    public void NQuadRelation() throws IOException, JSONException {
		String jsonString = FileUtils.readFileToString(EwFileSystem.findFile("4bc.relation.json", CruncherJsonLdExpandTest.class, false, false), Charset.forName("UTF-8"));
		 CruncherJsonLdToNQuads c = new CruncherJsonLdToNQuads();
        JSONObject unexpanded;
        try{
       	 unexpanded = new JSONObject(jsonString);
        }catch(JSONException e){
       	 unexpanded = new JSONArray(jsonString).getJSONObject(0);
        }
        
        System.out.println(unexpanded.toString(2));
        
        c.build("context", unexpanded.get("@context"));
        c.build("obj", unexpanded);
        String nquads = (String)c.resolve(new Context(), new HashMap<>(), null);
        
        System.out.println(nquads);
        
        CruncherNQuadsToJsonLd cr = new CruncherNQuadsToJsonLd();
        cr.build("obj", nquads);
        cr.build("context", unexpanded.get("@context"));
        JSONObject json = (JSONObject) cr.resolve(new Context(), new HashMap<>(), null);
        
        System.out.println(json.toString(2));
        
        Assert.assertTrue("JSON-LD from NQuads does not match original", TestHelper.compareJSON(json, unexpanded));
	}
	
	@Test
    public void NQuadLevel() throws IOException, JSONException {
		String jsonString = FileUtils.readFileToString(EwFileSystem.findFile("d66.level.json", CruncherJsonLdExpandTest.class, false, false), Charset.forName("UTF-8"));
		 CruncherJsonLdToNQuads c = new CruncherJsonLdToNQuads();
        JSONObject unexpanded;
        try{
       	 unexpanded = new JSONObject(jsonString);
        }catch(JSONException e){
       	 unexpanded = new JSONArray(jsonString).getJSONObject(0);
        }
        
        System.out.println(unexpanded.toString(2));
        
        c.build("context", unexpanded.get("@context"));
        c.build("obj", unexpanded);
        String nquads = (String)c.resolve(new Context(), new HashMap<>(), null);
        
        System.out.println(nquads);
        
        CruncherNQuadsToJsonLd cr = new CruncherNQuadsToJsonLd();
        cr.build("obj", nquads);
        cr.build("context", unexpanded.get("@context"));
        JSONObject json = (JSONObject) cr.resolve(new Context(), new HashMap<>(), null);
        
        System.out.println(json.toString(2));
        
        Assert.assertTrue("JSON-LD from NQuads does not match original", TestHelper.compareJSON(json, unexpanded));
	}
	
	@Test
    public void NQuadRollupRule() throws IOException, JSONException {
		String jsonString = FileUtils.readFileToString(EwFileSystem.findFile("3a4.rollupRule.json", CruncherJsonLdExpandTest.class, false, false), Charset.forName("UTF-8"));
		 CruncherJsonLdToNQuads c = new CruncherJsonLdToNQuads();
        JSONObject unexpanded;
        try{
       	 unexpanded = new JSONObject(jsonString);
        }catch(JSONException e){
       	 unexpanded = new JSONArray(jsonString).getJSONObject(0);
        }
        
        System.out.println(unexpanded.toString(2));
        
        c.build("context", unexpanded.get("@context"));
        c.build("obj", unexpanded);
        String nquads = (String)c.resolve(new Context(), new HashMap<>(), null);
        
        System.out.println(nquads);
        
        CruncherNQuadsToJsonLd cr = new CruncherNQuadsToJsonLd();
        cr.build("obj", nquads);
        cr.build("context", unexpanded.get("@context"));
        JSONObject json = (JSONObject) cr.resolve(new Context(), new HashMap<>(), null);
        
        System.out.println(json.toString(2));
        
        Assert.assertTrue("JSON-LD from NQuads does not match original", TestHelper.compareJSON(json, unexpanded));
	}
	
	@Test
    public void NQuadAssertion() throws IOException, JSONException {
		String jsonString = FileUtils.readFileToString(EwFileSystem.findFile("0ca.assertion.partial.json", CruncherJsonLdExpandTest.class, false, false), Charset.forName("UTF-8"));
		CruncherJsonLdToNQuads c = new CruncherJsonLdToNQuads();
        JSONObject unexpanded;
        try{
       	 	unexpanded = new JSONObject(jsonString);
        }catch(JSONException e){
       	 	unexpanded = new JSONArray(jsonString).getJSONObject(0);
        }
        
        System.out.println(unexpanded.toString(2));
        
        c.build("context", unexpanded.get("@context"));
        c.build("obj", unexpanded);
        String nquads = (String)c.resolve(new Context(), new HashMap<>(), null);
        
        System.out.println(nquads);
        
        CruncherNQuadsToJsonLd cr = new CruncherNQuadsToJsonLd();
        cr.build("obj", nquads);
        cr.build("context", unexpanded.get("@context"));
        JSONObject json = (JSONObject) cr.resolve(new Context(), new HashMap<>(), null);
        
        System.out.println(json.toString(2));
        
        Assert.assertTrue("JSON-LD from NQuads does not match original", TestHelper.compareJSON(json, unexpanded));
        
	}
}
