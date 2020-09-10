package com.cpqd.wf.rest.resource;

import static com.cpqd.wf.rest.WebServer.TYPE_APPLICATION_JSON;
import static org.boon.json.JsonFactory.toJson;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.cpqd.wf.rest.filter.BaseFilter.RESTMessage;

import com.cpqd.wf.rest.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import spark.Request;
import spark.Response;

import java.util.Map;

@Api("users")
@Path("/users")
@Produces(TYPE_APPLICATION_JSON)
public class UserRESTResource {
	
	@GET
	@ApiOperation(value = "Retorna todos os usuários", nickname = "findAllUsers")
	@ApiImplicitParams({})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = Map.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Error", response = RESTMessage.class)
	})
	public String findAllUsers(@ApiParam(hidden=true) Request request, @ApiParam(hidden=true)Response response) throws Exception {
		return toJson(UserService.findAll());
	}

	@Path("/{userId}")
	@GET
	@ApiOperation(value = "Retorna informações de um usuário", nickname = "findProcessesBySpaceAndProject")
	@ApiImplicitParams({
		@ApiImplicitParam(required = true, dataType = "string", name = "userId", paramType = "path"),
	})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = Map.class),
			@ApiResponse(code = 500, message = "Error", response = RESTMessage.class)
	})
	public String findProcessesBySpaceAndProject(@ApiParam(hidden=true) Request request, @ApiParam(hidden=true)Response response) throws Exception {
		return toJson(UserService.findById(request.params("userId")));
	}

}
