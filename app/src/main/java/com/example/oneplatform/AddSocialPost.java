package com.example.oneplatform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class AddSocialPost extends AppCompatActivity {
    private String index_person;
    private RequestQueue requestQueue;
    private TextView newPost;
    private static final String SUBMIT_URL = "https://studev.groept.be/api/a19sd604/socialPost/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        index_person = intent.getStringExtra("index_person");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_social_post);
        newPost=(TextView)findViewById(R.id.newsocialpost);
    }

    public void submit(View caller)
    {
        requestQueue = Volley.newRequestQueue(this);
        String post =newPost.getText().toString();
        String submitURL = SUBMIT_URL + post + "/" + index_person;
        System.out.println(submitURL);
        StringRequest submitRequest = new StringRequest(Request.Method.GET, submitURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(AddSocialPost.this, " Add post succeed", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddSocialPost.this, "Unable to place the order", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(submitRequest);

    }
}
