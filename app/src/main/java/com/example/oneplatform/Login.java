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

import java.io.IOException;
import java.util.List;



public class Login extends AppCompatActivity {

    private RequestQueue requestSql;
    private  static String SUBMIT_URL = null;
    private static final String QUEUE_URL = "https://studev.groept.be/api/a19sd604/Get_Password/";
    private EditText account;
    private EditText passWord;
    private Button login;
    private  String accountValue;
    private  String passwordValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        account = (EditText)findViewById(R.id.username);
        passWord = (EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);

    }

    public void test(View caller)
    {
        passwordValue=passWord.getText().toString();
        accountValue=account.getText().toString();
       // System.out.println(account.getText().toString());
        SUBMIT_URL=QUEUE_URL + account.getText().toString();
        requestSql = Volley.newRequestQueue(this);
       // System.out.println(SUBMIT_URL);
        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, SUBMIT_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0;i<response.length();++i)
                        {
                            JSONObject o = null;
                            try {
                                o= response.getJSONObject(i);
                                String s =o.get("Password").toString();
                                String index = o.get("index").toString();
                                if(s.equals(passwordValue))
                                {
                                    Intent intent = new Intent(Login.this,MainPage.class);
                                    intent.putExtra("index_person",index);
                                    startActivity(intent);
                                }
                                else
                                { Toast.makeText(Login.this, "Wrong password", Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Login.this, "No such account", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });

        requestSql.add(request);

    }
    public void reSetPassword(View caller)
    {
        Intent intent = new Intent(this, forgetPassword.class);
        startActivity(intent);
    }
    public void Register(View caller)
    {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }



}
