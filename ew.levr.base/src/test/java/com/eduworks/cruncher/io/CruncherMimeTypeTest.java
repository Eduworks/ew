package com.eduworks.cruncher.io;

import java.io.InputStream;
import java.util.HashMap;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import com.eduworks.cruncher.file.CruncherFileLoad;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.io.CruncherMimeType;

public class CruncherMimeTypeTest
{
	@Test
	public void testXml()
	{
		CruncherFileLoad load = new CruncherFileLoad();
		load.build("path", "pom.xml");
		CruncherMimeType cmt = new CruncherMimeType();
		cmt.build("file", load);

		Object result = null;
		try
		{
			result = cmt.resolve(new Context(), new HashMap<String, String[]>(), new HashMap<String, InputStream>());
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		if (result.equals("text/xml"))
			Assert.assertEquals("text/xml", result);
		else
			Assert.assertEquals("application/xml", result);
	}
}
