package de.home.vs.resource;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import java.util.List;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.home.vs.model.DataSource;
import de.home.vs.model.Item;
import de.home.vs.rest_service_app.ItemService;



@SuppressWarnings("unused")
@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ItemRessource {

    private final ItemService service;


 
 
 public ItemRessource() {
     service = new ItemService();
 }

 
 @GET
 @Produces(MediaType.APPLICATION_JSON)
 public Response getAllItems(@Context UriInfo uriInfo) {
     List<Item> items = service.getAllItems();
     JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
     for (Item item : items) {
         JsonObjectBuilder itemBuilder = Json.createObjectBuilder()
                 .add("id", item.getId())
                 .add("name", item.getName())
                 .add("link", getItemURL(uriInfo, item.getId()));

         jsonArrayBuilder.add(itemBuilder);
     }

     JsonArray jsonArray = jsonArrayBuilder.build();
     return Response.ok(jsonArray).build();
 }

 private String getItemURL(UriInfo uriInfo, int itemId) {
     String baseUri = uriInfo.getBaseUri().toString();
     String itemUrl = baseUri + "items/" + itemId;
     return itemUrl;
 }

 

 
 
 
 @GET
 @Path("/{id}")
 public Response getItemById(@PathParam("id") int id) {
    System.out.println("getItemById() called ...");
	 Item item = service.getItemById(id);
     if (item != null) {
         javax.json.JsonObject jsonObject = Json.createObjectBuilder()
                 .add("id", item.getId())
                 .add("name", item.getName())
                 .add("description", item.getDescription())
                 .add("price", item.getPrice())
                 .build();
         return Response.ok(jsonObject).build();
     } else {
         return Response.status(Response.Status.NOT_FOUND).build();
     }
 }

 
 
 
     @POST
     public Response addItem(String json) {
    	 System.out.println("createItem() Called ....");
    	 try {
    		 ObjectMapper objectMapper = new ObjectMapper();
    		 Item newItem = objectMapper.readValue(json, Item.class);
    		 service.createItem(newItem);
    		 System.out.println("New item added: " + newItem.toString());
    		 return Response.status(Response.Status.CREATED).build();
     	         
    	 	} catch (IOException e) {
    	 		System.out.println("item not created");
    	 		e.printStackTrace();
    	 		return Response.status(Response.Status.BAD_REQUEST).build();
    	 	}
}
 
 
     @PUT
     @Path("/{id}")
     public Response updateItem(@PathParam("id") int id, String updatedItemJson) {
    	 System.out.println("updateItem() called ...");
         try {
        	 ObjectMapper objectMapper = new ObjectMapper();
             Item updatedItem = objectMapper.readValue(updatedItemJson, Item.class);          
             service.updateItem(updatedItem);
             System.out.println("Item " +updatedItem.getName()  +"updated ...");
             return Response.ok().build();
         } catch (Exception e) {
             return Response.status(Response.Status.BAD_REQUEST).build();
         }
     }
     
     


 @DELETE
 @Path("/{id}")
 public Response deleteItem(@PathParam("id") int id) {
     service.deleteItem(id);
     System.out.println("item  deleted");
     return Response.ok().build();
 }
 
}