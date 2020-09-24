package com.example.oneplatform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class EditPage extends AppCompatActivity {
    private TextView name;
    private TextView age;
    private TextView gender;
    private TextView description;
    private Button submit;
    private String index_person;

    private String profileName = null;
    private String profileAge = null;
    private String profileDescription = null;
    private String profileGender = null;
    private RequestQueue requestQueue;
    private static final String SUBMIT_URL = "https://studev.groept.be/api/a19sd604/edit_profile/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);
        name = (TextView) findViewById(R.id.change_profile_name);
        age = (TextView) findViewById(R.id.change_profile_age);
        gender = (TextView) findViewById(R.id.change_profile_gender);
        description = (TextView) findViewById(R.id.change_profile_description);
        submit = (Button) findViewById(R.id.chang_profile_submit);
        Intent intent = getIntent();
        index_person = intent.getStringExtra("index_person");
        profileName = intent.getStringExtra("profileName");
        profileAge = intent.getStringExtra("profileAge");
        profileGender = intent.getStringExtra("profileGender");
        profileDescription = intent.getStringExtra("profileDescription");
        loadPreviousProfile();
    }
    public void loadPreviousProfile()
    {

        name.setText(profileName);
        age.setText(profileAge);
        gender.setText(profileGender);
        description.setText(profileDescription);
    }
    public void submitEdit(View caller)
    {
        profileName = name.getText().toString();
        profileAge = age.getText().toString();
        profileGender = gender.getText().toString();
        profileDescription = description.getText().toString();

        requestQueue = Volley.newRequestQueue(this);
        String submitURL = SUBMIT_URL  + profileName + "/" + profileAge
                + "/" +profileGender +"/" +profileDescription + "/" + index_person;
        System.out.println(submitURL);
        StringRequest submitRequest = new StringRequest(Request.Method.GET, submitURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(EditPage.this, " Edit succeed", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditPage.this, "Unable to like this post due to internet problem", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(submitRequest);
    }
}
