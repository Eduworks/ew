package com.eduworks.cruncher.cache;

import java.io.InputStream;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

/**
 * Retrieves a variable from the variable store (set by #variableGet). Used when simple internal state is absolutely necessary.
 *
 * rs2: result = #variableGet(key="unique key");<br>
 * LevrJS: result = variableGet.call(this,"unique key");
 *
 * @class variableGet
 * @module ew.levr.base
 * @author fritz.ray@eduworks.com
 */
public class CruncherVariableGet extends Cruncher {

    /**
     * @method variableGet
     * @param key {String} Unique key used to retrieve the variable.
     * @return {Object} Variable that was previously stored.
     */

    @Override
    public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException {
        return CruncherVariableSet.store.get(getAsString("key", c, parameters, dataStreams));
    }

    @Override
    public String getDescription() {
        return "Retreives a variable from the variable 'cache' (set by #variableGet). Used when simple internal state is absolutely necessary.";
    }

    @Override
    public String getReturn() {
        return "Object";
    }

    @Override
    public String getAttribution() {
        return ATTRIB_NONE;
    }

    @Override
    public JSONObject getParameters() throws JSONException {
        return jo("key", "String");
    }

}
