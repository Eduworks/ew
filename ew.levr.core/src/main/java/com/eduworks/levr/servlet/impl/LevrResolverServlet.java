package com.eduworks.levr.servlet.impl;

import com.eduworks.interfaces.EwJsonSerializable;
import com.eduworks.lang.EwList;
import com.eduworks.lang.EwMap;
import com.eduworks.lang.json.EwJsonCollection;
import com.eduworks.lang.util.EwCache;
import com.eduworks.lang.util.EwJson;
import com.eduworks.levr.servlet.LevrServlet;
import com.eduworks.resolver.*;
import com.eduworks.resolver.exception.SoftException;
import com.eduworks.resolver.lang.LevrJsParser;
import com.eduworks.resolver.lang.LevrResolverParser;
import com.eduworks.resolver.lang.LevrResolverV2Parser;
import com.eduworks.util.Tuple;
import com.eduworks.util.io.EwFileSystem;
import com.eduworks.util.io.InMemoryFile;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.script.Bindings;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@SuppressWarnings("serial")
public class LevrResolverServlet extends LevrServlet
{

    public static Map<String, Resolvable> resolvableWebServices;
    public static Map<String, Resolvable> resolvableFunctions;
    public static List<File> codeFiles;
    static long codeFilesLastModifiedMs = 0;
    public static long codeFilesLastCheckedMs = 0;
    public static Object lock = new Object();

    private static final String FAVICON_REQUEST_STRING = "favicon.ico";

    static
    {
        ResolverFactory.populateFactorySpecsDynamically();
    }

    public static boolean initConfig(PrintStream pw, ServletContext servletContext) throws IOException
    {
        if (codeFilesLastCheckedMs + 5000 < System.currentTimeMillis())
        {
            codeFilesLastCheckedMs = System.currentTimeMillis();
            if (resolvableWebServices == null || getFilesLastModified(new File(EwFileSystem.getWebConfigurationPath())) != codeFilesLastModifiedMs)
            {
                FileReader input = null;
                try
                {
                    synchronized (lock)
                    {
                        resolvableWebServices = new EwMap<String, Resolvable>();
                        resolvableFunctions = new EwMap<String, Resolvable>();
                        codeFiles = new EwList<File>();

                        if (servletContext != null)
                        {
                            loadAdditionalConfigFilesFromServletContext("/WEB-INF/lib/", servletContext);
                            loadAdditionalConfigFilesFromServletContext("/WEB-INF/classes/", servletContext);
                            //loadAdditionalConfigFilesFromServletContext("/", servletContext);
                        }
                        loadAdditionalConfigFiles(new File(EwFileSystem.getWebConfigurationPath()));
                        codeFilesLastModifiedMs = getFilesLastModified(new File(EwFileSystem.getWebConfigurationPath()));
                    }
                    for (String webService : resolvableFunctions.keySet())
                        if (webService.toLowerCase().endsWith("autoexecute"))
                        {
                            Context c = new Context();
                            try
                            {
                                execute(log, true, webService, c, new HashMap<String, String[]>(), new HashMap<String, InputStream>(), true);
                                c.success();
                            }
                            catch (Exception ex)
                            {
                                c.failure();
                                log.debug("Auto-Execute failed.", ex);
                            }
                            c.finish();
                        }
                    return true;
                }
                catch (JSONException e)
                {
                    pw.println("Error in config: " + e.getMessage());
                    e.printStackTrace();
                    return false;
                }
                finally
                {
                    IOUtils.closeQuietly(input);
                }
            }
        }
        return true;
    }

    private static void loadAdditionalConfigFilesFromServletContext(String path, ServletContext servletContext) throws IOException, JSONException
    {
        if (path.endsWith(".jar") && (path.toLowerCase().contains("levr")))
        {
            ZipInputStream zip = new ZipInputStream(servletContext.getResourceAsStream(path));
            while (true)
            {
                ZipEntry e = zip.getNextEntry();
                if (e == null)
                    break;
                String name = e.getName();
                if (name.endsWith(".rs2") || (name.endsWith(".js") && name.contains("node_modules") == false))
                {
                    File createTempFile = File.createTempFile(path.replace("/", "").replace("\\", ""), e.getName().replace("/", "").replace("\\", ""));
                    FileWriter fileWriter = new FileWriter(createTempFile);
                    IOUtils.copy(zip, fileWriter);
                    fileWriter.close();
                    loadAdditionalConfigFiles(createTempFile);
                    createTempFile.delete();
                }
            }
            zip.close();
        }
        else if (path.endsWith("/"))
        {
            Set<String> resourcePaths = servletContext.getResourcePaths(path);
            if (resourcePaths != null && resourcePaths.size() > 0)
                for (String contextPath : resourcePaths)
                {
                    if (contextPath.endsWith("/") || contextPath.endsWith(".jar"))
                    {
                        loadAdditionalConfigFilesFromServletContext(contextPath, servletContext);
                        continue;
                    }
                    File createTempFile = File.createTempFile("inWar", contextPath.replace("/", "").replace("\\", ""));
                    FileWriter fileWriter = new FileWriter(createTempFile);
                    InputStream resourceAsStream = servletContext.getResourceAsStream(contextPath);
                    IOUtils.copy(resourceAsStream, fileWriter);
                    resourceAsStream.close();
                    fileWriter.close();
                    loadAdditionalConfigFiles(createTempFile);
                    createTempFile.delete();
                }
        }
        // else
        // System.out.println("Failed: " + path);
    }

    private static void loadAdditionalConfigFilesFromContext(String path) throws IOException, JSONException
    {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null)
        {
            Enumeration<URL> resourcePaths = classLoader.getResources(path);
            if (resourcePaths != null)
                while (resourcePaths.hasMoreElements())
                {
                    String contextPath = resourcePaths.nextElement().toString();
                    if (contextPath.endsWith("/"))
                    {
                        loadAdditionalConfigFilesFromContext(contextPath);
                        continue;
                    }
                    File createTempFile = File.createTempFile("inWar", contextPath.replace("/", "").replace("\\", ""));
                    FileWriter fileWriter = new FileWriter(createTempFile);
                    IOUtils.copy(classLoader.getResourceAsStream(contextPath), fileWriter);
                    fileWriter.close();
                    loadAdditionalConfigFiles(createTempFile);
                    createTempFile.delete();
                }
        }
    }

    public static void loadAdditionalConfigFiles(File codeFile) throws JSONException
    {
        if (codeFile.canRead())
            if (codeFile.isDirectory())
                for (File f2 : codeFile.listFiles())
                    loadAdditionalConfigFiles(f2);
            else if (codeFile.isFile())
            {
                FileInputStream fileHandle = null;
                try
                {
                    if (codeFile.getName().endsWith(".rsl"))
                    {
                        log.debug("Loading: " + codeFile.getPath());
                        codeFiles.add(codeFile);
                        bindWebServices(resolvableWebServices, LevrResolverParser.decodeStreams(codeFile));
                    }
                    if (codeFile.getName().endsWith(".rs2"))
                    {
                        log.debug("Loading: " + codeFile.getPath());
                        codeFiles.add(codeFile);
                        bindWebServicesAndFunctions(resolvableWebServices, resolvableFunctions, LevrResolverV2Parser.decodeStreams(codeFile), codeFile);
                    }
                    JSONObject scriptPack = null;
                    Map<String, JSONObject> scriptStreams = null;
                    if (codeFile.getName().endsWith(".psl"))
                    {
                        log.debug("Loading: " + codeFile.getPath());
                        codeFiles.add(codeFile);
                        fileHandle = new FileInputStream(codeFile);
                        String cleanFilename = codeFile.getName().substring(0, codeFile.getName().lastIndexOf("."));
                        scriptPack = new JSONObject();
                        scriptPack.put("function", "python");
                        scriptPack.put("expression", IOUtils.toString(fileHandle));
                        scriptStreams = new EwMap<String, JSONObject>();
                        scriptStreams.put(cleanFilename, scriptPack);
                        bindWebServices(resolvableWebServices, scriptStreams);
                    }
                    if (codeFile.getName().endsWith(".jsl"))
                    {
                        log.debug("Loading: " + codeFile.getPath());
                        codeFiles.add(codeFile);
                        fileHandle = new FileInputStream(codeFile);
                        String cleanFilename = codeFile.getName().substring(0, codeFile.getName().lastIndexOf("."));
                        scriptPack = new JSONObject();
                        scriptPack.put("function", "javascript");
                        scriptPack.put("expression", IOUtils.toString(fileHandle));
                        scriptStreams = new EwMap<String, JSONObject>();
                        scriptStreams.put(cleanFilename, scriptPack);
                        bindWebServices(resolvableWebServices, scriptStreams);
                    }
                    if (codeFile.getName().endsWith(".js") && codeFile.getPath().contains("node_modules") == false)
                    {
                        log.debug("Loading: " + codeFile.getPath());
                        codeFiles.add(codeFile);
                        if (levrFunctionUseCodeFileAsNamespace)
                            LevrJsParser.reinitialize();
                        bindJavascriptFunctions(resolvableFunctions, LevrJsParser.decodeStreams(codeFile), codeFile);
                    }
                }
                catch (NullPointerException ex)
                {
                    System.out.println("Failed on " + codeFile.getPath());
                    ex.printStackTrace();
                }
                catch (IOException e)
                {
                    System.out.println("Failed on " + codeFile.getPath());
                    e.printStackTrace();
                }
                finally
                {
                    if (fileHandle != null)
                        IOUtils.closeQuietly(fileHandle);
                }
            }
    }

    private static void bindWebServices(Map<String, Resolvable> config2, Map<String, JSONObject> decodeStreams) throws JSONException
    {
        for (Entry<String, JSONObject> entry : decodeStreams.entrySet())
            config2.put((entry.getKey().startsWith("/") ? "" : "/") + entry.getKey(), ResolverFactory.create(entry.getValue()));
    }

    public static boolean levrFunctionUseCodeFileAsNamespace = false;

    private static void bindWebServicesAndFunctions(Map<String, Resolvable> config2, Map<String, Resolvable> functions2,
            Tuple<Map<String, JSONObject>, Map<String, JSONObject>> decodeStreams, File codeFile) throws JSONException
    {
        for (Entry<String, JSONObject> entry : decodeStreams.getSecond().entrySet())
        {
            final Resolvable resolverFunction = ResolverFactory.create(entry.getValue());
            String preamble = levrFunctionUseCodeFileAsNamespace ? codeFile.getName().substring(0, codeFile.getName().length() - 3) : "";
            functions2.put(preamble + entry.getKey().substring(1), resolverFunction);
            LevrJsParser.createJavascriptFunctionBinding(entry.getKey().substring(1), resolverFunction);
        }
        bindWebServices(config2, decodeStreams.getFirst());
    }

    private static void bindJavascriptFunctions(Map<String, Resolvable> resolvableFunctions, Bindings bindings, File codeFile)
    {
        CruncherJavascriptBinder cj = new CruncherJavascriptBinder();
        for (String s : bindings.keySet())
        {
            CruncherJavascriptBinder jb = new CruncherJavascriptBinder();
            jb.build("function", s);
            if (ResolverFactory.cruncherSpecs.containsKey(s))
                continue;
            if (bindings.get(s) instanceof ScriptObjectMirror)
            {
                ScriptObjectMirror binding = (ScriptObjectMirror) bindings.get(s);
                if (binding.isFunction())
                    if (LevrResolverServlet.resolvableFunctions.get(s) == null || LevrResolverServlet.resolvableFunctions.get(s) instanceof CruncherJavascriptBinder)
                    {
                        String preamble = levrFunctionUseCodeFileAsNamespace ? codeFile.getName().substring(0, codeFile.getName().length() - 2) : "";
                        resolvableFunctions.put(preamble+s, jb);
                    }
            }
        }
    }

    @Override
    public String getServletPath()
    {
        return "/api/custom/*";
    }

    @Override
    public String getServletPathExample()
    {
        return "/api/custom";
    }

    @Override
    public void go(String methodType, HttpServletRequest request, HttpServletResponse response, ServletOutputStream outputStream) throws IOException
    {
        String requestURI = request.getRequestURI();

        String requestString = requestURI;
        if (requestString.contains(getServletPathExample()))
            requestString = requestString.substring(requestURI.indexOf(getServletPathExample()) + getServletPathExample().length());
        else if (requestString.contains("/api"))
            requestString = requestString.substring(requestURI.indexOf("/api") + "/api".length());
        if (requestString.toLowerCase().endsWith(FAVICON_REQUEST_STRING.toLowerCase()))
            return;

        Map<String, String[]> parameterMap = Collections.synchronizedMap(new HashMap<String, String[]>(request.getParameterMap()));
        String jsonpSecurityKey = getStringFromParameter(request, "sec", "");
        parameterMap.put("methodType", new String[]
        {
            methodType
        });
        Map<String, InputStream> dataStreams = null;

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream pw = new PrintStream(outputStream);

        Context c = new Context(request, response, pw);

        if (isPost(methodType))
            if (ServletFileUpload.isMultipartContent(request))
                try
                {
                    dataStreams = decodeMultipartContent(c, request);
                }
                catch (FileUploadException e)
                {
                    throw new IOException(e.getMessage());
                }
            else
                try
                {
                    dataStreams = decodeSimpleContent(request);
                }
                catch (FileUploadException e)
                {
                    throw new IOException(e.getMessage());
                }

        if (isJsonpRequest(isPost(methodType), jsonpSecurityKey))
            pw = new PrintStream(os);
        else
            startJsonpPayload(request, pw);

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
        // NAUGHTY! We added signatureSheet for one of our projects.
        response.setHeader("Access-Control-Allow-Headers",
                "If-Modified-Since, Content-Type, Content-Range, Content-Disposition, Content-Description, signatureSheet");

        if (isJsonpPayloadRequest(isPost(methodType), jsonpSecurityKey))
            retreiveJsonpPayload(jsonpSecurityKey, pw);
        else
            try
            {
                execute(request, response, requestString, c, parameterMap, pw, dataStreams);
                c.success();
            }
            catch (JSONException e)
            {
                c.failure();
                if (response != null)
                    response.setContentType("text/plain");
                try
                {
                    JSONObject jo = new JSONObject();
                    jo.put("error", e.toString());
                    pw.print(jo.toString(2));
                }
                catch (JSONException e1)
                {
                    e1.printStackTrace();
                }
            }
            finally
            {
                c.finish();
            }

        if (isJsonpRequest(isPost(methodType), jsonpSecurityKey))
            storeJsonpPayload(jsonpSecurityKey, os.toByteArray());
        else
            finishJsonpPayload(request, pw);

        pw.flush();
    }

    private boolean isPost(String methodType)
    {
        return methodType.equals(HTTP_POST);
    }

    private boolean isJsonpPayloadRequest(boolean isPost, String jsonpSecurityKey)
    {
        return !isPost && !jsonpSecurityKey.isEmpty();
    }

    private boolean isJsonpRequest(boolean isPost, String jsonpSecurityKey)
    {
        return isPost && !jsonpSecurityKey.isEmpty();
    }

    private void retreiveJsonpPayload(String jsonpSecurityKey, PrintStream pw)
    {
        try
        {
            SoftReference<byte[]> softReference = holdingCache.get(jsonpSecurityKey);
            if (softReference == null)
                throw new Exception("Cannot find your data payload. Sorry.");
            byte[] payload = softReference.get();
            if (payload == null)
                throw new Exception("Lost your data payload. Please try again.");
            holdingCache.remove(jsonpSecurityKey);
            pw.write(payload);
        }
        catch (Exception e)
        {
            pw.print("{\"error\":\"" + e.toString() + "\"}");
        }
    }

    static HashMap<String, SoftReference<byte[]>> holdingCache = new HashMap<String, SoftReference<byte[]>>();

    private void storeJsonpPayload(String sec, byte[] resultsAsString)
    {
        if (sec == null || sec.isEmpty())
            return;
        holdingCache.put(sec, new SoftReference<byte[]>(resultsAsString));
    }

    private Map<String, InputStream> decodeMultipartContent(Context c, HttpServletRequest request) throws FileUploadException, IOException
    {
        LinkedHashMap<String, InputStream> results = new LinkedHashMap<String, InputStream>();
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> parseRequest = upload.parseRequest(request);

        for (FileItem item : parseRequest)
        {
            // IE artifact. When files are attached, they are sent using their
            // full path in 'name' and their filename in 'FieldName'.
            if (item.getName() != null && item.getName().contains("\\"))
                c.filenames.put(item.getFieldName(), item.getFieldName());
            else
                c.filenames.put(item.getFieldName(), item.getName());
            InputStream inputStream = item.getInputStream();
            try
            {
                results.put(item.getFieldName(), new ByteArrayInputStream(IOUtils.toByteArray(inputStream)));
            }
            finally
            {
                EwFileSystem.closeIt(inputStream);
                item.delete();
            }
        }
        log.debug("Decoded " + results.size() + " multi part mime inputs.");
        return results;
    }

    private Map<String, InputStream> decodeSimpleContent(HttpServletRequest request) throws FileUploadException, IOException
    {
        LinkedHashMap<String, InputStream> results = new LinkedHashMap<String, InputStream>();

        ServletInputStream inputStream = request.getInputStream();
        try
        {
            results.put("simple", new ByteArrayInputStream(IOUtils.toByteArray(inputStream)));
        }
        finally
        {
            EwFileSystem.closeIt(inputStream);
        }
        log.debug("Decoded " + results.size() + " raw input.");
        return results;
    }

    public static void execute(HttpServletRequest request, HttpServletResponse response, String requestString, Context c, Map<String, String[]> parameterMap,
            PrintStream pw, Map<String, InputStream> dataStreams) throws IOException, JSONException
    {
        HttpSession session = null;
        try
        {
            session = request.getSession();
        }
        catch (IllegalStateException ex)
        {
            log.warn("Could not create session as part of this request. Run-time-modification of LEVR scripts may be disabled.");
        }
        if (!initConfig(pw, session == null ? null : session.getServletContext()))
            return;

        final boolean flushAllCache = getParameter("flushAllCache", parameterMap);
        final boolean inline = getParameter("inline", parameterMap);

        if (flushAllCache)
            EwCache.clearAll();

        try
        {
            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.isEmpty())
                ip = request.getRemoteAddr();
            parameterMap.put("ip", new String[]
            {
                ip
            });
            parameterMap.put("threadId", new String[]
            {
                Thread.currentThread().getName()
            });

            Object result = execute(log, false, requestString, c, parameterMap, dataStreams, true);

            response.setHeader("cache-control", "private, no-cache, no-store");
            if (result instanceof String)
            {
                final EwJsonCollection json = EwJson.tryConvert(result);

                if (response != null && request != null && !response.isCommitted())
                    if (((String) result).startsWith("<html>"))
                        response.setContentType("text/html");
                    else if (getStringFromParameter(request, "callback", null) != null)
                        response.setContentType("text/javascript");
                    else if (json != null)
                        response.setContentType("application/json");
                    else
                        response.setContentType("text/plain");

                pw.println(result.toString());
            }
            else if (result instanceof Number)
            {
                if (response != null && !response.isCommitted())
                    response.setContentType("text/plain");
                pw.println(result.toString());
            }
            else if (result instanceof EwJsonSerializable)
            {
                if (response != null && !response.isCommitted())
                    response.setContentType("text/plain");
                pw.println(((EwJsonSerializable) result).toJsonObject());
            }
            else if (result instanceof InMemoryFile)
            {
                InMemoryFile f = (InMemoryFile) result;

                if (response != null && !response.isCommitted())
                {
                    response.setContentType(((InMemoryFile) result).mime);
                    if (f.name != null && !f.name.isEmpty())
                        if (inline)
                        {
                            response.setHeader("cache-control", "public, max-age=3600");
                            response.setHeader("content-disposition", "filename=" + f.name);
                        }
                        else
                            response.setHeader("content-disposition", "attachment; filename=" + f.name);
                }

                pw.write(f.data);
                pw.flush();
                pw.close();
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            throw e;
        }
        catch (SoftException ex)
        {
            execute(request, response, requestString, c, parameterMap, pw, dataStreams);
        }
        finally
        {
        }
    }

    public static Object execute(Logger log, boolean useFunctions, String requestString, Context c, Map<String, String[]> parameterMap,
            Map<String, InputStream> dataStreams, boolean noisy) throws JSONException
    {
        Resolvable resolver = requestStringBackoff(requestString, useFunctions, parameterMap);
        if (noisy)
            log.info("Request: " + requestString + toString(parameterMap));
        long ms = System.currentTimeMillis();
        long nanos = System.nanoTime();
        Object result = null;
        try
        {
            result = resolver.resolve(c, parameterMap, dataStreams);
        }
        catch (Throwable ex)
        {
            ArrayList<StackTraceElement> list = new ArrayList<StackTraceElement>();
            String cruncherName = resolver.getClass().getSimpleName().replace("Cruncher", "");
            StackTraceElement el = new StackTraceElement(requestString, cruncherName.substring(0, 1).toLowerCase() + cruncherName.substring(1),
                    "\n\t\t@params: " + Cruncher.debugParameters(parameterMap), c.size());
            for (StackTraceElement l : ex.getStackTrace())
            {
                if (l.getClassName().contains("com.eduworks.cruncher") || l.getClassName().contains("com.eduworks.resolver")
                        || l.getClassName().contains("com.eduworks.levr.servlet"))
                {
                    if (el != null)
                        list.add(el);
                    el = null;
                    continue;
                }
                list.add(l);
            }
            ex.setStackTrace((StackTraceElement[]) list.toArray(new StackTraceElement[0]));
            throw ex;
        }
        long elapsed = System.nanoTime() - nanos;
        if (resolver instanceof Cruncher)
        {
            ((Cruncher) resolver).nanosProcessing.addAndGet(elapsed);
            ((Cruncher) resolver).nanosInside.addAndGet(elapsed);
            ((Cruncher) resolver).executions.incrementAndGet();
        }
        if (noisy)
            log.info("Response (" + (System.currentTimeMillis() - ms) + "ms): " + requestString + toString(parameterMap));
        return result;
    }

    public static Resolvable requestStringBackoff(String requestString, boolean useFunctions, Map<String, String[]> parameterMap) throws JSONException
    {
        // Try full string, then back off a directory at a time until it works.
        // If it works, then add the remaining part to a parameter.
        String oldRequestString = requestString;
        String paramString = "";
        while (requestString.contains("/") && requestString.length() > 0 && resolvableWebServices.containsKey(requestString) == false
                && (!useFunctions || resolvableFunctions.containsKey(requestString)))
        {
            paramString = requestString.substring(requestString.lastIndexOf("/")) + paramString;
            requestString = requestString.substring(0, requestString.lastIndexOf("/"));
        }

        if (requestString.equals(""))
            requestString = oldRequestString;

        if (!parameterMap.containsKey("urlRemainder"))
        parameterMap.put("urlRemainder", new String[]
        {
            paramString
        });
        Resolvable resolvable = null;

        synchronized (lock)
        {
            resolvable = resolvableWebServices.get(requestString);
            if (useFunctions && resolvable == null)
                resolvable = resolvableFunctions.get(requestString);
            if (resolvable == null)
                throw new RuntimeException("Service does not exist: " + requestString);
            return resolvable;
        }
    }

    private static long getFilesLastModified(File dir)
    {
        long lmodified = 0;
        if (dir == null || dir.listFiles() == null)
            return codeFilesLastModifiedMs;
        for (File f : dir.listFiles())
            if (f.canRead())
                if (f.isDirectory())
                    lmodified = Math.max(lmodified, getFilesLastModified(f));
                else if (f.isFile())
                {
                    if (f.getName().endsWith(".rsl"))
                        lmodified = Math.max(f.lastModified(), lmodified);
                    if (f.getName().endsWith(".rs2"))
                        lmodified = Math.max(f.lastModified(), lmodified);
                    if (f.getName().endsWith(".psl"))
                        lmodified = Math.max(f.lastModified(), lmodified);
                    if (f.getName().endsWith(".jsl"))
                        lmodified = Math.max(f.lastModified(), lmodified);
                    if (f.getName().endsWith(".js"))
                        lmodified = Math.max(f.lastModified(), lmodified);
                }
        return lmodified;
    }

    private static boolean getParameter(String name, Map<String, String[]> parameterMap)
    {
        boolean flushCache = false;
        if (parameterMap.containsKey(name))
            flushCache = Boolean.parseBoolean(parameterMap.get(name)[0]);
        return flushCache;
    }

    private static String toString(Map<String, String[]> parameterMap)
    {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String[]> e : parameterMap.entrySet())
        {
            sb.append("~" + e.getKey());
            if (e.getValue() != null)
                for (int i = 0; i < e.getValue().length; i++)
                    sb.append(":" + e.getValue()[i]);
        }
        return sb.toString();
    }

}
