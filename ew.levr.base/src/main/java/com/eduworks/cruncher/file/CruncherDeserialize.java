package com.eduworks.cruncher.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
 * Deserializes an object from a file. Uses Java Object Serialization, and is
 * subject to version number changes.
 *
 * rs2: result = obj.deserialize(obj="File or InMemoryFile");<br>
 * LevrJS: result = deserialize.call(this,obj);
 *
 * @class deserialize
 * @module ew.levr.base
 * @author fritz.ray@eduworks.com
 */
public class CruncherDeserialize extends Cruncher {

/**
 * @method deserialize
 * @param obj {File|InMemoryFile} File or In Memory File to extract object from.
 * @return {Object} Object that has been deserialized.
 */
    @Override
    public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException {
        Object obj = getObj(c, parameters, dataStreams);
        if (obj instanceof InMemoryFile) {
            InMemoryFile imf = (InMemoryFile) obj;
            return SerializationUtils.deserialize(imf.data);
        } else if (obj instanceof File) {
            FileInputStream openInputStream = null;
            try {
                openInputStream = FileUtils.openInputStream((File) obj);
                return SerializationUtils.deserialize(openInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(openInputStream);
            }
        }
        return null;
    }

    @Override
    public String getDescription() {
        return "Deserializes an object using Java Object Deserialization and returns it.";
    }

    @Override
    public String getReturn() {
        return "Object";
    }

    @Override
    public String getAttribution() {
        return ATTRIB_NONE;
    }

    @Override
    public JSONObject getParameters() throws JSONException {
        return jo("obj", "File|InMemoryFile");
    }

}
