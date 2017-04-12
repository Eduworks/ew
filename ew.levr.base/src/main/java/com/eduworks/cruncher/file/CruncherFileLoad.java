package com.eduworks.cruncher.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.util.io.InMemoryFile;

/**
 * Loads a file in a filesystem. Will attempt to copy the file into memory if the file is less than 2GB.
 *
 * rs2: file = #fileLoad(path="Path to File"); levrJs: file = fileLoad.call(this,"Path to File");
 *
 * @class fileLoad
 * @module ew.levr.base
 * @author fritz.ray@eduworks.com
 */
public class CruncherFileLoad extends Cruncher {

    /**
     * @method fileLoad
     * @param path (String) Relative or Full Path to file.
     * @param [safe=true] {Boolean} For security, will not obey paths that contain ".." or start with "/".
     * @param [file=false] {Boolean} Will not load the file into memory. '..' or startsWIth('/') paths.
     * @return (InMemoryFile|File) File object.
     */
    public Object resolve(Context c, java.util.Map<String, String[]> parameters, java.util.Map<String, java.io.InputStream> dataStreams)
            throws org.json.JSONException {
        String path = getAsString("path", c, parameters, dataStreams);
        if (optAsBoolean("safe", true, c, parameters, dataStreams) && CruncherCreateDirectory.pathUnsafe(path)) {
            throw new RuntimeException("Cannot go up in filesystem.");
        }
        File f = new File(path);
        if (optAsBoolean("file", false, c, parameters, dataStreams)) {
            return f;
        }
        try {
            if (f.length() > Integer.MAX_VALUE) {
                if (optAsBoolean("text", false, c, parameters, dataStreams)) {
                    return IOUtils.toString(new FileInputStream(f));
                }
                return f;
            } else {
                InMemoryFile imf = new InMemoryFile(f);
                if (optAsBoolean("text", false, c, parameters, dataStreams)) {
                    return IOUtils.toString(imf.getInputStream());
                }
                return imf;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String getDescription() {
        return "Loads a file in the filesystem.";
    }

    @Override
    public String getReturn() {
        return "InMemoryFile|File";
    }

    @Override
    public String getAttribution() {
        return ATTRIB_NONE;
    }

    @Override
    public JSONObject getParameters() throws JSONException {
        return jo("path", "String", "?safe", "Boolean", "?file", "Boolean");
    }
;
}
