package edu.gatech.cs3311.athletics_inventory;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StudentCheckout extends AppCompatActivity implements View.OnClickListener {

    Button sCheckoutBtn;
    TextView name, phone, email, id, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_checkout);

        name = (TextView) findViewById(R.id.studentname);
        phone = (TextView) findViewById(R.id.studentphone);
        email = (TextView) findViewById(R.id.studentemail);
        id = (TextView) findViewById(R.id.studentid);
        date = (TextView) findViewById(R.id.date);

        sCheckoutBtn = (Button)findViewById(R.id.studentCheckOut);
        sCheckoutBtn.setOnClickListener(this);

    }

    public void onClick(View v){
        if(v.getId()==R.id.studentCheckOut && !(id.getText().toString().equals("")) && !(name.getText().toString().equals(""))) {
            AlertDialog alertDialog = new AlertDialog.Builder(StudentCheckout.this).create();
            alertDialog.setTitle("Checking Out");
            alertDialog.setMessage("A Student is checking out " + id.getText().toString() + " " + name.getText().toString());
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

        }
    }
}
