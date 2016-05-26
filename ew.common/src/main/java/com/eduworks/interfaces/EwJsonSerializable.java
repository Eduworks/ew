package com.eduworks.interfaces;

import org.json.JSONException;
import org.json.JSONObject;

/***
 * Allow an object to serialize into a JSON Object.
 * 
 * @author Fritz
 * 
 */
public interface EwJsonSerializable
{
	/***
	 * Returns the object serialized as a JSON Object.
	 * @return Serialized Object
	 * @throws JSONException If the JSON object being constructed is malformed.
	 */
	JSONObject toJsonObject() throws JSONException;
}
