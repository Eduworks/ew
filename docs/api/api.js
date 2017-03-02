YUI.add("yuidoc-meta", function(Y) {
   Y.YUIDoc = { meta: {
    "classes": [
        "add",
        "base64ToFile",
        "cache",
        "createDirectory",
        "deserialize",
        "fileDelete",
        "fileExists",
        "fileHash",
        "fileList",
        "fileLoad",
        "fileSave",
        "variableGet",
        "variableSet",
        "wsBroadcast",
        "wsEmit"
    ],
    "modules": [
        "ew.levr.base"
    ],
    "allModules": [
        {
            "displayName": "ew.levr.base",
            "name": "ew.levr.base",
            "description": "Caches a result, and fetches it automatically (without executing the code in obj) if it is in cache.<br>Cache, by default, persists over the web service request.\n\nrs2: result = obj.cache(name=\"unique name\");<br>\nLevrJS: result = cache.call(this,obj,\"unique name\");"
        }
    ],
    "elements": []
} };
});