package com.eduworks.cruncher.file;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.util.io.InMemoryFile;
import java.security.InvalidParameterException;

/**
 * Returns the filename of a file.
 *
 * rs2: obj = obj.filename(); levrJs: obj = filename.call(this,obj); 
 * rs2: obj = obj.fileName(); levrJs: obj = fileName.call(this,obj);
 *
 * @class filename
 * @module ew.levr.base
 * @author fritz.ray@eduworks.com
 */
public class CruncherFilename extends Cruncher {

    /**
     * @method filename
     * @param obj (File|InMemoryFile) File.
     * @return (String) Name of the file.
     */
    /**
     * @method fileName
     * @param obj (File|InMemoryFile) File.
     * @return (String) Name of the file.
     */
    @Override
    public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException {
        Object obj = getObj(c, parameters, dataStreams);
        if (obj == null)
            return null;
        if (obj instanceof InMemoryFile)
            return ((InMemoryFile) obj).name;
        else if (obj instanceof File)
            return ((File) obj).getName();
        throw new InvalidParameterException("obj is not a valid file type.");
    }

    @Override
    public String getDescription() {
        return "Retreives the filename of a file";
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
    public String[] getResolverNames() {
        return new String[]{
            "c" + getResolverName(), getResolverName(), "fileName"
        };
    }

    @Override
    public JSONObject getParameters() throws JSONException {
        return jo("obj", "File|InMemoryFile");
    }

}
