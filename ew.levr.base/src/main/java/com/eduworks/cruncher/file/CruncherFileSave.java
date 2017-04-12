package com.eduworks.cruncher.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SerializationUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.util.io.InMemoryFile;

/**
 * Saves a file onto the filesystem. If passed a string, uses default encoding.
 *
 * rs2: obj = obj.fileSave(path="Path to save to."); levrJs: obj = fileSave.call(this,obj,"Path to save to.");
 *
 * @class fileSave
 * @module ew.levr.base
 * @author fritz.ray@eduworks.com
 */
public class CruncherFileSave extends Cruncher {

    /**
     * @method fileSave
     * @param obj (InMemoryFile|File|String) Data to save to a file.
     * @param path (String) Path to save the file.
     * @param [overwrite=true] Overwrite the file if it already exists.
     * @param [safe=true] {Boolean} For security, will not obey paths that contain '..'or startsWIth('/') paths.
     * @return (InMemoryFile|File|String) Value passed into obj.
     */
    @Override
    public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException {
        String path = getAsString("path", c, parameters, dataStreams);
        if (optAsBoolean("safe", true, c, parameters, dataStreams) && CruncherCreateDirectory.pathUnsafe(path)) {
            throw new RuntimeException("Cannot go up in filesystem.");
        }
        File f = new File(path);
        Object o = getObj(c, parameters, dataStreams);
        try {
            if (optAsBoolean("overwrite", true, c, parameters, dataStreams) || f.exists() == false) {
                if (o instanceof InMemoryFile) {
                    if (f.isDirectory()) {
                        FileUtils.writeByteArrayToFile(new File(f, ((InMemoryFile) o).name), ((InMemoryFile) o).data);
                    } else {
                        FileUtils.writeByteArrayToFile(f, ((InMemoryFile) o).data);
                    }
                } else if (o instanceof File) {
                    if (f.isDirectory()) {
                        FileUtils.copyFile(((File) o), new File(f, ((File) o).getName()));
                    } else {
                        FileUtils.copyFile((File) o, f);
                    }
                } else if (o instanceof String) {
                    FileUtils.writeStringToFile(f, o.toString());
                } else {
                    FileOutputStream openOutputStream = FileUtils.openOutputStream(f);
                    try {
                        SerializationUtils.serialize((Serializable) o, openOutputStream);
                    } finally {
                        IOUtils.closeQuietly(openOutputStream);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public String getDescription() {
        return "Saves a file into the config system. Base path is ./";
    }

    @Override
    public String getReturn() {
        return "InMemoryFile|File|String";
    }

    @Override
    public String getAttribution() {
        return ATTRIB_NONE;
    }

    @Override
    public JSONObject getParameters() throws JSONException {
        return jo("obj", "InMemoryFile|File|String", "path", "String", "?overwrite", "Boolean", "?safe", "Boolean");
    }

}
