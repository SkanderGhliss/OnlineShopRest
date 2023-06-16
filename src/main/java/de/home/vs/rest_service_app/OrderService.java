package de.home.vs.rest_service_app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.glassfish.jersey.model.internal.RankedComparator.Order;
import org.jboss.resteasy.spi.NotFoundException;

import de.home.vs.model.Item;
import de.home.vs.model.OrderItem;
import de.home.vs.model.OrderModel;
import de.home.vs.rest_service_app.OrderService;


public class OrderService {
	private static OrderService instance;
	private Map<Integer, OrderModel> orderMap;

	    public OrderService() {
	        orderMap = new HashMap<>();
	        // Add the prefilled orders to the map
	        List<OrderItem> orderItems1 = new ArrayList<>();
	        orderItems1.add(new OrderItem(new Item(1, "Fußball Trikot","adidas DFB Deutschland Trikot Home WM 2022 WeiSS",29.99), 2));
	        orderItems1.add(new OrderItem(new Item(2,  "Schienbeinschoner", "Nike Schienbeinschoner Mercurial Lite weiß",18.99), 3));
	        OrderModel order1 = new OrderModel(1, orderItems1);
	        orderMap.put(order1.getId(), order1);

	        List<OrderItem> orderItems2 = new ArrayList<>();
	        orderItems2.add(new OrderItem(new Item(3, "IGNITE 2 - SPORTUHR", "Schlichte Sportuhr mit intelligenten Funktionen; GPS, GLONASS, Galileo und QZSS mit Assisted GPS für die schnelle Positionsbestimmung; Farb-Touch-Display (Auflösung 240 x 204); Farbvarianten in der Displayansicht; optische Pulsmessung am Handgelenk dank Precision Prime™ Sensor-Fusionstechnologie; nächtliche Erholungsanalyse durch Nightly Recharge™; Herzfrequenzmesser; Pulssensor-Modus; täglich personalisierte FitSpark™-Trainingsvorschläge basierend auf Tagesform und Trainingshistorie",189.99), 1));
	        OrderModel order2 = new OrderModel(2, orderItems2);
	        orderMap.put(order2.getId(), order2);
	    }
   

	    public static OrderService getInstance() {
	        if (instance == null) {
	            synchronized (OrderService.class) {
	                if (instance == null) {
	                    instance = new OrderService();
	                }
	            }
	        }
	        return instance;
	    }
	    
	    public List<OrderModel> getAllOrders() {
	        return new ArrayList<>(orderMap.values());
	    }

	    public OrderModel getOrderById(int id) {
	        return orderMap.get(id);
	    }

	    public void addOrder(OrderModel order) {
	        orderMap.put(order.getId(), order);
	    }

	    
	    public boolean updateOrder(OrderModel order) {
	        if (orderMap.containsKey(order.getId())) {
	            orderMap.put(order.getId(), order);
	            return true;
	        } else {
	            return false;
	        }
	    }

	    
	    
	    public boolean deleteOrder(int id) {
	        return orderMap.remove(id) != null;
	    }
	
}
