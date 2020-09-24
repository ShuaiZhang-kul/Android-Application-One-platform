package com.example.oneplatform;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;

public class commentActivity extends AppCompatActivity {
    private String index_person;
    private String index_post;
    private List<UserComment> commentList = new ArrayList<>();
    private RequestQueue requestQueue;
    private String queueURL;
    private static final String QUEUE_URL = "https://studev.groept.be/api/a19sd604/fetch_comment/";
    private static final String SUBMIT_URL = "https://studev.groept.be/api/a19sd604/Like/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent = getIntent();
        index_person = intent.getStringExtra("index_person");
        index_post = intent.getStringExtra("index_post");
        System.out.println(index_post);
        queueURL= QUEUE_URL + index_post;
        fetchCommentList();
    }
    private void fetchCommentList() {
        requestQueue = Volley.newRequestQueue(this);
        System.out.println(QUEUE_URL);
        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, queueURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String name = null;
                        String comment =null;
                        for (int i = 0;i<response.length();++i)
                        {
                            JSONObject o = null;
                            try {
                                o= response.getJSONObject(i);
                                name = o.get("UserName").toString();
                                comment =  o.get("comment").toString();
                                UserComment userComment = new UserComment(name,comment);
                                commentList.add(userComment);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                //Toast.makeText(SocialPlatform.this, "No object", Toast.LENGTH_LONG).show();

                            }
                            //all the data has been fetched successfully
                            putIntoListView();
                        }
                    }
                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(commentActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();

            }
        });

        requestQueue.add(request);
    }
    private void putIntoListView() {
        UserCommentAdapter adapter = new UserCommentAdapter(this,R.layout.comment,commentList);
        ListView listView1 = (ListView)findViewById(R.id.list_view_comment);
        listView1.setAdapter(adapter);
    }
    public class UserCommentAdapter extends ArrayAdapter<UserComment> {
        private int resourceId;
        //resource and recouceid is used to get the xml file
        public UserCommentAdapter(@NonNull Context context, int resource, @NonNull List<UserComment> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

        //using getView method to write the data from object to xml file
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //get the current object from object list
            UserComment usercomment = getItem(position);
            //get the current xml file from xml group
            View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

            //get the elements id from this xml file
            TextView username =(TextView) view.findViewById(R.id.comment_username);
            TextView userComment =(TextView) view.findViewById(R.id.comment_usercomment);


            //put the data from current object to current xml file
            username.setText(usercomment.getUserName());
            userComment.setText(usercomment.getUserComment());

            //return the complete xml file view which is with data currently
            return view;
        }
    }
    public void addComment(View caller)
    {
        System.out.println(index_post);
        Intent intent = new Intent(commentActivity.this,AddComment.class);
        intent.putExtra("index_person",index_person);
        intent.putExtra("index_post",index_post);
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
    public void toSelfPage(View caller)
    {
        finish();
        Intent intent = new Intent(this, SelfPage.class);
        intent.putExtra("index_person",index_person);
        startActivity(intent);
    }
    public void Like(View caller)
    {
        requestQueue = Volley.newRequestQueue(this);
        String submitURL = SUBMIT_URL  + index_post;
        //System.out.println(submitURL);
        StringRequest submitRequest = new StringRequest(Request.Method.GET, submitURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(commentActivity.this, " Like succeed", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(commentActivity.this, "Unable to like this post due to internet problem", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(submitRequest);

    }
}
