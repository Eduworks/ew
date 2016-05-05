package com.eduworks.resolver.db.couchdb;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.lang.EwList;
import com.eduworks.lang.json.impl.EwJsonObject;
import com.eduworks.lang.threading.EwThreading;
import com.eduworks.lang.util.EwJson;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.fourspaces.couchdb.Document;

public abstract class CruncherDocument extends Cruncher
{
	public static final List<String> lockArray = Collections.synchronizedList(new EwList<String>());

	private final static String BYPASS_VALUE = "__bypass";
	private final static String NEW_PASS_KEY = "_newPassword";
	private final static String PASS_KEY = "password";

	public CruncherDocument()
	{
		super();
	}

	/** @return true if the password is the {@link #BYPASS_VALUE}, false otherwise */
	protected boolean bypassLogin(Context c,Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		return getAsString(PASS_KEY, c,parameters,dataStreams).equals(BYPASS_VALUE);
	}

	/** @return true if there is a new password in play, false otherwise */
	protected boolean creatingNewLogin(Context c,Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		return (!getAsString(NEW_PASS_KEY, c,parameters,dataStreams).isEmpty());
	}

	/** @return true if there is no current user and no new password, false otherwise */
	protected boolean isLogging(Context c,Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		return (getAsString("_id", c,parameters,dataStreams).isEmpty() && !creatingNewLogin(c,parameters,dataStreams));
	}

	/** @return true if there is a new password in play, false otherwise */
	protected boolean isMD5(String password)
	{
		return password.startsWith("MD5");
	}

	/**
	 * Checks the security of the document, throwing a security exception if the
	 * password has been violated.
	 * @param d The document to check.
	 * @param parameters
	 * @throws JSONException
	 */
	protected void security(Document d, Context c,Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		if (!bypassLogin(c,parameters,dataStreams) && d.containsKey(PASS_KEY))
		{
			final String curPassValue = getAsString(PASS_KEY, c,parameters,dataStreams);
			final String docPassValue = d.getString(PASS_KEY);
			final String invalidPassMsg = "Password not provided or invalid.";

			if (isMD5(curPassValue))
			{
				if (!docPassValue.equals(curPassValue))
					throw new SecurityException(invalidPassMsg);
			}
			else
				try
				{
					if (!docPassValue.equals(MessageDigest.getInstance("MD5").digest(curPassValue.getBytes())))
						throw new SecurityException(invalidPassMsg);
				}
				catch (NoSuchAlgorithmException e)
				{
					e.printStackTrace();
				}
		}
	}

	/** Updates the document with an MD5 of the current password if it hasn't already been hashed. */
	protected void updatePasswordIfNecessary(Document d, String key, Context c,Map<String, String[]> parameters, Map<String, InputStream> dataStreams)
			throws JSONException
	{
		final String password = getAsString(NEW_PASS_KEY, c,parameters,dataStreams);

		if (key.equals(NEW_PASS_KEY) && !password.isEmpty())
			try
			{
				d.put(PASS_KEY, (isMD5(password)) ? password : MessageDigest.getInstance("MD5").digest(password.getBytes()));
			}
			catch (NoSuchAlgorithmException e)
			{
				e.printStackTrace();
			}
	}

	protected JSONObject dataToJsonObject(Map<String, InputStream> dataStreams) throws JSONException
	{
		JSONObject jo = new EwJsonObject();
		if (dataStreams == null)
			return jo;
		for (String key : dataStreams.keySet())
		{
			byte[] byteArray = null;
			try
			{
				InputStream input = dataStreams.get(key);
				input.reset();
				byteArray = IOUtils.toByteArray(input);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			JSONArray ja = new JSONArray(byteArray);
			jo.put(key,ja);
		}
		return jo;
	}

	protected Map<String, InputStream> jsonObjectToDataStreams(JSONObject dataStreams) throws JSONException
	{
		Map<String, InputStream> jo = new HashMap<String, InputStream>();
		for (String key : EwJson.getKeys(dataStreams))
		{
			JSONArray ja = dataStreams.getJSONArray(key);
			byte[] byteArray = new byte[ja.length()];
			for (int i = 0;i<ja.length();i++)
			byteArray[i] = (byte) ja.getInt(i);
			jo.put(key,new ByteArrayInputStream(byteArray));
		}
		return jo;
	}

	protected JSONObject paramsToJsonObject(Map<String, String[]> parameters) throws JSONException
	{
		JSONObject jo = new EwJsonObject();
		for (String key : parameters.keySet())
		{
			JSONArray ja = new JSONArray(parameters.get(key));
			jo.put(key,ja);
		}
		return jo;
	}
	protected Map<String, String[]> jsonObjectToParams(JSONObject parameters) throws JSONException
	{
		Map<String, String[]> jo = new HashMap<String,String[]>();
		for (String key : EwJson.getKeys(parameters))
		{
			JSONArray jsonArray = parameters.getJSONArray(key);
			String[] ja = new String[jsonArray.length()];
			for (int i = 0;i < ja.length;i++)
				ja[i] = jsonArray.getString(i);
			jo.put(key,ja);
		}
		return jo;
	}

	protected void lockDocument(String id)
	{
		boolean go = true;
		while (go)
		{
			synchronized (lockArray)
			{
				if (lockArray.contains(id))
				{
					// return;
				}
				else
				{
					lockArray.add(id);
					return;
				}
			}
			EwThreading.sleep(10);
		}
	}

	protected void unlockDocument(String id)
	{
		lockArray.remove(id);
	}
}