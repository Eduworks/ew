package com.eduworks.cruncher.idx;

import com.eduworks.db.mapdb.EwDB;
import com.eduworks.lang.EwList;
import com.eduworks.lang.util.EwJson;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mapdb.Fun;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;

public class CruncherIdxGet extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		String _databasePath = decodeValue(getAsString("indexDir", c, parameters, dataStreams));
		String _databaseName = decodeValue(getAsString("databaseName", c, parameters, dataStreams));
		boolean optCommit = optAsBoolean("_commit", true, c, parameters, dataStreams);
		String index = decodeValue(getAsString("index", c, parameters, dataStreams));
		String key = getAsString("key", c, parameters, dataStreams);
		if (key == null)
		{
			Object obj = getObj(c, parameters, dataStreams);
			if (obj != null)
				key = obj.toString();
		}
		
		EwDB ewDB = null;
		try
		{
			ewDB = EwDB.get(_databasePath, _databaseName);

			if (optCommit)
				ewDB.db.commit();

			if (index == null)
			{
				Map<String, Object> all = ewDB.db.getAll();
				List<String> keys = new EwList(all.keySet());
				for (int i = 0;i < keys.size();i++)
				{
					Object result = getObject(c, parameters, dataStreams, keys.get(i), key, ewDB);
					if (result != null)
						return result;
				}
			}
			return getObject(c, parameters, dataStreams, index, key, ewDB);
		}
		finally
		{
			if (ewDB != null)
				ewDB.close();
		}
	}

	private Object getObject(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams, String index, String key, EwDB ewDB) throws JSONException {
		if (optAsString("multi", "false", c, parameters, dataStreams).equals("false"))
		{
			Object object = ewDB.db.getHashMap(index).get(key);
			return object;
		}
		else
		{
			NavigableSet<Fun.Tuple2<String, Object>> multiMap = ewDB.db.getTreeSet(index);
			JSONArray ja = new JSONArray();
			for (Object l : Fun.filter(multiMap, key))
			{
				ja.put(EwJson.tryParseJson(l,false));
			}
			if (ja.length() > 0)
				return ja;
			return null;
		}
	}

	@Override
	public String getDescription()
	{
		return "Gets from a string only on-disk multimap defined by indexDir+databaseName->index->key += value.\n" +
				"If #idxAdd was used, multi must be set to true, and will return array.";
	}

	@Override
	public String getReturn()
	{
		return "String|JSONArray";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("indexDir","String","databaseName","String","index","String","key","String","multi","Boolean");
	}

}
