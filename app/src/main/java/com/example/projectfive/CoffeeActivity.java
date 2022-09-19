package com.example.projectfive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Class used to run the Coffee Activity and add coffees to the order
 *
 * @author Oliver Rzepecki, Tom Linteau
 */

public class CoffeeActivity extends AppCompatActivity {

    private Order currentOrder;
    private double coffeeTotal;
    private static final DecimalFormat moneyFormat = new DecimalFormat("$0.00");

    //RADIO GROUP / BUTTON
    private RadioGroup sizeRGroup;

    private RadioButton selectedSize; //not defined in onCreate because you might not need it

    //CHECKBOXES
    private CheckBox creamCB;

    private CheckBox syrupCB;

    private CheckBox caramelCB;

    private CheckBox milkCB;

    private CheckBox whippedCreamCB;

    //QUANTITY INPUT
    private EditText quantityText;

    //CHANGING COFFEE TOTAL
    private TextView coffeeTotalText;

    //ADD TO ORDER BUTTON
    private Button coffeeOrderButton;

    /**
     * Adds a text listener to quantity input
     */
    private void addTextListener(){

        quantityText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(final Editable s){
                updatePrice(quantityText);
            }
        });
    }

    /**
     * Runs when activity is launched
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);

        //RADIO GROUP
        sizeRGroup = findViewById(R.id.sizeRGroup);

        //CHECKBOXES
        creamCB = findViewById(R.id.creamCB);
        syrupCB = findViewById(R.id.syrupCB);
        caramelCB = findViewById(R.id.caramelCB);
        milkCB = findViewById(R.id.milkCB);
        whippedCreamCB = findViewById(R.id.whippedCreamCB);

        //QUANTITY INPUT
        quantityText = findViewById(R.id.quantityText);
        quantityText.setText("1");
        addTextListener();

        //CHANGING COFFEE TOTAL
        coffeeTotalText = findViewById(R.id.coffeeTotalText);

        //ORDER BUTTON
        coffeeOrderButton = findViewById(R.id.coffeeOrderButton);

        coffeeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                addToOrder();
            }
        });

        this.currentOrder = MainActivity.currentOrder;
    }

    /**
     * Returns the size of the coffee that the user currently has selected
     * @return the currently-selected size of the coffee
     */
    private String getCoffeeSize(){
        int radioID = sizeRGroup.getCheckedRadioButtonId();
        selectedSize = findViewById(radioID);
        return (String)selectedSize.getText();
    }

    /**
     * Returns a coffee object from the selections
     * @return a Coffee object
     */
    private Coffee getCoffee(){
        return new Coffee(getCoffeeSize(), getSelectedAddIns().size(), getSelectedAddIns());
    }

    /**
     * Returns the quantity the user has inputted
     * @return int the quantity the user has inputted
     */
    private int getQuantity(){
        return Integer.parseInt(quantityText.getText().toString());
    }

    /**
     * Updates the total based on the selected options
     */
    private void updateTotal () {

        if(etIsEmpty(quantityText)){
            Toast.makeText(this, "Missing quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        if( quantityText.getText().toString().equals("0")) {
            coffeeTotalText.setText((String.valueOf(moneyFormat.format(0))));
            return;
        }
        if(sizeSelected()) {

            this.coffeeTotal = getCoffee().itemPrice() * getQuantity();
            coffeeTotalText.setText((String.valueOf(moneyFormat.format(coffeeTotal))));

        } else{
            Toast.makeText(this, "No size selected", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Updates the price whenever a size or add-in is selected or quantity is set
     * @param v Necessary for onClick event for the buttons connected to this
     */
    public void updatePrice(View v){
        //This method gets called whenever a radio button / checkbox is clicked and quantity is inputted
        if(!sizeSelected() || etIsEmpty(quantityText)) {
            coffeeTotalText.setText((String.valueOf(moneyFormat.format(0))));
            return;
        }
        updateTotal();
    }

    /**
     * Checks if the user selected a coffee size
     * @return true if a coffee size is selected, false otherwise
     */
    private boolean sizeSelected(){
        return sizeRGroup.getCheckedRadioButtonId() != -1;
    }

    /**
     * Gets all the add ins the user has selected
     * @return an ArrayList of strings of each add in
     */
    private ArrayList<String> getSelectedAddIns() {
        ArrayList<String> addInStrings = new ArrayList<String>();
        if(creamCB.isChecked()){
            addInStrings.add((String)creamCB.getText());
        }
        if(caramelCB.isChecked()) {
            addInStrings.add((String)caramelCB.getText());
        }
        if(milkCB.isChecked()) {
            addInStrings.add((String) milkCB.getText());
        }
        if(whippedCreamCB.isChecked()) {
            addInStrings.add((String) whippedCreamCB.getText());
        }
        if(syrupCB.isChecked()) {
            addInStrings.add((String) syrupCB.getText());
        }
        return addInStrings;
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
     * Checks if the user inputted a valid quantity for an order
     * @return true if the quantity is positive and greater than 0, false otherwise
     */
    public boolean isValidQty(int qty){
        return qty > 0;
    }

    /**
     * Clears the coffee view of user's selections
     */
    private void clearOrder(){
        sizeRGroup.clearCheck();
        caramelCB.setChecked(false);
        creamCB.setChecked(false);
        syrupCB.setChecked(false);
        whippedCreamCB.setChecked(false);
        milkCB.setChecked(false);
        quantityText.setText("1");
    }

    /**
     * Adds a coffee to the current order based on the user's current selections
     */
    public void addToOrder(){
        if(etIsEmpty(quantityText)){
            Toast.makeText(this, "Missing quantity", Toast.LENGTH_SHORT).show();
            return;
        }
        //Adds coffee to order
        if(sizeSelected()) {

            int qty = getQuantity();
            if(isValidQty(qty)) {
                for(int i = 0; i < qty; i++) {
                    currentOrder.add(getCoffee());
                }

                //Alert user that their coffee was added to the order
                if(qty>1) {
                    Toast.makeText(this, "Coffees added to order", Toast.LENGTH_SHORT).show();
                } else if(qty==1)
                    Toast.makeText(this, "Coffee added to order", Toast.LENGTH_SHORT).show();
                clearOrder();
                this.finish();
            }
            else
                Toast.makeText(this, "Invalid quantity", Toast.LENGTH_SHORT).show();

        } else{
            Toast.makeText(this, "No size selected", Toast.LENGTH_SHORT).show();
        }

    }

}