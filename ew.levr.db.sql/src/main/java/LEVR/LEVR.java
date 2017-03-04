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

        public static class sql
        {

            public static JSONArray sql(String obj, String sqlConnectionString, String sqlUsername, String sqlPassword, boolean sqlMysql, boolean sqlSqlServer, boolean sqlJtds, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
            {
                Cruncher cru = new com.eduworks.levr.db.sql.CruncherSql();
                cru.build("obj", obj);
                cru.build("sqlConnectionString", sqlConnectionString);
                cru.build("sqlUsername", sqlUsername);
                cru.build("sqlPassword", sqlPassword);
                cru.build("sqlMysql", sqlMysql);
                cru.build("sqlSqlServer", sqlSqlServer);
                cru.build("sqlJtds", sqlJtds);
                return (JSONArray) cru.resolve(c, parameters, dataStreams);
            }

            public static Number sqlUpdate(String obj, String sqlConnectionString, String sqlUsername, String sqlPassword, boolean sqlMysql, boolean sqlSqlServer, boolean sqlJtds, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
            {
                Cruncher cru = new com.eduworks.levr.db.sql.CruncherSqlUpdate();
                cru.build("obj", obj);
                cru.build("sqlConnectionString", sqlConnectionString);
                cru.build("sqlUsername", sqlUsername);
                cru.build("sqlPassword", sqlPassword);
                cru.build("sqlMysql", sqlMysql);
                cru.build("sqlSqlServer", sqlSqlServer);
                cru.build("sqlJtds", sqlJtds);
                return (Number) cru.resolve(c, parameters, dataStreams);
            }
        }
    }
}
