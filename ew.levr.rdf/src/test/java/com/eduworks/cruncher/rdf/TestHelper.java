package com.eduworks.cruncher.rdf;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class TestHelper {

	public static final String CONTEXT_ATTR = "@context";

	public static boolean compareJSONArray(JSONArray original, Object compare) {
		if (original.length() == 1 && compare instanceof String) {
			JSONArray compareArr = new JSONArray().put(compare);

			return compareJSONArray(original, compareArr);
		}

		return false;
	}

	public static boolean compareJSONArray(JSONArray original, JSONArray compare) {
		if (original.length() != compare.length()) {
			System.out.println("Different JSONArray - original:" + original + " compare:" + compare);
			return false;
		}


		for (int i = 0; i < compare.length(); i++) {
			try {
				boolean hasVal = false;
				Object compareVal = compare.get(i);

				for (int j = 0; j < original.length(); j++) {
					Object origVal = original.get(j);
					quietly = true;
					if (TestHelper.compareJSON(origVal, compareVal)) {
						hasVal = true;
					}
					quietly = false;
				}

				if (!hasVal) {
					System.out.println("Different JSONArray value - missing:" + compareVal);
					return false;
				}
			} catch (JSONException e) {
				System.out.println("Different JSONArray - original:" + original + " compare:" + compare);
				return false;
			}
		}

		return true;
	}

	public static boolean compareJSONObject(JSONObject original, JSONObject compare) {
		Iterator<String> keyIt = compare.keys();

		while (keyIt.hasNext()) {
			String key = keyIt.next();

			if (original.opt(key) != null) {
				try {
					if (!TestHelper.compareJSON(original.get(key), compare.get(key))) {
						if (!quietly) System.out.println("Different JSONObject - on key: " + key + "\n original:" + original.get(key) + " \n compare:" + compare.get(key));
						if (!key.equals(CONTEXT_ATTR))
							return false;
					}
				} catch (JSONException e) {
					System.out.println("Different JSONObject - missing key:" + key);
					return false;
				}
			} else if (!key.equals(CONTEXT_ATTR)) {
				System.out.println("Different JSONObject - missing key:" + key);
				return false;
			}
		}

		keyIt = original.keys();
		while (keyIt.hasNext()) {
			String key = keyIt.next();

			if (compare.opt(key) != null) {
				try {
					if (!TestHelper.compareJSON(original.get(key), compare.get(key))) {
						System.out.println("Different JSONObject - on key: " + key + "\n original:" + original.get(key) + " \n compare:" + compare.get(key));
						if (!key.equals(CONTEXT_ATTR))
							return false;
					}
				} catch (JSONException e) {
					System.out.println("Different JSONObject - missing key:" + key);
					return false;
				}
			} else if (!key.equals(CONTEXT_ATTR)) {

				System.out.println("Different JSONObject - missing key:" + key);
				return false;
			}
		}


		return true;
	}

	public static boolean quietly = false;
	public static boolean compareJSON(Object original, Object compare) {
		if (original == null) {
			System.out.println("Null Value - original:" + original);
			return false;
		} else if (compare == null) {
			System.out.println("Null Value - compare:" + compare);
			return false;
		}

		if (original instanceof JSONObject && compare instanceof JSONObject) {
			return compareJSONObject((JSONObject) original, (JSONObject) compare);
		} else if (original instanceof JSONArray && compare instanceof JSONArray) {
			return compareJSONArray((JSONArray) original, (JSONArray) compare);
		} else if (original instanceof JSONArray) {
			return compareJSONArray((JSONArray) original, compare);
		} else if (compare instanceof JSONArray) {
			return compareJSONArray((JSONArray) compare, original);
		} else {
			return original.equals(compare);
		}
	}

}
