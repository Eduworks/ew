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

    public static class game
    {
    }

    public static class cache
    {

        public static Object variableGet(String key, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.cache.CruncherVariableGet();
            cru.build("key", key);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object cache(Object obj, String name, Boolean remove, Boolean global, Boolean justLock, Boolean removeAllGlobal, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.cache.CruncherCache();
            cru.build("obj", obj);
            cru.build("name", name);
            cru.build("remove", remove);
            cru.build("global", global);
            cru.build("justLock", justLock);
            cru.build("removeAllGlobal", removeAllGlobal);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object cacheGet(String obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.cache.CruncherCacheGet();
            cru.build("obj", obj);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object variableSet(Object obj, String key, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.cache.CruncherVariableSet();
            cru.build("obj", obj);
            cru.build("key", key);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object getCallCache(String obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.cache.CruncherGetCallCache();
            cru.build("obj", obj);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }
    }

    public static class string
    {

        public static String hexToString(String obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.string.CruncherHexToString();
            cru.build("obj", obj);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static String randomString(Number length, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.string.CruncherRandomString();
            cru.build("length", length);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static String toLower(String obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.string.CruncherToLower();
            cru.build("obj", obj);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static int wrapQuotes(String str, String substr, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.string.CruncherWrapQuotes();
            cru.build("str", str);
            cru.build("substr", substr);
            return (int) cru.resolve(c, parameters, dataStreams);
        }

        public static String urlDecode(String obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.string.CruncherUrlDecode();
            cru.build("obj", obj);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static String urlEncode(String obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.string.CruncherUrlEncode();
            cru.build("obj", obj);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONArray removeDuplicates(JSONArray obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.string.CruncherRemoveDuplicates();
            cru.build("obj", obj);
            return (JSONArray) cru.resolve(c, parameters, dataStreams);
        }

        public static String randomBase64(Number length, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.string.CruncherRandomBase64();
            cru.build("length", length);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static String stringToHex(String obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.string.CruncherStringToHex();
            cru.build("obj", obj);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONArray split(String obj, String split, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.string.CruncherSplit();
            cru.build("obj", obj);
            cru.build("split", split);
            return (JSONArray) cru.resolve(c, parameters, dataStreams);
        }

        public static String toUpper(String obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.string.CruncherToUpper();
            cru.build("obj", obj);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static boolean endsWith(String string1, String string2, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.string.CruncherEndsWith();
            cru.build("string1", string1);
            cru.build("string2", string2);
            return (boolean) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONArray join(String obj, String divider, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.string.CruncherJoin();
            cru.build("obj", obj);
            cru.build("divider", divider);
            return (JSONArray) cru.resolve(c, parameters, dataStreams);
        }

        public static int indexOf(String str, String substr, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.string.CruncherIndexOf();
            cru.build("str", str);
            cru.build("substr", substr);
            return (int) cru.resolve(c, parameters, dataStreams);
        }

        public static Number hash(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.resolver.string.CruncherHash();
            int i = 0;
            for (Object o : any)
                cru.build("" + 'a' + i, o);
            return (Number) cru.resolve(c, parameters, dataStreams);
        }

        public static String byteAsString(String obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.string.CruncherByteAsString();
            cru.build("obj", obj);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static String padLeft(String obj, Number pad, String with, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.string.CruncherPadLeft();
            cru.build("obj", obj);
            cru.build("pad", pad);
            cru.build("with", with);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static boolean startsWith(String string1, String string2, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.string.CruncherStartsWith();
            cru.build("string1", string1);
            cru.build("string2", string2);
            return (boolean) cru.resolve(c, parameters, dataStreams);
        }
    }

    public static class io
    {

        public static InMemoryFile displayText(String obj, String name, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.io.CruncherDisplayText();
            cru.build("obj", obj);
            cru.build("name", name);
            return (InMemoryFile) cru.resolve(c, parameters, dataStreams);
        }

        public static String displayJsonML(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.resolver.io.CruncherDisplayJsonML();
            int i = 0;
            for (Object o : any)
                cru.build("" + 'a' + i, o);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static String displayXml(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.resolver.io.CruncherDisplayXml();
            int i = 0;
            for (Object o : any)
                cru.build("" + 'a' + i, o);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static InMemoryFile displayXML(String obj, String name, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.io.CruncherDisplayXML();
            cru.build("obj", obj);
            cru.build("name", name);
            return (InMemoryFile) cru.resolve(c, parameters, dataStreams);
        }

        public static void displayCsv(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.resolver.io.CruncherDisplayCsv();
            cru.resolve(c, parameters, dataStreams);
        }

        public static String mimeType(Object file, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.resolver.io.CruncherMimeType();
            cru.build("file", file);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static void setHeader(String obj, String name, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.io.CruncherSetHeader();
            cru.build("obj", obj);
            cru.build("name", name);
            cru.resolve(c, parameters, dataStreams);
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

    public static class parse
    {

        public static String parseUri(String obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.parse.CruncherParseUri();
            cru.build("obj", obj);
            return (String) cru.resolve(c, parameters, dataStreams);
        }
    }

    public static class manip
    {

        public static Object reduce(Object obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.resolver.manip.CruncherReduce();
            cru.build("obj", obj);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONArray removeByParam(JSONArray obj, String param, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.manip.CruncherRemoveByParam();
            cru.build("obj", obj);
            cru.build("param", param);
            return (JSONArray) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONObject objectPivot(JSONObject obj, String key, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.manip.CruncherObjectPivot();
            cru.build("obj", obj);
            cru.build("key", key);
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONObject put(JSONObject obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.manip.CruncherPut();
            cru.build("obj", obj);
            int i = 0;
            for (Object o : any)
                cru.build("" + 'a' + i, o);
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONObject pivotOrdered(JSONObject obj, Boolean removeZeros, Number limit, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.manip.CruncherPivotOrdered();
            cru.build("obj", obj);
            cru.build("removeZeros", removeZeros);
            cru.build("limit", limit);
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }

        public static void leaves(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.manip.CruncherLeaves();
            cru.resolve(c, parameters, dataStreams);
        }

        public static JSONArray getKeysByValuesCompareByToString(JSONObject obj, String matches, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.manip.CruncherGetKeysByValuesCompareByToString();
            cru.build("obj", obj);
            cru.build("matches", matches);
            return (JSONArray) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONObject extractObjectsFromArray(JSONArray obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.manip.CruncherExtractObjectsFromArray();
            cru.build("obj", obj);
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONArray merge(JSONArray obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.manip.CruncherMerge();
            cru.build("obj", obj);
            int i = 0;
            for (Object o : any)
                cru.build("" + 'a' + i, o);
            return (JSONArray) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONArray difference(JSONArray diff, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.manip.CruncherDifference();
            cru.build("diff", diff);
            return (JSONArray) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONObject pivot(JSONObject obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.manip.CruncherPivot();
            cru.build("obj", obj);
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONArray sortBy(JSONArray obj, JSONArray by, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.manip.CruncherSortBy();
            cru.build("obj", obj);
            cru.build("by", by);
            return (JSONArray) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONArray subArray(JSONArray obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.manip.CruncherSubArray();
            cru.build("obj", obj);
            int i = 0;
            for (Object o : any)
                cru.build("" + 'a' + i, o);
            return (JSONArray) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONObject collapse(Object obj, String keyKey, String valueKey, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.manip.CruncherCollapse();
            cru.build("obj", obj);
            cru.build("keyKey", keyKey);
            cru.build("valueKey", valueKey);
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONArray append(JSONArray obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.manip.CruncherAppend();
            cru.build("obj", obj);
            int i = 0;
            for (Object o : any)
                cru.build("" + 'a' + i, o);
            return (JSONArray) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONObject group(JSONArray obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.manip.CruncherGroup();
            cru.build("obj", obj);
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }
    }

    public static class refl
    {

        public static void headers(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.refl.CruncherHeaders();
            cru.resolve(c, parameters, dataStreams);
        }

        public static JSONObject reflectionFiles(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.refl.CruncherReflectionFiles();
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }

        public static Object interpretCode(JSONObject obj, JSONObject params, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.refl.CruncherInterpretCode();
            cru.build("obj", obj);
            cru.build("params", params);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONObject reflectionManifest(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.refl.CruncherReflectionManifest();
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }

        public static void reflectionCommit(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.refl.CruncherReflectionCommit();
            cru.resolve(c, parameters, dataStreams);
        }

        public static Object execute(String service, Object obj, JSONObject paramObj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.refl.CruncherExecute();
            cru.build("service", service);
            cru.build("obj", obj);
            cru.build("paramObj", paramObj);
            int i = 0;
            for (Object o : any)
                cru.build("" + 'a' + i, o);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONObject reflectionPerformance(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.refl.CruncherReflectionPerformance();
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONObject serializeCode(Resolvable obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.refl.CruncherSerializeCode();
            cru.build("obj", obj);
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }
    }

    public static class uuid
    {

        public static String generateUUID(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.uuid.CruncherGenerateUUID();
            return (String) cru.resolve(c, parameters, dataStreams);
        }
    }

    public static class security
    {

        public static String encrypt(String key, String value, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.resolver.security.CruncherEncrypt();
            cru.build("key", key);
            cru.build("value", value);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static String decrypt(String key, String value, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.resolver.security.CruncherDecrypt();
            cru.build("key", key);
            cru.build("value", value);
            return (String) cru.resolve(c, parameters, dataStreams);
        }
    }

    public static class file
    {

        public static Map<String, String> objectToFiles(JSONObject obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.file.CruncherObjectToFiles();
            cru.build("obj", obj);
            return (Map<String, String>) cru.resolve(c, parameters, dataStreams);
        }

        public static File stringToFile(JSONObject obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.file.CruncherStringToFile();
            cru.build("obj", obj);
            return (File) cru.resolve(c, parameters, dataStreams);
        }

        public static String[] runProcess(String[] args, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.file.CruncherRunProcess();
            cru.build("args", args);
            return (String[]) cru.resolve(c, parameters, dataStreams);
        }

        public static String fileHash(InMemoryFile obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.file.CruncherFileHash();
            cru.build("obj", obj);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static String fileToString(InMemoryFile obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.file.CruncherFileToString();
            cru.build("obj", obj);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static InMemoryFile fileExists(String path, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.file.CruncherFileExists();
            cru.build("path", path);
            return (InMemoryFile) cru.resolve(c, parameters, dataStreams);
        }

        public static InMemoryFile setFilename(InMemoryFile obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.file.CruncherSetFilename();
            cru.build("obj", obj);
            return (InMemoryFile) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONArray getTableFromCsv(String obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.file.CruncherGetTableFromCsv();
            cru.build("obj", obj);
            return (JSONArray) cru.resolve(c, parameters, dataStreams);
        }

        public static String filename(InMemoryFile obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.file.CruncherFilename();
            cru.build("obj", obj);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static InMemoryFile fileDelete(String path, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.file.CruncherFileDelete();
            cru.build("path", path);
            return (InMemoryFile) cru.resolve(c, parameters, dataStreams);
        }

        public static String filepath(InMemoryFile obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.file.CruncherFilepath();
            cru.build("obj", obj);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONObject fileToBase64(Object obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.file.CruncherFileToBase64();
            cru.build("obj", obj);
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }

        public static String fileSize(InMemoryFile obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.file.CruncherFileSize();
            cru.build("obj", obj);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static InMemoryFile filesToZip(JSONObject obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.file.CruncherFilesToZip();
            cru.build("obj", obj);
            return (InMemoryFile) cru.resolve(c, parameters, dataStreams);
        }

        public static InMemoryFile setMimetype(InMemoryFile obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.file.CruncherSetMimetype();
            cru.build("obj", obj);
            return (InMemoryFile) cru.resolve(c, parameters, dataStreams);
        }

        public static Object base64ToFile(JSONObject obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.file.CruncherBase64ToFile();
            cru.build("obj", obj);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static InMemoryFile createDirectory(String path, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.file.CruncherCreateDirectory();
            cru.build("path", path);
            return (InMemoryFile) cru.resolve(c, parameters, dataStreams);
        }

        public static InMemoryFile fileList(String path, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.file.CruncherFileList();
            cru.build("path", path);
            return (InMemoryFile) cru.resolve(c, parameters, dataStreams);
        }

        public static Object deserialize(Object obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.file.CruncherDeserialize();
            cru.build("obj", obj);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }
    }

    public static class service
    {

        public static JSONObject sendEmail(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.resolver.service.CruncherSendEmail();
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }
    }

    public static class xml
    {

        public static String prettyPrintHtml(String obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.xml.CruncherPrettyPrintHtml();
            cru.build("obj", obj);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static String prettyPrintXml(String obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.xml.CruncherPrettyPrintXml();
            cru.build("obj", obj);
            return (String) cru.resolve(c, parameters, dataStreams);
        }
    }

    public static class time
    {
    }

    public static class math
    {

        public static Object add(Boolean _string, Boolean _fancyNumber, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.math.CruncherAdd();
            cru.build("_string", _string);
            cru.build("_fancyNumber", _fancyNumber);
            int i = 0;
            for (Object o : any)
                cru.build("" + 'a' + i, o);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Number average(JSONArray obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.math.CruncherAverage();
            cru.build("obj", obj);
            return (Number) cru.resolve(c, parameters, dataStreams);
        }

        public static Number mod(Number obj, Number operator, Number operand, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.math.CruncherMod();
            cru.build("obj", obj);
            cru.build("operator", operator);
            cru.build("operand", operand);
            return (Number) cru.resolve(c, parameters, dataStreams);
        }

        public static Number max(JSONArray obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.math.CruncherMax();
            cru.build("obj", obj);
            return (Number) cru.resolve(c, parameters, dataStreams);
        }

        public static Number subtract(Number operator, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.resolver.math.CruncherSubtract();
            cru.build("operator", operator);
            int i = 0;
            for (Object o : any)
                cru.build("" + 'a' + i, o);
            return (Number) cru.resolve(c, parameters, dataStreams);
        }

        public static Number numberThresholdAccumulator(JSONObject input, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.resolver.math.CruncherNumberThresholdAccumulator();
            cru.build("input", input);
            int i = 0;
            for (Object o : any)
                cru.build("" + 'a' + i, o);
            return (Number) cru.resolve(c, parameters, dataStreams);
        }

        public static Number sum(JSONArray obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.math.CruncherSum();
            cru.build("obj", obj);
            return (Number) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONArray reverse(JSONArray obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.math.CruncherReverse();
            cru.build("obj", obj);
            return (JSONArray) cru.resolve(c, parameters, dataStreams);
        }

        public static Number min(JSONArray obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.math.CruncherMin();
            cru.build("obj", obj);
            return (Number) cru.resolve(c, parameters, dataStreams);
        }

        public static Object top(Object obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.math.CruncherTop();
            cru.build("obj", obj);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Number rootMeanSquared(JSONArray obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.math.CruncherRootMeanSquared();
            cru.build("obj", obj);
            return (Number) cru.resolve(c, parameters, dataStreams);
        }

        public static Object floor(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.resolver.math.CruncherFloor();
            int i = 0;
            for (Object o : any)
                cru.build("" + 'a' + i, o);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Number multiply(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.resolver.math.CruncherMultiply();
            int i = 0;
            for (Object o : any)
                cru.build("" + 'a' + i, o);
            return (Number) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONObject numberObject(Number min, Number max, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.math.CruncherNumberObject();
            cru.build("min", min);
            cru.build("max", max);
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }
    }

    public static class lang
    {

        public static JSONArray valueSet(JSONObject obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherValueSet();
            cru.build("obj", obj);
            return (JSONArray) cru.resolve(c, parameters, dataStreams);
        }

        public static Object abortable(Object obj, String contentType, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherAbortable();
            cru.build("obj", obj);
            cru.build("contentType", contentType);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object toParameter(Object obj, String paramName, Resolvable op, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherToParameter();
            cru.build("obj", obj);
            cru.build("paramName", paramName);
            cru.build("op", op);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static void fromDotAndArrayNotationObject(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherFromDotAndArrayNotationObject();
            cru.resolve(c, parameters, dataStreams);
        }

        public static JSONObject decode(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.resolver.lang.CruncherDecode();
            int i = 0;
            for (Object o : any)
                cru.build("" + 'a' + i, o);
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }

        public static String error(String msg, Number code, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.resolver.lang.CruncherError();
            cru.build("msg", msg);
            cru.build("code", code);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONObject paramsToObject(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherParamsToObject();
            int i = 0;
            for (Object o : any)
                cru.build("" + 'a' + i, o);
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }

        public static Integer toInteger(Object obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherToInteger();
            cru.build("obj", obj);
            return (Integer) cru.resolve(c, parameters, dataStreams);
        }

        public static Double toDouble(Object obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherToDouble();
            cru.build("obj", obj);
            return (Double) cru.resolve(c, parameters, dataStreams);
        }

        public static Boolean toBoolean(String obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherToBoolean();
            cru.build("obj", obj);
            return (Boolean) cru.resolve(c, parameters, dataStreams);
        }

        public static void toDotNotationObject(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherToDotNotationObject();
            cru.resolve(c, parameters, dataStreams);
        }

        public static JSONObject toObject(String obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherToObject();
            cru.build("obj", obj);
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }

        public static Boolean has(Object obj, Object has, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherHas();
            cru.build("obj", obj);
            cru.build("has", has);
            return (Boolean) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONArray keySet(JSONObject obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherKeySet();
            cru.build("obj", obj);
            return (JSONArray) cru.resolve(c, parameters, dataStreams);
        }

        public static void getParam(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherGetParam();
            cru.resolve(c, parameters, dataStreams);
        }

        public static Object debug(Object obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherDebug();
            cru.build("obj", obj);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static void fromDotNotationObject(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherFromDotNotationObject();
            cru.resolve(c, parameters, dataStreams);
        }

        public static Long toLong(Object obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherToLong();
            cru.build("obj", obj);
            return (Long) cru.resolve(c, parameters, dataStreams);
        }

        public static Number count(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherCount();
            int i = 0;
            for (Object o : any)
                cru.build("" + 'a' + i, o);
            return (Number) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONObject thread(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherThread();
            int i = 0;
            for (Object o : any)
                cru.build("" + 'a' + i, o);
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }

        public static void toBracketNotationObject(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherToBracketNotationObject();
            cru.resolve(c, parameters, dataStreams);
        }

        public static Object call(Resolvable obj, Boolean background, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams, Object... any) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherCall();
            cru.build("obj", obj);
            cru.build("background", background);
            int i = 0;
            for (Object o : any)
                cru.build("" + 'a' + i, o);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static void fromBracketNotationObject(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherFromBracketNotationObject();
            cru.resolve(c, parameters, dataStreams);
        }

        public static Object opt(Object obj, Object opt, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherOpt();
            cru.build("obj", obj);
            cru.build("opt", opt);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static void toDotAndArrayNotationObject(final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherToDotAndArrayNotationObject();
            cru.resolve(c, parameters, dataStreams);
        }

        public static Object toDatastream(Object obj, String paramName, Resolvable op, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherToDatastream();
            cru.build("obj", obj);
            cru.build("paramName", paramName);
            cru.build("op", op);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static String toString(Object obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherToString();
            cru.build("obj", obj);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static Object getByParam(JSONObject obj, String param, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.lang.CruncherGetByParam();
            cru.build("obj", obj);
            cru.build("param", param);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }
    }

    public static class ws
    {

        public static Object wsBroadcast(Object obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ws.CruncherWsBroadcast();
            cru.build("obj", obj);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object wsEmit(String to, Object obj, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ws.CruncherWsEmit();
            cru.build("to", to);
            cru.build("obj", obj);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }
    }
}
