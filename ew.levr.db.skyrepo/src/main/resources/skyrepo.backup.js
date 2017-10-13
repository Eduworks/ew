skyrepoBackup = function(){
	if (this.params.secret != skyIdSecret())
		error("You must provide secret=`cat skyId.secret` to invoke backup.",401);
	var backup = {};
	var settings = elasticMapping();
	var indices = EcObject.keys(settings);
	var types = [];
	for (var i = 0;i < indices.length;i++)
		types = types.concat(EcObject.keys(settings[indices[i]].mappings));
	for (var i = 0;i < types.length;i++)
	{
		var keys = idxKeys(".","skyrepo",types[i]);
		for (var j = 0;j < keys.length;j++)
		{
			backup[types[i]+"/"+keys[j]]=JSON.parse(idxGet(".","skyrepo",types[i],keys[j]));
		}
	}

	var firstQueryPost = {query:{query_string:{query:"*:*"}},explain:"false",size:"50",sort:"_doc"};
	var firstQueryUrl = urlBase() + "/_search?scroll=1m&version";

	var results = httpPost(JSON.stringify(firstQueryPost),firstQueryUrl,"application/json","false");
	var scroll = results["_scroll_id"];
	while (results != null && scroll != null && scroll != "")
	{
		scroll = results["_scroll_id"];
		var hits = results.hits.hits;
		if (hits.length == 0)
			break;
		for (var i = 0;i < hits.length;i++)
		{
			var key = hits[i]["_type"]+"/"+hits[i]["_id"]+"/"+hits[i]["_version"];
			if (backup[key] == null)
				backup[key]=hits[i]["_source"];
		}
		results = httpGet(urlBase() + "/_search/scroll?scroll=1m&scroll_id="+scroll);
	}
	return JSON.stringify(backup,null,2);
}
bindWebService("/util/backup",skyrepoBackup);

skyrepoRestore = function(){
	var log = [];
	if (this.params.secret != skyIdSecret())
		error("You must provide secret=`cat skyId.secret` to invoke a restore.",401);

	var file = getFileFromPost.call(this,"data");
	if(file == undefined || file == null)
		error("Unable to find restore file. Please attach via a POST request with the name = 'data'.",400);
	if (EcArray.isArray(file))
		file = file[0];
	file = JSON.parse(fileToString(file));
	var keys = EcObject.keys(file);
	for (var i = 0;i < keys.length;i++)
	{
		var parts = keys[i].split("/");
		log.push(skyrepoPutInternal({"obj":JSON.stringify(file[keys[i]]), type:parts[0], id:parts[1], version:parts[2]}));
	}
	return JSON.stringify(log,null,2);
}
bindWebService("/util/restore",skyrepoRestore);

skyrepoPurge = function(){
	if (this.params.secret != skyIdSecret())
		error("You must provide secret=`cat skyId.secret` to invoke purge.",401);
	var log = [];
	var settings = elasticMapping();
	var indices = EcObject.keys(settings);
	var types = [];
	for (var i = 0;i < indices.length;i++){
		types = types.concat(EcObject.keys(settings[indices[i]].mappings));
		log.push(httpDelete(urlBase()+"/"+indices[i]));
	}
	cache(null,"elasticMapping","true","true");
	cache(null,"elasticSettings","true","true");
	for (var i = 0;i < types.length;i++)
	{
		var keys = idxKeys(".","skyrepo",types[i]);
		for (var j = 0;j < keys.length;j++)
		{
			idxDelete(".","skyrepo",types[i],keys[j]);
		}
	}
	return JSON.stringify(log,null,2);
}
bindWebService("/util/purge",skyrepoPurge);