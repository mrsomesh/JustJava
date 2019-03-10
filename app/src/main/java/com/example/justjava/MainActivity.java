/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.justjava;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        enterName();
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        createOrderSummary(price, hasWhippedCream, hasChocolate);

    }

    private void enterName() {
        EditText text = (EditText) findViewById(R.id.name_Input);
        name = text.getText().toString();
    }

    public void increment(View view) {
        if (quantity < 100)
            quantity += 1;
        else {
            quantity = 100;
            Context context = getApplicationContext();
            CharSequence text = "Too many Coffees";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        display(quantity);
    }

    public void decrement(View view) {
        if (quantity > 1)
            quantity -= 1;
        else {
            quantity = 1;
            Context context = getApplicationContext();
            CharSequence text = "Too few coffees";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        display(quantity);
    }

    public void createOrderSummary(int number, boolean hasWhippedCream, boolean hasChocolate) {
        String priceMessage = getString( R.string.order_summary_name,name)+
                "\n"+getString(R.string.order_summary_whipped_cream,hasWhippedCream)+
                "\n"+getString(R.string.order_summary_chocolate,hasChocolate)+
                "\n"+getString(R.string.order_summary_quantity,quantity)+
                "\n"+getString(R.string.order_summary_price,""+number)+
                "\n"+getString(R.string.thank_you);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.order_summary_email_subject,name));
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        if (hasWhippedCream && hasChocolate)
            return quantity * (5 + 3);
        else if (hasWhippedCream)
            return quantity * (5 + 1);
        else if (hasChocolate)
            return quantity * (5 + 2);
        else
            return quantity * 5;

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}
