import com.eduworks.cruncher.security.CruncherRsaSign;
import com.eduworks.cruncher.security.CruncherRsaSignSha256;
import com.eduworks.cruncher.security.CruncherRsaVerify;
import com.eduworks.cruncher.security.CruncherRsaVerifySha256;
import com.eduworks.resolver.Context;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;

public class CruncherRsaSignTest
{
	@Test
	public void testRsa() throws NoSuchProviderException, NoSuchAlgorithmException, IOException, InvalidKeySpecException, InvalidKeyException, SignatureException
	{
		BouncyCastleProvider provider = new BouncyCastleProvider();
		if (Security.getProvider(provider.getName()) == null)
			Security.insertProviderAt(provider, 0);
		StringWriter sw = new StringWriter();
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		keyGen.initialize(2048, random);
		KeyPair keyPair = keyGen.generateKeyPair();
		PemWriter pw = new PemWriter(sw);
		pw.writeObject(new PemObject("RSA PRIVATE KEY", keyPair.getPrivate().getEncoded()));
		pw.flush();
		pw.close();

		PemReader pr = new PemReader(new StringReader(sw.toString()));
		PemObject po = pr.readPemObject();

		PKCS8EncodedKeySpec bobPubKeySpec = new PKCS8EncodedKeySpec(po.getContent());
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey bobPubKey = keyFactory.generatePrivate(bobPubKeySpec);
		Signature sig = Signature.getInstance("sha1withrsa");
		sig.initSign(bobPubKey);
		sig.update("{}".getBytes());
		byte[] signature = sig.sign();

		try
		{
			{
				CruncherRsaSign crs = new CruncherRsaSign();
				crs.build("ppk", "-----BEGIN RSA PRIVATE KEY-----MIIEpAIBAAKCAQEApFF4wJjtXMiK6rieUbSrjLT6eJSeIPnFZ2spEpJv91iusy1zUU5bpIwNdLCKBbx7AHmwi4lrYYAQ+zpwXUc9HVDzrXAnUtVzHOQpQqb7NDqk6aMsyo8ECRUMKBKbalnIzRDUSlbAsrxVJ6iKMnue0Wq58jtDQg3R/+u7p9voqaYUeYeXLgnqBugcF+5Bua+33HEH7aOVp+R6a5PYYRJzii14F2F7Q8kaB+D4RuRH/zAX3OB6K9NPmaptFELOXRVZ6I9NSTp612ZML+zNzm4oNoRnFwPhOG9dS5yUlI8+p+X3ylxz8+ashKdQYAmo5Y3VZ4xQ0M9Tjz7dppPeFu9TOwIDAQABAoIBABRrS2Yjmtt80EZ6yrG8kdTaaIWzcjMG1tETL/du2xsRmiQJ8dqYsiqwfLuTdJNh6KcWeCne2ckz8OhHSs9MmvuZo65Z0YkpnTRp5fayaWUEJJoj/NzvCMFYLDqOlWGR5rIU9UyHm0qavLpA52t+kS3U5WaElscOkJoM4TTqzu7F0Wtgi+vQktdmPELAv+seV7KRUrEa9lpm2LwJHzTIllXy7ipmWWGChghXOLd92NLAmJjNeSJ6WTNrWD4pTstrZVrkFqQVOpN+jV3ubM4SBViosKohYEqFqiY+bpaayNbCwOgRkaDZ8lF5oNHkq3PjJynwXmpfKhSlkSu2WTwPpwECgYEA4pLmNuyLEEAzV9SCfB4GZRjDQLc4eM+4yGW863QR9fowELMChBOHyg7tvWMM8Jx5E72Vw3/UC45NxhxOvBPxKTprOzHEkjw4/Fxo7sILH7TlofcVaoAVUA3itt3N3CA7w4n8QaSlLvBrx3+Pjc9IM+nZqHnoBhJQnDvHDvrghrMCgYEAuai0wvKknSwEHgIhwTYe7KM059PqH7GII5klCCaYiKxd1yFWS/CCdVoa7frFObFhqUvdAp5O3gQtgvXkk2T8btkY9Sqz0DDEq/nfEfTJBiipCfCXIFeZRrSnm6tlXPLuXScmjkXWxCKxp7KJRxPNFEEbgsilRtR0XAh9MBpFBVkCgYEAx5D2f7g4APdbgegniqVzooZ+LLl1X0wviSu7jHRvAYb94SebdrQxLbOaie79zc01BEM/77PfFNKiBHaYjybNiy/ulXQPnXK4CuhmcK6v15yhiti0n1rz6tBgOgwCL/qgSWp3q2YXLNINTOo5ioHiKf+3fKLr0LU/FW29tp8ZNusCgYA0YghWdNXDTtb+jIVvmVCSXd4ghuco+BM8UAplyVYCQffcKhjKJlr4t2qfuMCK7aRreu8pvPbtp2ob6DsM51JeCD0Fd33ygMAM8dxhmZpadYB0QICzbGPf0jr0LiDUX+qnN9OmEshs4IkVkscu2cRkMZaMiQYw/OKz0OVZfB/7SQKBgQCmZLr+KPn9AP+mNIYHYkLx9Ve56WtSueP9Up1KwdhA6LU0wdPsuTZi+V6gCmk6345ztdP2iy5644RzFS7IUizarFRAkRLYUhB9d0qaWVp2hTp+FaIpmshEOLxQZWaS1URZvzHuhRGdPnjW1e1cWXmXW9MKKKGrLa5bDYDMjZTMNg==-----END RSA PRIVATE KEY-----");
				crs.build("obj", new JSONObject());
				crs.resolve(new Context(), new HashMap<>(), new HashMap<>());

				crs = new CruncherRsaSign();
				crs.build("ppk", "-----BEGIN PRIVATE KEY-----MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCCdrskQMCh9Q98Ed/VEOzjC7S70Kbz3LYqbeV/W6t0DJ2M58nq8gWoJ2CQgD2os24yzdckvD4MIpBb+P2UJ0+qb92/UzsHpnhK0/nrsG3vE62Goli2OijC15qsa9fIyBUzsuPMfVCUC4sV8cNlUjYEBnUMXtoiiNQVz376zbE6kF/IcnGafXhIbmT2wYbd4dZw71JIufZzpHbQhSF/yRwgmb5r7/kV5pWtLvrfqAAE3jcOAtRp/K/ph64mzYtqWd4E4m2cYKuoAwhjRtvr9FVokSAA7HZojGTPv8K9+snS4sbI4WBaCnUZY4ms5Pb2TV+rfgUBBG1Ki5qMVvL1fKRBAgMBAAECggEAedD1EnenplHUwItMDX09Qw6mFgxQJzl5mop8lHt6zuQMpAm1nruXDpid8K7dY80OYz+R2EowQVJwqoAF/jTqjQDg84l6f70vaucc37YgH/CmwSSLYrNRZgS89qvHk0FHkrtBOqnz+BgDKV1RjiTCLOX+mqtiUIakSS0yqmovRrcrequRaaxWA3NusRr+8k0MVgorF5jEmQqxJFQkQdPbA12FCfb+WNOeHMh6Z6kFpMbNBsnX252c2+ApyrCmO85hnetK7Pv1ml7KyCPqJGy7SRk3CHqp8XTlRf9wf/NAblJFR0jF/jq700DXBT3yRr1vKGomJEyp+phHCKMUS4H2sQKBgQD6S8d/jGxXlRZy1Bz19Si0xkfKcg6y/q5D3f622LKbCuPtQbPWh+6SifCRR5qxTckBIvSXLP5ydFxmlEDpsZNGGUwMKA81HqUTeM8T9zMU2fVcytMn8eYeNtHsNwuTxPBV8oyUc5FKyy9R+ubrUMmE6BGUTo0mVRQJu/+gkUcUpQKBgQCFb9qB2UOWCA6nnvtDrkJNF0BbYbMICzf/AD3UqHwWmgTiGyc+20nB/8cmHjGSpb6gN9PJ/m5rEXSiWs10hG8AhdqTibNTKN+PeIlACIFyZlpVEMzX2lFKFjvnZ+PK9pZzqKSnZBicNZPDVeJ8mQdQCAGhHFi4R68bcCqfIGxSbQKBgFsg/gilYsjcgijaq5StZt2yl27Q6iMncOFK0TNXYGfspiG62zRPl4AI8uqiLGNDk9xzEqR8AERBs2axsZSkT5+lFDgU2byYZLfbM2Hsjc+Yr6nSOuD/RK5ZfULIo9FwUaSpQ5/FF/AoXBk4BZ1Fc6mNKOFbXC7JqRsGNMMMQdn1AoGAdMziAYNCS9OlsEfTPP0OABj4NoUgh0v6qfV2Ke2JIOeUmq6V/clJmMzhb5ZCYlE+uVPkyQ0zbfkLlQnSiclauKRyHW8NTx+aASXLTgej95VsQMXB6LerLCSmLvqbRI/wRjE6X2OYvzOOsg89fz8cTYuUkYgUiMoGlwfp5vwklUkCgYEA8g6LQSV56o05j/+VCeIE9W2UzQvYdebp/MQ4fRsxPkyfqUmZFnRI2P6edgabCSBzjREajHgFy1BG877RCYrlsqISJf6KvfLzNeQTyxfHGlWWb5wa7pHcCSFjmCV26tuLy3qn/X1zddJyb2ji0UqUkQf0JqV7wu1fcKHXm/AW7A4=-----END PRIVATE KEY-----");
				crs.build("obj", new JSONObject());
				crs.resolve(new Context(), new HashMap<>(), new HashMap<>());

				crs = new CruncherRsaSign();
				crs.build("ppk", "-----BEGIN RSA PRIVATE KEY-----MIIEpQIBAAKCAQEAp1qCdEqc280vzHxhdiBuEiFIQiR9c3Tu7pTqJFt6aB9voGSH6e5WvxkUf1Q/Ab9VSGbxM4h+EtB3KUKPepWC5ry24F2g2HP/XtPzyQ+LeC8K0xMAhvVMzx6sVfwDWQg/ZGkSFY0Oup7+7V948VrP1Go/30Z6ac5cpFzXA9/oF5engQIkK8pBWinlVOnSo8n4ELl0K1ly2EUoOHwM5rDfGfUoUCHEVrxmS70dT6I8gTVgbf3OIXzVOPFFn8jXyRSD/TWQDE15pi5CGPXwOTD4KvUILO5dyGw3KJRdZA5oX/efOZ80QhuG1WU+TwzFEkvIRZas5cKnRztHIgk0+MGvwQIDAQABAoIBAQALIBJ3kiU63Hleve8JuA6xMFq6IOxhEkc2/5nQKG+q/9n/HufFJqXIJu3sqAhXQKW1/jfvLdX5wLeHO+hHZSySU73RjQ/eC9oQbwUwGtMx/miZDxSUJDcCtt6MicgcMVbrgSulWJ7/y4WVeKkU3G1gCN8to+UL5x7UA2L83DP063GSYlt7lmaj8iwswPduG/NdXyFoF64FsaCIrnyeylVHJVn1mZ9k7CG6DGchvCoeoAKp82+UlV8m4NBQsH1gxWDKOIS1VNqyNppEADn7GVKVgv2bu4V4jw4NQ5VQ6b92oECwXy/iR0fTOLBhKvIU2GjNHP09dKp2OHANajWpvjnRAoGBAOe5Mt1QqIAtREZ/UNuKYzHXZMECrwFBfhfSkVox2ZLFP9jGgGaAKv8ufhkqaVwZaBRRkaxKhYDZOTjjjAHb3TSWM6VLRflkJr9uRyNxm+ot+7F8krhguF5b4s9BHNrnW74nfqCVuJSSsGNUwsOp3kLyOxtk6G65zydHRJk/le05AoGBALji6qbbfcInZjVSTlg9HpcFbpEnR6XRnclxVF+xRixSrGcHj8S5JIcN1/TQ10koDheJz9SR6XaWKVsa/6YB+PK7pZ/2TkKoz7R1kJV/TheIGN7lDruYw93zoOCmS+NEavkNUPcCF5TRfbBmdfFg/st345NbhC/za33OB/xt697JAoGATbvZ7VZegEkvKzsQg+Vjq1ena/wbex9eTl2L9ybb5xmWdoD4l+oNAne1Q3nvyD6ZpJ9Mb/C74mJ8iLIEyKbaCQcOIjgjLCtFuFglMyWseR2d3od8rK99ieHnL0GXjXOpWhvjpbk0J1siIuBPSdYVQn4CCAmM04AvluyYfZRQFjkCgYEAoeiV9WlhyBUYvLlHQp7Dmx12pkbvx5a5xx8n9GUaPtZnhBJrJKu+AUduR0ZcQgSB0rss9cqR2PfHX7FVD9vKV0dc06ivAFE1E6Og6kqB6LmWjhupuz8cx9ICoLkA3BWeWFwA+08pt3l9yGzx/GAkwapL8U/Dgvp4t8Ml2zmnxYECgYEAl6cOGQdkMl9karRurQhl/GvEx8pzTDJssvBw0Dl55yNIENPc6gcAZxI57O1jX7Bv4V+G5ekKqSQUmwnmBGUvRm4QuET5olav3F92mJjqGjhQ0HUpkjFM070OXV/7uLB4zChC72cKsVu7JJ7cwaKRIpuPmymZ55TU/xEN6wPovVk=-----END RSA PRIVATE KEY-----");
				crs.build("obj", new JSONObject());
				crs.resolve(new Context(), new HashMap<>(), new HashMap<>());
			}
			{
				Object obj;
				CruncherRsaSignSha256 crs = new CruncherRsaSignSha256();
				crs.build("ppk", "-----BEGIN RSA PRIVATE KEY-----MIIEpQIBAAKCAQEAp1qCdEqc280vzHxhdiBuEiFIQiR9c3Tu7pTqJFt6aB9voGSH6e5WvxkUf1Q/Ab9VSGbxM4h+EtB3KUKPepWC5ry24F2g2HP/XtPzyQ+LeC8K0xMAhvVMzx6sVfwDWQg/ZGkSFY0Oup7+7V948VrP1Go/30Z6ac5cpFzXA9/oF5engQIkK8pBWinlVOnSo8n4ELl0K1ly2EUoOHwM5rDfGfUoUCHEVrxmS70dT6I8gTVgbf3OIXzVOPFFn8jXyRSD/TWQDE15pi5CGPXwOTD4KvUILO5dyGw3KJRdZA5oX/efOZ80QhuG1WU+TwzFEkvIRZas5cKnRztHIgk0+MGvwQIDAQABAoIBAQALIBJ3kiU63Hleve8JuA6xMFq6IOxhEkc2/5nQKG+q/9n/HufFJqXIJu3sqAhXQKW1/jfvLdX5wLeHO+hHZSySU73RjQ/eC9oQbwUwGtMx/miZDxSUJDcCtt6MicgcMVbrgSulWJ7/y4WVeKkU3G1gCN8to+UL5x7UA2L83DP063GSYlt7lmaj8iwswPduG/NdXyFoF64FsaCIrnyeylVHJVn1mZ9k7CG6DGchvCoeoAKp82+UlV8m4NBQsH1gxWDKOIS1VNqyNppEADn7GVKVgv2bu4V4jw4NQ5VQ6b92oECwXy/iR0fTOLBhKvIU2GjNHP09dKp2OHANajWpvjnRAoGBAOe5Mt1QqIAtREZ/UNuKYzHXZMECrwFBfhfSkVox2ZLFP9jGgGaAKv8ufhkqaVwZaBRRkaxKhYDZOTjjjAHb3TSWM6VLRflkJr9uRyNxm+ot+7F8krhguF5b4s9BHNrnW74nfqCVuJSSsGNUwsOp3kLyOxtk6G65zydHRJk/le05AoGBALji6qbbfcInZjVSTlg9HpcFbpEnR6XRnclxVF+xRixSrGcHj8S5JIcN1/TQ10koDheJz9SR6XaWKVsa/6YB+PK7pZ/2TkKoz7R1kJV/TheIGN7lDruYw93zoOCmS+NEavkNUPcCF5TRfbBmdfFg/st345NbhC/za33OB/xt697JAoGATbvZ7VZegEkvKzsQg+Vjq1ena/wbex9eTl2L9ybb5xmWdoD4l+oNAne1Q3nvyD6ZpJ9Mb/C74mJ8iLIEyKbaCQcOIjgjLCtFuFglMyWseR2d3od8rK99ieHnL0GXjXOpWhvjpbk0J1siIuBPSdYVQn4CCAmM04AvluyYfZRQFjkCgYEAoeiV9WlhyBUYvLlHQp7Dmx12pkbvx5a5xx8n9GUaPtZnhBJrJKu+AUduR0ZcQgSB0rss9cqR2PfHX7FVD9vKV0dc06ivAFE1E6Og6kqB6LmWjhupuz8cx9ICoLkA3BWeWFwA+08pt3l9yGzx/GAkwapL8U/Dgvp4t8Ml2zmnxYECgYEAl6cOGQdkMl9karRurQhl/GvEx8pzTDJssvBw0Dl55yNIENPc6gcAZxI57O1jX7Bv4V+G5ekKqSQUmwnmBGUvRm4QuET5olav3F92mJjqGjhQ0HUpkjFM070OXV/7uLB4zChC72cKsVu7JJ7cwaKRIpuPmymZ55TU/xEN6wPovVk=-----END RSA PRIVATE KEY-----");
				crs.build("obj", obj = new JSONObject());
				String signature2 = (String)crs.resolve(new Context(), new HashMap<>(), new HashMap<>());

				CruncherRsaVerifySha256 crv = new CruncherRsaVerifySha256();
				crv.build("pk", "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp1qCdEqc280vzHxhdiBuEiFIQiR9c3Tu7pTqJFt6aB9voGSH6e5WvxkUf1Q/Ab9VSGbxM4h+EtB3KUKPepWC5ry24F2g2HP/XtPzyQ+LeC8K0xMAhvVMzx6sVfwDWQg/ZGkSFY0Oup7+7V948VrP1Go/30Z6ac5cpFzXA9/oF5engQIkK8pBWinlVOnSo8n4ELl0K1ly2EUoOHwM5rDfGfUoUCHEVrxmS70dT6I8gTVgbf3OIXzVOPFFn8jXyRSD/TWQDE15pi5CGPXwOTD4KvUILO5dyGw3KJRdZA5oX/efOZ80QhuG1WU+TwzFEkvIRZas5cKnRztHIgk0+MGvwQIDAQAB-----END PUBLIC KEY-----");
				crv.build("obj", signature2);
				crv.build("against", obj);
				System.out.println((Boolean)crv.resolve(new Context(), new HashMap<>(), new HashMap<>()));
				Assert.assertTrue((Boolean)crv.resolve(new Context(), new HashMap<>(), new HashMap<>()));
			}

		} catch (IllegalArgumentException ex)
		{
			ex.printStackTrace();
		}
	}
}
