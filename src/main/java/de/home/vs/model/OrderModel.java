package de.home.vs.model;

import java.util.ArrayList;
import java.util.List;

public class OrderModel {
	  private int id;
	  private List<OrderItem> orderItems;

	  
	  
	  
	public OrderModel(int id, List<OrderItem> orderItems) {
		  	this.id = id;
	        this.orderItems = orderItems;
	        orderItems = new ArrayList<>();
	}

	
	  
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public List<OrderItem> getOrderItems() {
		return orderItems;
	}


	
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	
	public void addItem(OrderItem orderItem) {
	        orderItems.add(orderItem);
	    }

	public void removeItem(OrderItem orderItem) {
	        orderItems.remove(orderItem);
	    }




	@Override
	public String toString() {
		return "OrderModel [id=" + id + ", orderItems=" + orderItems + "]";
	}




	public void set(int i, OrderModel updatedOrder) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	  public double getTotalPrice() {
	        double totalPrice = 0.0;
	        for (OrderItem orderItem : orderItems) {
	            totalPrice += orderItem.getQuantity() * orderItem.getItem().getPrice();
	        }
	        return totalPrice;
	    }
	  
}
