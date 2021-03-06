package com.eduworks.lang.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

import com.eduworks.interfaces.EwJsonSerializable;
import com.eduworks.lang.EwList;
import com.eduworks.lang.json.EwJsonCollection;
import com.eduworks.lang.json.impl.EwJsonArray;
import com.eduworks.lang.json.impl.EwJsonObject;

/**
 * Provides general helper methods for {@link JSONArray} and {@link JSONObject}.
 * When methods deal in specific {@link EwJsonCollection}s, they should be put
 * in their respective classes. This class is reserved specifically for the
 * basic classes in org.json.*.
 *
 * @author dharvey
 * @since September, 2011
 */
public class EwJson
{
	public static final boolean DEFAULT_BOOLEAN = false;
	public static final double DEFAULT_DOUBLE = Double.NaN;
	public static final int DEFAULT_INT = 0;
	public static final int DEFAULT_INDEX = -1;
	public static final long DEFAULT_LONG = 0;
	public static final String DEFAULT_STRING = "";
	public static final Object DEFAULT_VALUE = null;

	private final static Pattern KEY_PATTERN = Pattern.compile("\\.");

	private final static char ARR_OPEN = '[';
	private final static char ARR_CLOSE = ']';
	private final static char KEY_DELIM = '.';
	private final static char INDEX_DELIM = ':';

	private EwJson()
	{
	}

	@Deprecated
	public static EwJsonCollection accumulate(EwJsonCollection json, Object ref, Object value) throws JSONException
	{
		if (json == null)
			return null;

		validateDecimal(value);

		final Object object = json.opt(ref);

		if (EwJson.isNull(object))
		{
			// First JSONArray in the slot is wrapped, to prevent accumulation
			// to it later
			json.putOpt(ref, (value instanceof JSONArray) ? new EwJsonArray().put(value) : value);
		}
		else if (object instanceof JSONArray)
		{
			// Later items in the slot are accumulated to an existing JSONArray
			EwJsonArray.convert((JSONArray) object).put(value);
		}
		else
		{
			// Slot contains a non-accumulated value, wrap it in an array and
			// append to it
			json.putOpt(ref, ((new EwJsonArray()).put(object)).put(value));
		}

		return json;
	}

	@Deprecated
	public static boolean contains(JSONArray json, Object element)
	{
		if (json == null || element == null)
			return false;

		for (int i = 0; i < json.length(); i++)
			try
			{
				if (elementsEqual(json.get(i), element))
					return true;
			}
			catch (Exception e)
			{
				continue;
			}

		return false;
	}

	@Deprecated
	public static boolean contains(JSONObject json, Object element)
	{
		if (json == null || element == null)
			return false;

		@SuppressWarnings("unchecked")
		final Iterator<String> keys = json.keys();

		while (keys.hasNext())
			try
			{
				if (elementsEqual(json.get(keys.next()), element))
					return true;
			}
			catch (Exception e)
			{
				continue;
			}

		return false;
	}

	@Deprecated
	public static boolean equals(EwJsonCollection thisOne, EwJsonCollection thatOne)
	{
		if (thisOne == null && thatOne == null)
			return true;

		if (thisOne == null || thatOne == null)
			return false;

		for (String key : thisOne.keySet())
			if (!thatOne.hasComplex(key))
				return false;
			else if (!thisOne.opt(key).equals(thatOne.opt(key)))
				return false;

		for (String key : thatOne.keySet())
			if (!thisOne.hasComplex(key))
				return false;
			else if (!thatOne.opt(key).equals(thisOne.opt(key)))
				return false;

		return true;
	}

	@Deprecated
	private static boolean elementsEqual(Object value, Object element)
	{
		return (value != null && value.equals(element));
	}

	/***
	 * Recurses over coll, and adds all nested non-null, non-json elements.
	 * 
	 * @param coll
	 *            Collection to recurse over.
	 * @return Set of all elements.
	 */
	@Deprecated
	public static Set<String> getNodes(EwJsonCollection coll)
	{
		final Set<String> nodes = new HashSet<String>();

		if (coll instanceof JSONArray)
		{
			final JSONArray array = (JSONArray) coll;

			for (int i = 0; i < array.length(); i++)
			{
				final Object inner = wrap(array.opt(i), true);

				if (inner instanceof EwJsonCollection)
					nodes.addAll(getNodes((EwJsonCollection) inner));

				else if (isNode(inner))
					nodes.add(inner.toString());
			}
		}
		else if (coll instanceof JSONObject)
		{
			final JSONObject object = (JSONObject) coll;

			@SuppressWarnings("unchecked")
			final Iterator<String> keys = object.keys();

			while (keys.hasNext())
			{
				final Object inner = wrap(object.opt(keys.next()), true);

				if (inner instanceof EwJsonCollection)
					nodes.addAll(getNodes((EwJsonCollection) inner));

				else if (isNode(inner))
					nodes.add(inner.toString());
			}
		}

		return nodes;
	}

	/***
	 * Recurses over coll, and adds any nested non-null, non-json elements at
	 * key regardless of depth
	 * 
	 * @param coll
	 *            Collection to recurse over.
	 * @param key
	 *            Key to use to dive in.
	 * @return Set of elements.
	 */
	@Deprecated
	public static Set<String> getNodesByKey(EwJsonCollection coll, String key)
	{
		final Set<String> nodes = new HashSet<String>();

		if (coll instanceof JSONArray)
		{
			final JSONArray array = (JSONArray) coll;

			for (int i = 0; i < array.length(); i++)
			{
				/* Wrap each array element, and recurse if possible */

				final Object inner = wrap(array.opt(i), true);

				if (inner instanceof EwJsonCollection)
					nodes.addAll(getNodesByKey((EwJsonCollection) inner, key));
			}
		}
		else if (coll instanceof JSONObject)
		{
			/* Wrap element at key, and recurse if possible */

			final Object inner = wrap(coll.opt(key), true);

			if (inner instanceof EwJsonCollection)
				nodes.addAll(getNodesByKey((EwJsonCollection) inner, key));

			else if (isNode(inner))
				nodes.add(inner.toString());
		}

		return nodes;
	}

	@Deprecated
	private static boolean isNode(Object node)
	{
		return (node instanceof String && !((String) node).trim().isEmpty());
	}

	@Deprecated
	public static JSONArray merge(JSONArray into, JSONArray from)
	{
		return merge(into, from, 0, into.length(), from.length());
	}

	@Deprecated
	public static JSONArray merge(JSONArray into, JSONArray from, int startCopy, int startPut, int length)
	{
		if (into == null)
			return null;

		if (from == null)
			return into;

		if (!isValidIndex(from, startCopy))
			return into;

		try
		{
			// Loop may break before limit
			final int limit = (startCopy + length);

			final int to = (startPut < 0) ? into.length() // Start after last
															// element
					: startPut; // Start at specified element

			for (int here = to, there = startCopy; there < limit; here++, there++)
			{
				if (there < from.length())
					into.put(here, wrap(from.get(there)));

				else
					break; // Nothing more to copy
			}

		}
		catch (JSONException ex)
		{
		}

		return into;
	}

	@SuppressWarnings("unchecked")
	@Deprecated
	public static JSONObject merge(JSONObject into, JSONObject from)
	{
		if (into == null)
			return null;

		if (from == null)
			return into;

		try
		{
			Iterator<String> keys = from.keys();
			while (keys.hasNext())
			{
				String s = keys.next();
				into.put(s, wrap(from.get(s)));
			}
		}
		catch (JSONException ex)
		{
		}

		return into;
	}

	@SuppressWarnings("unchecked")
	public static List<String> getKeys(JSONObject edges)
	{
		final List<String> keys = new ArrayList<String>();
		final Iterator<String> sortedKeys = edges.keys();

		while (sortedKeys.hasNext())
			keys.add(sortedKeys.next());
		if (!(edges instanceof EwJsonObject))
			EwList.sort(keys);
		return keys;
	}

	@SuppressWarnings("unchecked")
	public static List getValues(JSONObject edges) throws JSONException
	{
		final List<Object> values = new ArrayList<Object>();
		final Iterator<String> sortedKeys = edges.keys();

		while (sortedKeys.hasNext())
			values.add(edges.get(sortedKeys.next()));
		return values;
	}

	@SuppressWarnings("unchecked")
	public static List<String> getKeysUnsorted(JSONObject edges)
	{
		final List<String> keys = new ArrayList<String>();
		final Iterator<String> unsortedKeys = edges.keys();

		while (unsortedKeys.hasNext())
			keys.add(unsortedKeys.next());

		return keys;
	}

	public static JSONArray copyFromObject(JSONObject object) throws JSONException
	{
		final JSONArray result = new EwJsonArray();

		for (String key : getKeys(object))
			result.put(object.get(key));

		return result;
	}

	@Deprecated
	public static Object derefComplexKey(Object object, String key) throws JSONException
	{
		if (isNull(object))
			return null;

		if (!isComplexKey(key))
			return getElement(object, key);

		final EwList<Object> results = processComplexKey(object, KEY_PATTERN.split(key));

		switch (results.size())
		{
			case 0:
				return null;

			case 1:
				return results.get(0);

			default:
				return results;
		}
	}

	@Deprecated
	public static boolean hasComplexKey(Object object, String key) throws JSONException
	{
		final Object json = tryParseJson(object, true);

		if (!isComplexKey(key) || !isJson(json))
			return false;

		return (processComplexKey(json, KEY_PATTERN.split(key)).size() > 0);
	}

	@Deprecated
	@SuppressWarnings("unchecked")
	private static EwList<Object> processComplexKey(Object object, String[] innerKeys) throws JSONException
	{
		if (isNull(object) || innerKeys == null)
			return new EwList<Object>();

		final EwList<Object> results = new EwList<Object>();
		final int lastIndex = (innerKeys.length - 1);
		final String lastKey = innerKeys[lastIndex];

		switch (lastIndex)
		{
			case -1:
				return results;

			case 0:
				// Try as bracketed "element[key]" or object index key
				// "element:index"
				results.add(derefCompositeKey(object, lastKey));

				return results;

			default:
				results.add(tryParseJson(object, false));
		}

		/*
		 * Iterate over all but last key segment to add qualifying json objects
		 * to list
		 */

		for (int i = 0; i < lastIndex; i++)
		{
			final String innerKey = innerKeys[i];

			/* Process this level of qualifying json objects */

			for (Object o : (EwList<Object>) results.clone())
			{
				final Object value = derefCompositeKey(o, innerKey);

				results.clear(); // Remove previous level objects

				if (value instanceof JSONObject)
				{
					results.add(value);
				}
				else if (value instanceof JSONArray)
				{
					final JSONArray ja = (JSONArray) value;
					for (int j = 0; j < ja.length(); j++)
					{
						final Object innerValue = derefCompositeKey(ja, (new Integer(j).toString()));

						if (isJson(innerValue))
							results.add(innerValue);
					}
				}
				else
					return results;
			}
		}

		final EwList<Object> finalResults = new EwList<Object>();

		for (Object o : results)
		{
			final Object value = derefCompositeKey(o, lastKey);

			if (!isNull(value))
				finalResults.add(value);
		}

		return finalResults;
	}

	@Deprecated
	private static Object derefCompositeKey(Object object, String key) throws JSONException
	{
		return derefCompositeKey(object, key, false);
	}

	@Deprecated
	private static Object derefCompositeKey(Object object, String key, boolean reduce) throws JSONException
	{
		if (object instanceof String)
			object = tryParseJson(object, false, reduce);

		if (object instanceof JSONObject)
		{
			final JSONObject jo = (JSONObject) object;

			// Check for key as it is
			if (jo.has(key))
				return jo.get(key);

			// Try parsing "element[key]"
			else if (isBracketedKey(key))
				return derefBracketedKey(object, key);

			// Try parsing "element:index"
			else if (isObjectIndexKey(key))
				return derefObjectIndexKey(object, key);
		}
		else if (object instanceof JSONArray)
		{
			// Check for integer or bracketed key: "0" or "[0]"
			if (isValidIndex(key))
				return ((JSONArray) object).opt(keyToIndex(key));

			// Try as composite array key: "key[1]"
			else if (isBracketedKey(key))
				return derefBracketedKey(object, key);

			// Try as object index key: "0:2", "key:2", "key[2]:3"
			else if (isObjectIndexKey(key))
				return derefObjectIndexKey(object, key);
		}

		// Try again, but reduce single-element json objects to the one element
		return (reduce) ? null : derefCompositeKey(object, key, true);
	}

	@Deprecated
	private static Object derefBracketedKey(Object object, String key) throws JSONException
	{
		final Object jsonObject = tryParseJson(object, true);

		if (jsonObject == null || key == null)
			return null;

		if (key.indexOf(KEY_DELIM) != -1)
			return derefComplexKey(jsonObject, key);

		if (!isBracketedKey(key))
			return getElement(jsonObject, key);

		/*
		 * Dereference key or index from array or object -- null if non-existent
		 */

		// For key "arr[0][1]": outer is "arr", inner is "0", next is "[1]"
		final int keyDelim = key.indexOf(ARR_OPEN);
		final int keyLength = key.indexOf(ARR_CLOSE) + 1;
		final String outerKey = key.substring(0, keyDelim);
		final String innerKey = key.substring(keyDelim + 1, keyLength - 1);

		if (isBracketedKey(innerKey))
			throw new JSONException("Invalid inner key: " + key);

		final String nextKey = (keyLength < key.length()) ? key.substring(keyLength) : "";

		if (nextKey.length() > 0 && nextKey.charAt(0) != ARR_OPEN)
			throw new JSONException("Invalid next key: " + key);

		final Object nextObject = (outerKey.isEmpty()) ? getElement(jsonObject, innerKey) : derefCompositeKey(getElement(jsonObject, outerKey), innerKey);

		return (nextKey.isEmpty()) ? nextObject : derefCompositeKey(nextObject, nextKey);
	}

	@Deprecated
	private static Object derefObjectIndexKey(Object object, String key) throws JSONException
	{
		object = tryParseJson(object, true);

		if (object == null || key == null)
		{
			return null;
		}
		else if (key.indexOf(KEY_DELIM) != -1)
		{
			return derefComplexKey(object, key);
		}
		else if (isObjectIndexKey(key))
		{
			final int delimIndex = key.indexOf(INDEX_DELIM);

			// For key "obj[0]:4", "obj[0]" is outer, "4" is inner
			final String outerKey = key.substring(0, delimIndex);
			final String innerKey = key.substring(delimIndex + 1);

			// For key "arr[0]:1" we return the 2nd value of arr's 1st element
			// For key "obj:2" we deref "obj" and return the value from its 3rd
			// key

			final Object deref;

			if (outerKey.isEmpty())
				deref = object;
			else
				deref = (isBracketedKey(outerKey)) ? derefBracketedKey(object, outerKey) : getElement(object, outerKey);

			// Call this with the numeric string value following ':'
			return derefObjectIndexKey(deref, innerKey);
		}
		else if (isValidIndex(key))
		{
			if (object instanceof JSONObject)
			{
				final JSONObject jo = (JSONObject) object;

				// Get the value at key specified by parsed index
				final List<String> keys = EwJson.getKeys(jo);
				final int index = keyToIndex(key);

				return (keys.size() > index) ? jo.opt(keys.get(index)) : null;
			}
		}

		return getElement(object, key);
	}

	@Deprecated
	private static Object getElement(Object object, Object ref)
	{
		final Object json = tryParseJson(object, true);

		if (json == null || ref == null)
			return null;

		if (json instanceof JSONArray)
			return ((JSONArray) json).opt(keyToIndex(ref));

		if (json instanceof JSONObject)
			return ((JSONObject) json).opt(ref.toString());

		return null;
	}

	@Deprecated
	public static int getElements(Object object)
	{
		final Object json = tryParseJson(object, true);

		if (json instanceof JSONArray)
			return ((JSONArray) json).length();

		if (json instanceof JSONObject)
			return ((JSONObject) json).length();

		return 0;
	}

	/**
	 * Casts object as {@link JSONArray} if it is an instance of one, or parses
	 * a {@link JSONArray} from object if it is a String.
	 * 
	 * @param object
	 *            the object to convert to a JSONArray
	 * @return a JSONArray object if it can be converted, or null
	 */
	public static JSONArray getInstanceOfJsonArray(Object object)
	{
		if (object instanceof JSONArray)
			return (JSONArray) object;

		else if (object instanceof JSONObject)
			return null; // Skip unnecessary parsing

		else if (object instanceof String && object.toString().startsWith("["))
			return getJsonArray((String) object);

		else if (object != null)
			return getJsonArray(object.toString());

		return null;
	}

	/**
	 * Casts object as {@link JSONObject} if it is an instance of one, or parses
	 * a {@link JSONObject} from object if it is a String.
	 * 
	 * @param object
	 *            the object to convert to a JSONObject
	 * @return a JSONObject object if it can be converted, or null
	 */
	public static JSONObject getInstanceOfJsonObject(Object object)
	{
		if (object instanceof JSONArray)
			return null; // Skip unnecessary parsing

		else if (object instanceof JSONObject)
			return (JSONObject) object;

		else if (object instanceof String && object.toString().startsWith("{"))
			return getJsonObject((String) object);

		else if (object != null)
			return getJsonObject(object.toString());

		return null;
	}

	@Deprecated
	public static JSONArray getJsonArray(String json)
	{
		if (!isNull(json))
			if (json.trim().startsWith("["))
				try
				{
					return EwJsonArray.parse(json);
				}
				catch (JSONException je)
				{
				}

		return null;
	}

	@Deprecated
	public static JSONObject getJsonObject(String json)
	{
		if (!isNull(json))
			if (json.trim().startsWith("{"))
				try
				{
					return EwJsonObject.parse(json);
				}
				catch (JSONException je)
				{
				}

		return null;
	}

	@Deprecated
	public static int keyToIndex(Object ref)
	{
		final int index;

		if (ref == null)
			index = DEFAULT_INDEX;
		else if (ref instanceof Number)
			index = ((Number) ref).intValue();

		else
			try
			{
				final String key = (String) ref;
				int start = 0;
				int end = key.length();

				// Trim one set of matching brackets from key
				if (key.charAt(start) == ARR_OPEN && key.charAt(end - 1) == ARR_CLOSE)
				{
					start++;
					end--;
				}

				index = Integer.valueOf(key.substring(start, end));
			}
			catch (Exception e)
			{
				return DEFAULT_INDEX;
			}

		return (index >= 0) ? index : DEFAULT_INDEX;
	}

	@Deprecated
	public static boolean isComplexKey(Object ref) throws JSONException
	{
		if (ref == null || !(ref instanceof String))
			return false;

		final String key = (String) ref;

		return (key.indexOf(KEY_DELIM) != -1 || isBracketedKey(key) || isObjectIndexKey(key));
	}

	@Deprecated
	private static boolean isBracketedKey(String key)
	{
		if (key == null)
			return false;

		return (key.indexOf(ARR_OPEN) != -1 && key.charAt(key.length() - 1) == ARR_CLOSE);
	}

	@Deprecated
	private static boolean isObjectIndexKey(String key)
	{
		if (key == null)
			return false;

		final int delimIndex = key.indexOf(INDEX_DELIM);

		// One and only one colon followed by a valid index
		if (-1 < delimIndex && delimIndex == key.lastIndexOf(INDEX_DELIM))
		{
			// For key "obj[0]:4", "obj[0]" is outer, "4" is inner
			final String innerKey = key.substring(delimIndex + 1);

			return !isBracketedKey(innerKey) && isValidIndex(innerKey);
		}

		return false;
	}

	/**
	 * Throw an exception if the object is a NaN or infinite number. Roughly
	 * equivalent to {@link JSONObject#testValidity(Object)}.
	 * 
	 * @param object
	 *            the object to test
	 * @return true if object is a non-finite number, false otherwise
	 * @throws JSONException Malformed object.
	 */
	@Deprecated
	public static boolean isBadDecimal(Object object) throws JSONException
	{
		if (object instanceof Double)
		{
			final double d = ((Number) object).doubleValue();
			if (Double.isInfinite(d) || Double.isNaN(d))
				return true;
		}
		else if (object instanceof Float)
		{
			final float f = ((Number) object).floatValue();
			if (Float.isInfinite(f) || Float.isNaN(f))
				return true;
		}

		return false;
	}

	public static boolean isJson(Object object)
	{
		return (object instanceof JSONArray || object instanceof JSONObject);
	}

	public static boolean isJson(String json)
	{
		return (isJsonArray(json) || isJsonObject(json));
	}

	public static boolean isJsonArray(String json)
	{
		return (getJsonArray(json) != null);
	}

	public static boolean isJsonObject(String json)
	{
		return (getJsonObject(json) != null);
	}

	@Deprecated
	public static boolean isNull(Object object)
	{
		return (object == null || JSONObject.NULL.equals(object) || JSONObject.NULL.toString().equals(object));
	}

	@Deprecated
	public static boolean isValidIndex(JSONArray array, int index)
	{
		if (array == null)
			return false;

		return (-1 < index && index < array.length());
	}

	@Deprecated
	public static boolean isValidIndex(Object key)
	{
		return (-1 < keyToIndex(key));
	}

	@Deprecated
	public static Object reduce(JSONArray array)
	{
		if (array == null)
			return null;

		switch (array.length())
		{
			case 0:
				return null;
			case 1:
				return array.opt(0);

			default:
				return array;
		}
	}

	@Deprecated
	public static Object reduce(JSONObject object)
	{
		if (object == null)
			return null;

		switch (object.length())
		{
			case 0:
				return null;
			case 1:
				return object.opt((String) object.keys().next());

			default:
				return object;
		}
	}

	@Deprecated
	public static Object reduce(Object object)
	{
		if (object instanceof JSONArray)
			return reduce((JSONArray) object);

		if (object instanceof JSONObject)
			return reduce((JSONObject) object);

		return object;
	}

	@Deprecated
	public static boolean parseBoolean(Object object) throws JSONException
	{
		if (Boolean.FALSE.equals(object))
			return false;

		if (Boolean.TRUE.equals(object))
			return true;

		if (object instanceof String)
		{
			final String string = (String) object;

			if (Boolean.FALSE.toString().equalsIgnoreCase(string))
				return false;
			if (Boolean.TRUE.toString().equalsIgnoreCase(string))
				return true;
		}

		throw new JSONException("Object is not a boolean.");
	}

	@Deprecated
	public static double parseDouble(Object object) throws JSONException
	{
		if (object instanceof Number)
			return ((Number) object).doubleValue();

		try
		{
			return Double.parseDouble((String) object);
		}
		catch (Exception e)
		{
		}

		throw new JSONException("Object is not a double.");
	}

	@Deprecated
	public static int parseInt(Object object) throws JSONException
	{
		if (object instanceof Number)
			return ((Number) object).intValue();

		if (object instanceof String)
			try
			{
				return Integer.valueOf((String) object);
			}
			catch (Exception e)
			{
			}

		throw new JSONException("Object is not an integer.");
	}

	@Deprecated
	public static long parseLong(Object object) throws JSONException
	{
		if (object instanceof Number)
			return ((Number) object).longValue();

		try
		{
			return Long.parseLong((String) object);
		}
		catch (Exception e)
		{
		}

		throw new JSONException("Object is not a long.");
	}

	@Deprecated
	public static String parseString(Object object) throws JSONException
	{
		if (object instanceof String)
			return (String) object;

		throw new JSONException("Object is not a string");
	}

	@Deprecated
	public static EwJsonCollection tryConvert(Object object)
	{
		final Object converted = tryParseJson(object, false);

		if (converted instanceof JSONArray)
			return EwJsonArray.convert((JSONArray) converted);

		if (converted instanceof JSONObject)
			return EwJsonObject.convert((JSONObject) converted);

		return null;
	}

	@Deprecated
	public static EwJsonCollection tryMerge(Object into, Object from, Object ref)
	{
		if (isJson(into))
		{
			final EwJsonCollection converted = tryConvert(into);

			try
			{
				if (converted instanceof JSONArray)
					return EwJsonArray.tryMergeAny((JSONArray) converted, from, ref);

				if (converted instanceof JSONObject)
					return EwJsonObject.tryMergeAny((JSONObject) converted, from, ref);
			}
			catch (JSONException je)
			{
			}
		}

		return null;
	}

	@Deprecated
	public static Object tryParseJson(Object object, boolean nullify)
	{
		return tryParseJson(object, nullify, false);
	}

	@Deprecated
	public static Object tryParseJson(Object object, boolean nullify, boolean reduce)
	{
		if (isNull(object))
			return null;

		final JSONArray ja = getInstanceOfJsonArray(object);
		if (ja != null)
			return (reduce) ? tryReduce(ja, nullify) : ja;

		final JSONObject jo = getInstanceOfJsonObject(object);
		if (jo != null)
			return (reduce) ? tryReduce(jo, nullify) : jo;

		return (nullify) ? null : object;
	}

	@Deprecated
	public static Object tryReduce(Object object, boolean nullify)
	{
		if (isNull(object))
			return null;

		if (object instanceof JSONArray)
			return reduce((JSONArray) object);

		if (object instanceof JSONObject)
			return reduce((JSONObject) object);

		return (nullify) ? null : object;
	}

	public static EwList<Object> toArray(JSONArray inner) throws JSONException
	{
		final EwList<Object> elements = new EwList<Object>();

		if (inner == null)
			return elements;

		for (int i = 0; i < inner.length(); i++)
		{
			if (inner.isNull(i))
				continue;
			Object object = inner.get(i);
			if (object instanceof JSONArray)
				elements.add(toArray((JSONArray) object));
			else
				elements.add(object);
		}

		return elements;
	}

	@Deprecated
	public static Object wrap(Object object)
	{
		return wrap(object, false);
	}

	/***
	 * Ensure primitive values are wrapped as Objects and strings are parsed as
	 * JSON if possible. If "convert" is specified, Collections/Maps are
	 * converted to {@link EwJsonCollection}s, or if the string value of the
	 * object is not parsable as json, a new EwJsonObject is created and
	 * populated from the object as though it were a "bean" (getters to keys
	 * 
	 * @see #tryParseJson(Object, boolean)
	 * @see #populateMap(JSONObject, Object)
	 * @param object Object to wrap.
	 * @param convert Convert to a JSON type.
	 * @return Wrapped object.
	 */
	@Deprecated
	public static Object wrap(Object object, boolean convert)
	{
		try
		{
			if (isNull(object))
				return null;

			else if (isWrapped(object))
				return object;

			else if (object instanceof JSONArray)
				return new EwJsonArray(object);

			else if (object instanceof JSONObject)
				return new EwJsonObject(object);

			else if (object instanceof EwJsonSerializable)
				return ((EwJsonSerializable) object).toString();

			else if (convert)
			{
				if (object.getClass().isArray())
					return new EwJsonArray(object);

				else if (object instanceof Collection)
					return new EwJsonArray((Collection<?>) object);

				else if (object instanceof Map)
					return new EwJsonObject((Map<?, ?>) object);

				else if (object instanceof String)
					return tryParseJson(object, false);

				else
				{
					// Try parsing object.toString() as JSON
					final Object json = tryParseJson(object, true);

					// Either return the parsed json, or converted bean
					return (isNull(json)) ? populateMap(new EwJsonObject(), object) : json;
				}
			}
		}
		catch (Exception exception)
		{
		}

		return object;
	}

	@Deprecated
	private static boolean isWrapped(Object object)
	{
		if (object == null)
			return false;

		return (JSONObject.NULL.equals(object) || object instanceof EwJsonCollection || object instanceof JSONString || object instanceof Boolean
				|| object instanceof Byte || object instanceof Character || object instanceof Double || object instanceof Float || object instanceof Integer
				|| object instanceof Long || object instanceof Short);
	}

	@Deprecated
	public static EwJsonCollection populateMap(JSONObject object, Object bean) throws JSONException
	{
		if (object == null || isNull(bean))
			return null;

		final EwJsonObject ewObject = EwJsonObject.convert(object);
		final Class<?> beanClass = bean.getClass();

		// No super classes for System classes
		final boolean includeSuperClass = (beanClass.getClassLoader() != null);
		final Method[] methods = (includeSuperClass) ? beanClass.getMethods() : beanClass.getDeclaredMethods();

		for (int i = 0; i < methods.length; i++)
		{
			try
			{
				final Method method = methods[i];

				if (Modifier.isPublic(method.getModifiers()))
				{
					final String name = method.getName();
					String key;

					if (name.startsWith("get"))
						key = (!name.equals("getClass") && !name.equals("getDeclaringClass")) ? name.substring(3) : "";
					else if (name.startsWith("is"))
						key = name.substring(2);
					else
						key = "";

					if (key.length() > 0 && Character.isUpperCase(key.charAt(0)) && method.getParameterTypes().length == 0)
					{
						if (key.length() == 1)
							key = key.toLowerCase();
						else if (!Character.isUpperCase(key.charAt(1)))
							key = key.substring(0, 1).toLowerCase() + key.substring(1);

						Object result = method.invoke(bean, (Object[]) null);
						if (result != null)
							ewObject.put(key, wrap(result));
					}
				}
			}
			catch (Exception ignore)
			{
			}
		}

		return ewObject;
	}

	@Deprecated
	public static void validateDecimal(Object decimal) throws JSONException
	{
		if (isBadDecimal(decimal))
			throw new JSONException("JSON does not allow non-finite numbers.");
	}

	@Deprecated
	public static String valueToString(Object value) throws JSONException
	{
		if (isNull(value))
			return "null";

		if (value instanceof Boolean || value instanceof JSONObject || value instanceof JSONArray)
			return value.toString();

		if (value instanceof Number)
		{
			validateDecimal(value);

			String string = value.toString();

			if (string.indexOf('.') > 0 && string.indexOf('e') < 0 && string.indexOf('E') < 0)
			{
				int length = string.length();

				while (string.charAt(length - 1) == '0')
					length--;

				if (string.charAt(length - 1) == '.')
					length--;

				string = string.substring(0, length);
			}

			return string;
		}

		if (value instanceof Map)
			return new EwJsonObject((Map<?, ?>) value).toString();

		if (value instanceof Collection)
			return new EwJsonArray((Collection<?>) value).toString();

		if (value.getClass().isArray())
			return new EwJsonArray(value).toString();

		return JSONObject.quote(value.toString());
	}

	/***
	 * Deep copies all JSON elements, shallow copies anything else.
	 * 
	 * @param cl Object to clone.
	 * @return Cloned object.
	 * @throws JSONException Malformed object.
	 */
	public static JSONObject clone(JSONObject cl) throws JSONException
	{
		JSONObject jo = new JSONObject();
		Iterator<String> it = cl.keys();
		while (it.hasNext())
		{
			String next = it.next();
			Object o = cl.get(next);
			if (o == null)
				continue;
			if (o instanceof JSONObject)
				jo.put(next, clone((JSONObject) o));
			else if (o instanceof JSONArray)
				jo.put(next, clone((JSONArray) o));
			else
				jo.put(next, o);
		}
		return jo;
	}

	/***
	 * Deep copies all JSON elements, shallow copies anything else.
	 * 
	 * @param ar Array to clone.
	 * @return Cloned array.
	 * @throws JSONException Malformed object.
	 */
	private static JSONArray clone(JSONArray ar) throws JSONException
	{
		JSONArray result = new JSONArray();
		for (int i = 0; i < ar.length(); i++)
		{
			if (ar.isNull(i))
				continue;
			Object o = ar.get(i);
			if (o == null)
				continue;
			if (o instanceof JSONObject)
				result.put(clone((JSONObject) o));
			else if (o instanceof JSONArray)
				result.put(clone((JSONArray) o));
			else
				result.put(o);
		}
		return result;
	}

}
