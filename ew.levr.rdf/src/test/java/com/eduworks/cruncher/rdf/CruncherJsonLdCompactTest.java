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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author fray
 */
public class CruncherJsonLdCompactTest
{

    @Test
    public void compactFramework() throws JSONException, IOException
    {
        String json = FileUtils.readFileToString(EwFileSystem.findFile("012.framework.json", CruncherJsonLdCompactTest.class, false, false), Charset.forName("UTF-8"));
        CruncherJsonLdCompact c = new CruncherJsonLdCompact();
        JSONObject uncompacted;
        try{
        	uncompacted = new JSONObject(json);
        }catch(JSONException e){
        	uncompacted = new JSONArray(json).getJSONObject(0);
        }
        
        c.build("obj", uncompacted);
        JSONObject compacted = (JSONObject) c.resolve(new Context(), new HashMap<>(), null);
        compacted.put("@context", uncompacted.get("@context"));
        System.out.println(compacted.toString(2));
        for (String s : EwJson.getKeys(compacted))
        {
//            System.out.println(s);
//            System.out.println(uncompacted.get(s).toString());
//            System.out.println(compacted.get(s).toString());
            Assert.assertTrue("Compacted Framework is not equivalent to Uncompacted Framework", uncompacted.get(s).toString().contains(compacted.get(s).toString()));
        }
        for (String s : EwJson.getKeys(uncompacted))
        {
//            System.out.println(s);
//            System.out.println(uncompacted.get(s).toString());
//            System.out.println(compacted.get(s).toString());
            Assert.assertTrue("Compacted Framework is not equivalent to Uncompacted Framework", uncompacted.get(s).toString().contains(compacted.get(s).toString()));
        }
    }
    @Test
    public void compactCompetency() throws JSONException, IOException
    {
        String json = FileUtils.readFileToString(EwFileSystem.findFile("94c.competency.json", CruncherJsonLdCompactTest.class, false, false), Charset.forName("UTF-8"));
        CruncherJsonLdCompact c = new CruncherJsonLdCompact();
        JSONObject uncompacted = new JSONObject(json);
        c.build("obj", uncompacted);
        JSONObject compacted = (JSONObject) c.resolve(new Context(), new HashMap<>(), null);
//        System.out.println(compacted.toString(2));
        compacted.put("@context", uncompacted.get("@context"));
        for (String s : EwJson.getKeys(compacted))
        {
//            System.out.println(s);
//            System.out.println(uncompacted.get(s).toString());
//            System.out.println(compacted.get(s).toString());
            Assert.assertTrue("Compacted Competency is not equivalent to Uncompacted Competency", uncompacted.get(s).toString().contains(compacted.get(s).toString()));
        }
        for (String s : EwJson.getKeys(uncompacted))
        {
//            System.out.println(s);
//            System.out.println(uncompacted.get(s).toString());
//            System.out.println(compacted.get(s).toString());
            Assert.assertTrue("Compacted Competency is not equivalent to Uncompacted Competency", uncompacted.get(s).toString().contains(compacted.get(s).toString()));
        }
    }
    @Test
    public void compactRelation() throws JSONException, IOException
    {
        String json = FileUtils.readFileToString(EwFileSystem.findFile("4bc.relation.json", CruncherJsonLdCompactTest.class, false, false), Charset.forName("UTF-8"));
        CruncherJsonLdCompact c = new CruncherJsonLdCompact();
        JSONObject uncompacted = new JSONObject(json);
        c.build("obj", uncompacted);
        JSONObject compacted = (JSONObject) c.resolve(new Context(), new HashMap<>(), null);
//        System.out.println(compacted);
        compacted.put("@context", uncompacted.get("@context"));
        for (String s : EwJson.getKeys(compacted))
        {
//            System.out.println(s);
//            System.out.println(uncompacted.get(s).toString());
//            System.out.println(compacted.get(s).toString());
            Assert.assertTrue("Compacted Relation is not equivalent to Uncompacted Relation", uncompacted.get(s).toString().contains(compacted.get(s).toString()));
        }
        for (String s : EwJson.getKeys(uncompacted))
        {
//            System.out.println(s);
//            System.out.println(uncompacted.get(s).toString());
//            System.out.println(compacted.get(s).toString());
            Assert.assertTrue("Compacted Relation is not equivalent to Uncompacted Relation", uncompacted.get(s).toString().contains(compacted.get(s).toString()));
        }
    }
}
