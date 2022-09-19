package com.example.projectfive;

import java.util.ArrayList;

/**
 * The Coffee class is a MenuItem that has a cost depending on size and addins.
 *
 * @author Oliver Rzepecki, Tom Linteau
 */
public class Coffee extends MenuItem implements Customizable{
    protected String size;
    protected int addIns;
    protected ArrayList<String> addInStrings;

    final private double ADDIN_COST = 0.30;
    final private double SHORT_COST = 1.69;
    final private double SIZE_COST = 0.40;

    final private String SHORT = "Short";
    final private String TALL = "Tall";
    final private String GRANDE = "Grande";
    final private String VENTI = "Venti";

    /**
     * Coffee constructor that creates new Coffee object
     * @param size The size of the coffee
     * @param addIns The amoutn of add-ins in the coffee
     */
    public Coffee(String size, int addIns, ArrayList<String> addInStrings) {
        this.size = size;
        this.addIns = addIns;
        this.addInStrings = addInStrings;
    }

    /**
     * Retrieve the price of the coffee based on the size and the number of addIns
     * @return double the price of the coffee, 0.0 if the case was not found
     */
    public double itemPrice(){

        double addInCost = addIns * ADDIN_COST;

        switch(size){
            case SHORT:
                return SHORT_COST + addInCost;
            case TALL:
                return SHORT_COST + SIZE_COST + addInCost;
            case GRANDE:
                return SHORT_COST + SIZE_COST * 2 + addInCost;
            case VENTI:
                return SHORT_COST + SIZE_COST * 3 + addInCost;
        }

        return 0.0;
    }

    /**
     * Add an addIn to the coffee
     * @param obj The object to add to the ArrayList
     * @return true if the object was added to the ArrayList, false otherwise
     */
    public boolean add(Object obj) {
        if(obj instanceof String == true) {
            String addIn = (String) obj;
            this.addInStrings.add(addIn);
            return true;
        } else return false;
    }

    /**
     * Remove an addIn from this coffee's addIns ArrayList
     * @param obj the addIn to remove from the ArrayList
     * @return true if the addIn was removed from the ArrayList, false otherwise
     */
    public boolean remove(Object obj) {
        if(obj instanceof String) {
            String addIn = (String) obj;
            this.addInStrings.remove(addIn);
            return true;
        } else return false;
    }

    /**
     * Returns a String representation of the Coffee object
     * @return The String representation of the Coffee object
     */
    @Override
    public String toString(){

        String s = this.size + " Coffee with " + this.addIns + " add-ins";
        if(addInStrings.size() > 0) s+= ":";
        for(String str : addInStrings) {
            s += " [" + str + "]";
        }
        return s;
    }

}
