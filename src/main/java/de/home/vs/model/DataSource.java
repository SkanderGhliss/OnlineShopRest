package de.home.vs.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.home.vs.model.*;

@SuppressWarnings("unused")
public class DataSource {

	
	
	private static DataSource instance = null;
	//ArrayList<Item> itemList = new ArrayList<Item>();
    private  List<Item> itemList = new ArrayList<Item>();

	private static int nextId = 1;

	
	public static DataSource getInstance() {
		if (instance == null) {
			instance = new DataSource();
		}
		return instance;
	}
	
	
	   
	   // Create prefill items
	
	public List<Item> prefillData() {
//	ArrayList<Item> itemList = new ArrayList<Item>();

        itemList.add(new Item(1, "A", "demo@gmail.com", 11));
        itemList.add(new Item(2, "B", "demo1@gmail.com",12));
        itemList.add(new Item(3, "C", "demo2@gmail.com",13));
        return itemList;
    }

     	  
      
      public List<Item> getAllItems() {
          return this.prefillData();
      }
      
     

      
    public boolean deleteItem(int id ) {
   
         itemList = (ArrayList<Item>) this.prefillData();

    	for(int i =0; i< itemList.size() ;i++) {
    	if(itemList.get(i).getId() == id){
    	itemList.remove(itemList.get(i));
    	   return true;
    		}
    	}
    return false;
    }


    
	public boolean createNewItem(Item item) {
    return itemList.add(item);  
	}
	
}

	    
		



