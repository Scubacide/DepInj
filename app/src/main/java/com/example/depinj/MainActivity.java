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

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

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
            TextView employeeTitle = findViewById(R.id.tvInputEmail);
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
    public void gInfo(View v) {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("Employees");
            ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                Log.d("Details-->", jo_inside.getString("userId"));
                String firstName = jo_inside.getString("firstName");
                String jobTitleName = jo_inside.getString("jobTitleName");
                String email = jo_inside.getString("emailAddress");

                //Add your values in your `ArrayList` as below:
                m_li = new HashMap<String, String>();
                m_li.put("Name", firstName);
                m_li.put("Title", jobTitleName);
                m_li.put("Email", email);

                formList.add(m_li);
                TextView tv = findViewById(R.id.tvInputName);
                TextView tv2 = findViewById(R.id.tvInputTitle);
                TextView tv3 = findViewById(R.id.tvInputEmail);
                tv.setText(m_li.get("Name"));
                tv2.setText(m_li.get("Title"));
                tv3.setText(m_li.get("Email"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
