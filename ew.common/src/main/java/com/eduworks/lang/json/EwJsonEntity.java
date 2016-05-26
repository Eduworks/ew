package com.eduworks.lang.json;

import java.io.Writer;

import org.json.JSONException;

/**
 * Defines a basic, Eduworks JSON entity. Provides an API for methods that all
 * JSON based entities tend to have even though they are not related.
 * 
 * @author dharvey
 * @since September, 2011
 */
@Deprecated
public interface EwJsonEntity
{
	/***
	 * Pretty prints the string.
	 * 
	 * @param indentFactor
	 *            Indentation in spaces.
	 * @return pretty-printed JSON text of this JSON.
	 * @throws JSONException
	 *             If the JSON is malformed.
	 */
	public String toString(int indentFactor) throws JSONException;

	/***
	 * Write the contents of the JSON as text to a writer.
	 * 
	 * @param writer Writer to write to.
	 * @return The writer we wrote to.
	 * @throws JSONException If the JSON generated becomes malformed.
	 */
	public Writer write(Writer writer) throws JSONException;
}
