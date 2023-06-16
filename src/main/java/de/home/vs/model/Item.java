package de.home.vs.model;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.ws.rs.core.Link;

import com.google.gson.Gson;

@SuppressWarnings("unused")
public class Item {


	private int id ;
	private String name ;
	private String description ;
	private double price ;

	public Item() {
		
	}
  
	public Item(int id, String name, String description, double price) {
		
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;		
	
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	
    // Override toString() method for printing item details

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + "]";
	}

	
	    
}
	
