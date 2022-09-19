package com.example.projectfive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Class used to run the Donut Activity and add donuts to the order
 *
 * @author Oliver Rzepecki, Tom Linteau
 */
public class DonutActivity extends AppCompatActivity implements RecyclerArrayListAdapter.ItemClickListener {

    private ArrayList<Donut> donutOptions;
    private int selectedRow; //Tracks selected row in Recyclerview
    private int selectedDonut; //Tracks selected row in basket

    private static final DecimalFormat moneyFormat = new DecimalFormat("$0.00");

    private RecyclerView donutsRView;
    private RecyclerArrayListAdapter adapter;

    private EditText donutQuantityText;

    private Button addDonutButton;

    private ListView donutListView;
    private ArrayList<Donut> donutList;


    private TextView donutSubtotal;

    private Button removeDonutButton;

    private Button donutOrderButton;

    /**
     * Creates the list of donuts in the recyclerview and sets the adapter for donutsRView
     */
    private void setupRView(){

        donutOptions = new ArrayList<Donut>();
        donutOptions.add(new Donut("Yeast Donut", "Chocolate"));
        donutOptions.add(new Donut("Yeast Donut", "Vanilla"));
        donutOptions.add(new Donut("Yeast Donut", "Boston Cream"));
        donutOptions.add(new Donut("Yeast Donut", "Peanut Butter"));
        donutOptions.add(new Donut("Cake Donut", "Birthday"));
        donutOptions.add(new Donut("Cake Donut", "Ice Cream"));
        donutOptions.add(new Donut("Cake Donut", "Red Velvet"));
        donutOptions.add(new Donut("Cake Donut", "Cheesecake"));
        donutOptions.add(new Donut("Donut Hole", "Chocolate"));
        donutOptions.add(new Donut("Donut Hole", "Vanilla"));
        donutOptions.add(new Donut("Donut Hole", "Frosted"));
        donutOptions.add(new Donut("Donut Hole", "Poptart"));

        donutsRView.setLayoutManager(new LinearLayoutManager(this));

        donutsRView.setLayoutManager(new LinearLayoutManager(this));

        this.adapter = new RecyclerArrayListAdapter(this, donutOptions);

        adapter.setClickListener(this);

        donutsRView.setAdapter(adapter);

    }

    /**
     * Runs when activity is launched
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donut);

        donutsRView = findViewById(R.id.donutsRView);
        setupRView();

        donutQuantityText = findViewById(R.id.donutQuantityText);
        donutQuantityText.setText("1");
        addTextListener();

        addDonutButton = findViewById(R.id.addDonutButton);

        addDonutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                addDonut();
            }
        });

        donutListView = findViewById(R.id.donutListView);
        donutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id){
                selectedDonut = position;
            }
        });
        donutList = new ArrayList<Donut>();

        donutSubtotal = findViewById(R.id.donutSubtotal);

        removeDonutButton = findViewById(R.id.removeDonutButton);

        removeDonutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                removeDonut();
            }
        });

        donutOrderButton = findViewById(R.id.donutOrderButton);

        donutOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                addToOrder();
            }
        });

    }

    /**
     * Sets the selected row when an item is clicked
     * @param view The view being analyzed
     * @param position The position of what is being clicked
     */
    @Override
    public void onItemClick(View view, int position) {
        this.selectedRow = position;
    }

    /**
     * Updates the subtotal based on the donuts in the basket
     */
    private void updatePrice(){

        if(etIsEmpty(donutQuantityText)){
            Toast.makeText(this, "Missing quantity", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            double total = 0;
            for (Donut d : donutList) {
                total += d.itemPrice();
            }
            donutSubtotal.setText((String.valueOf(moneyFormat.format(total))));
        }
    }

    /**
     * Adds a text listener to quantity input
     */
    private void addTextListener(){

        donutQuantityText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(final Editable s){
                updatePrice();
            }
        });
    }

    /**
     * Helper method that checks if an EditText object is empty
     * @param etText the EditText object to check if it's empty
     * @return true if user made no input to etText
     */
    private boolean etIsEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    /**
     * Returns the quantity the user has inputted
     * @return int the quantity the user has inputted
     */
    private int getQuantity(EditText text){
        return Integer.parseInt(text.getText().toString());
    }

    /**
     * Checks if the user inputted a valid quantity for an order
     * @return true if the quantity is positive and greater than 0, false otherwise
     */
    public boolean isValidQty(int qty){
        return qty > 0;
    }

    /**
     * Updates the donutsListView
     */
    private void updateBasket(){
        if(this.donutList.size() > 0) {
            ArrayAdapter<Donut> itemsAdapter =
                    new ArrayAdapter<Donut>(this, android.R.layout.simple_list_item_1, this.donutList);
            donutListView.setAdapter(itemsAdapter);
        } else {
            //Clear listview
            donutListView.setAdapter(null);
        }
        updatePrice();
    }

    /**
     * Returns the donut that user has last selected from the RecyclerView
     * @return Donut the donut object the user selected
     */
    private Donut getSelectedDonut(){
        return donutOptions.get(this.selectedRow);
    }

    /**
     * Adds the selected donut and quantity to the donutListView
     */
    private void addDonut(){
        if(etIsEmpty(donutQuantityText)){
            Toast.makeText(this, "Missing quantity", Toast.LENGTH_SHORT).show();
            return;
        }
        int qty = getQuantity(donutQuantityText);
        if(isValidQty(qty)){
            //Add selected donut and quantity to basket
            for(int i = 0; i < qty; i++){
                donutList.add(getSelectedDonut());
            }
            updateBasket();
        } else {
            Toast.makeText(this, "Invalid quantity", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Removes the selected donut from donutListView adapter
     */
    private void removeDonut(){
        if(etIsEmpty(donutQuantityText)){
            Toast.makeText(this, "Missing quantity", Toast.LENGTH_SHORT).show();
            return;
        }
        donutList.remove(this.selectedDonut);
        updateBasket();
    }

    /**
     * Adds all donuts in the basket to the currentOrder and exits to main menu
     */
    private void addToOrder(){
        if(donutList.size()>0) {
            for(Donut donut : donutList) {
                MainActivity.currentOrder.add(donut);
            }
            //Alert user that their donut(s) were added to the order
            if(donutList.size()>1) {
                Toast.makeText(this, "Donuts added to order", Toast.LENGTH_SHORT).show();
            } else if(donutList.size()==1)
                Toast.makeText(this, "Donut added to order", Toast.LENGTH_SHORT).show();
            this.finish();
        } else{
            Toast.makeText(this, "Empty basket", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}