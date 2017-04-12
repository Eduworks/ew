package com.eduworks.cruncher.cache;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.lang.util.EwCache;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

/**
 * Caches a result, and fetches it automatically (without executing the code in obj) if it is in cache.<br>Cache, by default, persists over the web service
 * request.
 *
 * rs2: result = obj.cache(name="unique name");<br>
 * LevrJS: result = cache.call(this,obj,"unique name");
 *
 * @class cache
 * @module ew.levr.base
 * @author fritz.ray@eduworks.com
 */
public class CruncherCache extends Cruncher {

    public static Map<String, Object> obj = Collections.synchronizedMap(new HashMap<String, Object>());

    /**
     * @method cache
     * @param obj {Cruncher|Function} Cruncher or Function that will return an object to cache.
     * @param name {String} Unique name used to store and retrieve the cached object.
     * @param [remove=false] {Boolean} Removes the cached value using the name. 'obj' can be null. May be combined with 'global'.
     * @param [global=false] {Boolean} Add to global cache, persists over web service requests.
     * @param [justLock=false] {Boolean} Don't deal with the cache, but use the cache locking mechanism to prevent recurrent entrance.
     * @param [removeAllGlobal=false] {Boolean} Clears all caches in LEVR.
     * @return {Object} Cached value if in cache, computed value if not in cache.
     */

    @Override
    public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException {
        String cacheName = "cruncherCache" + getAsString("name", c, parameters, dataStreams);
        Object result = null;
        Object lock = null;
        if (optAsBoolean("removeAllGlobal", false, c, parameters, dataStreams)) {
            EwCache.caches.clear();
            obj.clear();
            return null;
        }
        boolean global = optAsBoolean("global", false, c, parameters, dataStreams);
        synchronized (getClass()) {
            if (global) {
                lock = obj.get(cacheName);
                if (lock == null) {
                    obj.put(cacheName, lock = new Object());
                }
            } else {
                lock = c.get("cache" + cacheName);
                if (lock == null) {
                    obj.put("cache" + cacheName, lock = new Object());
                }
            }
        }
        synchronized (lock) {
            if (optAsBoolean("remove", false, c, parameters, dataStreams)) {
                if (global) {
                    EwCache.getCache("GlobalCache").remove(cacheName);
                } else {
                    c.remove(cacheName);
                }
            } else {
                if (global) {
                    result = EwCache.getCache("GlobalCache").get(cacheName);
                } else {
                    result = c.get(cacheName);
                }
                if (result == null) {
                    result = getObj(c, parameters, dataStreams);
                    if (!optAsBoolean("justLock", false, c, parameters, dataStreams)) {
                        if (global) {
                            EwCache.getCache("GlobalCache").put(cacheName, result);
                        } else {
                            c.put(cacheName, result);
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public String getDescription() {
        return "Caches a result, and fetches it automatically if it is in cache. Use Name to specify cache key.";
    }

    @Override
    public String getReturn() {
        return "Object";
    }

    @Override
    public JSONObject getParameters() throws JSONException {
        return jo("obj", "Object", "name", "String", "remove", "Boolean", "global", "Boolean", "justLock", "Boolean", "removeAllGlobal", "Boolean");
    }

    @Override
    public String getAttribution() {
        return ATTRIB_NONE;
    }

}
