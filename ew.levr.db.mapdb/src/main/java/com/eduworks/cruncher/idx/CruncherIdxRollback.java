package com.eduworks.cruncher.idx;

import java.io.InputStream;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.db.mapdb.EwDB;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherIdxRollback extends Cruncher
{

    @Override
    public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
    {
        String _databasePath = decodeValue(getAsString("indexDir", c, parameters, dataStreams));
        String _databaseName = decodeValue(getAsString("databaseName", c, parameters, dataStreams));
        Integer numberOfTimes = optAsInteger("count",1,c,parameters,dataStreams);

        EwDB ewDB = null;
        try
        {
            ewDB  = EwDB.get(_databasePath, _databaseName);
            for (int i = 0;i < numberOfTimes;i++)
                ewDB.db.rollback();
        }
        finally
        {
            if (ewDB != null)
                ewDB.close();
        }
        return null;
    }

    @Override
    public String getDescription()
    {
        return "Rolls back an IDX Database.";
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
        return jo("indexDir","String","databaseName","String", "count","Integer");
    }

}
