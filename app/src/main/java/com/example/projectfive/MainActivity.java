package com.example.projectfive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class used to run the Main Activity and manage current orders and store orders
 *
 * @author Oliver Rzepecki, Tom Linteau
 */

public class MainActivity extends AppCompatActivity {

    private Button donutButton;
    private Button coffeeButton;
    private Button viewOrderButton;
    private Button viewStoreOrdersButton;

    public static Order currentOrder;
    public static StoreOrders orderList;
    public static int orderCount;

    /**
     * Runs when activity is launched
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        donutButton = findViewById(R.id.donutButton);
        donutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openDonutView();
            }
        });

        coffeeButton = findViewById(R.id.coffeeButton);
        coffeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openCoffeeView();
            }
        });

        viewOrderButton = findViewById(R.id.viewOrderButton);
        viewOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openOrderView();
            }
        });

        viewStoreOrdersButton = findViewById(R.id.viewStoreOrdersButton);
        viewStoreOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openStoreOrdersView();
            }
        });

        this.orderCount = 1;
        this.currentOrder = new Order(orderCount);
        this.orderList = new StoreOrders();

    }

    /**
     * Places an order, resets current order, and starts a new order
     */
    public static void placeOrder() {
        orderList.add(currentOrder);
        orderCount++;
        currentOrder = new Order(orderCount);
    }

    /**
     * Opens the Donut Activity screen
     */
    public void openDonutView(){
        Intent donutOpen = new Intent(this, DonutActivity.class);
        startActivity(donutOpen);
    }

    /**
     * Opens the Coffee View screen
     */
    public void openCoffeeView(){
        Intent coffeeOpen = new Intent(this, CoffeeActivity.class);
        startActivity(coffeeOpen);
    }

    /**
     * Opens the Order View screen
     */
    public void openOrderView(){
        Intent orderOpen = new Intent(this, OrderActivity.class);
        startActivity(orderOpen);
    }

    /**
     * Opens the Store Orders View screen
     */
    public void openStoreOrdersView(){
        Intent storeOrdersOpen = new Intent(this, StoreOrdersActivity.class);
        startActivity(storeOrdersOpen);
    }

}