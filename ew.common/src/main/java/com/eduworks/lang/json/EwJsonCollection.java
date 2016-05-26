package com.eduworks.lang.json;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.lang.json.impl.EwJsonArray;
import com.eduworks.lang.json.impl.EwJsonObject;
import com.eduworks.lang.util.EwJson;

/**
 * Defines a common API for classes extending members of the org.json library,
 * such that the underlying functionality can be manipulated as an
 * EwJsonCollection.
 * 
 * Provides these basic features and functionality (see implementations for
 * specifics):
 * <ul>
 * <li>an extensible API of customized JSON functionality</li>
 * <li>an API for converting or merging common classes into JSON</li>
 * <li>an API for encoding and decoding string values</li>
 * </ul>
 * Known implementations:
 * <ul>
 * <li>{@link EwJsonArray}</li>
 * <li>{@link EwJsonObject}</li>
 * </ul>
 * 
 * @author dharvey
 * @since September, 2011
 */
@SuppressWarnings("rawtypes")
@Deprecated
public interface EwJsonCollection extends EwJsonEntity
{
	/***
	 * Accumulate values under a key as a JSONArray.
	 * 
	 * @param ref
	 *            Object to use as a key.
	 * @param value
	 *            Object to use as a value.
	 * @return This collection.
	 * @throws JSONException
	 *             If the JSON becomes malformed due to the values.
	 */
	public EwJsonCollection accumulate(Object ref, Object value) throws JSONException;

	/***
	 * True IFF the collection contains element.
	 * 
	 * @param element
	 *            The element to test for.
	 * @return true if the object is stored as a value in the collection
	 */
	public boolean contains(Object element);

	public Object get(Object ref) throws JSONException;

	public boolean getBoolean(Object ref) throws JSONException;

	public double getDouble(Object ref) throws JSONException;

	public int getInt(Object ref) throws JSONException;

	public EwJsonArray getJSONArray(Object ref) throws JSONException;

	public EwJsonObject getJSONObject(Object ref) throws JSONException;

	public EwJsonCollection getJSONCollection(Object ref) throws JSONException;

	public long getLong(Object ref) throws JSONException;

	public String getString(Object ref) throws JSONException;

	public Set<String> keySet();

	public Iterator<String> keys();

	/***
	 * Returns a newly instantiated {@link EwJsonCollection} of the same type as
	 * this one.
	 * 
	 * @return A newly instantiated {@link EwJsonCollection} of the same type as
	 *         this one
	 */
	public EwJsonCollection emptyInstance();

	/***
	 * Checks for presence of a value corresponding to either a simple or
	 * complex key/index in the collection.
	 * 
	 * @param ref
	 *            Dot/Bracket Notated Complex String.
	 * @return True IFF the value was found.
	 */
	public boolean hasComplex(Object ref);

	/**
	 * Checks for presence of a value corresponding to a non-complex key/index
	 * in the collection.
	 * 
	 * @param ref
	 *            Key to use to find the object.
	 * @return True IFF the value was found.
	 */
	public boolean hasSimple(Object ref);

	public boolean isEmpty();

	public boolean isNull(Object ref);

	public String join(String separator) throws JSONException;

	/***
	 * Measure the number of items in the collection.
	 * 
	 * @return the number of keys or indices stored in this collection.
	 */
	public int length();

	/***
	 * Copy everything from the incoming collection to this one. Overwrites
	 * existing values.
	 * 
	 * @param value
	 *            Other collection.
	 * @return This collection with additional values.
	 * @throws JSONException
	 *             If objects from the other collection malformed this
	 *             collection.
	 */
	public EwJsonCollection merge(EwJsonCollection value) throws JSONException;

	/***
	 * Attempt to parse value as json and merge values; if not parsable, do
	 * nothing.
	 * 
	 * @param value
	 *            Other value, assumed to be JSON.
	 * @return This, with additional values.
	 * @throws JSONException
	 *             If objects from the other collection malformed this
	 *             collection.
	 */
	public EwJsonCollection merge(Object value) throws JSONException;

	public Object opt(Object ref);

	public Object opt(Object ref, Object defaultValue);

	public boolean optBoolean(Object ref);

	public boolean optBoolean(Object ref, boolean defaultValue);

	public double optDouble(Object ref);

	public double optDouble(Object ref, double defaultValue);

	public int optInt(Object ref);

	public int optInt(Object ref, int defaultValue);

	public EwJsonArray optJSONArray(Object ref);

	public EwJsonArray optJSONArray(Object ref, JSONArray defaultValue);

	public EwJsonObject optJSONObject(Object ref);

	public EwJsonObject optJSONObject(Object ref, JSONObject defaultValue);

	public EwJsonCollection optJSONCollection(Object ref);

	public EwJsonCollection optJSONCollection(Object ref, EwJsonCollection defaultValue);

	public long optLong(Object ref);

	public long optLong(Object ref, long defaultValue);

	public String optString(Object ref);

	public String optString(Object ref, String defaultValue);

	public EwJsonCollection put(Object ref, boolean value) throws JSONException;

	public EwJsonCollection put(Object ref, Collection value) throws JSONException;

	public EwJsonCollection put(Object ref, double value) throws JSONException;

	public EwJsonCollection put(Object ref, int value) throws JSONException;

	public EwJsonCollection put(Object ref, long value) throws JSONException;

	public EwJsonCollection put(Object ref, Map value) throws JSONException;

	public EwJsonCollection put(Object ref, Object value) throws JSONException;

	public EwJsonCollection putOnce(Object ref, Object value) throws JSONException;

	public EwJsonCollection putOpt(Object ref, Object value);

	/**
	 * Reduce collections to the single element they contain if possible, but
	 * leave the original object unchanged.
	 * 
	 * @return the reduced value, but leave this instance unchanged
	 * @see EwJson#reduce(Object)
	 */
	public Object reduce();

	/***
	 * Decode a String value at ref using URL encoding.
	 * 
	 * @param ref
	 *            The key to look up.
	 * @return The URL decoded value.
	 */
	public String urlDecode(Object ref);

	/***
	 * Encode a String value at ref for the URL.
	 * 
	 * @param ref
	 *            The key to look up.
	 * @return The URL encoded value.
	 */
	public String urlEncode(Object ref);

}
