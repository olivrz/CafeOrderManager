package com.example.projectfive;

/**
 * The Donut class is a MenuItem that has three types of donut types, five flavors, and a price depending on the type
 *
 * @author Oliver Rzepecki, Tom Linteau
 */
public class Donut extends MenuItem{

    final private String YEAST = "Yeast Donut";
    final private String CAKE = "Cake Donut";
    final private String HOLE = "Donut Hole";

    protected String donutType;
    protected String donutFlavor;

    /**
     * Create a new instance of a donut of flavor specified by type
     * @param type the string name of the type of donut
     */
    public Donut(String type, String flavor) {
        this.donutType = type;
        this.donutFlavor = flavor;
    }

    /**
     * Retrieve the price of the donut based on the donutType
     * @return double the price of the donut
     */
    public double itemPrice(){
        switch(donutType){
            case YEAST:
                return 1.59;
            case CAKE:
                return 1.79;
            case HOLE:
                return 0.39;
        }
        return 0.0;
    }

    /**
     * Returns a String representation of the Donut object
     * @return The String representation of the Donut object
     */
    @Override
    public String toString(){
        return this.donutFlavor + " " + this.donutType;
    }

}
