package com.eduworks.cruncher.file;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

/**
 * Creates a directory. Will throw exception if the directory exists, use alongside fileExists to prevent this. Will not create parent directories.
 *
 * rs2: #createDirectory(path="Relative or Full Path to directory");<br>
 * LevrJS: createDirectory.call(this,"Relative or Full Path to directory");
 *
 * @class createDirectory
 * @module ew.levr.base
 * @author fritz.ray@eduworks.com
 */
public class CruncherCreateDirectory extends Cruncher {

    /**
     * @method createDirectory
     * @param path {String} Relative or Full Path to directory.
     * @param [safe=true] {Boolean} For security, will not obey paths that contain '..'or startsWIth('/') paths.
     * @return {Boolean} True if the directory was created.
     */
    public Object resolve(Context c,
            java.util.Map<String, String[]> parameters, java.util.Map<String, java.io.InputStream> dataStreams)
            throws org.json.JSONException {

        String path = getAsString("path", c, parameters, dataStreams);
        if (optAsBoolean("safe", true, c, parameters, dataStreams) && pathUnsafe(path)) {
            throw new RuntimeException("Cannot go up in filesystem.");
        }

        File f = new File(path);

        if (f.exists()) {
            throw new RuntimeException("Directory Already Exists");
        } else {
            if (f.mkdir()) {
                return true;
            }

            throw new RuntimeException("Unable to create directory at " + path);
        }

    }

    public static boolean pathUnsafe(String path) {
        return path.contains("..") || path.startsWith("/");
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
        return jo("path", "String");
    }
;
}
