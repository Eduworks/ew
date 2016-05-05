package com.eduworks.cruncher.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherAesDecrypt extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		String cipherText = getObj(c, parameters, dataStreams).toString();
		String secret = getAsString("secret", c, parameters, dataStreams);
		String iv = getAsString("iv", c, parameters, dataStreams);

		try
		{
			IvParameterSpec ivParameter = new IvParameterSpec(Base64.decode(iv));
			SecretKeySpec aesKey = new SecretKeySpec(Base64.decode(secret), "AES");

			Cipher decryptCipher = Cipher.getInstance("AES/CTR/PKCS5Padding");
			decryptCipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameter);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ByteArrayInputStream inStream = new ByteArrayInputStream(Base64.decode(cipherText));
			CipherInputStream cipherInputStream = new CipherInputStream(inStream, decryptCipher);
			byte[] buf = new byte[1024];
			int bytesRead;
			try
			{
				while ((bytesRead = cipherInputStream.read(buf)) >= 0)
				{
					outputStream.write(buf, 0, bytesRead);
				}
			}
			finally
			{
				cipherInputStream.close();
			}
			return outputStream.toString();
		}
		catch (InvalidKeyException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchPaddingException e)
		{
			e.printStackTrace();
		}
		catch (InvalidAlgorithmParameterException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getDescription()
	{
		return "Decrypts an AES string using a secret and IV.";
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
		return jo("obj", "String", "iv", "String","secret","String");
	}
}
