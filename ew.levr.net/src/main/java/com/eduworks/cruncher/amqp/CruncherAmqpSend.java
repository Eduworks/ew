package com.eduworks.cruncher.amqp;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class CruncherAmqpSend extends Cruncher {

	public static Map<String, ConnectionFactory> factories = new HashMap<>();
	public static Map<ConnectionFactory, Connection> connections = new HashMap<>();
	public static Map<String, Channel> channels = new HashMap<>();

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException {
		String hostName = optAsString("hostName", "localhost", c, parameters, dataStreams);
		Integer port = optAsInteger("port", -1, c, parameters, dataStreams);
		String username = optAsString("username","", c, parameters, dataStreams);
		String password = optAsString("password","", c, parameters, dataStreams);
		String queueName = optAsString("queue","", c, parameters, dataStreams);
		String routingKey = optAsString("routingKey","", c, parameters, dataStreams);
		String exchangeName = optAsString("exchangeName","",c,parameters,dataStreams);
		String exchangeType = optAsString("exchangeType","topic",c,parameters,dataStreams);
		boolean durable = optAsBoolean("durable", false, c, parameters, dataStreams);
		boolean exclusive = optAsBoolean("exclusive", false, c, parameters, dataStreams);
		boolean autoDelete = optAsBoolean("autoDelete", false, c, parameters, dataStreams);
		Object o = getObj(c, parameters, dataStreams);

		Channel channel = getChannel(hostName, port, username,password,queueName, exchangeName,exchangeType,routingKey,durable, exclusive, autoDelete);
		try {
			channel.basicPublish(exchangeName, exchangeName.isEmpty() ? queueName : routingKey, null, o.toString().getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	public static Channel getChannel(String hostName, Integer port,String username,String password, String queueName, String exchangeName,String exchangeType,String routingKey,boolean durable, boolean exclusive, boolean autoDelete) {
		ConnectionFactory factory = null;
		synchronized (factories) {
			if (factories.containsKey(hostName+username+password+port))
				factory = factories.get(hostName+username+password+port);
			else {
				factory = new ConnectionFactory();
				factory.setHost(hostName);
				if (username != null && !username.isEmpty())
				factory.setUsername(username);
				if (password != null && !password.isEmpty())
				factory.setPassword(password);
				if (port != null)
					factory.setPort(port);
				factories.put(hostName+username+password+port, factory);
			}
		}
		Connection connection = null;
		synchronized (connections) {
			if (connections.containsKey(factory))
				connection = connections.get(factory);
			else {
				try {
					connection = factory.newConnection();
					connections.put(factory, connection);
				} catch (IOException e) {
					throw new RuntimeException(e);
				} catch (TimeoutException e) {
					throw new RuntimeException(e);
				}
			}
		}
		String cacheName = hostName + port +username+password+ queueName+":"+exchangeName+exchangeType+routingKey + durable + exclusive + autoDelete;
		Channel channel = null;
		synchronized (channels) {
			try {
				if (!channels.containsKey(cacheName)) {
					channel = connection.createChannel();
					if (!queueName.isEmpty())
					channel.queueDeclare(queueName, durable, exclusive, autoDelete, null);
					if (!exchangeName.isEmpty())
						channel.exchangeDeclare(exchangeName,exchangeType);
					if (!queueName.isEmpty() && !exchangeName.isEmpty())
						channel.queueBind(queueName,exchangeName,routingKey);
					channels.put(cacheName, channel);
				} else
					channel = channels.get(cacheName);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return channel;
	}

	@Override
	public String getDescription() {
		return "Sends an AMQP Message. May only work with RabbitMQ. RIP EDI.";
	}

	@Override
	public String getReturn() {
		return null;
	}

	@Override
	public String getAttribution() {
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException {
		return jo("obj","Object","hostname","String","port","Integer","queue","String","?durable","Boolean","?exclusive","Boolean","?autoDelete","Boolean");
	}
}