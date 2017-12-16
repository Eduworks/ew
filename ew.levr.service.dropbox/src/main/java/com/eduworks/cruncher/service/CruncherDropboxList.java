package com.eduworks.cruncher.service;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Map;

public class CruncherDropboxList extends Cruncher {
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
            ListFolderResult result = client.files().listFolder(path);
            while (true) {
                for (Metadata metadata : result.getEntries()) {
                    results.put(metadata.getPathLower());
                }
                if (!result.getHasMore()) {
                    break;
                }
                result = client.files().listFolderContinue(result.getCursor());
            }
        } catch (DbxApiException e) {
            e.printStackTrace();
            return null;
        } catch (DbxException e) {
            e.printStackTrace();
            return null;
        }
        return results;
    }

    @Override
    public String getDescription() {
        return "Lists files in a directory in dropbox.";
    }

    @Override
    public String getReturn() {
        return "Array";
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
