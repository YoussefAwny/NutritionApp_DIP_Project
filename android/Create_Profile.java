package com.example.spoon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static com.example.spoon.MainActivity.names_strings;

public class Create_Profile extends AppCompatActivity {

    private static final String TAG = "mark";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__profile);
        Spinner genderlist = (Spinner)findViewById(R.id.gender_list);
        String[] items = new String[]{" ","Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        genderlist.setAdapter(adapter);
    }

    public void createDone(View view)
    {
        EditText create_name = (EditText)findViewById(R.id.create_name);
        EditText create_age = (EditText) findViewById(R.id.create_age);
        Spinner gender_list = (Spinner)findViewById(R.id.gender_list);
        if(create_name.getText().toString().length()==0)
        {
            Toast.makeText(this, "Please Enter a Name", Toast.LENGTH_LONG).show();
            return;
        }
        String name = create_name.getText().toString();;
        if(create_age.getText().toString().length()==0)
        {
            Toast.makeText(this, "Please Enter an age", Toast.LENGTH_LONG).show();
            return;
        }
        int age = Integer.parseInt(create_age.getText().toString());
        String gender = gender_list.getSelectedItem().toString();
        if(names_strings.indexOf(name)>=0)
        {
            Toast.makeText(this, "Name Is Taken, Please Enter Another Name", Toast.LENGTH_LONG).show();
            return;
        }
        if(gender == " ") {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_LONG).show();
            return;
        }
        long n = MainActivity.dbHandler.addProfile(new Profile(age, name, gender));
        //Intent I = new Intent(this, MainActivity.class);
        //startActivity(I);
        finish();
    }
}
