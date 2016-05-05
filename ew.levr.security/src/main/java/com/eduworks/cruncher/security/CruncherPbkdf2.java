package com.eduworks.cruncher.security;

import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.bouncycastle.util.encoders.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherPbkdf2 extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		String obj = getObj(c, parameters, dataStreams).toString();
		String salt = getAsString("salt", c, parameters, dataStreams);
		int iterations = Integer.parseInt(getAsString("iterations", c, parameters, dataStreams));
		int hashLength = Integer.parseInt(getAsString("hashLength", c, parameters, dataStreams));
		PBEKeySpec spec = new PBEKeySpec(obj.toCharArray(), salt.getBytes(), iterations, hashLength * 8);
		try
		{
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] hash = skf.generateSecret(spec).getEncoded();
			return new String(Base64.encode(hash));
		}
		catch (InvalidKeySpecException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getDescription()
	{
		return "Hashes the text using strong hashing a lot of times. Returns base64 string.";
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
		return jo("obj","String");
	}

}
