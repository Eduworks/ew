package com.eduworks.resolver.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.eduworks.resolver.Context;
import com.eduworks.resolver.Cruncher;
import com.eduworks.util.io.EwFileSystem;
import com.eduworks.util.io.InMemoryFile;

public class CruncherMimeType extends Cruncher
{

    @Override
    public Object resolve(Context c, Map<String, String[]> parameters, Map<String, InputStream> dataStreams) throws JSONException
    {
        Object obj = get("file", c, parameters, dataStreams);
        JSONObject extensionMimes = (JSONObject) get("extensions", c, parameters, dataStreams);
        try
        {
            if (obj instanceof File)
            {
                return getMimeType((File) obj, extensionMimes);
            }
            else if (obj instanceof InMemoryFile)
            {
                return getMimeType((InMemoryFile) obj, extensionMimes);
            }
            else
            {
                return null;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static String getMimeType(InMemoryFile file, JSONObject extensions) throws IOException, JSONException
    {
        String mimeType = null;
        File temporaryFile = file.toTemporaryFile();
        mimeType = Files.probeContentType(temporaryFile.toPath());
        EwFileSystem.deleteEventually(temporaryFile);
        mimeType = checkExtensions(mimeType, file, extensions);
        mimeType = fixMimeType(mimeType, file.name, file.data);
        return mimeType;
    }

    public static String getMimeType(File file, JSONObject extensions) throws IOException, JSONException
    {
        String mimeType = null;
        mimeType = Files.probeContentType(file.toPath());
        mimeType = checkExtensions(mimeType, file, extensions);
        mimeType = fixMimeType(mimeType, file.getName(), FileUtils.readFileToByteArray(file));
        return mimeType;
    }

    public static String checkExtensions(String mime, Object f, JSONObject extensions) throws JSONException
    {
        if (extensions != null)
        {
            String filename = "";
            if (f instanceof InMemoryFile)
            {
                filename = ((InMemoryFile) f).name;
            }
            else if (f instanceof File)
            {
                filename = ((File) f).getName();
            }
            for (Iterator<String> cursor = extensions.keys(); cursor.hasNext();)
            {
                String extension = cursor.next();
                if (filename.endsWith(extension))
                {
                    return extensions.getString(extension);
                }
            }
        }
        return mime;
    }

    private static String fixMimeType(String mime, String url, byte[] firstBytes)
    {
        if ((url == null || url.endsWith(".fla")) && firstBytes != null && firstBytes.length >= 4 && firstBytes[0] == -48 && firstBytes[1] == -49
                && firstBytes[2] == 17 && firstBytes[3] == -32)
        {
            return "application/octet-stream";
        }
        // Shockwave audio files (.swa) are just specialized .mp3 files
        if (url != null && url.toLowerCase().endsWith(".swa") && firstBytes != null && firstBytes.length > 4 && firstBytes[0] == 0 && firstBytes[1] == 0
                && firstBytes[3] == 32)
        {
            return "audio/mpeg";
        }
        // Javascript is sometimes misclassified as HTML
        if (mime != null && (mime.equalsIgnoreCase("text/html") || mime.equalsIgnoreCase("application/xhtml+xml")) && url.toLowerCase().endsWith(".js"))
        {
            return "text/javascript";
        }
        if (mime != null && (mime.equalsIgnoreCase("text/xml")) && url.toLowerCase().endsWith("html"))
        {
            return "application/xhtml+xml";
        }
        return mime == null ? "application/octet-stream" : mime;
    }

    @Override
    public String getDescription()
    {
        return "Detects the mime type of a file";
    }

    @Override
    public String[] getResolverNames()
    {
        return new String[]
        {
            getResolverName(), "mimetype"
        };
    }

    @Override
    public String getReturn()
    {
        return "String";
    }

    @Override
    public String getAttribution()
    {
        return ATTRIB_PROPRIETARY;
    }

    @Override
    public JSONObject getParameters() throws JSONException
    {
        return jo("file", "File|InMemoryFile");
    }

}
