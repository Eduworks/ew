package com.eduworks.cruncher.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.util.io.InMemoryFile;
import java.security.InvalidParameterException;

/**
 * Converts one or more files to a JSON Object with the file's name as the key, and the file's data as a base64 encoded string as the value.
 *
 * {"filename":"Base64 encoded file data"}
 *
 * rs2: obj = obj.fileToBase64(); levrJs: obj = fileToBase64.call(this,obj);
 *
 * @class fileToBase64
 * @module ew.levr.base
 * @author fritz.ray@eduworks.com
 */
public class CruncherFileToBase64 extends Cruncher {

    /**
     * @method fileToBase64
     * @param obj (File|InMemoryFile|List<File|InMemoryFile>) File(s) to convert.
     * @return (JSONObject) JSON Object with file(s) as key/value pairs.
     */
    @Override
    public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException {
        Object obj = getObj(c, parameters, dataStreams);

        if (obj == null)
            return null;
        JSONObject result = new JSONObject();
        try {
            if (obj instanceof File)
                encodeFile(result, (File) obj);
            else if (obj instanceof InMemoryFile)
                encodeFile(result, (InMemoryFile) obj);
            else if (obj instanceof List) {
                List files = (List) obj;
                for (Object f : files)
                    if (f instanceof File)
                        encodeFile(result, (File) f);
                    else if (f instanceof InMemoryFile)
                        encodeFile(result, (InMemoryFile) f);
                    else
                        throw new InvalidParameterException("List does not contain valid files.");
            } else
                throw new InvalidParameterException("obj is not a valid file type, or list of files.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void encodeFile(JSONObject result, File obj) throws JSONException, IOException {
        result.put(obj.getName(), Base64.encodeBase64String(FileUtils.readFileToByteArray(obj)));
    }

    private void encodeFile(JSONObject result, InMemoryFile obj) throws JSONException, IOException {
        result.put(obj.name, Base64.encodeBase64String(obj.data));
    }

    @Override
    public String getDescription() {
        return "Converts one or more files to a JSONObject where the filenames are keys and the Base64 encoded files are the values.";
    }

    @Override
    public String getReturn() {
        return "JSONObject";
    }

    @Override
    public String getAttribution() {
        return ATTRIB_NONE;
    }

    @Override
    public JSONObject getParameters() throws JSONException {
        return jo("obj", "File|InMemoryFile|List<File|InMemoryFile>");
    }

}
