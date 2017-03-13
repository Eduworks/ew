package com.eduworks.resolver;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.lang.EwList;
import com.eduworks.lang.json.impl.EwJsonObject;
import com.eduworks.lang.util.EwUri;
import com.eduworks.resolver.exception.EditableRuntimeException;

/*
 * High performance zero flexibility implementation of Resolver
 * 
 * Rules of implementation:
 * Crunchers are NOT JSON OBJECTS.
 * Abstract nothing. Make it as lean as possible.
 * Crunchers are immutable. This is important for things like memory allocation and loops.
 * One use case per cruncher. No special considerations, multi-purpose or anything like that.
 * Define cruncher's activity at top of cruncher.
 */
public abstract class Cruncher implements Resolvable
{

    public AtomicLong nanosProcessing = new AtomicLong(0);
    public AtomicLong nanosInside = new AtomicLong(0);
    public AtomicLong executions = new AtomicLong(0);
    public Map<String, Object> data = null;
    public boolean resolverCompatibilityReplaceMode = true;
    public static Logger log = null;
    protected Integer codeLineNumber = null;
    protected Integer codeColNumber = null;
    protected String codeFileName = null;
    protected String codeMethod = null;

    static
    {
        try
        {
            log = Logger.getLogger(Cruncher.class);
        }
        catch (Exception ex)
        {

        }
    }

    protected JSONObject jo(Object... strings) throws JSONException
    {
        JSONObject jo = new EwJsonObject();
        for (int i = 0; i < strings.length; i += 2)
            jo.put(strings[i].toString(), strings[i + 1]);
        return jo;
    }

    @Override
    public void setLineAndColAndSource(Integer line, Integer col, String file, String method)
    {
        codeLineNumber = line;
        codeColNumber = col;
        codeFileName = file;
        codeMethod = method;
    }

    protected boolean isObj(String key)
    {
        return key.equals("obj");
    }

    protected static String encodeValue(String value) throws UnsupportedEncodingException
    {
        return URLEncoder.encode(decodeValue(value), EwUri.UTF_8);
    }

    public static String decodeValue(String value)
    {
        return EwUri.decodeValue(value);
    }

    public EwList<String> getAsStrings(String string, Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
    {
        Object o = get(string, c, parameters, dataStreams);
        if (o == null)
            return null;
        if (o instanceof String)
        {
            EwList<String> l = new EwList<String>();
            l.add(o.toString());
            return l;
        }
        return new EwList<Object>(getAsJsonArray(string, c, parameters, dataStreams)).toStringsEwList();
    }

    public String toJson() throws JSONException
    {
        JSONObject jo = new JSONObject();
        for (String key : data.keySet())
        {
            Object object = data.get(key);
            if (object instanceof Resolvable)
                jo.put(key, ((Resolvable) object).toJson());
            else
                jo.put(key, object);
        }

        return jo.toString();
    }

    public String toOriginalJson() throws JSONException
    {
        JSONObject jo = new JSONObject();
        if (data != null)
            for (String key : data.keySet())
            {
                Object object = data.get(key);
                if (object == null)
                    continue;
                if (object instanceof Resolvable)
                    jo.put(key, new JSONObject(((Resolvable) object).toOriginalJson()));
                else
                    jo.put(key, object);
            }

        String func = getClass().getSimpleName().replace("Resolver", "").replace("Cruncher", "");
        func = Character.toLowerCase(func.charAt(0)) + func.substring(1);

        jo.put("function", func);
        return jo.toString();
    }

    protected boolean has(String string)
    {
        if (data == null)
            return false;
        return data.containsKey(string);
    }

    public Iterator<String> sortedKeys()
    {
        EwList<String> results = new EwList<String>(keySet());
        results.sort(results);
        return results.iterator();
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return null;
    }

    public Set<String> keySet()
    {
        if (data == null)
            return new HashSet<String>();
        return data.keySet();
    }

    protected Object resolveAChild(String key, Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
    {
        if (c.shouldAbort())
            return null;
        Object o = null;
        o = get(key);
        if (o instanceof Cruncher)
        {
            long nanos = System.nanoTime();
            Cruncher cruncher = (Cruncher) o;
            Object result = resolveAChild(c, parameters, dataStreams, key, cruncher);
            long diff = System.nanoTime() - nanos;
            cruncher.nanosProcessing.addAndGet(diff);
            cruncher.nanosInside.addAndGet(diff);
            cruncher.executions.incrementAndGet();
            nanosProcessing.addAndGet(-diff);
            return result;
        }
        if (o instanceof Scripter)
        {
            Scripter scripter = (Scripter) o;
            return resolveAChild(c, parameters, dataStreams, key, scripter);
        }
        return o;
    }

    private static String debugObject(Map<String, Object> os)
    {
        StringBuilder b = new StringBuilder();
        b.append("{");
        boolean first = true;
        for (String key : os.keySet())
        {
            if (!first)
                b.append(",");
            b.append("\n\t\t\t");
            first = false;
            Object o = os.get(key);
            b.append(key);
            b.append(":");
            if (o instanceof Cruncher)
                b.append(o.getClass().getSimpleName());
            else
                b.append(o.toString());
        }
        b.append("\n\t\t}");
        return b.toString();
    }

    public static String debugParameters(Map<String, String[]> os)
    {
        StringBuilder b = new StringBuilder();
        b.append("{");
        boolean first = true;
        for (String key : os.keySet())
        {
            String[] o = os.get(key);
            if (o == null || o.length == 0 || o[0].length() == 0)
                continue;
            if (!first)
                b.append(",");
            b.append("\n\t\t\t");
            first = false;
            b.append(key);
            b.append(":");
            for (int i = 0; i < o.length; i++)
            {
                if (i != 0)
                    b.append("|");
                b.append(o[i]);
            }
        }
        b.append("\n\t\t}");
        return b.toString();
    }

    private Object resolveAChild(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams, String key, Resolvable thing)
            throws JSONException
    {
        if (c.shouldAbort())
            return null;
        try
        {
            if (thing instanceof Cruncher)
                return ((Cruncher) thing).resolve(c, parameters, dataStreams);
            if (thing instanceof Scripter)
                return ((Scripter) thing).resolve(c, parameters, dataStreams);
        }
        catch (Throwable ex)
        {
            ArrayList<StackTraceElement> list = new ArrayList<StackTraceElement>();
            String cruncherName = thing.getClass().getSimpleName().replace("Cruncher", "");
            StackTraceElement el = new StackTraceElement(codeMethod, cruncherName.substring(0, 1).toLowerCase() + cruncherName.substring(1) + " ", codeFileName
                    + ":" + codeLineNumber + ":" + codeColNumber + ")\n\t\tparams: " + debugObject(data) + "\n\t\t@params: " + debugParameters(parameters),
                    c.size());
            for (StackTraceElement l : ex.getStackTrace())
            {
                if (l.getClassName().contains("com.eduworks.cruncher") || l.getClassName().contains("com.eduworks.resolver")
                        || l.getClassName().contains("com.eduworks.levr.servlet"))
                {
                    if (el != null)
                        list.add(el);
                    el = null;
                }
                list.add(l);
            }
            ex.setStackTrace((StackTraceElement[]) list.toArray(new StackTraceElement[0]));
            throw ex;
        }
        throw new RuntimeException("Don't understand how to resolve " + thing);
    }

    private Set<String> getKeys(Cruncher cruncher)
    {
        return data.keySet();
    }

    public Object get(String key)
    {
        if (data == null)
            return null;
        return data.get(key);
    }

    protected boolean hasParam(String key)
    {
        if (data != null)
            if (data.containsKey(key))
                if (data.get(key) instanceof String)
                    if (data.get(key).toString().length() > 1)
                        return ((String) data.get(key)).startsWith("@");
        return false;
    }

    public String optAsString(String key, String defaultx, Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams)
            throws JSONException
    {
        String result = getAsString(key, c, parameters, dataStreams);
        if (result == null)
            return defaultx;
        return result;
    }

    protected Double getAsDouble(String key, Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
    {
        Object obj = get(key, c, parameters, dataStreams);
        return objectToDouble(obj);
    }

    public Double objectToDouble(Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof Integer)
            obj = new Double((Integer) obj);
        if (obj instanceof Long)
            obj = new Double((Long) obj);
        if (!(obj instanceof Double) && !(obj instanceof String))
            throw new EditableRuntimeException("Expected String or Double, got " + obj.getClass().getSimpleName());
        if (obj instanceof Double)
            return (Double) obj;
        else
            try
            {
                return NumberFormat.getNumberInstance(java.util.Locale.US).parse((String) obj).doubleValue();
            }
            catch (ParseException e)
            {
                return Double.parseDouble((String) obj);
            }
    }

    public Double optAsDouble(String key, double defaultx, Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams)
            throws JSONException
    {
        if (has(key))
            try
            {
                return getAsDouble(key, c, parameters, dataStreams);
            }
            catch (NumberFormatException ex)
            {
                return defaultx;
            }
        else
            return defaultx;
    }

    public String getAsString(String key, Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
    {
        Object obj = get(key, c, parameters, dataStreams);
        return objectToString(obj);
    }

    public Integer getAsInteger(String key, Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
    {
        Object obj = get(key, c, parameters, dataStreams);
        if (obj == null)
            return null;
        if (obj instanceof Double)
            if (((Double) obj) == Math.round(((Double) obj)))
                return ((Double) obj).intValue();
            else
                return ((Double) obj).intValue();
        if (!(obj instanceof Integer))
            throw new EditableRuntimeException("Expected Integer, got " + obj.getClass().getSimpleName());
        return (Integer) obj;
    }

    public String objectToString(Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof Integer)
            return Integer.toString((Integer) obj);
        if (obj instanceof Long)
            return Long.toString((Long) obj);
        if (obj instanceof Double)
            if (((Double) obj) == Math.round(((Double) obj)))
                return Integer.toString(((Double) obj).intValue());
            else
                return Double.toString((Double) obj);
        if (!(obj instanceof String))
            throw new EditableRuntimeException("Expected String, got " + obj.getClass().getSimpleName());
        return (String) obj;
    }

    protected boolean optAsBoolean(String key, boolean defaultx, Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams)
            throws JSONException
    {
        Object val = get(key, c, parameters, dataStreams);
        if (val instanceof Boolean)
            return (Boolean)val;
        if (val == null)
            return defaultx;
        return Boolean.parseBoolean(objectToString(val));
    }

    protected int optAsInteger(String key, int defaultx, Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
    {
        String string = getAsString(key, c, parameters, dataStreams);
        if (string == null)
            return defaultx;
        return Integer.parseInt(string);
    }

    protected JSONObject getObjAsJsonObject(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
    {
        String key = "obj";
        return getAsJsonObject(key, c, parameters, dataStreams);
    }

    protected Object getObj(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
    {
        String key = "obj";
        return get(key, c, parameters, dataStreams);
    }

    protected String getObjAsString(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
    {
        String key = "obj";
        return getAsString(key, c, parameters, dataStreams);
    }

    protected JSONArray getObjAsJsonArray(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
    {
        String key = "obj";
        return getAsJsonArray(key, c, parameters, dataStreams);
    }

    public JSONObject getAsJsonObject(String key, Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
    {
        Object obj = get(key, c, parameters, dataStreams);
        if (obj == null)
            return null;
        if (!(obj instanceof JSONObject))
            throw new EditableRuntimeException("Expected JSONObject, got " + obj.getClass().getSimpleName());
        return (JSONObject) obj;
    }

    public JSONArray getAsJsonArray(String key, Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
    {
        Object obj = get(key, c, parameters, dataStreams);
        if (obj == null)
            return null;
        if (obj instanceof EwList)
            obj = new JSONArray((List) obj);
        if (!(obj instanceof JSONArray))
            throw new EditableRuntimeException("Expected JSONArray, got " + obj.getClass().getSimpleName());
        return (JSONArray) obj;
    }

    public static boolean isSetting(String key)
    {
        return key != null && !key.isEmpty() && key.charAt(0) == '_';
    }

    public void build(String key, Object value)
    {
        if (data == null)
            data = new LinkedHashMap<String, Object>();
        data.put(key, value);
    }

    protected Object get(String key, Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
    {
        if (has("_" + key))
            key = "_" + key;
        if (hasParam(key))
            return getParameter(data.get(key).toString().substring(1), parameters);

        Object obj = resolveAChild(key, c, parameters, dataStreams);
        return obj;
    }

    public static Object getParameter(String key, Map<String, String[]> parameters)
    {
        if (parameters == null)
            return null;

        String[] params = parameters.get(key);
        if (params == null)
            return null;
        else if (params.length == 1)
            return params[0];

        return params;
    }

    public String getResolverName()
    {
        return getClass().getSimpleName().replace("Cruncher", "").toLowerCase().substring(0, 1)
                + getClass().getSimpleName().replace("Cruncher", "").substring(1);
    }

    public String[] getResolverNames()
    {
        return new String[]
        {
            "c" + getResolverName(), getResolverName()
        };
    }
}
