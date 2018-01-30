package ie.bask.donation10;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Donate extends AppCompatActivity {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_donate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            totalDonated+=amount;
            progressBar.setProgress(totalDonated);
            targetArchieved = totalDonated > 10000;
            String totalStr = "$" + totalDonated;
            total.setText(totalStr);
        }
        else{
            Toast.makeText(this, "Target Exceeded!", Toast.LENGTH_SHORT).show();
        }



        Log.v("Donate", "Donate Pressed! with amount " + amount + " and payment method " + method + ". The current total is " + totalDonated);

    }
}
