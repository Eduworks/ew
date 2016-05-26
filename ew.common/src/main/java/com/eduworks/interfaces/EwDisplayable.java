package com.eduworks.interfaces;

/***
 * Typically, toString is used to describe things in a debug way. Displayable
 * provides a user-friendly representation of the object.
 * 
 * @author fray
 * 
 */
public interface EwDisplayable
{
	/***
	 * Returns the object in a user friendly format.
	 * @return User readable string.
	 */
	String toDisplayString();
}
