package com.eduworks.resolver;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.eduworks.lang.EwMap;
import com.eduworks.lang.EwSet;
import com.eduworks.lang.json.impl.EwJsonArray;
import com.eduworks.lang.util.EwJson;
import com.google.common.base.Predicate;

public class ResolverFactory
{
	public static Map<String, Class<? extends Cruncher>> cruncherSpecs = null;
	public static Map<String, Class<? extends Scripter>> scripterSpecs = null;

	// static { populateFactorySpecsDynamically(); }

	/** Cast or parse object as JSON object, or return the object */
	private static Object cast(Object o) throws JSONException
	{
		JSONArray ja = EwJson.getInstanceOfJsonArray(o);
		if (ja != null)
			return create(ja);

		JSONObject jo = EwJson.getInstanceOfJsonObject(o);
		if (jo != null)
			return create(jo);

		if (o instanceof Cruncher)
			return create((Cruncher) o);

		return o;
	}

	private static Object create(JSONArray array) throws JSONException
	{
		if (array.length() == 0)
			return new EwJsonArray();

		EwJsonArray ja = new EwJsonArray();
		for (int i = 0; i < array.length(); i++)
			ja.put(cast(array.get(i)));

		return ja;
	}

	private static Object create(Cruncher c) throws JSONException
	{
		return c;
	}

	@SuppressWarnings("rawtypes")
	public static Resolvable create(JSONObject obj) throws JSONException
	{
		Resolvable r = null;

		if (r == null)
			r = getCorrectResolver(obj.getString("function"));

		Iterator i = obj.keys();
		while (i.hasNext())
		{
			final String key = i.next().toString();

			if ("function".equals(key))
				continue;

			r.build(key, cast(obj.get(key)));
		}
		return r;
	}

	public static synchronized void populateFactorySpecsDynamically()
	{
		if (cruncherSpecs != null)
			return;
		Collection<URL> urlsForCurrentClasspath = new ArrayList<URL>(ClasspathHelper.forManifest());
		urlsForCurrentClasspath.addAll(ClasspathHelper.forJavaClassPath());
		urlsForCurrentClasspath.addAll(ClasspathHelper.forClassLoader());
		EwSet<URL> urls = new EwSet<URL>();
		for (URL url : urlsForCurrentClasspath)
		{
			if (!url.toString().contains("icu4j"))
				urls.add(url);
		}
		System.out.println(urls.toString());
		System.out.println("We are now going to scan for any Resolvers, Crunchers, or Scripters.");
		List<ClassLoader> classLoadersList = new ArrayList<ClassLoader>();
		classLoadersList.add(ClasspathHelper.contextClassLoader());
		classLoadersList.add(ClasspathHelper.staticClassLoader());
		Reflections reflections = new Reflections(new ConfigurationBuilder().addClassLoaders(classLoadersList).setUrls(urls)
				.setScanners(new SubTypesScanner(), new TypeAnnotationsScanner().filterResultsBy(new Predicate<String>()
				{
					@Override
					public boolean apply(String input)
					{
						if (input.contains("Cruncher"))
							return true;
						if (input.contains("Scripter"))
							return true;
						if (input.startsWith("Command"))
							return true;
						return false;
					}
				}), new ResourcesScanner()));
		System.out.println("Finished Scanning. Getting subtypes and initializing classes.");
		int crunchers = 0, scripters = 0;
		Set<Class<? extends Cruncher>> csubTypesOf = reflections.getSubTypesOf(Cruncher.class);
		cruncherSpecs = new EwMap<String, Class<? extends Cruncher>>();

		for (Class<? extends Cruncher> c : csubTypesOf)
		{
			try
			{
				Cruncher newInstance = c.newInstance();
				for (String s : newInstance.getResolverNames())
				{
					if (cruncherSpecs.containsKey(s))
						System.out.println("Duplicate Cruncher Found: " + s);
					cruncherSpecs.put(s, c);
				}
				crunchers++;
			}
			catch (InstantiationException ex)
			{
			}
			catch (NoClassDefFoundError e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch (Throwable t)
			{
				t.printStackTrace();
			}
		}

		System.out.println(crunchers + " Crunchers.");

		Set<Class<? extends Scripter>> ssubTypesOf = reflections.getSubTypesOf(Scripter.class);
		scripterSpecs = new EwMap<String, Class<? extends Scripter>>();

		for (Class<? extends Scripter> s : ssubTypesOf)
		{
			try
			{
				Scripter newInstance = s.newInstance();
				for (String key : newInstance.getResolverNames())
				{
					scripterSpecs.put(key, s);
				}
				scripters++;
			}
			catch (InstantiationException ex)
			{

			}
			catch (NoClassDefFoundError e)
			{

			}
			catch (IllegalAccessException e)
			{

			}
		}
		System.out.println(scripters + " Scripters.");
		System.out.println("Free Memory - " + ((Runtime.getRuntime().freeMemory() / 1024) / 1024));
		System.out.println("Total Memory - " + ((Runtime.getRuntime().totalMemory() / 1024) / 1024));
		System.out.println("Max Memory - " + ((Runtime.getRuntime().maxMemory() / 1024) / 1024));
		System.out.println("Available CPU - " + (Runtime.getRuntime().availableProcessors()));
	}

	private static Resolvable getCorrectResolver(String name) throws JSONException
	{
		if (cruncherSpecs == null)
			populateFactorySpecsDynamically();

		Class<? extends Cruncher> c2 = cruncherSpecs.get(name);
		Class<? extends Scripter> s = scripterSpecs.get(name);

		try
		{
			if (c2 == null && s == null)
			{
				c2 = cruncherSpecs.get("execute");
				if (c2 == null)
					throw new RuntimeException("Neither cruncher nor function exist: "+name);
				Cruncher cruncher = c2.newInstance();
				cruncher.build("service", name);
				return cruncher;
			}

			if (c2 == null && s == null)
				throw new JSONException("Cannot resolve name: " + name);

			if (c2 == null)
				return s.newInstance();
			else
				return c2.newInstance();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
