package de.home.vs.resource;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
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
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.model.internal.RankedComparator.Order;

import de.home.vs.model.Item;
import de.home.vs.model.OrderItem;
import de.home.vs.model.OrderModel;
import de.home.vs.rest_service_app.ItemService;
import de.home.vs.rest_service_app.OrderService;

@SuppressWarnings("unused")
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class OrderRessource {

	   private OrderService orderService;
	   private ItemService itemService;

	   public OrderRessource() {
	        orderService = OrderService.getInstance();
	        itemService = ItemService.getInstance();
	    }

	   
	   
	   @GET
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response getAllOrders(@Context UriInfo uriInfo) {
		   	System.out.println("getAllOrders()  Called...");
	        List<OrderModel> orders = orderService.getAllOrders();
	        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
	        for (OrderModel order : orders) {
	            JsonObjectBuilder orderBuilder = Json.createObjectBuilder()
	                    .add("id", order.getId())
	                	.add("Total Price", order.getTotalPrice())
	                    .add("link", getOrderURL(uriInfo, order.getId()));
	            jsonArrayBuilder.add(orderBuilder);
	        }

	        JsonArray jsonArray = jsonArrayBuilder.build();
	        return Response.ok(jsonArray).build();
	    }

	    private String getOrderURL(UriInfo uriInfo, int orderId) {
	        String baseUri = uriInfo.getBaseUri().toString();
	        String orderUrl = baseUri + "orders/" + orderId;
	        return orderUrl;
	    }
	
	    
	    
	    
	    
	    
	    @GET
	    @Path("/{id}")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response getOrderById(@PathParam("id") int id) {
	        OrderModel order = orderService.getOrderById(id);
	        if (order != null) {
	            JsonArrayBuilder orderItemsArrayBuilder = Json.createArrayBuilder();
	            for (OrderItem orderItem : order.getOrderItems()) {
	                JsonObjectBuilder itemObjectBuilder = Json.createObjectBuilder()
	                        .add("id", orderItem.getItem().getId())
	                        .add("name", orderItem.getItem().getName())
	                        .add("description", orderItem.getItem().getDescription())
	                        .add("price", orderItem.getItem().getPrice());
	                JsonObjectBuilder orderItemObjectBuilder = Json.createObjectBuilder()
	                        .add("item", itemObjectBuilder)
	                        .add("quantity", orderItem.getQuantity());
	                orderItemsArrayBuilder.add(orderItemObjectBuilder);
	            }

	            JsonObjectBuilder orderObjectBuilder = Json.createObjectBuilder()
	                    .add("id", order.getId())
	                	.add("Total Price", order.getTotalPrice())
	                    .add("orderItems", orderItemsArrayBuilder);

	            JsonObject jsonObject = orderObjectBuilder.build();
	            return Response.ok(jsonObject, MediaType.APPLICATION_JSON).build();
	        } else {
	            return Response.status(Response.Status.NOT_FOUND).build();
	        }
	    }

	    
	    
	    @POST
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response addOrder(JsonObject jsonObject) {
	    	System.out.println("addOrder() called ...");
	        // Parse the JSON and extract the necessary data
	        int id = jsonObject.getInt("id");
	        List<OrderItem> orderItems = new ArrayList<>();
	        JsonArray orderItemsArray = jsonObject.getJsonArray("orderItems");
	      
	        for (JsonValue jsonValue : orderItemsArray) {
	        	JsonObject orderItemObject = (JsonObject) jsonValue;
	            JsonObject itemObject = orderItemObject.getJsonObject("item");
	            
	            int itemId = itemObject.getInt("id");
	            String itemName = itemObject.getString("name");
	            String itemDescription = itemObject.getString("description");
	            double itemPrice = itemObject.getJsonNumber("price").doubleValue();
	            
	            Item item = itemService.getItemById(itemId);
	            
	            int quantity = orderItemObject.getInt("quantity");
	            OrderItem orderItem = new OrderItem(item, quantity);
	            orderItems.add(orderItem);
	        }

	        // Create the Order object
	        OrderModel order = new OrderModel(id, orderItems);
	        orderService.addOrder(order);
	        System.out.println("order "+order+ "  added");
	        return Response.status(Response.Status.CREATED).build();
	    }
	    
	    
	    
	    @PUT
	    @Path("/{id}")
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response updateOrder(@PathParam("id") int id, JsonObject jsonObject) {
	    	System.out.println("updateOrder() called ...");
	    	OrderModel existingOrder = orderService.getOrderById(id);
	        if (existingOrder != null) {
	            // Parse the JSON and extract the necessary data
	            List<OrderItem> orderItems = new ArrayList<>();
	            JsonArray orderItemsArray = jsonObject.getJsonArray("orderItems");
	            
	            for (JsonValue jsonValue : orderItemsArray) {
		        	JsonObject orderItemObject = (JsonObject) jsonValue;
	                JsonObject itemObject = orderItemObject.getJsonObject("item");
	                
	                int itemId = itemObject.getInt("id");
	                String itemName = itemObject.getString("name");
	                String itemDescription = itemObject.getString("description");
	                double itemPrice = itemObject.getJsonNumber("price").doubleValue();
	                
	                Item item = new Item(itemId, itemName, itemDescription, itemPrice);
	                int quantity = orderItemObject.getInt("quantity");
	                OrderItem orderItem = new OrderItem(item, quantity);
	                orderItems.add(orderItem);
	            }

	            // Create the updated Order object
	            OrderModel updatedOrder = new OrderModel(id, orderItems);
	            if (orderService.updateOrder(updatedOrder)) {
	            	System.out.println("order updated ....");
	                return Response.ok().build();
	            } else {
	                return Response.status(Response.Status.NOT_FOUND).build();
	            }
	        } else {
	            return Response.status(Response.Status.NOT_FOUND).build();
	        }
	    }

	    
	    

	    @DELETE
	    @Path("/{id}")
	    public Response deleteOrder(@PathParam("id") int id) {
	    	System.out.println("deleteOrder() called ...");
	        boolean deleted = orderService.deleteOrder(id);
	        if (deleted) {
	        	System.out.println("order with id "+id+ "is deleted");
	            return Response.noContent().build();
	        } else {
	            return Response.status(Response.Status.NOT_FOUND).build();
	        }
	    }
}
