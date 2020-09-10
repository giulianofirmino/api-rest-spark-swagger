package com.cpqd.wf.rest.resource;

import static spark.Spark.get;

import java.nio.charset.Charset;
import java.util.Set;

import org.apache.maven.model.Model;
import org.apache.maven.model.Organization;

import com.cpqd.wf.rest.WebServer;
import com.cpqd.wf.rest.util.MavenUtil;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.jaxrs.Reader;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.Swagger;

public class SwaggerResource {

	static final String SWAGGER_JSON;
	
	static {
		try {
			SWAGGER_JSON = getSwaggerJson();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao iniciar swagger", e);
		}
	}
	
	public static String getSwaggerJson() throws Exception {
		Swagger swagger = getSwagger();
		String json = swaggerToJson(swagger);
		return json;
	}

	public static Swagger getSwagger() throws Exception {

		Model maven = MavenUtil.readPom();

		Organization organization = maven.getOrganization();

		io.swagger.models.Contact contact = new io.swagger.models.Contact();
		contact.setName(organization.getName());
		contact.setUrl(organization.getUrl());

		io.swagger.models.Info info = new io.swagger.models.Info();
		info.setContact(contact);
		
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setHost(WebServer.REST_ADDRESS);
		beanConfig.setInfo(info);
		beanConfig.setDescription(new String(maven.getDescription().getBytes(), Charset.forName("UTF-8")));
		beanConfig.setVersion(maven.getVersion() == null ? maven.getParent().getVersion() : maven.getVersion());
		beanConfig.setTitle(maven.getName());
		beanConfig.setBasePath(WebServer.BASE_PATH);
		beanConfig.setResourcePackage(WebServer.APP_PACKAGE);
		beanConfig.setScan(true);
		
		Swagger swagger = beanConfig.getSwagger();

		Reader reader = new Reader(swagger);

		Set<Class<?>> apiClasses = WebServer.SCAN_PACKAGES.getTypesAnnotatedWith(Api.class);
		return reader.read(apiClasses);
	}

	public static String swaggerToJson(Swagger swagger) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_EMPTY);
		String json = objectMapper.writeValueAsString(swagger);
		return json;
	}
	
	public static void prepare() {
		get("/swagger.json", (req, res) -> {
			return SWAGGER_JSON;
		});
	}
	
}
