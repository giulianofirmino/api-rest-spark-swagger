package com.cpqd.wf.rest.util;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.lang.reflect.Method;
import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.cpqd.wf.rest.WebServer;

import io.swagger.annotations.Api;
import spark.Request;
import spark.Response;
import spark.Route;

public class AppDiscoverApi {

	public static void prepareAll() throws InstantiationException, IllegalAccessException {

		Set<Class<?>> apiRoutes = WebServer.SCAN_PACKAGES.getTypesAnnotatedWith(Api.class);

		for (Class<?> clazz : apiRoutes) {
			Object router = clazz.newInstance();

			Path path = clazz.getAnnotation(Path.class);
			Produces produces = clazz.getAnnotation(Produces.class);
			
			for (final Method method : clazz.getMethods()) {
				Route sparkRoute = new Route() {
					@Override
					public Object handle(Request request, Response response) throws Exception {
						return method.invoke(router, request, response);
					}
				};

				Path methodPath = method.getAnnotation(Path.class);
				Produces methodProduces = method.getAnnotation(Produces.class);

				String acceptType = methodProduces == null ? (produces == null ? "*/*" : produces.value()[0]) : methodProduces.value()[0]; 

				String friendlyRoute = String.format("%s%s%s", WebServer.BASE_PATH, path.value(), methodPath == null ? "" : methodPath.value()).replaceAll("\\{(.*?)\\}", ":$1");

				POST post = method.getAnnotation(POST.class);
				if (post != null) {
					post(friendlyRoute, acceptType, sparkRoute);
					continue;
				}

				GET get = method.getAnnotation(GET.class);
				if (get != null) {
					get(friendlyRoute, acceptType, sparkRoute);
					continue;
				}

				DELETE delete = method.getAnnotation(DELETE.class);
				if (delete != null) {
					delete(friendlyRoute, acceptType, sparkRoute);
					continue;
				}

				PUT put = method.getAnnotation(PUT.class);
				if (put != null) {
					put(friendlyRoute, acceptType, sparkRoute);
					continue;
				}
			}
		}
	}

}
