package com.eduworks.cruncher.security;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Map;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.json.JSONException;
import org.json.JSONObject;

import sun.misc.BASE64Encoder;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherRsaGenerate extends Cruncher
{

	public static void checkProvider()
	{
		BouncyCastleProvider provider = new BouncyCastleProvider();
		if (Security.getProvider(provider.getName()) == null)
			Security.addProvider(provider);
	}
	
	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		checkProvider();
		StringWriter sw = new StringWriter();
		try
		{
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			keyGen.initialize(2048, random);
			KeyPair keyPair = keyGen.generateKeyPair();
			PemWriter pw = new PemWriter(sw);
			pw.writeObject(new PemObject("RSA PRIVATE KEY", keyPair.getPrivate().getEncoded()));
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
		catch (NoSuchProviderException e)
		{
			e.printStackTrace();
		}
		return sw.toString();
	}

	@Override
	public String getDescription()
	{
		return "Generates an RSA Private Key and returns it in PEM form.";
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
