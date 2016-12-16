package com.eduworks.resolver;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class AnonymousInnerResolvable implements Resolvable
{

	protected Integer codeLineNumber = null;
	protected Integer codeColNumber = null;
	protected String codeFileName = null;
	protected String codeMethod = null;

	@Override
	public void setLineAndColAndSource(Integer line, Integer col, String file, String method)
	{
		codeLineNumber = line;
		codeColNumber = col;
		codeFileName = file;
		codeMethod = method;
	}

	@Override
	public void build(String key, Object value) throws JSONException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String toOriginalJson() throws JSONException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toJson() throws JSONException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getReturn()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAttribution()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
