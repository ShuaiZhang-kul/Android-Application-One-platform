package com.example.oneplatform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ClickedUserPage extends AppCompatActivity {
    private String index_person;
    private TextView name;
    private TextView age;
    private TextView gender;
    private TextView description;
    private RequestQueue requestQueue;
    private static final String QUEUE_URL = "https://studev.groept.be/api/a19sd604/get_profile/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_user_page);
        name = (TextView) findViewById(R.id.clicked_name);
        age = (TextView) findViewById(R.id.clicked_age);
        gender = (TextView) findViewById(R.id.clicked_gender);
        description = (TextView) findViewById(R.id.clicked_description);

        Intent intent = getIntent();
        index_person = intent.getStringExtra("index_person");
        LoadInforFromSQL();
    }
    public void LoadInforFromSQL()
    {
        String finalURL = QUEUE_URL + index_person;
        requestQueue = Volley.newRequestQueue(this);
        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, finalURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String profileName = null;
                        String profileAge = null;
                        String profileDescription = null;
                        String profileGender = null;
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
                                Toast.makeText(ClickedUserPage.this, "No Internet", Toast.LENGTH_LONG).show();

                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ClickedUserPage.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();

            }
        });

        requestQueue.add(request);
    }
}
