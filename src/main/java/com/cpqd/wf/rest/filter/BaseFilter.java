package com.cpqd.wf.rest.filter;

import static com.cpqd.wf.rest.util.ExceptionUtil.getStackTraceException;
import static org.boon.json.JsonFactory.toJson;
import static spark.Spark.after;
import static spark.Spark.exception;
import static spark.Spark.externalStaticFileLocation;

import java.io.File;

import org.apache.log4j.Logger;

import com.cpqd.wf.rest.WebServer;
import com.cpqd.wf.rest.util.ConfigUtil;

public class BaseFilter {

	public static final String REST_WEBAPP = ConfigUtil.getProperty("rest.webapp");

	private static Logger logger = Logger.getLogger(BaseFilter.class);

	private BaseFilter() {}
	
	public static class RESTMessage {

		private String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
	}
	
	public static void prepare() {
		
		File file = new File("webapp");
		if (!file.exists()) {
			file = new File("target/webapp");
			if (!file.exists()) {
				file = new File(REST_WEBAPP);	
			}
		}
		externalStaticFileLocation(file.getAbsolutePath());
		

		exception(Exception.class, (exception, request, response) -> {

			String detailMessage = exception.getMessage();

			response.status(500);

			detailMessage = getStackTraceException(exception, true);
			logger.error(detailMessage);

			RESTMessage result = new RESTMessage();
			result.setMessage(detailMessage);
			
			response.type(WebServer.TYPE_APPLICATION_JSON);
			response.body(toJson(result));
		});

		after((request, response) -> {
			String contentType = response.raw().getContentType();
			if (contentType == null) {
				response.raw().setContentType(WebServer.TYPE_APPLICATION_JSON);
			}
		});
	}

}