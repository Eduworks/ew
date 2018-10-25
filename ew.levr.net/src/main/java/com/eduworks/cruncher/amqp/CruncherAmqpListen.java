package com.eduworks.cruncher.amqp;

import com.eduworks.levr.servlet.impl.LevrResolverServlet;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.resolver.Resolvable;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CruncherAmqpListen extends Cruncher {
	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException {
		String hostName = optAsString("hostName", "localhost", c, parameters, dataStreams);
		Integer port = optAsInteger("port", -1, c, parameters, dataStreams);
		String username = optAsString("username","", c, parameters, dataStreams);
		String password = optAsString("password","", c, parameters, dataStreams);
		String queueName = optAsString("queue","", c, parameters, dataStreams);
		String exchangeName = optAsString("exchangeName",optAsString("exchange","",c,parameters,dataStreams),c,parameters,dataStreams);
		String exchangeType = optAsString("exchangeType","topic",c,parameters,dataStreams);
		String routingKey = optAsString("routingKey","", c, parameters, dataStreams);
		boolean durable = optAsBoolean("durable", false, c, parameters, dataStreams);
		boolean exclusive = optAsBoolean("exclusive", false, c, parameters, dataStreams);
		boolean autoDelete = optAsBoolean("autoDelete", false, c, parameters, dataStreams);

		Channel channel = CruncherAmqpSend.getChannel(hostName, port, username,password,queueName, exchangeName,exchangeType,routingKey,durable, exclusive, autoDelete);
		final Resolvable op = (Resolvable) get("obj");
		try {
			channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
					Context c = new Context();
					try {
						if (body == null)
							return;
						Map<String, String[]> newParameters = new HashMap<String, String[]>(parameters);
						newParameters.put("body", new String[]{new String(body)});
						newParameters.put("consumerTag", new String[]{consumerTag});
						newParameters.put("routingKey", new String[]{envelope.getRoutingKey()});
						newParameters.put("exchange", new String[]{envelope.getExchange()});
						newParameters.put("deliveryTag", new String[]{Long.toString(envelope.getDeliveryTag())});
						newParameters.put("contentEncoding", new String[]{properties.getContentEncoding()});
						newParameters.put("contentType", new String[]{properties.getContentType()});
						newParameters.put("type", new String[]{properties.getType()});
						newParameters.put("appId", new String[]{properties.getAppId()});
						newParameters.put("clusterId", new String[]{properties.getClusterId()});
						newParameters.put("userId", new String[]{properties.getUserId()});
						newParameters.put("correlationId", new String[]{properties.getCorrelationId()});
						newParameters.put("expiration", new String[]{properties.getExpiration()});
						newParameters.put("replyTo", new String[]{properties.getReplyTo()});
						newParameters.put("className", new String[]{properties.getClassName()});
						newParameters.put("messageId", new String[]{properties.getMessageId()});
						newParameters.put("classId", new String[]{Integer.toString(properties.getClassId())});
						log.debug(consumerTag + " Received: " + body);
						LevrResolverServlet.initConfig(System.out, null);
						((Resolvable) op).resolve(c, newParameters, dataStreams);
						c.success();
					} catch (Throwable e) {
						c.failure();
						if (!(e instanceof RuntimeException))
							e.printStackTrace();
						else if (e.getMessage() != null && !e.getMessage().isEmpty())
							System.out.println(e.getMessage());
					} finally {
						c.finish();
					}
					super.handleDelivery(consumerTag, envelope, properties, body);
				}
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	public String getDescription() {
		return "Listens for AMQP messages and executes 'obj'. Payload is in @body.";
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
		return jo("obj","Resolvable","hostname","String","port","Integer","queue","String","?durable","Boolean","?exclusive","Boolean","?autoDelete","Boolean");

	}
}
