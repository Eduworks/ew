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
        "fileSize",
        "fileToBase64",
        "fileToString",
        "filename",
        "filepath",
        "jsonLdCompact",
        "jsonLdExpand",
        "variableGet",
        "variableSet",
        "wsBroadcast",
        "wsEmit"
    ],
    "modules": [
        "ew.levr.base",
        "ew.levr.rdf"
    ],
    "allModules": [
        {
            "displayName": "ew.levr.base",
            "name": "ew.levr.base",
            "description": "Caches a result, and fetches it automatically (without executing the code in obj) if it is in cache.<br>Cache, by default, persists over the web service\nrequest.\n\nrs2: result = obj.cache(name=\"unique name\");<br>\nLevrJS: result = cache.call(this,obj,\"unique name\");"
        },
        {
            "displayName": "ew.levr.rdf",
            "name": "ew.levr.rdf",
            "description": "Converts a JSON object to JSON-LD and performs a Compact operation.\n\nrs2: result = obj.jsonLdCompact();<br>\nLevrJS: result = jsonLdCompact.call(this,obj);"
        }
    ],
    "elements": []
} };
});