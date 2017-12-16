package com.eduworks.cruncher.service;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.Metadata;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.util.io.InMemoryFile;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class CruncherDropboxLoad extends Cruncher {
    @Override
    public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException {
        String path = getAsString("path", c, parameters, dataStreams);
        String clientIdentifier = getAsString("clientIdentifier", c, parameters, dataStreams);
        String access_token = getAsString("accessToken", c, parameters, dataStreams);
        JSONArray results = new JSONArray();
        try {
            // Create Dropbox client
            DbxRequestConfig config = new DbxRequestConfig(clientIdentifier);
            DbxClientV2 client = new DbxClientV2(config, access_token);
            InMemoryFile imf = new InMemoryFile();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DbxDownloader<FileMetadata> download = client.files().download(path);
            Metadata md = download.download(baos);
            imf.name = md.getName();
            imf.path = md.getPathLower();
            imf.data = baos.toByteArray();
            return imf;
        } catch (DbxApiException e) {
            e.printStackTrace();
            return null;
        } catch (DbxException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getDescription() {
        return "Loads a file into memory from Dropbox.";
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
        return jo("accessToken","String","path","String");
    }
}
