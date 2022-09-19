package com.example.projectfive;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * The Order class keeps track of all the MenuItems in a single store order
 *
 * @author Oliver Rzepecki, Tom Linteau
 */
public class Order implements Customizable {

    private ArrayList<MenuItem> orderItems;

    private static final DecimalFormat moneyFormat = new DecimalFormat("$0.00");

    private double orderCost;

    private int orderNumber;

    /**
     * Creates a new instance of Order that has a list of items, a cost, and an order ID
     * @param orderNum The order ID
     */
    public Order(int orderNum) {
        this.orderItems = new ArrayList<MenuItem>();
        this.orderCost = 0;
        this.orderNumber = orderNum;
    }

    /**
     * Add a MenuItem to orderItems
     * @param obj The object to add to the ArrayList
     * @return true if the object was added to the ArrayList, false otherwise
     */
    public boolean add(Object obj) {
        if(obj instanceof MenuItem) {
            MenuItem item = (MenuItem) obj;
            orderItems.add(item);
            orderCost += item.itemPrice();
            return true;
        } else return false;
    }

    /**
     * Remove an order from orderItems
     * @param obj The object to remove from the ArrayList
     * @return true if the object was removed from the ArrayList, false otherwise
     */
    public boolean remove(Object obj) {
        if(obj instanceof MenuItem) {
            MenuItem item = (MenuItem) obj;
            orderItems.remove(item);
            orderCost -= item.itemPrice();
            return true;
        } else return false;
    }

    /**
     * Returns the cost of the order
     * @return The cost of the order
     */
    public double getOrderCost(){
        return orderCost;
    }

    /**
     * Returns the order ID
     * @return the order ID
     */
    public int getOrderID(){
        return orderNumber;
    }

    /**
     * Sets the final cost given a total after taxes
     * @param total The final total/cost
     */
    public void setFinalCost(double total){
        orderCost = total;
    }

    /**
     * Returns the list of items of an order
     * @return The list of order items
     */
    public ArrayList<MenuItem> getList(){
        return orderItems;
    }

    /**
     *  Get a string representation of all the MenuItem's in the order
     * @return String representation of the order
     */
    @Override
    public String toString(){
        String s = "Order " + this.orderNumber;

        for(int x = 0; x < orderItems.size(); x++){
            s += "\n" + orderItems.get(x) + " " + String.valueOf(moneyFormat.format(orderItems.get(x).itemPrice()));
        }

        s += "\nOrder total: " + String.valueOf(moneyFormat.format(this.orderCost));

        return s;
    }

}
