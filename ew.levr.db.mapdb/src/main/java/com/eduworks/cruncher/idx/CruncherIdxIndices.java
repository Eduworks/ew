package com.eduworks.cruncher.idx;

import com.eduworks.db.mapdb.EwDB;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Map;

public class CruncherIdxIndices extends Cruncher {

    @Override
    public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
    {
        String _databasePath = decodeValue(getAsString("indexDir", c, parameters, dataStreams));
        String _databaseName = decodeValue(getAsString("databaseName", c, parameters, dataStreams));
        JSONArray ja = new JSONArray();
        EwDB ewDB = null;
        try
        {
            ewDB  = EwDB.get(_databasePath, _databaseName);
            for (String key : ewDB.db.getAll().keySet())
                ja.put(key);
        }
        finally
        {
            if (ewDB != null)
                ewDB.close();
        }
        return ja;
    }

    @Override
    public String getDescription()
    {
        return "Return all indices in an IDX Database.";
    }

    @Override
    public String getReturn()
    {
        return null;
    }

    @Override
    public String getAttribution()
    {
        return ATTRIB_NONE;
    }

    @Override
    public JSONObject getParameters() throws JSONException
    {
        return jo("indexDir","String","databaseName","String");
    }

}
