package com.cpqd.wf.rest;

import org.reflections.Reflections;

import com.cpqd.wf.rest.filter.BaseFilter;
import com.cpqd.wf.rest.filter.CorsFilter;
import com.cpqd.wf.rest.resource.SwaggerResource;
import com.cpqd.wf.rest.util.AppDiscoverApi;
import com.cpqd.wf.rest.util.ConfigUtil;

import spark.Spark;

public class WebServer {
    
	public static final String TYPE_APPLICATION_JSON = "application/json";
	public static final String TYPE_ZIP = "application/zip";
	public static final String TYPE_TEXT_HTML = "text/html";
	
	public static final String REST_HOST = ConfigUtil.getProperty("rest.host");
	public static final String REST_PORT = ConfigUtil.getProperty("rest.port");
	public static final String REST_ADDRESS = String.format("%s:%s", WebServer.REST_HOST, WebServer.REST_PORT);

	public static final String BASE_PATH = "/rest";

	public static final String APP_PACKAGE = WebServer.class.getPackage().getName();
	public static final Reflections SCAN_PACKAGES = new Reflections(APP_PACKAGE);
	
	public static void main(String[] args) throws Exception {

		Spark.ipAddress(REST_HOST);
		Spark.port(Integer.parseInt(REST_PORT));

		BaseFilter.prepare();
		CorsFilter.prepare();
		
		SwaggerResource.prepare();

		AppDiscoverApi.prepareAll();
	}

}