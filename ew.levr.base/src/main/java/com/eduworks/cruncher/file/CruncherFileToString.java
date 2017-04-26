package com.eduworks.cruncher.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.lang.EwList;
import com.eduworks.lang.json.impl.EwJsonArray;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.util.io.InMemoryFile;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.InvalidParameterException;
import java.util.List;
import org.json.JSONArray;

/**
 * Converts the contents of one or more files to a UTF-8 encoded string.
 *
 * rs2: obj = obj.fileToString(); levrJs: obj = fileToString.call(this,obj);
 *
 * @class fileToString
 * @module ew.levr.base
 * @author fritz.ray@eduworks.com
 */
public class CruncherFileToString extends Cruncher {

    /**
     * @method fileToString
     * @param obj (File|InMemoryFile|List<File|InMemoryFile>) File(s) to convert.
     * @param [encoding="UTF-8"] (String) Encoding to use when reading the string.
     * @return (String|JSONArray) UTF-8 encoded string(s) (or selected encoding).
     */
    @Override
    public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException {
        Object obj = getObj(c, parameters, dataStreams);
        String encoding = optAsString("encoding", "UTF-8", c, parameters, dataStreams);

        if (obj == null)
            return null;
        try {
            if (obj instanceof File)
                return fileToString((File) obj, encoding);
            else if (obj instanceof InMemoryFile)
                return fileToString((InMemoryFile) obj, encoding);
            else if (obj instanceof List) {
                List<?> fileList = (List<?>) obj;

                JSONArray array = new JSONArray();

                for (Object file : fileList) {
                    if (file == null)
                        continue;
                    if (file instanceof File)
                        array.put(fileToString((File) file, encoding));
                    else if (file instanceof InMemoryFile)
                        array.put(fileToString((InMemoryFile) file, encoding));
                }

                return array;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new InvalidParameterException("obj is not a valid file type. It is a: " + obj.getClass().getName());
    }

    @Override
    public String getDescription() {
        return "Converts an in memory file to a string.";
    }

    @Override
    public String getReturn() {
        return "String|JSONArray";
    }

    @Override
    public String getAttribution() {
        return ATTRIB_NONE;
    }

    @Override
    public JSONObject getParameters() throws JSONException {
        return jo("obj", "File|InMemoryFile|List<File|InMemoryFile>");
    }

    public static String fileToString(File file, String encoding) throws IOException {
        return IOUtils.toString(new FileInputStream(file), encoding);
    }

    public static String fileToString(InMemoryFile file, String encoding) throws IOException {
        return IOUtils.toString(new ByteArrayInputStream(file.data), encoding);
    }

}
