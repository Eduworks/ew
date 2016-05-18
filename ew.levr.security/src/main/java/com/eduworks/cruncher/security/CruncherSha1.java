package com.eduworks.cruncher.security;

import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherSha1 extends Cruncher
{

	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		String data = getObjAsString(c, parameters, dataStreams);
		String key = getAsString("key", c, parameters, dataStreams);
		try
		{

			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);
			return new String(Base64.encodeBase64(mac.doFinal(data.getBytes())));
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (InvalidKeyException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getDescription()
	{
		return "Returns HMAC-SHA1 Signature of a String.";
	}

	@Override
	public String getReturn()
	{
		return "String";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("obj", "String");
	}
}
