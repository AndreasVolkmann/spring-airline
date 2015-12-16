/**
 * 
 */
package com.airline.utils;

/**
 * @author
 *
 */
public class Util {
	public static String getNextHexString(String str){
		if(null == str)
			return str;
		Long i = Long.parseLong(str, 16);
		i++;
		return Long.toHexString(i);
	}
}
