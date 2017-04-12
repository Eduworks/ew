package com.eduworks.cruncher.file;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.lang.EwList;
import com.eduworks.lang.util.EwJson;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.resolver.io.CruncherMimeType;
import com.eduworks.util.io.InMemoryFile;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Converts one or more base 64 encoded files (represented as a JSON Object) to one or more InMemoryFiles
 *
 * Representation:<br>
 * {<br>
 * name:"base64EncodedFileAsString"<br>
 * ,...<br>
 * }
 *
 * rs2: result = obj.base64ToFile();<br>
 * LevrJS: result = base64ToFile.call(this,obj);
 *
 * @class base64ToFile
 * @module ew.levr.base
 * @author fritz.ray@eduworks.com
 */
public class CruncherBase64ToFile extends Cruncher {

    /**
     * @method base64ToFile
     * @param obj {JSONObject} JSON Object holding the Base64 representation of the file.
     * @param [mimeType] {String} Force the mime type of the resultant files to be this.
     * @return {InMemoryFile|List<InMemoryFile>} File Object.
     */
    @Override
    public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException {
        JSONObject obj = getObjAsJsonObject(c, parameters, dataStreams);

        List<InMemoryFile> result = new EwList<InMemoryFile>();
        for (String key : EwJson.getKeys(obj)) {
            InMemoryFile rf = new InMemoryFile();
            rf.name = key;
            rf.data = Base64.decodeBase64(obj.getString(key));
            try {
                rf.mime = optAsString("mimeType", CruncherMimeType.getMimeType(rf, null), c, parameters, dataStreams);
            } catch (IOException ex) {
                Logger.getLogger(CruncherBase64ToFile.class.getName()).log(Level.SEVERE, null, ex);
            }
            result.add(rf);
        }
        if (result.size() == 0) {
            return null;
        }
        if (result.size() == 1) {
            return result.get(0);
        }
        return result;
    }

    @Override
    public String getDescription() {
        return "Converts one or more base 64 files to an InMemoryFile";
    }

    @Override
    public JSONObject getParameters() throws JSONException {
        return jo("obj", "JSON Object, {name:'base64',...}");
    }

    @Override
    public String getReturn() {
        return "InMemoryFile|List<InMemoryFile>";
    }

    @Override
    public String getAttribution() {
        return ATTRIB_NONE;
    }
}
