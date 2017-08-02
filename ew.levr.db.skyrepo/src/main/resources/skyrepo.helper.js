


function flattenStringFields(jsonLd){

	if(jsonLd == undefined){
		jsonLd = JSON.parse(this.params.obj);
	}
	
	if(jsonLd === Object(jsonLd)){
		for(var key in jsonLd){
			try{
				var field = jsonLd[key];
				if(Array.isArray(field)){
					var flattenText = true;
					var textArray = [];
					var langArray = [];
					for(var idx in field){
						var obj = field[idx];
						
						if(obj === Object(obj)){
							if(Object.keys(obj).length == 2 && obj["@value"] != undefined && obj["@language"] != undefined){
								textArray.push(obj["@value"]);
								langArray.push(obj["@language"]);
							}else{
								flattenText = false
							}
						}else{
							flattenText = false;
						}
					}
					if(!flattenText){
						jsonLd[key] = flattenStringFields(field);
					}else{
						jsonLd[key] = textArray;
						jsonLd[key+"@language"] = langArray;
					}	
				}else if(field === Object(field)){
					if(Object.keys(field).length == 2 && field["@value"] != undefined && field["@language"] != undefined){
						jsonLd[key] = field["@value"];
						jsonLd[key+"@language"] = field["@language"];
					}else if (Object.keys(field).length > 0){
						jsonLd[key] = flattenStringFields(field);
					}	
				}
			}catch(exception){
				error("Exception when flattening json-ld text fields: "+exception.message)
			}
		}
	}
	return jsonLd;
}