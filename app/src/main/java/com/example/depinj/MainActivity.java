package com.example.depinj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    String JSON_STRING = "{\"employee\":{\"name\":\"Abhishek Saini\",\"salary\":65000}}";
    String name, salary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadInfo(View v) {
        try {
            TextView employeeName = findViewById(R.id.tvInputName);
            TextView employeeTitle = findViewById(R.id.tvInputTitle);
            // get JSONObject from JSON file
            JSONObject obj = new JSONObject(JSON_STRING);
            // fetch JSONObject named employee
            JSONObject employee = obj.getJSONObject("employee");
            // get employee name and salary
            name = employee.getString("name");
            salary = employee.getString("salary");
            // set employee name and salary in TextView's
            employeeName.setText("Name: "+name);
            employeeTitle.setText("Salary: "+salary);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getData(View view) {
        // ArrayList for person names, email Id's and mobile numbers
        ArrayList<String> personNames = new ArrayList<>();
        ArrayList<String> emailIds = new ArrayList<>();
        ArrayList<String> mobileNumbers = new ArrayList<>();

            try {
                // get JSONObject from JSON file
                JSONObject obj = new JSONObject(loadJSONFromAsset());
                // fetch JSONArray named users
                JSONArray userArray = obj.getJSONArray("users");
                // implement for loop for getting users list data
                for (int i = 0; i < userArray.length(); i++) {
                    // create a JSONObject for fetching single user data
                    JSONObject userDetail = userArray.getJSONObject(i);
                    // fetch email and name and store it in arraylist
                    personNames.add(userDetail.getString("name"));
                    emailIds.add(userDetail.getString("email"));
                    // create a object for getting contact data from JSONObject
                    JSONObject contact = userDetail.getJSONObject("contact");
                    // fetch mobile number and store it in arraylist
                    mobileNumbers.add(contact.getString("mobile"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //  call the constructor of CustomAdapter to send the reference and data to Adapter
            CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, personNames, emailIds, mobileNumbers);
        }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("employees.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}