package com.eduworks.resolver.db.couchdb;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.ace.product.levr.adapter.DocumentDbInterface;
import com.eduworks.resolver.Context;
import com.fourspaces.couchdb.Document;

public class CruncherPutAttachment extends CruncherDocument
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		Logger.getLogger(getClass()).info("This class has recently been converted to a Cruncher by Fritz.");

		final String id = getAsString("id", c,parameters,dataStreams);
		final String databaseName = getAsString("databaseName", c,parameters,dataStreams);
		final String attachmentId = getAsString("name", c,parameters,dataStreams);
		final String dataStreamId = getAsString("dataStreamId", c,parameters,dataStreams);
		final String lockName = databaseName + id;
		lockDocument(lockName);
		try
		{
			if (dataStreams.containsKey(dataStreamId) == false)
				throw new RuntimeException("Failed to read datastream.");
			Document d = DocumentDbInterface.getDocument(this, id, c,parameters,dataStreams);

			if (d == null)
				d = DocumentDbInterface.getDocument(this, decodeValue(id), c,parameters,dataStreams);
			if (d == null)
				d = DocumentDbInterface.getDocument(this, encodeValue(id), c,parameters,dataStreams);
			if (d == null)
				return null;

			security(d, c,parameters,dataStreams);
			return DocumentDbInterface
					.saveAttachment(this, id, encodeValue(attachmentId), d.getRev(), dataStreams.get(dataStreamId), c,parameters,dataStreams);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			unlockDocument(lockName);
		}

	}

	@Override
	public String getDescription()
	{
		return "Puts a file into a document in a NoSQL CouchDb table.\n" +
				"The endpoint is defined by _serverHostname, _serverPort, _serverLogin, _serverPassword, _databasePrefix and _databaseName\n" +
				"The NoSQL Document is defined by _id"+
				"The document variable is defined by 'name' and the stream to retreive the file from (in the MPM stream) is defined by dataStreamId";
	}

	@Override
	public String[] getResolverNames()
	{
		return new String[]{getResolverName(),"attachmentPut"};
	}
	@Override
	public String getReturn()
	{
		return "null";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_UCASTER;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("_serverHostname","String","_serverPort","Number","_serverLogin","String","_serverPassword","String","?_databasePrefix","String","_databaseName","String","_id","String","name","String","dataStreamId","String");
	}
}
