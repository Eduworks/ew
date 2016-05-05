package com.eduworks.cruncher.security;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherGenerateRsaPrivateKey extends Cruncher
{
	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		KeyPairGenerator keyPairGenerator;
		try
		{
			keyPairGenerator = KeyPairGenerator.getInstance("RSA","BC");

			keyPairGenerator.initialize(1024);
			KeyPair keyPair = keyPairGenerator.genKeyPair();
			PemObject obj = new PemObject("PRIVATE KEY", keyPair.getPrivate().getEncoded());
			ByteArrayOutputStream s;
			PemWriter privatepemWriter = null;
			try
			{
				privatepemWriter = new PemWriter(new OutputStreamWriter(s = new ByteArrayOutputStream()));
				privatepemWriter.writeObject(obj);
				privatepemWriter.flush();
				return s.toString();
			}
			finally
			{
				IOUtils.closeQuietly(privatepemWriter);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchProviderException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getDescription()
	{
		return "Generates a RSA Private Key. Returns in PEM form.";
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
		return jo();
	}

}
