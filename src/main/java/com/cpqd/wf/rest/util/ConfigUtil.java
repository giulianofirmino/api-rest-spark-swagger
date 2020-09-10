package com.cpqd.wf.rest.util;

import java.util.ResourceBundle;

public class ConfigUtil {

	private static ResourceBundle config = ResourceBundle.getBundle("config");
	
	public static String getProperty(String name) {
		return config.getString(name);
	}
	
}
