package org.cytoscape.rest.internal.resource;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.cytoscape.rest.internal.task.ResourceManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.swagger.annotations.ExternalDocs;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import io.swagger.jaxrs.Reader;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.config.ReaderListener;
import io.swagger.models.Swagger;
import io.swagger.util.Json;

@Path("/v1/swagger.json")
@Singleton
public class CyRESTSwagger extends AbstractResource
{
	private String swaggerDefinition;
	
	final Set<Class<?>> classes = new HashSet<Class<?>>();
	
	public void addResource(Class<?> clazz)
	{
		classes.add(clazz);
		updateSwagger();
	}
	
	public void removeResource(Class<?> clazz)
	{
		classes.remove(clazz);
		updateSwagger();
	}
	
	public CyRESTSwagger(){
		updateSwagger();
	}
	
	protected void updateSwagger()
	{
		swaggerDefinition = null;
	}
	
	public boolean isSwaggerDefinitionNull()
	{
		return (swaggerDefinition == null);
	}
	
	protected void buildSwagger()
	{
		final Set<Class<?>> classes = new HashSet<Class<?>>(this.classes);
		BeanConfig beanConfig = new BeanConfig(){
			public Set<Class<?>> classes()
			{
				//Set<Class<?>> classes = new HashSet<Class<?>>();
				//classes.addAll();
				classes.add(CyRESTSwaggerConfig.class);
				return classes;
			}
		};
	
		//FIXME This needs to get set from the ResourceManager
		beanConfig.setHost(ResourceManager.HOST + ":" + cyRESTPort);
		beanConfig.setScan(true);
		beanConfig.setPrettyPrint(true);
		
		Swagger swagger = beanConfig.getSwagger();

		// serialization of the Swagger definition
		try 
		{
			Json.mapper().enable(SerializationFeature.INDENT_OUTPUT);
			this.swaggerDefinition = Json.mapper().writeValueAsString(swagger);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public String get()
	{
		if (swaggerDefinition == null)
		{
			buildSwagger();
		}
		return swaggerDefinition;
	}
	
	@SwaggerDefinition(
			info = @Info(
					description = "A RESTful service for accessing Cytoscape 3.",
					version = "V2.0.0",
					title = "CyREST API"
					//termsOfService = "http://theweatherapi.io/terms.html",
					// contact = @Contact(
					//   name = "Rain Moore", 
					//    email = "rain.moore@theweatherapi.io", 
					//    url = "http://theweatherapi.io"
					// ),
					// license = @License(
					//    name = "Apache 2.0", 
					//    url = "http://www.apache.org/licenses/LICENSE-2.0"
					// )
					),
			//Be wary of this host parameter if you are using BeanConfig; use one or the other, as they will
			//cause conflicts.
			//host = "localhost:1234",
			basePath = "",
			consumes = {"application/json", "application/xml"},
			produces = {"application/json", "application/xml"},
			schemes = {SwaggerDefinition.Scheme.HTTP},
			tags = 
		{
			@Tag(name = CyRESTSwaggerConfig.APPS_TAG, description="Access to Apps"),
			@Tag(name = CyRESTSwaggerConfig.COLLECTIONS_TAG),
			@Tag(name = CyRESTSwaggerConfig.COMMANDS_TAG),
			@Tag(name = CyRESTSwaggerConfig.CYTOSCAPE_SYSTEM_TAG),
			@Tag(name = CyRESTSwaggerConfig.GROUPS_TAG),
			@Tag(name = CyRESTSwaggerConfig.LAYOUTS_TAG),
			@Tag(name = CyRESTSwaggerConfig.NETWORKS_TAG),
			@Tag(name = CyRESTSwaggerConfig.NETWORK_VIEWS_TAG),
			@Tag(name = CyRESTSwaggerConfig.REST_SERVICE_TAG),
			@Tag(name = CyRESTSwaggerConfig.SESSION_TAG),
			@Tag(name = CyRESTSwaggerConfig.TABLES_TAG),	
			@Tag(name = CyRESTSwaggerConfig.USER_INTERFACE_TAG),			
			@Tag(name = CyRESTSwaggerConfig.VISUAL_PROPERTIES_TAG),
			@Tag(name = CyRESTSwaggerConfig.VISUAL_STYLES_TAG)
				
		
			
		}, 
		externalDocs = @ExternalDocs(value = "Cytoscape", url = "http://cytoscape.org/")
			)
	public static class CyRESTSwaggerConfig implements ReaderListener
	{

		public static final String SESSION_TAG = "Session";
		public static final String APPS_TAG = "Apps";
		public static final String USER_INTERFACE_TAG = "User Interface";
		public static final String NETWORKS_TAG = "Networks";
		public static final String TABLES_TAG = "Tables";
		public static final String COMMANDS_TAG = "Commands";
		public static final String REST_SERVICE_TAG = "REST Service";
		public static final String LAYOUTS_TAG = "Layouts";
		public static final String NETWORK_VIEWS_TAG = "Network Views";
		public static final String VISUAL_PROPERTIES_TAG = "Visual Properties";
		public static final String VISUAL_STYLES_TAG = "Visual Styles";
		public static final String GROUPS_TAG = "Groups";
		public static final String COLLECTIONS_TAG = "Collections";
		public static final String CYTOSCAPE_SYSTEM_TAG = "Cytoscape System";

		@Override
		public void beforeScan(Reader arg0, Swagger arg1) 
		{
			
		}

		public void afterScan(Reader reader, Swagger swagger)
		{
		}
	}
}