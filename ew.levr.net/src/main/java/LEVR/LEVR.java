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

    public static class service
    {

        public static String oauth(String appId, String oauthToken, String oauthSecret, String serverUrl, String consumerKey, String consumerSecret, String federatedIdentity, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.resolver.service.CruncherOauth();
            cru.build("appId", appId);
            cru.build("oauthToken", oauthToken);
            cru.build("oauthSecret", oauthSecret);
            cru.build("serverUrl", serverUrl);
            cru.build("consumerKey", consumerKey);
            cru.build("consumerSecret", consumerSecret);
            cru.build("federatedIdentity", federatedIdentity);
            return (String) cru.resolve(c, parameters, dataStreams);
        }
    }

    public static class io
    {

        public static JSONObject getXmlFromUrlAsJson(String url, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.resolver.io.CruncherGetXmlFromUrlAsJson();
            cru.build("url", url);
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }
    }

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

    public static class net
    {

        public static JSONObject harvestOAI(String baseUrl, String metadataPrefix, Resolvable op, String set, String resumptionToken, String urlPrefix, Boolean memorySaver, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.resolver.net.CruncherHarvestOAI();
            cru.build("baseUrl", baseUrl);
            cru.build("metadataPrefix", metadataPrefix);
            cru.build("op", op);
            cru.build("set", set);
            cru.build("resumptionToken", resumptionToken);
            cru.build("urlPrefix", urlPrefix);
            cru.build("memorySaver", memorySaver);
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONObject harvestRss(String url, Resolvable op, Resolvable feedOp, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.resolver.net.CruncherHarvestRss();
            cru.build("url", url);
            cru.build("op", op);
            cru.build("feedOp", feedOp);
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }

        public static File getFileFromUrl(String url, Number timeout, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.resolver.net.CruncherGetFileFromUrl();
            cru.build("url", url);
            cru.build("timeout", timeout);
            return (File) cru.resolve(c, parameters, dataStreams);
        }

        public static Object httpDelete(String obj, String contentType, String name, String authToken, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.net.CruncherHttpDelete();
            cru.build("obj", obj);
            cru.build("contentType", contentType);
            cru.build("name", name);
            cru.build("authToken", authToken);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object httpPut(String obj, String contentType, String name, String authToken, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.net.CruncherHttpPut();
            cru.build("obj", obj);
            cru.build("contentType", contentType);
            cru.build("name", name);
            cru.build("authToken", authToken);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object httpPost(String obj, String url, String contentType, Boolean multipart, String name, String authToken, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.net.CruncherHttpPost();
            cru.build("obj", obj);
            cru.build("url", url);
            cru.build("contentType", contentType);
            cru.build("multipart", multipart);
            cru.build("name", name);
            cru.build("authToken", authToken);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static String httpStatus(String url, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.net.CruncherHttpStatus();
            cru.build("url", url);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static Object httpGet(String obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.net.CruncherHttpGet();
            cru.build("obj", obj);
            int i = 0;
            for (Object o : any)
                cru.build("" + 'a' + i, o);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }
    }

    public static class xmpp
    {

        public static void xmppListen(String serverHostname, String username, String password, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.xmpp.CruncherXmppListen();
            cru.build("serverHostname", serverHostname);
            cru.build("username", username);
            cru.build("password", password);
            cru.resolve(c, parameters, dataStreams);
        }

        public static void xmppSend(String serverHostname, String username, String password, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.xmpp.CruncherXmppSend();
            cru.build("serverHostname", serverHostname);
            cru.build("username", username);
            cru.build("password", password);
            cru.resolve(c, parameters, dataStreams);
        }
    }
}
