package com.example.praful.justjava;

import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.order;
import static android.R.string.no;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class MainActivity extends AppCompatActivity {
    int qty = 2;
    String orderSummaryText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    /*
     * This Method is called when + button is clicked
     */
    public void increment(View view){
        if(qty == 100) {
            Toast.makeText(MainActivity.this, "You cannt have more then 100 Coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        qty++;

        display(qty);
    }
    /*
     * This Method is called when - button is clicked
     */
    public void decrement(View view){
        if(qty == 1){
            Toast.makeText(MainActivity.this, "You cannt have Less then 1 Coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        qty--;

        display(qty);


    }
    /*
     * This Method is called when "order " button is clicked
     */
    public void submitOrder(View view){
        /*
            Name of Customer
         */
        EditText nameField = (EditText)findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        /*
        *   Cream Coffee check box declered.
        *   Check box checked or not ?
         */
        CheckBox creamCoffeCheckbox = (CheckBox)findViewById(R.id.cream_coffe_checkbox);
        boolean creamCofffe = creamCoffeCheckbox.isChecked();
        /*
        *   Chocolate Coffee check box declered.
        *   Check box checked or not ?
         */
        CheckBox chocolateCofeeBox = (CheckBox)findViewById(R.id.choco_coffe_checkbox);
        boolean chocoCofee = chocolateCofeeBox.isChecked();

        //Calculate price according to check box
        int tPrice = calculatePrice(creamCofffe,chocoCofee);

        //Display no of coffee
        display(qty);
        // store order summery in " priceMessage" to for display
        String priceMessage = createOrderSummary(tPrice, creamCofffe, chocoCofee, name);
        displayMessage(priceMessage);

        // orderSummary stores Details of order to be used in other INTENT like email
        orderSummaryText = priceMessage;
        //displayPrice(tPrice);
    }

    /*
     * This method calculates the price of the coffee according to Topping
     * base price of coffee is $5 (Assume)
     * if cream coffee is checked add base price to "$1
     * if chocolate coffee is checked add base price to "$2
     */
    private int calculatePrice(boolean addCreamCoffeTopping, boolean addChocoCoffeeTopping){
        int price = 5;

        if (addCreamCoffeTopping){
            price = price+ 1;
        }

        if(addChocoCoffeeTopping){
            price = price + 2;
        }
        return price * qty;
    }

    private void display(int num) {
        TextView qty= (TextView)findViewById(R.id.quntaty_for_order);
        qty.setText(""+num);
    }

    /*
        This method creats a summery of ordered coffe
        Name of customer
        Quantity
        Total Price
        @param addCreamCoffeeCheck & addChocoCoffeeCheck are boolean or CheckBox
        @ Price of the coffe
        @ Returns String
     */
    private String createOrderSummary(int num, boolean addCreamCoffeCheck, boolean addChocoCoffeeCheck, String nameOfCust){
        String summary = nameOfCust;
        summary = summary + "\n Add Cream Coffe : " + addCreamCoffeCheck;
        summary = summary + "\n Add Chocolate Coffe : " + addChocoCoffeeCheck;
        summary = summary + "\nQuantity : " + qty;
        summary = summary + "\nTotal Price :"+num;
        summary = summary + "\n"+getString(R.string.thank_you);
        return summary;
    }
    /*
    This method shows Total price of the coffe ordered
     */

    private void displayMessage(String price_name){
        TextView price = (TextView)findViewById(R.id.price_for_order);
        price.setText(price_name);
        /*This Log display at Log Screen
        @Parameter : Class Name &
                        The message you want to display on the screen
        */
        //Log.i("MainActivity.java","Display log at displayMessage() method");
    }

    public void emailOrder(View view){
        String[] toAddress = {"swetachoudhury88@gmail.com","prafulnayak@yahoo.com"};
        String[] ccAddress = {"swetachoudhury1988@gmail.com","prafulnayak88@yahoo.com"};
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL,toAddress);
        intent.putExtra(intent.EXTRA_CC,ccAddress);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java");
        intent.putExtra(Intent.EXTRA_TEXT, orderSummaryText);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void mapIntent(View view){

        Intent intent = new Intent(Intent.ACTION_SEND);
       // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setType("text/plain");
        //intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL,toAddress);
        //intent.putExtra(intent.EXTRA_CC,ccAddress);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java");
        intent.putExtra(Intent.EXTRA_TEXT, orderSummaryText);
        startActivity(Intent.createChooser(intent,"Share using"));

        // Map Intent
        /*Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:17.7349139,83.3112247?z=17"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }*/
    }

    public void shareIntent(View view){

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        //intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL,toAddress);
        //intent.putExtra(intent.EXTRA_CC,ccAddress);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java");
        intent.putExtra(Intent.EXTRA_TEXT, orderSummaryText);

    }


}
