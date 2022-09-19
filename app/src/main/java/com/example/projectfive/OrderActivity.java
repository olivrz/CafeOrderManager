package com.example.projectfive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Class used to run the Order Activity and manage the current order
 *
 * @author Oliver Rzepecki, Tom Linteau
 */

public class OrderActivity extends AppCompatActivity {

    private Order currentOrder;
    final private double TAX = 0.06625; //NJ Tax
    private static final DecimalFormat moneyFormat = new DecimalFormat("$0.00");
    private ArrayList<MenuItem> currentList;
    private int selectedPosition;
    private double total;

    //LIST VIEW
    private ListView itemsListView;

    //PRICE TEXTS
    private TextView subtotalText;
    private TextView salesTaxText;
    private TextView orderTotalText;

    //BUTTONS
    private Button removeButton;

    private Button placeOrderButton;

    /**
     * Runs when activity is launched
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //LIST VIEW
        itemsListView = findViewById(R.id.itemsListView);

        itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id){
                selectedPosition = position;
            }
        });

        //PRICE TEXTS
        subtotalText = findViewById(R.id.subtotalText);

        salesTaxText = findViewById(R.id.salesTaxText);

        orderTotalText = findViewById(R.id.orderTotalText);

        //BUTTONS
        removeButton = findViewById(R.id.removeButton);

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                removeSelectedItem();
            }
        });

        placeOrderButton = findViewById(R.id.placeOrderButton);

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                placeOrder();
            }
        });

        this.currentOrder = MainActivity.currentOrder;
        this.currentList = currentOrder.getList();

    }

    /**
     * Runs when activity is resumed
     * @param
     */
    @Override
    protected void onResume(){
        super.onResume();
        //Display items in the current order
        updateOrderList();
        updateTotals();
    }

    /**
     * Updates and displays the subtotal, tax, and total
     */
    private void updateTotals(){
        double subtotal = MainActivity.currentOrder.getOrderCost();
        subtotalText.setText(String.valueOf(moneyFormat.format(subtotal)));

        double salesTax = subtotal * TAX;
        salesTaxText.setText(String.valueOf(moneyFormat.format(salesTax)));

        total = subtotal + salesTax;
        orderTotalText.setText(String.valueOf(moneyFormat.format(total)));
    }

    /**
     * Updates the list view to display all items in the current order
     */
    private void updateOrderList() {
        if(currentOrder != null) {
            ArrayAdapter<MenuItem> itemsAdapter =
                    new ArrayAdapter<MenuItem>(this, android.R.layout.simple_list_item_1, currentList);
            itemsListView.setAdapter(itemsAdapter);
        }
    }

    /**
     * Removes selected item from order
     */
    public void removeSelectedItem(){
        if(currentList.size() != 0) {
            Object removedItem = currentList.get(selectedPosition);
            if(removedItem != null) {
                Toast.makeText(this, "REMOVED ITEM", Toast.LENGTH_SHORT).show();
                currentList.remove(selectedPosition);
                updateOrderList();
            }
        }

    }

    /**
     * Calls MainActivity to add current order to orderlist and exits to main menu
     */
    public void placeOrder(){
        //Check if order has at least 1 item in it
        if(MainActivity.currentOrder.getList().size() > 0) {
            MainActivity.currentOrder.setFinalCost(total);
            MainActivity.placeOrder();
            //Return to menu
            this.finish();
        } else {
            Toast.makeText(this, "No items in order", Toast.LENGTH_SHORT).show();
            return;
        }

    }

}