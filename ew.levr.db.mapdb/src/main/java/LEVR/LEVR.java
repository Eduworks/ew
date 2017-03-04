package LEVR;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.resolver.Resolvable;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.File;
import com.eduworks.util.io.InMemoryFile;
import java.util.Map;

public class LEVR
{

    public static Object javascriptBinder(Object obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
    {
        Cruncher cru = new com.eduworks.resolver.CruncherJavascriptBinder();
        cru.build("obj", obj);
        return (Object) cru.resolve(c, parameters, dataStreams);
    }

    public static void bindWebService(String endpoint, Resolvable obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
    {
        Cruncher cru = new com.eduworks.resolver.CruncherBindWebService();
        cru.build("endpoint", endpoint);
        cru.build("obj", obj);
        cru.resolve(c, parameters, dataStreams);
    }

    public static class idx
    {

        public static JSONArray idxKeys(String indexDir, String databaseName, String index, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.idx.CruncherIdxKeys();
            cru.build("indexDir", indexDir);
            cru.build("databaseName", databaseName);
            cru.build("index", index);
            return (JSONArray) cru.resolve(c, parameters, dataStreams);
        }

        public static Object idxSet(Object obj, String indexDir, String databaseName, String index, String key, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.idx.CruncherIdxSet();
            cru.build("obj", obj);
            cru.build("indexDir", indexDir);
            cru.build("databaseName", databaseName);
            cru.build("index", index);
            cru.build("key", key);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONArray idxEach(String indexDir, String databaseName, String index, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.idx.CruncherIdxEach();
            cru.build("indexDir", indexDir);
            cru.build("databaseName", databaseName);
            cru.build("index", index);
            return (JSONArray) cru.resolve(c, parameters, dataStreams);
        }

        public static Object idxAdd(Object obj, String indexDir, String databaseName, String index, String key, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.idx.CruncherIdxAdd();
            cru.build("obj", obj);
            cru.build("indexDir", indexDir);
            cru.build("databaseName", databaseName);
            cru.build("index", index);
            cru.build("key", key);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static void idxCompact(String indexDir, String databaseName, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.idx.CruncherIdxCompact();
            cru.build("indexDir", indexDir);
            cru.build("databaseName", databaseName);
            cru.resolve(c, parameters, dataStreams);
        }

        public static JSONArray idxValues(String indexDir, String databaseName, String index, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.idx.CruncherIdxValues();
            cru.build("indexDir", indexDir);
            cru.build("databaseName", databaseName);
            cru.build("index", index);
            return (JSONArray) cru.resolve(c, parameters, dataStreams);
        }

        public static Object idxDelete(String indexDir, String databaseName, String index, String key, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.idx.CruncherIdxDelete();
            cru.build("indexDir", indexDir);
            cru.build("databaseName", databaseName);
            cru.build("index", index);
            cru.build("key", key);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object idxPut(Object obj, String indexDir, String databaseName, String index, String key, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.idx.CruncherIdxPut();
            cru.build("obj", obj);
            cru.build("indexDir", indexDir);
            cru.build("databaseName", databaseName);
            cru.build("index", index);
            cru.build("key", key);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object idxHas(String indexDir, String databaseName, String index, String key, Boolean multi, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.idx.CruncherIdxHas();
            cru.build("indexDir", indexDir);
            cru.build("databaseName", databaseName);
            cru.build("index", index);
            cru.build("key", key);
            cru.build("multi", multi);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object idxGet(String indexDir, String databaseName, String index, String key, Boolean multi, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.idx.CruncherIdxGet();
            cru.build("indexDir", indexDir);
            cru.build("databaseName", databaseName);
            cru.build("index", index);
            cru.build("key", key);
            cru.build("multi", multi);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Number idxId(String indexDir, String databaseName, String index, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.idx.CruncherIdxId();
            cru.build("indexDir", indexDir);
            cru.build("databaseName", databaseName);
            cru.build("index", index);
            return (Number) cru.resolve(c, parameters, dataStreams);
        }
    }
}
