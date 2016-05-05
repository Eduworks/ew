package com.eduworks.cruncher.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherGetTableFromCsv extends Cruncher
{
public static boolean warned = false;
	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		try
		{
			if (!warned)
			{
				System.err.print("This was modified during the last migration to Maven. Please ensure it is working as expected.");
				warned = true;
			}
			CSVParser csvp = new CSVParser(new StringReader(getObj(c, parameters, dataStreams).toString()), CSVFormat.DEFAULT);
			boolean ignoreFirstLine = optAsBoolean("ignoreFirstLine", false, c, parameters, dataStreams);

			List<CSVRecord> allValues;
			allValues = csvp.getRecords();
			boolean first = true;
			JSONArray results = new JSONArray();
			for (CSVRecord ary : allValues)
			{
				if (first && ignoreFirstLine)
				{
					first = false;
					continue;
				}
				JSONArray interim = new JSONArray();
				results.put(interim);
				for (String s : ary)
					interim.put(s);
			}
			return results;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public String getDescription()
	{
		return "Converts a CSV document into a JSONArray of JSONArrays.";
	}

	@Override
	public String getReturn()
	{
		return "JSONArray";
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
