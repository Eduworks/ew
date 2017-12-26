package com.eduworks.cruncher.text.parse;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CruncherTextToNumber extends Cruncher {

	@Override
	
	
	
	public Object resolve(Context c, Map<String, String[]> parameters,
			Map<String, InputStream> dataStreams) throws JSONException {
		String asString = optAsString("obj", null, c, parameters, dataStreams).replaceAll(" and","").replace("[ ]+"," ");
		int result=0;
		
		HashMap<String,Integer> wordMap = new HashMap<String,Integer>();
		wordMap.put("one",1);
		wordMap.put("two",2);
		wordMap.put("three",3);
		wordMap.put("four",4);
		wordMap.put("five",5);
		wordMap.put("six",6);
		wordMap.put("seven",7);
		wordMap.put("eight",8);
		wordMap.put("nine",9);
		wordMap.put("oh",0);
		wordMap.put("zero",0);
		wordMap.put("naught",0);
		wordMap.put("nought",0);
		wordMap.put("ten",10);
		wordMap.put("eleven",11);
		wordMap.put("twelve",12);
		wordMap.put("thirteen",13);
		wordMap.put("fourteen",14);
		wordMap.put("fifteen",15);
		wordMap.put("sixteen",16);
		wordMap.put("seventeen",17);
		wordMap.put("eightteen",18);
		wordMap.put("nineteen",19);
		wordMap.put("twenty",20);
		wordMap.put("thirty",30);
		wordMap.put("forty",40);
		wordMap.put("fifty",50);
		wordMap.put("sixty",60);
		wordMap.put("seventy",70);
		wordMap.put("eighty",80);
		wordMap.put("ninety",90);		
		wordMap.put("hundred", 100);
		
		String[] threeDigitParts=asString.split(" thousand ");
		int firstPart=0;
		
		if (threeDigitParts.length == 2) {
			String[] firstSplitHundred = threeDigitParts[0].split(" hundred ");
			if (firstSplitHundred.length == 2) {
				firstPart += wordMap.get(firstSplitHundred[0])*100;
			}
			for (String word : firstSplitHundred[firstSplitHundred.length-1].split(" ")){
				firstPart += wordMap.get(word);
			}			
		}
		result += firstPart*1000;
		String[] secondSplitHundred = threeDigitParts[threeDigitParts.length-1].split(" hundred ");
		if (secondSplitHundred.length == 2) {
			result += wordMap.get(secondSplitHundred[0])*100;
		}
		for (String word : secondSplitHundred[secondSplitHundred.length-1].split(" ")){
			result += wordMap.get(word);
		}		
		
		return result;
	}

	@Override
	public String getDescription() {		
		return "Parses a text representation of a number into an integer.";
	}

	@Override
	public String getReturn() {
		return "Number";
	}

	@Override
	public String getAttribution() {
		return ATTRIB_PROPRIETARY;
	}

	@Override
	public JSONObject getParameters() throws JSONException {
		return jo("obj","String");
	}

}
