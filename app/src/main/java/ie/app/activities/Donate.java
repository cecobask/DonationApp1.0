package ie.app.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import ie.app.R;
import ie.app.models.Donation;

public class Donate extends Base {

    private Button donateButton;
    private RadioGroup paymentMethod;
    private ProgressBar progressBar;
    private NumberPicker amountPicker;
    private EditText amountInput;
    private TextView total;

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
        total.setText("$" + app.totalDonated);
        progressBar.setProgress(app.totalDonated);
        progressBar.setMax(10000);
    }


    public void donateButtonPressed(View view) {
        {
            String method = paymentMethod.getCheckedRadioButtonId() == R.id.paypalButton ? "PayPal" : "Direct";
            int donatedAmount = amountPicker.getValue();
            if (donatedAmount == 0) {
                String text = amountInput.getText().toString();
                if (!text.equals(""))
                    donatedAmount = Integer.parseInt(text);
            }
            if (donatedAmount > 0) {
                app.newDonation(new Donation(donatedAmount, method));
                progressBar.setProgress(app.totalDonated);
                String totalDonatedStr = "$" + app.totalDonated;
                total.setText(totalDonatedStr);
            }
        }
    }

    @Override
    public void reset(MenuItem item) {
        app.dbManager.reset();
        app.totalDonated = 0;
        String totalStr = "$" + app.totalDonated;
        total.setText(totalStr);
        progressBar.setProgress(app.totalDonated);
    }
}
