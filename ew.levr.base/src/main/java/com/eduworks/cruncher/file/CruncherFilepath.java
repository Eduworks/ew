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
 * Returns the path of a file.
 *
 * rs2: obj = obj.filepath(); levrJs: obj = filepath.call(this,obj); 
 * rs2: obj = obj.filePath(); levrJs: obj = filePath.call(this,obj);
 *
 * @class filepath
 * @module ew.levr.base
 * @author fritz.ray@eduworks.com
 */
public class CruncherFilepath extends Cruncher {

    @Override
    public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException {
        Object obj = getObj(c, parameters, dataStreams);
        if (obj == null)
            return null;
        if (obj instanceof InMemoryFile)
            return ((InMemoryFile) obj).path;
        else if (obj instanceof File)
            return ((File) obj).getPath();
        throw new InvalidParameterException("obj is not a valid file type.");
    }

    @Override
    public String getDescription() {
        return "Returns path for the file. If the file is in memory, the file at this path may be deleted or from another computer.";
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
        return jo("obj", "InMemoryFile");
    }

}
