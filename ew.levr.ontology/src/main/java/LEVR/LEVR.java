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

    public static class ontology
    {

        public static Object ontologyReadClassProperties(String ontologyId, String directory, String classId, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyReadClassProperties();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            cru.build("classId", classId);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object ontologyReadClass(String ontologyId, String directory, String classId, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyReadClass();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            cru.build("classId", classId);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object ontologyDeleteInstance(String ontologyId, String directory, String instanceId, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyDeleteInstance();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            cru.build("instanceId", instanceId);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object ontologyReadClassInstances(String ontologyId, String directory, String classId, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyReadClassInstances();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            cru.build("classId", classId);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object ontologyUpdateInstance(String ontologyId, String directory, String instanceId, Object vals, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyUpdateInstance();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            cru.build("instanceId", instanceId);
            cru.build("vals", vals);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object ontologyCreate(String ontologyId, String directory, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyCreate();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONObject ontologyCreateObjectProperty(String ontologyId, String directory, String propertyId, Object vals, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyCreateObjectProperty();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            cru.build("propertyId", propertyId);
            cru.build("vals", vals);
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }

        public static Object ontologyListOntologies(String ontologyId, String directory, String importId, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyListOntologies();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            cru.build("importId", importId);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONObject ontologyCreateDataProperty(String ontologyId, String directory, String propertyId, Object vals, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyCreateDataProperty();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            cru.build("propertyId", propertyId);
            cru.build("vals", vals);
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }

        public static Object ontologyTDBExport(String identifier, String extension, String tdbDirectory, String exportDirectory, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyTDBExport();
            cru.build("identifier", identifier);
            cru.build("extension", extension);
            cru.build("tdbDirectory", tdbDirectory);
            cru.build("exportDirectory", exportDirectory);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object ontologyUpdateProperty(String ontologyId, String directory, String propertyId, Object vals, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyUpdateProperty();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            cru.build("propertyId", propertyId);
            cru.build("vals", vals);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object ontologyGetFullIri(String ontologyId, String directory, String classId, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyGetFullIri();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            cru.build("classId", classId);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object ontologyTDBLoader(String ontologyId, String directory, String importId, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyTDBLoader();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            cru.build("importId", importId);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONArray ontologyQuery(String ontologyId, String directory, String query, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyQuery();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            cru.build("query", query);
            return (JSONArray) cru.resolve(c, parameters, dataStreams);
        }

        public static void ontologyDelete(String ontologyId, String directory, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyDelete();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            cru.resolve(c, parameters, dataStreams);
        }

        public static Object ontologyReadInstance(String ontologyId, String directory, String instanceId, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyReadInstance();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            cru.build("instanceId", instanceId);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object ontologyReadAllClasses(String ontologyId, String directory, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyReadAllClasses();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object ontologyAddImport(String ontologyId, String directory, String importId, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyAddImport();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            cru.build("importId", importId);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object ontologyReadProperty(String ontologyId, String directory, String propertyId, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyReadProperty();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            cru.build("propertyId", propertyId);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONObject ontologyCreateClass(String ontologyId, String directory, String classId, Object vals, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyCreateClass();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            cru.build("classId", classId);
            cru.build("vals", vals);
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }

        public static Object ontologySetUriPrefix(String ontologyId, String directory, String importId, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologySetUriPrefix();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            cru.build("importId", importId);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object ontologyDeleteProperty(String ontologyId, String directory, String propertyId, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyDeleteProperty();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            cru.build("propertyId", propertyId);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static String ontologyId(String id, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyId();
            cru.build("id", id);
            return (String) cru.resolve(c, parameters, dataStreams);
        }

        public static Object ontologyGetShortestPath(String ontologyId, String directory, String classId, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyGetShortestPath();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            cru.build("classId", classId);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static Object ontologyReadAllProperties(String ontologyId, String directory, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyReadAllProperties();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            return (Object) cru.resolve(c, parameters, dataStreams);
        }

        public static JSONObject ontologyCreateInstance(String ontologyId, String directory, String classId, Object vals, final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams) throws org.json.JSONException
        {
            Cruncher cru = new com.eduworks.cruncher.ontology.CruncherOntologyCreateInstance();
            cru.build("ontologyId", ontologyId);
            cru.build("directory", directory);
            cru.build("classId", classId);
            cru.build("vals", vals);
            return (JSONObject) cru.resolve(c, parameters, dataStreams);
        }
    }
}
