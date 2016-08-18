package com.eduworks.cruncher.security;

import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherRsaEncrypt extends Cruncher
{
	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		CruncherRsaGenerate.checkProvider();
		String payload = getObj(c, parameters, dataStreams).toString();
		String key = getAsString("pk", c, parameters, dataStreams);
		try
		{
			X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(key.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replace("-----BEGIN RSA PUBLIC KEY-----", "").replace("-----END RSA PUBLIC KEY-----", "").replaceAll("\r?\n","")));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey bobPubKey = keyFactory.generatePublic(bobPubKeySpec);
			
			Cipher cipher = Cipher.getInstance("RSA/None/OAEPWithSHA1AndMGF1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, bobPubKey);
			byte[] cipherData = cipher.doFinal(payload.getBytes());
			return new String(Base64.encodeBase64(cipherData));
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
		return "Encrypts a string using an RSA private key and returns the signature.";
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
