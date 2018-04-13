package edu.gatech.cs3311.athletics_inventory;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class checkout_activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageButton scanBtn,adderBtn;
    Button checkoutBtn;
    TextView contentTxt, quantity;
    TextView name;
    String location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_activity);

        Spinner spinner = (Spinner) findViewById(R.id.spinner5);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.location_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        scanBtn = (ImageButton)findViewById(R.id.imageButton);
        adderBtn = (ImageButton)findViewById(R.id.adder);
        name = (TextView) findViewById(R.id.itemName);
        contentTxt = (TextView)findViewById(R.id.barcode);
        quantity = (TextView)findViewById(R.id.quant);
        checkoutBtn = (Button)findViewById(R.id.checkout);
        //location = (TextView) findViewById(R.id.editText5);
        scanBtn.setOnClickListener(this);
        adderBtn = (ImageButton)findViewById(R.id.adder);
        adderBtn.setOnClickListener(this);
        checkoutBtn.setOnClickListener(this);
    }

    public void onClick(View v){
        if(v.getId()==R.id.imageButton){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }

        if(v.getId()==R.id.adder){
            int currentQuant;
            Log.i("button", quantity.getText().toString());
            if (quantity.getText().length() == 0) {
                currentQuant = 0;
            } else {
                currentQuant = Integer.parseInt(quantity.getText().toString());
            }
            quantity.setText(Integer.toString(++currentQuant));
        }
        if(v.getId()==R.id.checkout && !(quantity.getText().toString().equals("")) && !(name.getText().toString().equals("")) && location.equals("Student")){
            AlertDialog alertDialog = new AlertDialog.Builder(checkout_activity.this).create();
            alertDialog.setTitle("Checking Out");
            alertDialog.setMessage("A Student is checking out " + quantity.getText().toString() + " " + name.getText().toString());
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Go to Check Out Page",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent checkout = new Intent(getApplicationContext(),
                                    StudentCheckout.class);
                            startActivity(checkout);
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        } else if(v.getId()==R.id.checkout && !(quantity.getText().toString().equals("")) && !(name.getText().toString().equals(""))){
            AlertDialog alertDialog = new AlertDialog.Builder(checkout_activity.this).create();
            alertDialog.setTitle("Checking Out");
            alertDialog.setMessage("You are moving " + quantity.getText().toString() + " " + name.getText().toString() + " to the " + location);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent home = new Intent(getApplicationContext(),
                                    HomePage.class);
                            startActivity(home);
                            finish();
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        } else if(v.getId()==R.id.checkout && !(quantity.getText().toString().equals(""))) {
            AlertDialog alertDialog = new AlertDialog.Builder(checkout_activity.this).create();
            alertDialog.setTitle("Error Checking Out");
            alertDialog.setMessage("Please select an Item");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {dialog.dismiss();}
                    });
            alertDialog.show();
        } else if(v.getId()==R.id.checkout && !(name.getText().toString().equals(""))) {
            AlertDialog alertDialog = new AlertDialog.Builder(checkout_activity.this).create();
            alertDialog.setTitle("Error Checking Out");
            alertDialog.setMessage("Please specify how many items you are moving");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {dialog.dismiss();}
                    });
            alertDialog.show();
        } else if(v.getId()==R.id.checkout){
            AlertDialog alertDialog = new AlertDialog.Builder(checkout_activity.this).create();
            alertDialog.setTitle("Error Checking Out");
            alertDialog.setMessage("Please select an Item and a quantity you are moving");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {dialog.dismiss();}
                    });
            alertDialog.show();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            //String scanFormat = scanningResult.getFormatName();
            //formatTxt.setText("FORMAT: " + scanFormat);
            contentTxt.setText(scanContent.toString());
            if  (scanContent.equals("1029471038007")){
                name.setText(".25\" Bandages");
            }
            if (scanContent.equals("1029521094304")){
                name.setText("Cooling Gel");
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        Object selected = parent.getItemAtPosition(pos);
        location = (selected.toString());

    }
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

}