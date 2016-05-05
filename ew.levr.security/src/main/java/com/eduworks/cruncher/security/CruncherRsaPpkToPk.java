package com.eduworks.cruncher.security;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Security;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.pkcs.RSAPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherRsaPpkToPk extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		StringWriter sw = new StringWriter();
		String key = getObjAsString(c, parameters, dataStreams);
		try
		{
			PKCS8EncodedKeySpec bobPubKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(key.replace("-----BEGIN RSA PRIVATE KEY-----", "")
					.replace("-----END RSA PRIVATE KEY-----", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "")
					.replaceAll("\r?\n", "")));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPrivateCrtKey ppk = (RSAPrivateCrtKey) keyFactory.generatePrivate(bobPubKeySpec);
			PublicKey pk = keyFactory.generatePublic(new RSAPublicKeySpec(ppk.getModulus(), ppk.getPublicExponent()));
			PemWriter pw = new PemWriter(sw);
			pw.writeObject(new PemObject("PUBLIC KEY", pk.getEncoded()));
			pw.flush();
			pw.close();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new RuntimeException(e);
		}
		catch (InvalidKeySpecException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return sw.toString();
	}

	@Override
	public String getDescription()
	{
		return "Converts a RSA Private Key in PEM form to a RSA Public Key in PEM form.";
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
