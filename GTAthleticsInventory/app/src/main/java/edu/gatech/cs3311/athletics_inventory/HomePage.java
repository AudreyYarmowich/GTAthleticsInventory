package edu.gatech.cs3311.athletics_inventory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;


public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Button checkout = findViewById(R.id.checkout_button);
        checkout.setOnClickListener(view -> {
            // GET INTENT
            Intent checkout_Activity = new Intent(getApplicationContext(),
                    checkout_activity.class);
            startActivity(checkout_Activity);
        });
    }
}
