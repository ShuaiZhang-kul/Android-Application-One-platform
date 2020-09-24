package com.example.oneplatform;

import androidx.appcompat.app.AppCompatActivity;

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

public class Register extends AppCompatActivity {
    private TextView account;
    private TextView password;
    private TextView question;
    private Button submit;
    private RequestQueue requestQueue;
    private static final String SUBMIT_URL = "https://studev.groept.be/api/a19sd604/register/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        account = (TextView) findViewById(R.id.account_register);
        password = (TextView) findViewById(R.id.password_register);
        question = (TextView) findViewById(R.id.question_register);
        submit = (Button) findViewById(R.id.submit_register);
    }
    public void submit(View caller)
    {
       String txtAccount = account.getText().toString();
       String txtPassword = password.getText().toString();
       String txtQuestion = question.getText().toString();
        requestQueue = Volley.newRequestQueue(this);
        String submitURL = SUBMIT_URL  + txtAccount + "/" + txtPassword
                + "/" +txtQuestion;
        System.out.println(submitURL);
        StringRequest submitRequest = new StringRequest(Request.Method.GET, submitURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Register.this, " register succeed", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Register.this, "Unable to like this post due to internet problem", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(submitRequest);
    }
}
