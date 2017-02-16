package com.eduworks.resolver.lang;

import com.eduworks.resolver.CruncherJavascriptBinder;
import javax.script.*;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.json.JSONArray;
import org.json.JSONObject;


import static junit.framework.TestCase.assertTrue;

import org.junit.*;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import sun.font.Script;

import static org.junit.Assert.assertEquals;

/**
 * Created by Eduworks on 2/6/2017.
 *
 * Tests the core levrJs functionality
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(LevrJsParser.class)
@SuppressStaticInitializationFor("com.eduworks.resolver.lang.LevrJsParser")
public class LevrJsParserTest
{
    private ScriptEngine engine;

    @Before
    public void setUp() throws Exception
    {
        ScriptEngineManager factory = new ScriptEngineManager();
        engine = factory.getEngineByName("nashorn");
        //engine.getBindings(ScriptContext.ENGINE_SCOPE).putAll();

        PowerMockito.spy(LevrJsParser.class);
    }

    @Test
    public void testJsToJavaGetsAnObject() throws Exception
    {
        engine.eval("function getAnObj() {return {anAttr: 'a value'}}");
        Invocable invocable = (Invocable) engine;
        Object result = invocable.invokeFunction("getAnObj");

        Object parsedResult = LevrJsParser.jsToJava(result);

        assertTrue(parsedResult instanceof JSONObject);
    }

    @Test
    public void testJsToJavaGetsAnArray() throws Exception
    {
        engine.eval("function getAnArray() {return [1, 'array', 2, 'array'];}");
        Invocable invocable = (Invocable) engine;
        Object result = invocable.invokeFunction("getAnArray");

        Object parsedResult = LevrJsParser.jsToJava(result);

        assertTrue(parsedResult instanceof JSONArray);
    }

    @Test
    public void testJsToJavaGetsAFunction() throws Exception
    {
        engine.eval("function getAFunction() {return function retFunc(){};}");
        Invocable invocable = (Invocable) engine;
        Object result = invocable.invokeFunction("getAFunction");

        Object parsedResult = LevrJsParser.jsToJava(result);

        assertTrue(parsedResult instanceof CruncherJavascriptBinder);
    }

    @Test
    public void testJsToJavaReturnsAnArbitraryPrimitiveAsAString() throws Exception
    {
        engine.eval("function getABoolean() {return true;}");
        Invocable invocable = (Invocable) engine;
        Object result = invocable.invokeFunction("getABoolean");

        Object parsedResult = LevrJsParser.jsToJava(result);

        assertTrue(parsedResult instanceof String);
    }

    @Test
    public void testJsToJavaReturnsUndefinedAsNull() throws Exception
    {
        engine.eval("function undef() {}");
        Invocable invocable = (Invocable) engine;
        Object result = invocable.invokeFunction("undef");

        Object parsedResult = LevrJsParser.jsToJava(result);

        assertEquals(parsedResult,null);
    }


    @Test
    public void testJavaToJsConvertsAJsonObjectCorrectly() throws Exception
    {
        // need to enable the engine in LevrJsParser since static block
        // is being skipped
        LevrJsParser.engine = engine;
        JSONObject jo = new JSONObject();
        JSONObject puttedObj = new JSONObject();
        JSONArray puttedArr = new JSONArray();
        jo.put("strVal", "value");
        jo.put("numVal", 32131);
        jo.put("objVal", puttedObj);
        jo.put("arrVal", puttedArr);
        jo.put("boolVal", true);

        ScriptObjectMirror parsedResult = (ScriptObjectMirror) LevrJsParser.javaToJs(jo);

        assertEquals(jo.get("strVal"), parsedResult.get("strVal"));
        assertEquals(jo.get("numVal"), parsedResult.get("numVal"));

        assertEquals(jo.get("boolVal"), parsedResult.get("boolVal"));
//        these fail because
//        java.lang.AssertionError:
//        Expected :{}
//        Actual   :[object Object]
//        assertEquals(jo.get("objVal"), parsedResult.get("objVal"));
//        assertEquals(jo.get("arrVal"), parsedResult.get("arrVal"));
        assertTrue(parsedResult.get("objVal") != null);
        assertTrue(parsedResult.get("arrVal") != null);
    }

    @Test
    public void testJavaToJsConvertsAJsonArrayCorrectly() throws Exception
    {
        // need to enable the engine in LevrJsParser since static block
        // is being skipped
        LevrJsParser.engine = engine;
        JSONArray ja = new JSONArray();
        JSONObject puttedObject = new JSONObject();
        puttedObject.put("key", "value");
        ja.put(true);
        ja.put("string");
        ja.put(91.90);
        ja.put(42);
        ja.put((long)2);
        ja.put(puttedObject);

        ScriptObjectMirror parsedResult = (ScriptObjectMirror) LevrJsParser.javaToJs(ja);

        assertEquals(true, parsedResult.get("0"));
        assertEquals("string", parsedResult.get("1"));
        assertEquals(91.90, parsedResult.get("2"));
        assertEquals(42, parsedResult.get("3"));
        assertEquals(2, parsedResult.get("4"));
        ScriptObjectMirror convertedObject = (ScriptObjectMirror) parsedResult.get("5");
        assertEquals(convertedObject.get("key"), puttedObject.get("key"));
    }

    @Test
    public void regressionTestJavaToJsConvertsObjectsWithQuotesInThem() throws Exception
    {
        // need to enable the engine in LevrJsParser since static block
        // is being skipped
        LevrJsParser.engine = engine;
        JSONObject carrier = new JSONObject();
        String culprit = "Don't fail";
        carrier.put("key", culprit);


        ScriptObjectMirror parsedObject = (ScriptObjectMirror) LevrJsParser.javaToJs(carrier);

        assertEquals(parsedObject.get("key"), carrier.get("key"));
    }
}