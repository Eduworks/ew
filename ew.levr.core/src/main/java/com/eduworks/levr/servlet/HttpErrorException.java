package com.eduworks.levr.servlet;

public class HttpErrorException extends RuntimeException
{
	public HttpErrorException(String message)
	{
		super(message);
	}
	private static final long serialVersionUID = 1L;
	public short httpStatus = 500;
	@Override
	public String toString()
	{
		return this.getMessage();
	}
}
