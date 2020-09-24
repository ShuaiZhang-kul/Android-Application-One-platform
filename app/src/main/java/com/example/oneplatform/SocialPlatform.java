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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SocialPlatform extends AppCompatActivity {
    private String index_person;
    private List<UserPost> postList = new ArrayList<>();
    private RequestQueue requestQueue;
    private static final String QUEUE_URL = "https://studev.groept.be/api/a19sd604/fetch_post";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_platform);
        Intent intent =getIntent();
        index_person = intent.getStringExtra("index_person");
        fetchPostList();
    }

    private void fetchPostList() {
        requestQueue = Volley.newRequestQueue(this);
        System.out.println(QUEUE_URL);
        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, QUEUE_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String post = null;
                        String likeNum =null;
                        String commentNum =null;
                        String indexofpostsocial=null;
                        for (int i = 0;i<response.length();++i)
                        {
                            JSONObject o = null;
                            try {
                                o= response.getJSONObject(i);
                                likeNum = o.get("numOfLike").toString();
                                indexofpostsocial =  o.get("indexofpostsocial").toString();
                                post=o.get("socialpost").toString();
                                commentNum=o.get("numOFComment").toString();
                                UserPost userpost = new UserPost(R.drawable.logo,likeNum,commentNum, indexofpostsocial, post);
                                postList.add(userpost);
                               //
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

                Toast.makeText(SocialPlatform.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();

            }
        });

        requestQueue.add(request);
    }
    private void putIntoListView() {
        UserPostAdapter adapter = new UserPostAdapter(this,R.layout.social_post,postList);
        ListView listView1 = (ListView)findViewById(R.id.list_view_social);
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //parent is the list; view is the clicked view.
                TextView text1 = view.findViewById(R.id.postId);

                String index_post= text1.getText().toString();
                System.out.println(index_post);
               // Toast.makeText(SocialPlatform.this,"you are clicking index"+result,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SocialPlatform.this,commentActivity.class);
                intent.putExtra("index_person",index_person);
                intent.putExtra("index_post",index_post);
                startActivity(intent);
            }
        });
    }


    public class UserPostAdapter extends ArrayAdapter<UserPost> {
        private int resourceId;
        //resource and recouceid is used to get the xml file
        public UserPostAdapter(@NonNull Context context, int resource, @NonNull List<UserPost> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

        //using getView method to write the data from object to xml file
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //get the current object from object list
            UserPost userpost = getItem(position);
            //get the current xml file from xml group
            View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

            //get the elements id from this xml file
            ImageView icon =(ImageView) view.findViewById(R.id.icon_user);
            TextView socialPost =(TextView) view.findViewById(R.id.textSocialPost);
            TextView textLikeNum =(TextView) view.findViewById(R.id.textLikeNum);
            TextView textCommentNum =(TextView) view.findViewById(R.id.textCommentNum);
            TextView postId =(TextView) view.findViewById(R.id.postId);


            //put the data from current object to current xml file
            icon.setImageResource(userpost.getIconId());
            socialPost.setText(userpost.getPost());
            textLikeNum.setText(userpost.getLikeNum());
            textCommentNum.setText(userpost.getCommentNum());
            postId.setText(userpost.getPostId());
            //return the complete xml file view which is with data currently
            return view;
        }
    }
    public void addsocialPost(View caller)
    {

        Intent intent = new Intent(SocialPlatform.this,AddSocialPost.class);
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
    public void refresh(View caller)
    {
        finish();
        Intent intent = new Intent(this, SocialPlatform.class);
        intent.putExtra("index_person",index_person);
        startActivity(intent);

    }
}
