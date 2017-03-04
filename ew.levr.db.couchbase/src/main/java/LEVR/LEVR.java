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

    public static class db
    {

        public static class couchbase
        {

            public static JSONObject forEachInTable(String _serverUri, String _serverUsername, String _serverPassword, String _databaseName, Resolvable op, String paramName, String prevParamName, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
            {
                Cruncher cru = new com.eduworks.cruncher.db.couchbase.CruncherForEachInTable();
                cru.build("_serverUri", _serverUri);
                cru.build("_serverUsername", _serverUsername);
                cru.build("_serverPassword", _serverPassword);
                cru.build("_databaseName", _databaseName);
                cru.build("op", op);
                cru.build("paramName", paramName);
                cru.build("prevParamName", prevParamName);
                return (JSONObject) cru.resolve(c, parameters, dataStreams);
            }

            public static JSONObject getDocument(String _serverUri, String _serverUsername, String _serverPassword, String _databaseName, String _id, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
            {
                Cruncher cru = new com.eduworks.cruncher.db.couchbase.CruncherGetDocument();
                cru.build("_serverUri", _serverUri);
                cru.build("_serverUsername", _serverUsername);
                cru.build("_serverPassword", _serverPassword);
                cru.build("_databaseName", _databaseName);
                cru.build("_id", _id);
                return (JSONObject) cru.resolve(c, parameters, dataStreams);
            }

            public static JSONObject updateDocument(String _serverUri, String _serverUsername, String _serverPassword, String _databaseName, String _id, JSONObject obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
            {
                Cruncher cru = new com.eduworks.cruncher.db.couchbase.CruncherUpdateDocument();
                cru.build("_serverUri", _serverUri);
                cru.build("_serverUsername", _serverUsername);
                cru.build("_serverPassword", _serverPassword);
                cru.build("_databaseName", _databaseName);
                cru.build("_id", _id);
                cru.build("obj", obj);
                int i = 0;
                for (Object o : any)
                    cru.build("" + 'a' + i, o);
                return (JSONObject) cru.resolve(c, parameters, dataStreams);
            }
        }
    }
}
