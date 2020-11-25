package com.eduworks.cruncher.security;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.KeyFactorySpi;
import org.bouncycastle.util.io.pem.PemObject;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.lang.json.impl.EwJsonObject;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherRsaSignSha256 extends Cruncher
{
	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		CruncherRsaGenerate.checkProvider();
		String object = new EwJsonObject(getObj(c, parameters, dataStreams).toString()).toString();
		String key = getAsString("ppk", c, parameters, dataStreams);
		try
		{
			PemReader pr = new PemReader(new StringReader(key));
			PemObject po = pr.readPemObject();
			PKCS8EncodedKeySpec bobPubKeySpec = new PKCS8EncodedKeySpec(po.getContent());
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			//KeyFactorySpi kfs = new KeyFactorySpi();
			ASN1Sequence.fromByteArray(bobPubKeySpec.getEncoded());
			PrivateKey bobPubKey = keyFactory.generatePrivate(bobPubKeySpec);
			Signature sig = Signature.getInstance("sha256withrsa");
			sig.initSign(bobPubKey);
			sig.update(object.getBytes());
			byte[] signature = sig.sign();

			return new String(Base64.encodeBase64(signature));
		}
		catch (IllegalArgumentException e)
		{
			throw new RuntimeException(e);
		}
		catch (SignatureException e)
		{
			throw new RuntimeException(e);
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
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getDescription()
	{
		return "Signs a string using an RSA private key and returns the signature.";
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
