package com.eduworks.cruncher.file;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

/**
 * Lists all files in a directory. Only returns partial paths.
 *
 * rs2: filePaths = #fileList(path="Directory path") LevrJS: pathsArray = fileList.call(this,"Directory path")
 *
 * @class fileList
 * @module ew.levr.base
 * @author fritz.ray@eduworks.com
 */
public class CruncherFileList extends Cruncher {

    /**
     * @method fileList
     * @param path (String) Full or relative path to directory to list files.
     * @param [safe=true] {Boolean} For security, will not obey paths that contain '..'or startsWIth('/') paths.
     * @return (JSONArray) Relative paths from the directory root of all files or directories in that directory.
     */
    public Object resolve(Context c, java.util.Map<String, String[]> parameters, java.util.Map<String, java.io.InputStream> dataStreams)
            throws org.json.JSONException {
        String path = getAsString("path", c, parameters, dataStreams);
        if (optAsBoolean("safe", true, c, parameters, dataStreams) && CruncherCreateDirectory.pathUnsafe(path)) {
            throw new RuntimeException("Cannot go up in filesystem.");
        }
        File f = new File(path);
        JSONArray paths = new JSONArray();
        File dir = new File(path);
        if (dir.isDirectory() == false) {
            throw new RuntimeException("Path does not refer to a directory.");
        }
        for (String s : dir.list()) {
            paths.put(s);
        }
        return paths;
    }

    @Override
    public String getDescription() {
        return "Returns paths to all files referred to by path.";
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
