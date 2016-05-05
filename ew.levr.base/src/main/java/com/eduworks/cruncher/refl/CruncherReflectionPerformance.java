package com.eduworks.cruncher.refl;

import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.levr.servlet.impl.LevrResolverServlet;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.resolver.Resolvable;

public class CruncherReflectionPerformance extends Cruncher
{

	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		JSONObject results = new JSONObject();
		for (String webService : LevrResolverServlet.resolvableWebServices.keySet())
			results.put(webService, buildObj(LevrResolverServlet.resolvableWebServices.get(webService), null));
		for (String webService : LevrResolverServlet.resolvableFunctions.keySet())
			results.put(webService, buildObj(LevrResolverServlet.resolvableFunctions.get(webService), null));
		return results;
	}

	private JSONObject buildObj(Resolvable resolvable, Double parentMs) throws JSONException
	{
		JSONObject result = null;
		result = new JSONObject();
		Cruncher cruncher = (Cruncher) resolvable;
		Double myParentMs = null;
		if (resolvable instanceof Cruncher)
		{
			long executions = cruncher.executions.get();
			if (executions == 0)
				return null;
			long nanosProcessing = cruncher.nanosProcessing.get();
			long nanosInside = cruncher.nanosInside.get();
			if (nanosProcessing == 0 && nanosInside == 0)
				return null;
			double processingMs = nanosProcessing / 1000000.0;
			double processingMsAvg = processingMs / executions;
			double insideMs = nanosInside / 1000000.0;
			double insideMsAvg = insideMs / executions;
			if (!Double.isNaN(processingMs))
				result.put("_processingMs", processingMs);
			if (!Double.isNaN(insideMs))
				result.put("_executionMs", insideMs);
			result.put("_executions", cruncher.executions);
			if (!Double.isNaN(processingMsAvg))
				result.put("_processingMsAvg", processingMsAvg);
			if (!Double.isNaN(insideMsAvg))
				result.put("_executionMsAvg", insideMsAvg);
			if (parentMs != null)
			{
				double percentage = nanosInside / 1000000.0 / parentMs;
				if (!Double.isNaN(percentage))
					result.put("_percentage", percentage);
			}
			result.put("_cruncherName", cruncher.getClass().getSimpleName());
			myParentMs = insideMs;
		}
		for (String key : cruncher.keySet())
		{
			Object o = cruncher.get(key);
			if (o instanceof Resolvable)
				result.put(key, buildObj((Resolvable) o, myParentMs));
			else
				result.put(key, o);
		}
		return result;
	}

	@Override
	public String getDescription()
	{
		return "Retreives performance metrics from the syntax tree, allowing slowdowns to be inpointed.";
	}

	@Override
	public String getReturn()
	{
		return "JSONObject";
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
