package de.home.vs.rest_service_app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.home.vs.model.*;
@SuppressWarnings("unused")


public class ItemService {
	  
//    private List<Item> itemList = new ArrayList<Item>();
//    private List<Item> newlyAddedItems = new ArrayList<Item>();
	private static ItemService instance;
	private static Map<Integer, Item> itemList = new HashMap<Integer, Item>();

 // Prefilling the data with some items
    static {
    	 itemList.put(1, new Item(1, "Fußball Trikot", "adidas DFB Deutschland Trikot Home WM 2022 WeiSS",29.99));
    	 itemList.put(2, new Item(2, "Fußball Schuhe", "Nike Zoom Mercurial Vapor 15 Academy CR7 MG",89.99));
    	 itemList.put(3, new Item(3, "Fußball Trikot", "Kappa Tunisia Home Kombat Pro Trikot (Slim Fit) 2022-2023 ",30.00));
    	 itemList.put(4, new Item(4, "Schienbeinschoner", "Nike Schienbeinschoner Mercurial Lite weiß ",18.99));
    	 itemList.put(5, new Item(5, "Trainingshose", "adidas Match Fußball Trainingshose Herren ",45.00));
    	 itemList.put(6, new Item(6, "GALAXY 6 - FITNESSSCHUHE", "Nike Schienbeinschoner Mercurial Lite weiß ",46.99));
    	 itemList.put(7, new Item(7, "SKIJACKEN", " Snowboardjacke mit dauerhaft wasserabweisender und PFC-freier Bionic Finish® Eco Imprägnierung und einer Wassersäule von 8.000 mm; winddichte Funktion; versiegelte Nähte; Sturmkapuze; Kragen mit Kinnschutz; hinterlegter, wasserdichter",129.95));
    	 itemList.put(8, new Item(8, "IGNITE 2 - SPORTUHR", "Schlichte Sportuhr mit intelligenten Funktionen; GPS, GLONASS, Galileo und QZSS mit Assisted GPS für die schnelle Positionsbestimmung; Farb-Touch-Display (Auflösung 240 x 204); Farbvarianten in der Displayansicht; optische Pulsmessung am Handgelenk dank Precision Prime™ Sensor-Fusionstechnologie; nächtliche Erholungsanalyse durch Nightly Recharge™; Herzfrequenzmesser; Pulssensor-Modus; täglich personalisierte FitSpark™-Trainingsvorschläge basierend auf Tagesform und Trainingshistorie",189.99));

    }
	    
    
    
    public static ItemService getInstance() {
        if (instance == null) {
            synchronized (ItemService.class) {
                if (instance == null) {
                    instance = new ItemService();
                }
            }
        }
        return instance;
    }
    
    
    
    public List<Item> getAllItems() {
        return new ArrayList<>(itemList.values());
    }

    
    
    public Item getItemById(int id) {
        return itemList.get(id);
    }
    
    
    
    public Item createItem(Item item) {
        itemList.put(item.getId(), item);
        return item;
    }
    
    
    public Item updateItem(Item item) {
        itemList.put(item.getId(), item);
        return item;
    }

    
    public void deleteItem(int id) {
    	itemList.remove(id);
    }
    
    
    
    
}
