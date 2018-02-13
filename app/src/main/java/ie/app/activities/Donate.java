package ie.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import ie.app.R;
import ie.app.models.Donation;

public class Donate extends Base {

    private Button donateButton;
    private RadioGroup paymentMethod;
    private ProgressBar progressBar;
    private NumberPicker amountPicker;
    private EditText amountInput;
    private TextView total;
    private int totalDonated = 0;
    private boolean targetArchieved=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        donateButton = findViewById(R.id.donateButton);

        if(donateButton!=null){
            Log.v("Donate", "Really got the donate button");
        }

        amountPicker = findViewById(R.id.amountPicker);
        paymentMethod = findViewById(R.id.paymentMethod);
        progressBar = findViewById(R.id.progressBar);
        amountInput = findViewById(R.id.amountInput);
        total = findViewById(R.id.total);

        amountPicker.setMinValue(0);
        amountPicker.setMaxValue(1000);

        progressBar.setMax(10000);
    }


    public void donateButtonPressed(View view){
        int amount = amountPicker.getValue();
        int radioId = paymentMethod.getCheckedRadioButtonId();
        String method="";

        if(radioId == R.id.paypalButton){
            method="PayPal";
        }
        else{
            method="Direct";
        }

        if(amount==0){
            String text = amountInput.getText().toString();
            if (!text.equals("")) {
                amount = Integer.parseInt(text);
            }
        }

        if(!targetArchieved){
            newDonation(new Donation(amount, method));
            totalDonated+=amount;
            progressBar.setProgress(totalDonated);
            targetArchieved = totalDonated > 10000;
            String totalStr = "$" + totalDonated;
            total.setText(totalStr);
            Toast.makeText(this, "You donated amount:" + amount + ", Method: " + method, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Target Exceeded!", Toast.LENGTH_SHORT).show();
        }



        Log.v("Donate", "Donate Pressed! with amount " + amount + " and payment method " + method + ". The current total is " + totalDonated);

    }
}
