package com.eduworks.cruncher.parse;

import java.io.InputStream;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities.EscapeMode;
import org.jsoup.safety.Whitelist;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;

public class CruncherStripTags extends Cruncher
{
	@Override
	public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
	{
		String htmlText = (String)getObj(c, parameters, dataStreams);
        if (htmlText==null)
            htmlText = "";
		        
		// Customize the whitelist
		Whitelist wl;
        if (optAsBoolean("removeAll", false, c, parameters, dataStreams))
            wl = Whitelist.none();
        else
            wl = Whitelist.basic();
        
		JSONArray allowTags = getAsJsonArray("allowTags", c, parameters, dataStreams);
		if (allowTags != null) 
			for (int i = 0; i < allowTags.length(); i++) 
				wl.addTags(allowTags.getString(i));
        
        JSONArray allowAttributes = getAsJsonArray("allowAttributes", c, parameters, dataStreams);
        if (allowAttributes != null)
            for (int i = 0; i < allowAttributes.length(); i++) {
                JSONObject attribute = allowAttributes.getJSONObject(i);
                wl.addAttributes(attribute.getString("element"), attribute.getString("attribute"));
            }
            
		// Clean the text using the whitelist, and allow escaped characters
		Document doc = Jsoup.parse(htmlText);
		doc.outputSettings().charset("UTF-8");
		doc.outputSettings().escapeMode(EscapeMode.xhtml);
		htmlText = Jsoup.clean(doc.body().html(), wl);
		htmlText = StringEscapeUtils.unescapeHtml(htmlText);
        if (optAsBoolean("alphanumeric", false, c, parameters, dataStreams)) {
            StringBuilder sb = new StringBuilder();
            int size = htmlText.length();
            for (int i = 0; i < size; i++) {
                int ch = htmlText.codePointAt(i);
                if (ch == 32 || (ch >= 48 && ch <= 57) || (ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122))
                    sb.append((char)ch);
            }
            htmlText = sb.toString();
        }
		return htmlText;
	}

	@Override
	public String getDescription()
	{
		return "Strips any non-simple-text-manipulating HTML Tags from the input string.";
	}

	@Override
	public String getReturn()
	{
		return "String";
	}

	@Override
	public String getAttribution()
	{
		return ATTRIB_NONE;
	}

	@Override
	public JSONObject getParameters() throws JSONException
	{
		return jo("obj","String", "?allowTags", "JSONArray", "?allowAttributes", "JSONArray");
	}

}
