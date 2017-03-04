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

    public static class scripter
    {

        public static String r(String obj, String path, String returns, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.scripter.CruncherR();
            cru.build("obj", obj);
            cru.build("path", path);
            cru.build("returns", returns);
            int i = 0;
            for (Object o : any)
                cru.build("" + 'a' + i, o);
            return (String) cru.resolve(c, parameters, dataStreams);
        }
    }
}
