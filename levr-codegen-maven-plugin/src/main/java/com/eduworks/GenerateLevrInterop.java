package com.eduworks;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.eduworks.lang.EwList;
import com.eduworks.lang.EwMap;
import com.eduworks.lang.util.EwJson;
import com.eduworks.levr.servlet.impl.LevrResolverServlet;
import com.eduworks.resolver.Cruncher;
import com.eduworks.resolver.Resolvable;
import org.apache.commons.io.FileUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES, requiresDependencyResolution = ResolutionScope.COMPILE)
public class GenerateLevrInterop
        extends AbstractMojo
{

    @Parameter(defaultValue = "${project.build.sourceDirectory}", property = "sourceDir", required = true)
    private File sourceDirectory;

    @Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)
    private File outputDirectory;

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    private int cruncherCount = 0;
    Map<String, Object> packages = new HashMap<String, Object>();

    public void execute()
            throws MojoExecutionException
    {
        try
        {
            List<URL> urls = new ArrayList<URL>();
            URLClassLoader cl = loadProjectJars(urls);
            loadCrunchersFromJarsAndClasses(cl, urls);
            loadCrunchersFromRs2sAndLevrJs(urls);
            generateCodeFile();
        }
        catch (IOException ex)
        {
            Logger.getLogger(GenerateLevrInterop.class.getName()).log(Level.SEVERE, null, ex);
            throw new MojoExecutionException(ex.getMessage(), ex);
        }
    }

    private void loadCrunchersFromJarsAndClasses(URLClassLoader cl, List<URL> urls)
            throws MojoExecutionException
    {
        try
        {
            for (URL url : urls)
            {
                String scheme = url.getProtocol();
                if ("file".equals(scheme) && url.toString().endsWith(".jar"))
                {
                    ZipInputStream zip = new ZipInputStream(url.openConnection().getInputStream());
                    ZipEntry entry = null;
                    while ((entry = zip.getNextEntry()) != null)
                        if (entry.getName().contains("Cruncher"))
                            tryToGenerateCode(cl, entry.getName());
                }
                if ("file".equals(scheme) && url.toString().endsWith(".class"))
                    if (url.getFile().contains("Cruncher"))
                        tryToGenerateCode(cl, url.getFile());
            }
            getLog().info("Found " + cruncherCount + " crunchers in jars and classes.");
        }
        catch (Throwable ex)
        {
            Logger.getLogger(GenerateLevrInterop.class.getName()).log(Level.SEVERE, null, ex);
            throw new MojoExecutionException(ex.getMessage(), ex);
        }
    }

    private URLClassLoader loadProjectJars(List<URL> urls) throws MalformedURLException
    {
        for (Artifact a : project.getArtifacts())
            if (a.getFile() != null)
                urls.add(a.getFile().toURI().toURL());
        urls.addAll(getUrlsForAllJars(outputDirectory));
        getLog().info("Iterating " + urls.size() + " urls.");
        URLClassLoader cl = new URLClassLoader(urls.toArray(new URL[0]), this.getClass().getClassLoader());
        Thread.currentThread().setContextClassLoader(cl);
        return cl;
    }

    private void tryToGenerateCode(URLClassLoader cl, String name) throws InstantiationException, IllegalAccessException
    {
        try
        {
            getLog().info("Trying " + name);
            if (name.contains("$"))
                return;
            name = name.substring(name.indexOf("com"));
            Class c = cl.loadClass(name.replace("/", ".").replace(".class", "").replace(".java", ""));
            if (Modifier.isAbstract(c.getModifiers()))
                return;
            if (Modifier.isStatic(c.getModifiers()))
                return;
            if (c.getName().endsWith("CruncherFalse"))
                return;
            if (c.getName().endsWith("CruncherTrue"))
                return;
            getLog().info("Determining parent for " + c.getCanonicalName());
            Class superClass = c.getSuperclass();
            while (superClass != null && !superClass.getName().equals(Cruncher.class.getName()))
                superClass = superClass.getSuperclass();
            if (superClass != null)
                if (Cruncher.class.getName().equals(superClass.getName()))
                    generateCode(c);
        }
        catch (ClassNotFoundException ex)
        {
            Logger.getLogger(GenerateLevrInterop.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NoClassDefFoundError ex)
        {
            Logger.getLogger(GenerateLevrInterop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<URL> getUrlsForAllJars(File directory) throws MalformedURLException
    {
        List<URL> urls = new ArrayList<URL>();
        if (directory == null)
            return urls;
        String[] list = directory.list();
        if (list == null)
            return urls;
        for (String filename : list)
        {
            File f = new File(directory, filename);
            if (f.isDirectory())
            {
//                getLog().info(f.toURI().toURL().toString());
                urls.add(f.toURI().toURL());
                urls.addAll(getUrlsForAllJars(f));
            }
            if (f.getName().endsWith(".jar"))
                urls.add(f.toURI().toURL());
            if (f.getName().endsWith(".class"))
                urls.add(f.toURI().toURL());
        }
        return urls;
    }

    private void generateCode(Class c) throws InstantiationException, IllegalAccessException
    {
        getLog().info("Generating code for " + c.getCanonicalName());
        try
        {
            Map<String, Object> destination = generateCodeGetDestinationPackage(c.getPackage().getName());
            Cruncher cruncher = (Cruncher) c.newInstance();
            String code = "";
            String aReturn = cruncher.getReturn();
            if (aReturn != null && aReturn.contains("|"))
                aReturn = "Object";
            String resolverName = cruncher.getResolverName();
            if (resolverName.equals("try"))
                resolverName = "ttry";
            if (resolverName.equals("while"))
                resolverName = "wwhile";
            code += "public static " + (aReturn == null ? "void" : aReturn) + " " + resolverName + "(";
            JSONObject parameters = cruncher.getParameters();
            if (parameters == null)
                parameters = new JSONObject();
            boolean first = true;
            for (String key : EwJson.getKeys(parameters))
            {
                if (key.equals("try"))
                    key = "ttry";
                if (key.equals("operator"))
                    key = "toperator";
                if (key.equals("do") || key.equals("?do"))
                    key = "tdo";
                if (key.equals("true"))
                    key = "ttrue";
                if (key.equals("false"))
                    key = "tfalse";
                if (key.equals("<any>") || key.equals("?<any>"))
                    continue;
                if (first)
                    first = false;
                else
                    code += ", ";
                if (!parameters.getString(key).contains("|"))
                    code += parameters.getString(key) + " " + key.replace("?", "");
                else
                    code += "Object " + key.replace("?", "");

            }
            if (first)
                first = false;
            else
                code += ", ";
            code += "final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams";

            if (parameters.has("<any>") || parameters.has("?<any>"))
            {
                if (first)
                    first = false;
                else
                    code += ", ";
                code += "Object... any";
            }
            code += ") throws org.json.JSONException {";
            code += "com.eduworks.resolver.Cruncher cru = new " + c.getName() + "();";
            for (String key : EwJson.getKeys(parameters))
            {
                if (key.equals("try"))
                    key = "ttry";
                if (key.equals("operator"))
                    key = "toperator";
                if (key.equals("do") || key.equals("?do"))
                    key = "tdo";
                if (key.equals("true"))
                    key = "ttrue";
                if (key.equals("false"))
                    key = "tfalse";
                if (key.equals("<any>"))
                    continue;
                if (key.equals("?<any>"))
                    continue;
                code += "if ("+key.replace("?", "")+" != null) cru.build(\"" + key.replace("?", "") + "\"," + key.replace("?", "") + ");";
            }
            if (parameters.has("<any>") || parameters.has("?<any>"))
            {
                code += "int i = 0;";
                code += "for (Object o : any)";
                code += "    cru.build(\"\"+'a'+i,o);";
            }
            code += (aReturn != null ? "return (" + aReturn + ")" : "") + "cru.resolve(c,parameters,dataStreams);";
            code += "}";
            destination.put(resolverName, code);
            cruncherCount++;
        }
        catch (JSONException ex)
        {
            Logger.getLogger(GenerateLevrInterop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Map<String, Object> generateCodeGetDestinationPackage(String namespace)
    {
        String[] parts = namespace.split("\\.");
        getLog().info(namespace);
        Map<String, Object> destination = null;
        for (String s : parts)
        {
            if (s.equals("com") || s.equals("levr") || s.equals("eduworks") || s.equals("resolver") || s.equals("cruncher"))
                continue;
            if (destination == null)
            {
                if (!packages.containsKey(s))
                    packages.put(s, new HashMap<String, Object>());
                destination = (Map<String, Object>) packages.get(s);
            }
            else
            {
                if (!destination.containsKey(s))
                    destination.put(s, new HashMap<String, Object>());
                destination = (Map<String, Object>) destination.get(s);
            }
        }
        if (destination == null)
            destination = packages;
        return destination;
    }

    private void generateCodeFile() throws IOException
    {
        if (packages.isEmpty())
        {
            getLog().info("No packages found.");
            return;
        }
        String c = "";
        c += "package com.eduworks." + project.getArtifactId() + ";";
        c += "import com.eduworks.resolver.Context;";
        c += "import com.eduworks.resolver.Resolvable;";
        c += "import org.json.JSONArray;";
        c += "import org.json.JSONObject;";
        c += "import java.util.List;";
        c += "import java.io.InputStream;";
        c += "import java.io.File;";
        c += "import com.eduworks.util.io.InMemoryFile;";
        c += "import java.util.Map;";
        c += "public abstract class Cruncher extends com.eduworks.resolver.Cruncher{public static class levr{";
        for (String s : packages.keySet())
            c += generateClass(s, packages.get(s));
        c += "}}";
        File f = new File(sourceDirectory + "/" + ("com.eduworks." + project.getArtifactId()).replace(".", "/") + "/Cruncher.java");
        f.getParentFile().mkdir();
        getLog().info("Writing file to " + f.getPath());
        FileUtils.writeStringToFile(f, c);
    }

    private String generateClass(String s, Object o)
    {
        if (o instanceof String)
            return o.toString();
        Map<String, Object> m = (Map<String, Object>) o;
        String c = "";
        c += "public static class " + s + "{";
        for (String s2 : m.keySet())
            c += generateClass(s2, m.get(s2));
        c += "}";
        return c;
    }

    private void loadCrunchersFromRs2sAndLevrJs(List<URL> urls)
    {
        try
        {
            LevrResolverServlet.levrFunctionUseCodeFileAsNamespace = true;
            LevrResolverServlet.resolvableWebServices = new EwMap<String, Resolvable>();
            LevrResolverServlet.resolvableFunctions = new EwMap<String, Resolvable>();
            LevrResolverServlet.codeFiles = new EwList<File>();
            LevrResolverServlet.loadAdditionalConfigFiles(new File("target/classes"));
            for (URL url : urls)
            {
                String scheme = url.getProtocol();
                getLog().info(url.toString());
                if ("file".equals(scheme) && url.toString().endsWith(".jar") && (url.toString().contains("ep.") || url.toString().contains("ew.")))
                {
                    ZipInputStream zip = new ZipInputStream(url.openConnection().getInputStream());
                    ZipEntry entry = null;
                    while ((entry = zip.getNextEntry()) != null)
                        if (entry.getName().endsWith(".rs2") || entry.getName().endsWith(".js"))
                        {
                            getLog().info(entry.getName());
                            int size;
                            byte[] buffer = new byte[2048];
                            File createTempFile = new File(entry.getName().replace("/","."));
                            
                            FileOutputStream fos = new FileOutputStream(createTempFile);
                            while ((size = zip.read(buffer, 0, buffer.length)) != -1)
                                fos.write(buffer, 0, size);
                            fos.close();
                            LevrResolverServlet.loadAdditionalConfigFiles(createTempFile);
                            createTempFile.delete();
                        }
                }
            }
            for (String key : LevrResolverServlet.resolvableFunctions.keySet())
            {
                getLog().info("Trying: " + key);
                generateCode(key, LevrResolverServlet.resolvableFunctions.get(key));
            }
        }
        catch (Throwable ex)
        {
            Logger.getLogger(GenerateLevrInterop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generateCode(String key, Resolvable get)
    {
        String namespace = key.substring(0, key.lastIndexOf("."));
        String name = key.substring(key.lastIndexOf(".") + 1);
        namespace = namespace.replace("-","");
        Map<String, Object> destinationPackage = generateCodeGetDestinationPackage(namespace);
        String template;
        template = "public static Object " + name + "(Object obj,final Context c, final Map<String, String[]> parameters, final Map<String, InputStream> dataStreams,final Map<String, Object> build) throws org.json.JSONException{\n";
        template += "\tcom.eduworks.resolver.Cruncher cru = new com.eduworks.cruncher.refl.CruncherExecute();\n";
        template += "\tcru.build(\"service\",\"" + name + "\");\n";
        template += "\tcru.build(\"obj\",obj);\n";
        template += "\tif (build != null) for(String k : build.keySet()) if (build.get(k) != null) cru.build(k,build.get(k));\n";

        template += "\treturn cru.resolve(c,parameters,dataStreams);\n";
        template += "}";
        destinationPackage.put(name, template);
    }
}
