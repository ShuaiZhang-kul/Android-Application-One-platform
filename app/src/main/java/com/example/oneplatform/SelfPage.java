package com.example.oneplatform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SelfPage extends AppCompatActivity {
    private TextView name;
    private TextView age;
    private TextView gender;
    private TextView description;
    private Button edit;
    private String index_person;
    private RequestQueue requestQueue;
    private static final String QUEUE_URL = "https://studev.groept.be/api/a19sd604/get_profile/";
    private String profileName = null;
    private String profileAge = null;
    private String profileDescription = null;
    private String profileGender = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_page);
        name = (TextView) findViewById(R.id.profile_name);
        age = (TextView) findViewById(R.id.profile_age);
        gender = (TextView) findViewById(R.id.profile_gender);
        description = (TextView) findViewById(R.id.profile_description);
        edit = (Button) findViewById(R.id.editprofile);
        Intent intent = getIntent();
        index_person = intent.getStringExtra("index_person");
        String finalURL = QUEUE_URL + index_person;

        requestQueue = Volley.newRequestQueue(this);
        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, finalURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); ++i) {
                            JSONObject o = null;
                            try {
                                o = response.getJSONObject(i);
                                profileName = o.get("UserName").toString();
                                profileAge = o.get("Age").toString();
                                profileGender = o.get("Gender").toString();
                                profileDescription = o.get("Description").toString();

                                name.setText(profileName);
                                age.setText(profileAge);
                                gender.setText(profileGender);
                                description.setText(profileDescription);


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(SelfPage.this, "No Internet", Toast.LENGTH_LONG).show();

                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SelfPage.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();

            }
        });

        requestQueue.add(request);
    }
    public void refresh(View caller)
    {
        finish();
        Intent intent = new Intent(this, SelfPage.class);
        intent.putExtra("index_person",index_person);
        startActivity(intent);

    }
    public void toSocialPlatform(View caller)
    {
        finish();
        Intent intent = new Intent(this, SocialPlatform.class);
        intent.putExtra("index_person",index_person);
        startActivity(intent);
    }
    public void toJobPlatform(View caller)
    {
        finish();
        Intent intent = new Intent(this, MainPage.class);
        intent.putExtra("index_person",index_person);
        startActivity(intent);
    }
    public void editProfile(View caller)
    {
        Intent intent = new Intent(this, EditPage.class);
        intent.putExtra("index_person",index_person);

        intent.putExtra("profileName",profileName);
        intent.putExtra("profileAge",profileAge);
        intent.putExtra("profileGender",profileGender);
        intent.putExtra("profileDescription",profileDescription);
        startActivity(intent);
    }
}