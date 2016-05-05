package com.eduworks.levr.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.interfaces.EwJsonSerializable;
import com.eduworks.lang.EwList;
import com.eduworks.lang.util.EwJson;
import com.eduworks.levr.servlet.impl.LevrResolverServlet;
import com.eduworks.resolver.Context;
import com.eduworks.util.io.InMemoryFile;

@ServerEndpoint(value = "/ws/custom")
public class LevrResolverWebSocket
{
	protected static Logger log = Logger.getLogger(LevrResolverWebSocket.class);

	public LevrResolverWebSocket()
	{

	}

	@OnOpen
	public void onOpen(Session session) throws IOException
	{
		session.getBasicRemote().sendText("onOpen");
	}

	@OnMessage
	public void onMessage(Session session, String message)
	{
		try
		{
			JSONObject mObject = new JSONObject(message);
			Map<String, String[]> map = new HashMap<String, String[]>();
			for (String key : EwJson.getKeys(mObject))
			{
				String[] value = null;
				Object val = mObject.get(key);
				if (val instanceof String)
					value = new String[] { (String) val };
				else if (val instanceof JSONArray)
					value = new EwList<String>((JSONArray) val).toStringArray();
				else
					value = new String[] { val.toString() };
			}
			Object result = LevrResolverServlet
					.execute(log, false, mObject.getString("function"), new Context(), map, new HashMap<String, InputStream>(), true);
			if (result instanceof String)
			{
				session.getBasicRemote().sendText(result.toString());
			}
			else if (result instanceof Number)
			{
				session.getBasicRemote().sendText(result.toString());
			}
			else if (result instanceof EwJsonSerializable)
			{
				session.getBasicRemote().sendText(((EwJsonSerializable) result).toString());
			}
			else if (result instanceof InMemoryFile)
			{
				InMemoryFile f = (InMemoryFile) result;

				session.getBasicRemote().sendBinary(ByteBuffer.wrap(f.data));
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
			JSONObject error = new JSONObject();
			try
			{
				error.put("error", e.toString());
				session.getBasicRemote().sendText(error.toString());
			}
			catch (JSONException e1)
			{
				e1.printStackTrace();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			JSONObject error = new JSONObject();
			try
			{
				error.put("error", e.toString());
				session.getBasicRemote().sendText(error.toString());
			}
			catch (JSONException e1)
			{
				e1.printStackTrace();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
	}

	@OnError
	public void onError(Throwable t)
	{
		t.printStackTrace();
	}

	@OnClose
	public void onClose(Session session)
	{

	}

}
