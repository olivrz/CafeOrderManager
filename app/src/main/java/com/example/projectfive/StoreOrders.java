package com.example.projectfive;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The StoreOrders class keeps track of all the orders made in a store.
 *
 * @author Oliver Rzepecki, Tom Linteau
 */
public class StoreOrders implements Customizable {

    private ArrayList<Order> orderList;

    /**
     * Creates a new StoreOrders object. Initializes ArrayList of orders
     */
    public StoreOrders() {
        this.orderList = new ArrayList<Order>();
    }

    /**
     * Returns the amount of store orders
     * @return The amount of store orders
     */
    public int getListSize(){
        return orderList.size();
    }

    /**
     * Returns the list of orders
     * @return The list of orders
     */
    public ArrayList<Order> getList(){
        return orderList;
    }

    /**
     * Add an order to the StoreOrders orderList
     * @param obj The object to add to the ArrayList
     * @return true if the object was added to the ArrayList, false otherwise
     */
    public boolean add(Object obj) {
        if(obj instanceof Order) {
            Order tempOrder = (Order) obj;
            orderList.add(tempOrder);
            return true;
        } else return false;
    }

    /**
     * Remove an order to the StoreOrders orderList
     * @param obj The object to remove from the ArrayList
     * @return true if the object was removed from the ArrayList, false otherwise
     */
    public boolean remove(Object obj) {
        if(obj instanceof Order) {
            Order tempOrder = (Order) obj;
            orderList.remove(tempOrder);
            return true;
        } else return false;
    }

    /**
     * Get a String representation of all the orders in the store's orderList
     * @return String representation of StoreOrders
     */
    @Override
    public String toString(){
        String str = "Store Orders";
        for(Order order : orderList){
            str = str + "\n" + order;
        }
        return str;
    }

}
