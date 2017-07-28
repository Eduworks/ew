package com.eduworks.cruncher.file;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

/**
 * Returns true if the file or directory located at the path exists.
 *
 * rs2: result = #fileExists(path="Relative or Full Path to directory");<br>
 * LevrJS: fileExists.call(this,"Relative or Full Path to directory");
 *
 * @class fileExists
 * @module ew.levr.base
 * @author fritz.ray@eduworks.com
 */
public class CruncherFileExists extends Cruncher {

    /**
     * @method fileExists
     * @param path {String} Relative or Full Path to file or directory.
     * @param [safe=true] {Boolean} For security, will not obey paths that contain '..'or startsWIth('/') paths.
     * @return {Boolean} True if the file or directory exists.
     */
    public Object resolve(Context c,
            java.util.Map<String, String[]> parameters, java.util.Map<String, java.io.InputStream> dataStreams)
            throws org.json.JSONException {

        String path = getAsString("path", c, parameters, dataStreams);
        if (optAsBoolean("safe", true, c, parameters, dataStreams) && CruncherCreateDirectory.pathUnsafe(path)) {
            throw new RuntimeException("Cannot go up in filesystem.");
        }
        File f = new File(path);

        if (f.exists()) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public String getDescription() {
        return "Checks whether file exists on the filesystem.";
    }

    @Override
    public String getReturn() {
        return "InMemoryFile";
    }

    @Override
    public String getAttribution() {
        return ATTRIB_NONE;
    }

    @Override
    public JSONObject getParameters() throws JSONException {
        return jo("path", "String", "?safe", "Boolean");
    }
;
}
