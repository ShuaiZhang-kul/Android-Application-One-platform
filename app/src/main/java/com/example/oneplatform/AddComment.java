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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AddComment extends AppCompatActivity {
    private TextView newComment;
    private Button submit;
    private RequestQueue requestQueue;
    private String index_person;
    private String index_post;
    private static final String SUBMIT_URL = "https://studev.groept.be/api/a19sd604/add_comemnt";
    //private static final String PostID_URL = " https://studev.groept.be/api/a19sd604/Get_postIdCurrent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_comment);
        submit =(Button) findViewById(R.id.submit_post);
        newComment=(TextView)findViewById(R.id.newcomment);
        Intent intent = getIntent();
        index_person = intent.getStringExtra("index_person");
        index_post = intent.getStringExtra("index_post");


    }

    public void submit(View caller)
    {
        requestQueue = Volley.newRequestQueue(this);
        String newcomm =newComment.getText().toString();
        String submitURL = SUBMIT_URL+ "/"  + newcomm + "/" + index_post + "/" + index_person + "/" +index_post;
        System.out.println(submitURL);
        StringRequest submitRequest = new StringRequest(Request.Method.GET, submitURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(AddComment.this, " Add post succeed", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddComment.this, "Unable to place the order", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(submitRequest);
    }

}
