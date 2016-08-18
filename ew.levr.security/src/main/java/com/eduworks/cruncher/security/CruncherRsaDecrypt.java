package com.eduworks.cruncher.security;

import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.lang.json.impl.EwJsonObject;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherRsaDecrypt extends Cruncher
{
	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		CruncherRsaGenerate.checkProvider();
		byte[] ciphertext = Base64.decodeBase64(getObj(c, parameters, dataStreams).toString());
		String key = getAsString("ppk", c, parameters, dataStreams);
		try
		{
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(key.replace("-----BEGIN PRIVATE KEY-----", "")
					.replace("-----END PRIVATE KEY-----", "").replace("-----BEGIN RSA PRIVATE KEY-----", "")
					.replace("-----END RSA PRIVATE KEY-----", "").replaceAll("\r?\n", "")));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

			Cipher cipher = Cipher.getInstance("RSA/None/OAEPWithSHA1AndMGF1Padding");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] payload = cipher.doFinal(ciphertext);
			return new String(payload);
		}
		catch (InvalidKeyException e)
		{
			throw new RuntimeException(e);
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new RuntimeException(e);
		}
		catch (InvalidKeySpecException e)
		{
			throw new RuntimeException(e);
		}
		catch (NoSuchPaddingException e)
		{
			throw new RuntimeException(e);
		}
		catch (IllegalBlockSizeException e)
		{
			throw new RuntimeException(e);
		}
		catch (BadPaddingException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getDescription()
	{
		return "Decrypts a string using an RSA private key and returns the payload.";
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
		return jo("obj", "String", "ppk", "String");
	}

}
