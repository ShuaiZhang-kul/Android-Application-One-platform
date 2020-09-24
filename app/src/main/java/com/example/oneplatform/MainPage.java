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

public class MainPage extends AppCompatActivity {
    private String index_person;
    private RequestQueue requestQueue;
    private static final String QUEUE_URL = "https://studev.groept.be/api/a19sd604/fetchJobinformation";
   // private static final String submitURL = "https://studev.groept.be/api/a18sdtest4/order/Name/Espresso/+sugar/2";

    private List<UserCard> cardList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Intent intent = getIntent();
        index_person = intent.getStringExtra("index_person");
        fetchSql();


    }
    public void fetchSql()
    {
        requestQueue = Volley.newRequestQueue(this);
        System.out.println(QUEUE_URL);
        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, QUEUE_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String title = null;
                        String text = null;
                        String posterId= null;
                        for (int i = 0;i<response.length();++i)
                        {
                            JSONObject o = null;
                            try {
                                o= response.getJSONObject(i);

                                title = o.get("title").toString();
                                text = o.get("text").toString();
                                posterId = o.get("idofposter").toString();
                              //  System.out.println(title);

                                UserCard card = new UserCard(R.drawable.logo,title,text, posterId);
                                cardList.add(card);
                              //  Toast.makeText(MainPage.this, "No object", Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                             //   Toast.makeText(MainPage.this, "No object", Toast.LENGTH_LONG).show();

                            }
                            //all the data has been fetched successfully
                            putIntoListView();
                        }
                    }
                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainPage.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();

            }
        });

        requestQueue.add(request);

    }

    private void putIntoListView() {
        UserCardAdapter adapter = new UserCardAdapter(this,R.layout.card,cardList);
        ListView listView1 = (ListView)findViewById(R.id.list_view);
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //parent is the cardlist; view is the clicked view.
                TextView text1 = view.findViewById(R.id.jobplatposterid);
                String result= text1.getText().toString();
                //Toast.makeText(MainPage.this,"you are clicking"+result + "‘s post",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainPage.this,ClickedUserPage.class);
                intent.putExtra("index_person",result);
                startActivity(intent);
            }
        });
    }

    public class UserCardAdapter extends ArrayAdapter<UserCard> {
        private int resourceId;
        //resource and recouceid is used to get the xml file
        public UserCardAdapter(@NonNull Context context, int resource, @NonNull List<UserCard> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

        //using getView method to write the data from object to xml file
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //get the current object from object list
            UserCard usercard = getItem(position);
            //get the current xml file from xml group
            View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            //get the elements id from this xml file
            ImageView icon =(ImageView) view.findViewById(R.id.icon_user);
            TextView username =(TextView) view.findViewById(R.id.name_user);
            TextView demandBox =(TextView) view.findViewById(R.id.looking_for);
            TextView posterId =(TextView) view.findViewById(R.id.jobplatposterid);
            //put the data from current object to current xml file
            icon.setImageResource(usercard.getIconId());
            username.setText(usercard.getUserName());
            demandBox.setText(usercard.getUserText());
            posterId.setText(usercard.getPostId());
            //return the complete xml file view which is with data currently
            return view;
        }
    }

    public void addPost(View caller)
    {
        Intent intent = new Intent(MainPage.this,AddPost.class);
        intent.putExtra("index_person",index_person);
        startActivity(intent);
    }
    public void refresh(View caller)
    {
        finish();
        Intent intent = new Intent(this, MainPage.class);
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
    public void toSelfPage(View caller)
    {
        finish();
        Intent intent = new Intent(this, SelfPage.class);
        intent.putExtra("index_person",index_person);
        startActivity(intent);
    }
}


