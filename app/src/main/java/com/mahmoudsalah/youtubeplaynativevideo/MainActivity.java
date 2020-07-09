package com.mahmoudsalah.youtubeplaynativevideo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ListView;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    public ArrayList<String> chTitle = new ArrayList<>();
    public ArrayList<String> chURL = new ArrayList<>();
    public ArrayList<String> chIm = new ArrayList<>();
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.list);
        getdata();





        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,PlaylistVideo.class);
                intent.putExtra("channelTitle",chTitle.get(i));
                intent.putExtra("channelURL",chURL.get(i));
                startActivity(intent);
            }
        });

    }



    public class CustomLayoutAdapter extends ArrayAdapter<ChannelData> {
        public CustomLayoutAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.custom_layout,viewGroup,false);
            CircleImageView iconImage = view.findViewById(R.id.iconImage);
            TextView channelName = view.findViewById(R.id.channelName);
            Button subButton = view.findViewById(R.id.subButton);
             subButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                    if ( subButton.isEnabled()) {
                        subButton.setEnabled(false);
                    }else{
                        subButton.setEnabled(true);
                    }
                 }
             });
            channelName.setText(chTitle.get(i));
            Glide.with(getContext()).load(chIm.get(i)).into(iconImage);



            return view;
        }

        @Override
        public int getCount() {
            return chTitle.size();
        }
    }
    public void getdata(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://www.googleapis.com/youtube/v3/channels?part=snippet%2CcontentDetails%2Cstatistics&id=UCp6pSc-b5GB-Cx2vpKpdiUQ%2CUCfdjvGPgprBYu4Cdoxd9YBQ%2CUCcabW7890RKJzL968QWEykA%2CUCOLqdfg1EotV33S_m5yRwJw%2CUCzv6uVYjfvE8X-_F3cicWog&key=";
        String key = "AIzaSyB43vfEpUD3NFH5ZV3l1UXcJOZ5aZ7SIZQ";
        String link = url + key;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(link,null,this,this);
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray items = response.getJSONArray("items");
            for (int i = 0; i <items.length() ; i++) {
                JSONObject object = items.getJSONObject(i);
                String channelUrl = object.getString("id");
                String imageUrl = object.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url");
                String channelTitle = object.getJSONObject("snippet").getString("title");
                chURL.add(channelUrl);
                chIm.add(imageUrl);
                chTitle.add(channelTitle);


            }
            CustomLayoutAdapter customLayoutAdapter = new CustomLayoutAdapter(this,R.layout.custom_layout);
            list.setAdapter(customLayoutAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Volley Error",error.getMessage());
    }

}


