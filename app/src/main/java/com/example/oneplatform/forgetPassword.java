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

public class forgetPassword extends AppCompatActivity {
    private TextView account;
    private TextView answer;
    private TextView passNew;
    private Button submit;
    private RequestQueue requestQueue;
    private static final String QUEUE_URL = "https://studev.groept.be/api/a19sd604/get_answer/";
    private static final String SUBMIT_URL = "https://studev.groept.be/api/a19sd604/reset_password/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        account = (TextView) findViewById(R.id.reset_account);
        answer = (TextView) findViewById(R.id.reset_answer);
        passNew = (TextView) findViewById(R.id.reset_passnew);
        submit = (Button) findViewById(R.id.reset_submit);
    }
    public void submit(View caller)
    {
        final String txtAnswer = answer.getText().toString();
        final String txtaccount = account.getText().toString();
        String finalURL=QUEUE_URL + txtaccount;
        requestQueue = Volley.newRequestQueue(this);
        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, finalURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0;i<response.length();++i)
                        {
                            JSONObject o = null;
                            try {
                                o= response.getJSONObject(i);
                                String s =o.get("Question").toString();
                                if(s.equals(txtAnswer))
                                {
                                    reset(txtaccount);
                                }
                                else
                                { Toast.makeText(forgetPassword.this, "Wrong answer", Toast.LENGTH_LONG).show();
                                finish();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(forgetPassword.this, "No such account", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(forgetPassword.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(request);
    }
    public void reset(String txtaccount)
    {
        String txtPassNew =  passNew.getText().toString();
        String submitURL = SUBMIT_URL  + txtPassNew + "/" + txtaccount ;
        System.out.println(submitURL);
        StringRequest submitRequest = new StringRequest(Request.Method.GET, submitURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(forgetPassword.this, " reset succeed", Toast.LENGTH_SHORT).show();
              //  finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(forgetPassword.this, "Unable to like this post due to internet problem", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(submitRequest);
    }

}
