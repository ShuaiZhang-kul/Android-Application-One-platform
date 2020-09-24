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

public class AddPost extends AppCompatActivity {

    private RequestQueue requestQueue;
    private static final String SUBMIT_URL = "https://studev.groept.be/api/a19sd604/jobPost/";
    private String index_person;

    private TextView title;
    private TextView text;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        index_person = intent.getStringExtra("index_person");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        submit =(Button) findViewById(R.id.submit_post);
        text=(TextView)findViewById(R.id.text_post);
        title=(TextView)findViewById(R.id.title_post);
    }
    public void submit(View caller)
    {
        requestQueue = Volley.newRequestQueue(this);
        String title_str =title.getText().toString();
        String text_str =text.getText().toString();
        String submitURL = SUBMIT_URL + title_str + "/" + text_str +"/" + index_person;
        System.out.println(submitURL);
        StringRequest submitRequest = new StringRequest(Request.Method.GET, submitURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(AddPost.this, " Add post succeed", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddPost.this, "Unable to place the order", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(submitRequest);

    }

}
