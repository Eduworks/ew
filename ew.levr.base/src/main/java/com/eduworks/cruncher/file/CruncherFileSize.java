package com.eduworks.cruncher.file;

import java.io.InputStream;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.util.io.InMemoryFile;
import java.io.File;
import java.security.InvalidParameterException;

/**
 * Measures the size of a file on the filesystem. If passed a string, uses default encoding.
 *
 * rs2: obj = obj.fileSize(); levrJs: obj = fileSize.call(this,obj);
 *
 * @class fileSize
 * @module ew.levr.base
 * @author fritz.ray@eduworks.com
 */
public class CruncherFileSize extends Cruncher {

    /**
     * @method fileSize
     * @param obj (InMemoryFile) File to measure.
     * @return (Long) Length of the file.
     */
    @Override
    public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException {
        Object o = getObj(c, parameters, dataStreams);
        if (o == null) {
            return null;
        }
        if (o instanceof InMemoryFile) {
            return ((InMemoryFile) o).data.length;
        } else if (o instanceof File) {
            return ((File) o).length();
        } else if (o instanceof String) {
            return new File((String) o).length();
        } else {
            throw new InvalidParameterException("Unknown type to get file size from: " + o.getClass().getName());
        }
    }

    @Override
    public String getDescription() {
        return "Retrieves the size in bytes of an in-memory file";
    }

    @Override
    public String getReturn() {
        return "String";
    }

    @Override
    public String getAttribution() {
        return ATTRIB_NONE;
    }

    @Override
    public JSONObject getParameters() throws JSONException {
        return jo("obj", "InMemoryFile");
    }

}
