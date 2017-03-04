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

    public static class security
    {

        public static String rsaPpkToPk(String obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.security.CruncherRsaPpkToPk();
            cru.build("obj", obj);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static String pbkdf2(String obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.security.CruncherPbkdf2();
            cru.build("obj", obj);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static String rsaDecrypt(String obj, String ppk, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.security.CruncherRsaDecrypt();
            cru.build("obj", obj);
            cru.build("ppk", ppk);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static String generateRsaPrivateKey(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.security.CruncherGenerateRsaPrivateKey();
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static String sha1(String obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.security.CruncherSha1();
            cru.build("obj", obj);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static String aesEncrypt(String obj, String iv, String secret, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.security.CruncherAesEncrypt();
            cru.build("obj", obj);
            cru.build("iv", iv);
            cru.build("secret", secret);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static String rsaGenerate(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.security.CruncherRsaGenerate();
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static String rsaEncrypt(String obj, String ppk, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.security.CruncherRsaEncrypt();
            cru.build("obj", obj);
            cru.build("ppk", ppk);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static Boolean bCryptCompareHash(String password, String passwordHash, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.security.CruncherBCryptCompareHash();
            cru.build("password", password);
            cru.build("passwordHash", passwordHash);
            return (Boolean) cru.resolve(c, parameters, dataStreams);
        }

        public static String aesDecrypt(String obj, String iv, String secret, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.security.CruncherAesDecrypt();
            cru.build("obj", obj);
            cru.build("iv", iv);
            cru.build("secret", secret);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static String bCryptHash(String password, int rounds, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.security.CruncherBCryptHash();
            cru.build("password", password);
            cru.build("rounds", rounds);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static Boolean rsaVerify(String obj, String pk, String against, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.security.CruncherRsaVerify();
            cru.build("obj", obj);
            cru.build("pk", pk);
            cru.build("against", against);
            return (Boolean) cru.resolve(c, parameters, dataStreams);
        }

        public static void ntlmGetUsername(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.security.CruncherNtlmGetUsername();
            cru.resolve(c, parameters, dataStreams);
        }

        public static String rsaSign(String obj, String ppk, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.security.CruncherRsaSign();
            cru.build("obj", obj);
            cru.build("ppk", ppk);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static String md5(String obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.security.CruncherMd5();
            cru.build("obj", obj);
            return (String) cru.resolve(c, parameters, dataStreams);
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
}
