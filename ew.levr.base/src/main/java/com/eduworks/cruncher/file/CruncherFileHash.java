package com.eduworks.cruncher.file;

import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.util.io.InMemoryFile;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * Returns the MD5 hash of the file provided.
 *
 * @class fileHash
 * @module ew.levr.base
 * @author fritz.ray@eduworks.com
 */
public class CruncherFileHash extends Cruncher {

    /**
     * @method fileHash
     * @param obj {String|nMemoryFile|File) Path to, or File to hash and produce a result.
     * @param [hash=MD5] (String) Name of the hash to use to digest the file. See Java Hash Providers for a list.
     * @return (String) Hash of the file.
     */
    @Override
    public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException {
        Object obj = getObj(c, parameters, dataStreams);

        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance(optAsString("hash", "MD5", c, parameters, dataStreams));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        DigestInputStream dis = null;
        if (obj instanceof InMemoryFile) {
            InMemoryFile file = (InMemoryFile) obj;
            dis = new DigestInputStream(file.getInputStream(), md);
        } else if (obj instanceof File) {
            try {
                dis = new DigestInputStream(FileUtils.openInputStream((File) obj), md);
            } catch (IOException ex) {
                Logger.getLogger(CruncherFileHash.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (obj instanceof String) {
            try {
                dis = new DigestInputStream(FileUtils.openInputStream(new File((String) obj)), md);
            } catch (IOException ex) {
                Logger.getLogger(CruncherFileHash.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (dis == null) {
            return null;
        }

        int numRead;
        byte[] buffer = new byte[1024];

        try {
            do {
                numRead = dis.read(buffer);
            } while (numRead != -1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return md.digest();

    }

    @Override
    public String getDescription() {
        return "Converts an in memory file to a string.";
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
