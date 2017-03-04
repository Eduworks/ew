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

    public static class db
    {

        public static class couchdb
        {

            public static JSONArray getKeysInTable(String _serverHostname, Number _serverPort, String _serverLogin, String _serverPassword, String _databasePrefix, String _databaseName, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
            {
                Cruncher cru = new com.eduworks.resolver.db.couchdb.CruncherGetKeysInTable();
                cru.build("_serverHostname", _serverHostname);
                cru.build("_serverPort", _serverPort);
                cru.build("_serverLogin", _serverLogin);
                cru.build("_serverPassword", _serverPassword);
                cru.build("_databasePrefix", _databasePrefix);
                cru.build("_databaseName", _databaseName);
                return (JSONArray) cru.resolve(c, parameters, dataStreams);
            }

            public static void getAttachment(String _serverHostname, Number _serverPort, String _serverLogin, String _serverPassword, String _databasePrefix, String _databaseName, String _id, String name, String filename, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
            {
                Cruncher cru = new com.eduworks.resolver.db.couchdb.CruncherGetAttachment();
                cru.build("_serverHostname", _serverHostname);
                cru.build("_serverPort", _serverPort);
                cru.build("_serverLogin", _serverLogin);
                cru.build("_serverPassword", _serverPassword);
                cru.build("_databasePrefix", _databasePrefix);
                cru.build("_databaseName", _databaseName);
                cru.build("_id", _id);
                cru.build("name", name);
                cru.build("filename", filename);
                cru.resolve(c, parameters, dataStreams);
            }

            public static Object forEachInTable(String _serverHostname, Number _serverPort, String _serverLogin, String _serverPassword, String _databasePrefix, String _databaseName, String paramName, Resolvable op, String aggregation, Boolean memorySaver, Number count, String start, Number startCount, Boolean threaded, Number sequenceI, Number sequenceMod, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
            {
                Cruncher cru = new com.eduworks.resolver.db.couchdb.CruncherForEachInTable();
                cru.build("_serverHostname", _serverHostname);
                cru.build("_serverPort", _serverPort);
                cru.build("_serverLogin", _serverLogin);
                cru.build("_serverPassword", _serverPassword);
                cru.build("_databasePrefix", _databasePrefix);
                cru.build("_databaseName", _databaseName);
                cru.build("paramName", paramName);
                cru.build("op", op);
                cru.build("aggregation", aggregation);
                cru.build("memorySaver", memorySaver);
                cru.build("count", count);
                cru.build("start", start);
                cru.build("startCount", startCount);
                cru.build("threaded", threaded);
                cru.build("sequenceI", sequenceI);
                cru.build("sequenceMod", sequenceMod);
                return (Object) cru.resolve(c, parameters, dataStreams);
            }

            public static void deleteDocument(String _serverHostname, Number _serverPort, String _serverLogin, String _serverPassword, String _databasePrefix, String _databaseName, String _id, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
            {
                Cruncher cru = new com.eduworks.resolver.db.couchdb.CruncherDeleteDocument();
                cru.build("_serverHostname", _serverHostname);
                cru.build("_serverPort", _serverPort);
                cru.build("_serverLogin", _serverLogin);
                cru.build("_serverPassword", _serverPassword);
                cru.build("_databasePrefix", _databasePrefix);
                cru.build("_databaseName", _databaseName);
                cru.build("_id", _id);
                cru.resolve(c, parameters, dataStreams);
            }

            public static void updateDocument(String _serverHostname, Number _serverPort, String _serverLogin, String _serverPassword, String _databasePrefix, String _databaseName, String _id, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
            {
                Cruncher cru = new com.eduworks.resolver.db.couchdb.CruncherUpdateDocument();
                cru.build("_serverHostname", _serverHostname);
                cru.build("_serverPort", _serverPort);
                cru.build("_serverLogin", _serverLogin);
                cru.build("_serverPassword", _serverPassword);
                cru.build("_databasePrefix", _databasePrefix);
                cru.build("_databaseName", _databaseName);
                cru.build("_id", _id);
                int i = 0;
                for (Object o : any)
                    cru.build("" + 'a' + i, o);
                cru.resolve(c, parameters, dataStreams);
            }

            public static void saveDocument(String _serverHostname, Number _serverPort, String _serverLogin, String _serverPassword, String _databasePrefix, String _databaseName, String _id, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
            {
                Cruncher cru = new com.eduworks.resolver.db.couchdb.CruncherSaveDocument();
                cru.build("_serverHostname", _serverHostname);
                cru.build("_serverPort", _serverPort);
                cru.build("_serverLogin", _serverLogin);
                cru.build("_serverPassword", _serverPassword);
                cru.build("_databasePrefix", _databasePrefix);
                cru.build("_databaseName", _databaseName);
                cru.build("_id", _id);
                int i = 0;
                for (Object o : any)
                    cru.build("" + 'a' + i, o);
                cru.resolve(c, parameters, dataStreams);
            }

            public static void putAttachment(String _serverHostname, Number _serverPort, String _serverLogin, String _serverPassword, String _databasePrefix, String _databaseName, String _id, String name, String dataStreamId, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
            {
                Cruncher cru = new com.eduworks.resolver.db.couchdb.CruncherPutAttachment();
                cru.build("_serverHostname", _serverHostname);
                cru.build("_serverPort", _serverPort);
                cru.build("_serverLogin", _serverLogin);
                cru.build("_serverPassword", _serverPassword);
                cru.build("_databasePrefix", _databasePrefix);
                cru.build("_databaseName", _databaseName);
                cru.build("_id", _id);
                cru.build("name", name);
                cru.build("dataStreamId", dataStreamId);
                cru.resolve(c, parameters, dataStreams);
            }

            public static JSONObject getFromDocument(String _serverHostname, Number _serverPort, String _serverLogin, String _serverPassword, String _databasePrefix, String _databaseName, String _id, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
            {
                Cruncher cru = new com.eduworks.resolver.db.couchdb.CruncherGetFromDocument();
                cru.build("_serverHostname", _serverHostname);
                cru.build("_serverPort", _serverPort);
                cru.build("_serverLogin", _serverLogin);
                cru.build("_serverPassword", _serverPassword);
                cru.build("_databasePrefix", _databasePrefix);
                cru.build("_databaseName", _databaseName);
                cru.build("_id", _id);
                int i = 0;
                for (Object o : any)
                    cru.build("" + 'a' + i, o);
                return (JSONObject) cru.resolve(c, parameters, dataStreams);
            }
        }
    }
}
