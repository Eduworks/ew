package com.eduworks.cruncher.net;

import com.eduworks.lang.threading.EwThreading;
import com.eduworks.lang.util.EwJson;
import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.resolver.exception.SoftException;
import com.eduworks.util.io.InMemoryFile;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.AbstractContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.NoRouteToHostException;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.Map;

public class CruncherHttpPost extends Cruncher
{

    @Override
    public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
    {
        final Object o = getObj(c, parameters, dataStreams);
        String url = getAsString("url", c, parameters, dataStreams);
        String name = getAsString("name", c, parameters, dataStreams);
        String contentType = getAsString("contentType", c, parameters, dataStreams);
        // String accept = getAsString("accept", parameters, dataStreams);
        boolean multiPart = optAsBoolean("multipart", true, c, parameters, dataStreams);
        boolean formdata = optAsBoolean("formdata", false, c, parameters, dataStreams);
        boolean reliable = optAsBoolean("reliable", false, c, parameters, dataStreams);
        String authToken = getAsString("authToken", c, parameters, dataStreams);
        HttpPost post = new HttpPost(url);

        HttpEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName("UTF-8"));
        if (multiPart)
            if (formdata)
            {
                JSONObject fd = (JSONObject) o;
                AbstractContentBody contentBody = null;
                for (String key : EwJson.getKeys(fd))
                {
                    Object fdo = fd.get(key);
                    if (fdo instanceof File)
                        contentBody = new FileBody((File) fdo, contentType);
                    else if (fdo instanceof InMemoryFile)
                        contentBody = new InputStreamBody(((InMemoryFile) fdo).getInputStream(), contentType);
                    else if (fdo instanceof JSONObject || fdo instanceof JSONArray)
                        contentBody = new StringBody(fdo.toString(), ContentType.APPLICATION_JSON);
                    else
                        contentBody = new StringBody(fdo.toString(), ContentType.create("text/plain", Charset.forName("UTF-8")));
                    ((MultipartEntity) entity).addPart(key, contentBody);
                }
            }
            else
            {
                AbstractContentBody contentBody = null;
                if (o instanceof File)
                    contentBody = new FileBody((File) o, contentType);
                else if (o instanceof InMemoryFile)
                    contentBody = new InputStreamBody(((InMemoryFile) o).getInputStream(), contentType);
                else if (o instanceof JSONObject || o instanceof JSONArray)
                    contentBody = new StringBody(o.toString(), ContentType.APPLICATION_JSON);
                else
                    contentBody = new StringBody(o.toString(), ContentType.create(contentType, Charset.forName("UTF-8")));
                ((MultipartEntity) entity).addPart(name, contentBody);
            }
        else
            try
            {
                if (o instanceof File)
                    entity = new FileEntity((File) o);
                else if (o instanceof InMemoryFile)
                    entity = new InputStreamEntity(((InMemoryFile) o).getInputStream(), ((InMemoryFile) o).data.length);
                else
                {
                    byte[] bytes = o.toString().getBytes("UTF-8");
                    entity = new InputStreamEntity(new ByteArrayInputStream(bytes), bytes.length);
                }
                post.setHeader("Content-Type", contentType);
                // if (accept != null)
                // post.setHeader("Accept", accept);
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }

        if (authToken != null && !authToken.trim().isEmpty())
            post.setHeader("Authorization", "Basic " + authToken);
        for (String key : keySet())
        {
            if (key.equals("url"))
                continue;
            if (key.equals("obj"))
                continue;
            if (key.equals("authToken"))
                continue;
            if (key.equals("multipart"))
                continue;
            if (key.equals("name"))
                continue;
            if (key.equals("contentType"))
                continue;
            post.setHeader(key, getAsString(key, c, parameters, dataStreams));
        }

        post.setEntity(entity);

        CloseableHttpClient hc = HttpClients.createDefault();

        CloseableHttpResponse execute = null;
        try
        {
            do
                try
                {
                    execute = hc.execute(post);
                }
                catch (NoRouteToHostException e)
                {
                    if (reliable)
                        EwThreading.sleep(500);
                    else
                        e.printStackTrace();
                }
                catch (ClientProtocolException e)
                {
                    if (reliable)
                        EwThreading.sleep(500);
                    else
                        e.printStackTrace();
                }
                catch (SocketException e)
                {
                    if (reliable)
                        EwThreading.sleep(500);
                    else
                        throw new SoftException(e.getMessage());
                }
                catch (IOException e)
                {
                    if (reliable)
                        EwThreading.sleep(500);
                    else
                        e.printStackTrace();
                }
            while (execute == null && reliable);

            if (execute == null)
                return null;
            String string = null;
            try
            {
                string = EntityUtils.toString(execute.getEntity(),Charset.defaultCharset());
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            if (string == null)
                return null;

            try
            {
                return new JSONArray(string);
            }
            catch (JSONException ex)
            {
            }
            try
            {
                return new JSONObject(string);
            }
            catch (JSONException ex)
            {
            }
            return string;
        }
        finally
        {
            try
            {
                if (execute != null)
                    execute.close();
                if (hc != null)
                    hc.close();
            }
            catch (IOException e)
            {
            }
        }
    }

    @Override
    public String getDescription()
    {
        return "Performs an HTTP Post. The payload is provided by obj.\n"
                + "Will attach one file as a payload or multi-part mime message. (use multipart=true, name, and contentType)\n"
                + "Results will come back as JSON or a string.";
    }

    @Override
    public String getReturn()
    {
        return "JSONObject|JSONArray|String";
    }

    @Override
    public String getAttribution()
    {
        return ATTRIB_NONE;
    }

    @Override
    public JSONObject getParameters() throws JSONException
    {
        return jo("obj", "String", "url", "String", "contentType", "String", "?multipart", "Boolean", "?name", "String", "?authToken", "String", "?reliable", "Boolean");
    }

}
