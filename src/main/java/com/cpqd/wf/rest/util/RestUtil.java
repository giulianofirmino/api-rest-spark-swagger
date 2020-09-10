package com.cpqd.wf.rest.util;

import java.util.Map;

import org.boon.json.JsonFactory;

public class RestUtil {

	private RestUtil() {}
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> fromJson(String str) {
		return JsonFactory.fromJson(str, Map.class);
	}
	
	public static <E> E fromJson(String str, Class<?> clazz) {
		return (E) JsonFactory.fromJson(str, clazz);
	}

}
