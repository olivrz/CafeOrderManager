package com.example.projectfive;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.text.DecimalFormat;

/**
 * Class used to run the Store Order Activity and manage all store orders
 *
 * @author Oliver Rzepecki, Tom Linteau
 */

public class StoreOrdersActivity extends AppCompatActivity {

    private Order selectedOrder;

    private static final DecimalFormat moneyFormat = new DecimalFormat("$0.00");

    private Spinner orderSpinner;

    private ListView storeListView;

    private TextView totalText;

    private Button cancelButton;


    /**
     * Creates and displays an alert with a given message and title
     * @param message the message to be displayed on the alert
     * @param title the title of the alert
     */
    private void showNewAlert(String message, String title) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(message).setTitle(title);
        alert.setNeutralButton("OKAY", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    /**
     * Runs when activity is launched
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_orders);

        orderSpinner = findViewById(R.id.orderSpinner);

        updateSpinner();

        orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                updateListView();
                selectedOrder = MainActivity.orderList.getList().get(orderSpinner.getSelectedItemPosition());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }});

        storeListView = findViewById(R.id.storeListView);

        totalText = findViewById(R.id.totalText);

        cancelButton = findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                int spinnerIndex = orderSpinner.getSelectedItemPosition();

                if(MainActivity.orderList != null && spinnerIndex >= 0) {
                    cancelOrder(spinnerIndex);
                } else
                    showNewAlert("No orders to cancel", "Cancellation Failed");

            }
        });

        if(MainActivity.orderList.getList().size() > 0) {
            updateListView();
        }
    }

    /**
     * Clears all items in the listview
     */
    private void clearListView(){
        storeListView.setAdapter(null);
        orderSpinner.setAdapter(null);
        totalText.setText(String.valueOf(moneyFormat.format(0)));
    }

    /**
     * Removes the currently-selected order from the orderList
     */
    public void cancelOrder(int orderIndex){
        selectedOrder = MainActivity.orderList.getList().get(orderIndex);
        if(MainActivity.orderList != null) {
            if(selectedOrder != null) {

                Toast.makeText(this, "Removed Order", Toast.LENGTH_SHORT).show();
                MainActivity.orderList.remove(this.selectedOrder);
                if(MainActivity.orderList.getList().size() > 0) {
                    updateSpinner();
                    updateListView();
                }
                else {
                    clearListView();
                }

                return;
            }
        } else {
            Toast.makeText(this, "No order selected", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Updates and displays the total
     */
    private void updateTotal() {
        double total = selectedOrder.getOrderCost();
        totalText.setText(String.valueOf(moneyFormat.format(total)));
    }

    /**
     * Updates the spinner
     */
    public void updateSpinner(){

        if(MainActivity.orderList.getList().size() > 0) {

            orderSpinner.setAdapter(null);

            ArrayList<Order> orders = MainActivity.orderList.getList();

            ArrayList<Integer> idList = new ArrayList<Integer>();

            for (int x = 1; x < MainActivity.orderList.getListSize() + 1; x++) {
                idList.add(orders.get(x - 1).getOrderID());
            }

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, idList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            orderSpinner.setAdapter(arrayAdapter);

        }


    }

    /**
     * Updates the ListView based on the selected order in the Spinner
     */
    public void updateListView(){

        int selectedPosition = orderSpinner.getSelectedItemPosition();

        if (selectedPosition >= 0) {

            this.selectedOrder = MainActivity.orderList.getList().get(selectedPosition);

            if (selectedOrder == null) {
                Toast.makeText(this, "No order selected", Toast.LENGTH_SHORT).show();
                return;
            }

            ArrayList<MenuItem> currentList = selectedOrder.getList();

            ArrayAdapter<MenuItem> itemsAdapter =
                    new ArrayAdapter<MenuItem>(this, android.R.layout.simple_list_item_1, currentList);
            storeListView.setAdapter(itemsAdapter);

            updateTotal();

        }
    }
}