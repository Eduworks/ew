/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eduworks.cruncher.rdf;

import com.eduworks.resolver.Context;
import com.eduworks.util.io.EwFileSystem;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class CruncherJsonLdExpandTest
{
	@Test
    public void expandFramework() throws JSONException, IOException
    {
        String json = FileUtils.readFileToString(EwFileSystem.findFile("012.framework.json", CruncherJsonLdExpandTest.class, false, false), Charset.forName("UTF-8"));
        CruncherJsonLdExpand c = new CruncherJsonLdExpand();
        JSONObject unexpanded = new JSONObject(json);
    
        
        c.build("obj", unexpanded);
        JSONArray expanded = (JSONArray) c.resolve(new Context(), new HashMap<>(), null);
        
        System.out.println(expanded.toString(2));
        
        json = FileUtils.readFileToString(EwFileSystem.findFile("012.expanded.framework.json", CruncherJsonLdExpandTest.class, false, false), Charset.forName("UTF-8"));
        JSONArray correct = new JSONArray(json);
        
        Assert.assertTrue("Expanded framework is not equal to defined expanded framework.", TestHelper.compareJSON(expanded, correct));
        
        CruncherJsonLdCompact cr = new CruncherJsonLdCompact();
        cr.build("obj", expanded.get(0));
        cr.build("context", "http://schema.cassproject.org/0.2");
        JSONObject compacted = (JSONObject) cr.resolve(new Context(), new HashMap<>(), null);
        
        System.out.println(compacted.toString(2));
        
        Assert.assertTrue("Re-compacted framework is not equal to original.", TestHelper.compareJSON(unexpanded, compacted));
    }
    
    @Test
    public void expandCompetency() throws JSONException, IOException
    {
        String json = FileUtils.readFileToString(EwFileSystem.findFile("94c.competency.json", CruncherJsonLdExpandTest.class, false, false), Charset.forName("UTF-8"));
        CruncherJsonLdExpand c = new CruncherJsonLdExpand();
        JSONObject unexpanded = new JSONObject(json);
        
        System.out.println(unexpanded.toString(2));
        
        c.build("obj", unexpanded);
        JSONArray expanded = (JSONArray) c.resolve(new Context(), new HashMap<>(), null);
        
        System.out.println(expanded.toString(2));
        
        json = FileUtils.readFileToString(EwFileSystem.findFile("94c.expanded.competency.json", CruncherJsonLdExpandTest.class, false, false), Charset.forName("UTF-8"));
        JSONArray correct = new JSONArray(json);
        
        Assert.assertTrue("Expanded competency is not equal to defined expanded competency.", TestHelper.compareJSON(expanded, correct));
        
        
        CruncherJsonLdCompact cr = new CruncherJsonLdCompact();
        cr.build("obj", expanded.get(0));
        cr.build("context", "http://schema.cassproject.org/0.2");
        JSONObject compacted = (JSONObject) cr.resolve(new Context(), new HashMap<>(), null);
        
        System.out.println(compacted.toString(2));
        
        Assert.assertTrue("Re-compacted competency is not equal to original.", TestHelper.compareJSON(unexpanded, compacted));
    }
    
    @Test
    public void expandRelation() throws JSONException, IOException
    {
        String json = FileUtils.readFileToString(EwFileSystem.findFile("4bc.relation.json", CruncherJsonLdExpandTest.class, false, false), Charset.forName("UTF-8"));
        CruncherJsonLdExpand c = new CruncherJsonLdExpand();
        JSONObject unexpanded = new JSONObject(json);
        
        c.build("obj", unexpanded);
        JSONArray expanded = (JSONArray) c.resolve(new Context(), new HashMap<>(), null);
        
        System.out.println(expanded.toString(2));
        
        json = FileUtils.readFileToString(EwFileSystem.findFile("4bc.expanded.relation.json", CruncherJsonLdExpandTest.class, false, false), Charset.forName("UTF-8"));
        JSONArray correct = new JSONArray(json);
        
        Assert.assertTrue("Expanded relation is not equal to defined expanded relation.", TestHelper.compareJSON(expanded, correct));
        
        CruncherJsonLdCompact cr = new CruncherJsonLdCompact();
        cr.build("obj", expanded.get(0));
        cr.build("context", "http://schema.cassproject.org/0.2");
        JSONObject compacted = (JSONObject) cr.resolve(new Context(), new HashMap<>(), null);
        
        System.out.println(compacted.toString(2));
        
        Assert.assertTrue("Re-compacted relation is not equal to original.", TestHelper.compareJSON(unexpanded, compacted));
    }
    
    @Test
    public void expandLevel() throws JSONException, IOException
    {
    	 String json = FileUtils.readFileToString(EwFileSystem.findFile("d66.level.json", CruncherJsonLdExpandTest.class, false, false), Charset.forName("UTF-8"));
         CruncherJsonLdExpand c = new CruncherJsonLdExpand();
         JSONObject unexpanded = new JSONObject(json);
         
         c.build("obj", unexpanded);
         JSONArray expanded = (JSONArray) c.resolve(new Context(), new HashMap<>(), null);
         
         System.out.println(expanded.toString(2));
         
         json = FileUtils.readFileToString(EwFileSystem.findFile("d66.expanded.level.json", CruncherJsonLdExpandTest.class, false, false), Charset.forName("UTF-8"));
         JSONArray correct = new JSONArray(json);
         
         Assert.assertTrue("Expanded relation is not equal to defined expanded relation.", TestHelper.compareJSON(expanded, correct));
         
         CruncherJsonLdCompact cr = new CruncherJsonLdCompact();
         cr.build("obj", expanded.get(0));
         cr.build("context", "http://schema.cassproject.org/0.2");
         JSONObject compacted = (JSONObject) cr.resolve(new Context(), new HashMap<>(), null);
         
         System.out.println(compacted.toString(2));
         
         Assert.assertTrue("Re-compacted relation is not equal to original.", TestHelper.compareJSON(unexpanded, compacted));
    }
    
    @Test
    public void expandRollupRule() throws JSONException, IOException
    {
    	 String json = FileUtils.readFileToString(EwFileSystem.findFile("3a4.rollupRule.json", CruncherJsonLdExpandTest.class, false, false), Charset.forName("UTF-8"));
         CruncherJsonLdExpand c = new CruncherJsonLdExpand();
         JSONObject unexpanded = new JSONObject(json);
         
         c.build("obj", unexpanded);
         JSONArray expanded = (JSONArray) c.resolve(new Context(), new HashMap<>(), null);
         
         System.out.println(expanded.toString(2));
         
         json = FileUtils.readFileToString(EwFileSystem.findFile("3a4.expanded.rollupRule.json", CruncherJsonLdExpandTest.class, false, false), Charset.forName("UTF-8"));
         JSONArray correct = new JSONArray(json);
         
         Assert.assertTrue("Expanded relation is not equal to defined expanded relation.", TestHelper.compareJSON(expanded, correct));
         
         CruncherJsonLdCompact cr = new CruncherJsonLdCompact();
         cr.build("obj", expanded.get(0));
         cr.build("context", "http://schema.cassproject.org/0.2");
         JSONObject compacted = (JSONObject) cr.resolve(new Context(), new HashMap<>(), null);
         
         System.out.println(compacted.toString(2));
         
         Assert.assertTrue("Re-compacted relation is not equal to original.", TestHelper.compareJSON(unexpanded, compacted));
    }
    
    @Test
    public void expandAssertion() throws JSONException, IOException
    {
    	String json = FileUtils.readFileToString(EwFileSystem.findFile("0ca.assertion.partial.json", CruncherJsonLdExpandTest.class, false, false), Charset.forName("UTF-8"));
        CruncherJsonLdExpand c = new CruncherJsonLdExpand();
        JSONObject unexpanded = new JSONObject(json);
        
        c.build("obj", unexpanded);
        JSONArray expanded = (JSONArray) c.resolve(new Context(), new HashMap<>(), null);
        
        System.out.println(expanded.toString(2));
        
        json = FileUtils.readFileToString(EwFileSystem.findFile("0ca.expanded.assertion.partial.json", CruncherJsonLdExpandTest.class, false, false), Charset.forName("UTF-8"));
        JSONArray correct = new JSONArray(json);
        
        Assert.assertTrue("Expanded partial assertion is not equal to defined expanded partial assertion.", TestHelper.compareJSON(expanded, correct));
        
        CruncherJsonLdCompact cr = new CruncherJsonLdCompact();
        cr.build("obj", expanded);
        cr.build("context", "http://schema.cassproject.org/0.2");
        JSONObject compacted = (JSONObject) cr.resolve(new Context(), new HashMap<>(), null);
        
        System.out.println(compacted.toString(2));
        
        Assert.assertTrue("Re-compacted partial assertion is not equal to original.", TestHelper.compareJSON(unexpanded, compacted));
        
        
        json = FileUtils.readFileToString(EwFileSystem.findFile("a04.assertion.encrypted.json", CruncherJsonLdExpandTest.class, false, false), Charset.forName("UTF-8"));
        unexpanded = new JSONObject(json);
        
        c.build("obj", unexpanded);
        expanded = (JSONArray) c.resolve(new Context(), new HashMap<>(), null);
        
        System.out.println(expanded.toString(2));
        
        json = FileUtils.readFileToString(EwFileSystem.findFile("a04.expanded.assertion.encrypted.json", CruncherJsonLdExpandTest.class, false, false), Charset.forName("UTF-8"));
        correct = new JSONArray(json);
        
        Assert.assertTrue("Expanded encrypted assertion is not equal to defined expanded encrypted assertion.", TestHelper.compareJSON(expanded, correct));
        
        cr.build("obj", expanded);
        JSONArray context = new JSONArray();
        context.put("http://schema.cassproject.org/kbac/0.2");
        context.put("http://schema.cassproject.org/0.2");
        cr.build("context", context.toString());
        compacted = (JSONObject) cr.resolve(new Context(), new HashMap<>(), null);
        
        System.out.println(compacted.toString(2));
        
        Assert.assertTrue("Re-compacted encrypted assertion is not equal to original.", TestHelper.compareJSON(unexpanded, compacted));
    }
    
}
