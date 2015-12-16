package com.airline.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	private static Properties properties = new Properties();
	static {
		readConfigFile();
	}
	
	public static boolean readConfigFile(){
		try {
			properties.load(IOUtilities.getResourceInputStream("config.properties"));
			properties.load(IOUtilities.getResourceInputStream("Database.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static String getProperty(String key){
		return (null != properties.get(key)) ? properties.get(key).toString() : null;
	}
	
	public static Integer getIntProperty(String key){
		return (null != properties.get(key)) ? Integer.parseInt(properties.get(key).toString()) : null;
	}
	
	public static String getPDFFolder(){
		return getProperty(Constants.PDF_FOLDER);
	}
	public static String getQuickbookThreadSleep(){
		return getProperty(Constants.QUICKBOOK_THREAD_SLEEP);
	}
	public static String getRecurranceThreadSleep(){
		return getProperty(Constants.RECURRANCE_THREAD_SLEEP);
	}
	public static String getQuickBookODBC(){
		return getProperty(Constants.QUICKBOOK_ODBC);
	}
	public static String getQuickBookUsername(){
		return getProperty(Constants.QUICKBOOK_USERNAME);
	}
	public static String getQuickBookPassword(){
		return getProperty(Constants.QUICKBOOK_PASSWORD);
	}
	
}
