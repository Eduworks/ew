package com.eduworks.lang;

import java.util.AbstractCollection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;

/***
 * Helper methods on HashSet
 * 
 * @author Fritz
 *
 * @param <E>
 *            Set type
 */
public class EwHashSet<E> extends HashSet<E>
{
	private static final long serialVersionUID = 1L;

	public EwHashSet(Set<E> set)
	{
		super(set);
	}

	public EwHashSet(List<E> hs)
	{
		super(hs);
	}

	public EwHashSet()
	{
	}

	public EwHashSet(int count)
	{
		super(count);
	}

	public EwHashSet(AbstractCollection<E> objects)
	{
		super(objects);
	}

	public EwHashSet(JSONArray allowedWords)
	{
		for (int i = 0; i < allowedWords.length(); i++)
			try
			{
				add((E) allowedWords.get(i));
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
	}

	/***
	 * Returns true IFF any value in coll is found in this set.
	 * 
	 * @param coll
	 *            The collection to test against.
	 * @return If the collection contains any element in coll.
	 */
	public boolean containsAny(AbstractCollection<E> coll)
	{
		for (E e : coll)
			for (E r : this)
				if (e.equals(r))
					return true;
		return false;
	}
}