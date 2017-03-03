/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eduworks.cruncher.rdf;

import com.eduworks.lang.util.EwJson;
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
        
        Assert.assertTrue("Expanded framework is not equal to previously expanded framework.", correct.equals(expanded));
    }
    
    @Test
    public void expandCompetency() throws JSONException, IOException
    {
        String json = FileUtils.readFileToString(EwFileSystem.findFile("94c.competency.json", CruncherJsonLdExpandTest.class, false, false), Charset.forName("UTF-8"));
        CruncherJsonLdExpand c = new CruncherJsonLdExpand();
        JSONObject unexpanded = new JSONObject(json);
        c.build("obj", unexpanded);
        JSONArray expanded = (JSONArray) c.resolve(new Context(), new HashMap<>(), null);
        System.out.println(expanded.toString(2));
        json = FileUtils.readFileToString(EwFileSystem.findFile("94c.expanded.competency.json", CruncherJsonLdExpandTest.class, false, false), Charset.forName("UTF-8"));
        JSONArray correct = new JSONArray(json);
        
        Assert.assertTrue("Expanded competency is not equal to previously expanded competency.", correct.equals(expanded));
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
        
        Assert.assertTrue("Expanded relation is not equal to previously expanded relation.", correct.equals(expanded));
    }
}
