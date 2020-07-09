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
import android.widget.ImageView;
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

public class PlaylistVideo extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    String channelURL,videoImageURL,videoTitle,videoID,channelName;
    ListView fragmntList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_video);
        getVideData();
        fragmntList = findViewById(R.id.videoList);
        channelURL = getIntent().getStringExtra("channelURL");
        channelName = getIntent().getStringExtra("channelTitle");

        fragmntList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(PlaylistVideo.this,VideoPlayer.class);
                intent.putExtra("videoName",videoTitle);
                intent.putExtra("playlistId",videoID);
                startActivity(intent);
            }
        });
    }
    

    public class CustomfragmntAdapter extends ArrayAdapter<ChannelData> {

        public CustomfragmntAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.custom_playlist, viewGroup, false);
            ImageView videoImage = view.findViewById(R.id.videoImage);
            TextView channelVideoName = view.findViewById(R.id.channelVideoName);
            TextView videoName = view.findViewById(R.id.videoName);
            videoName.setText(videoTitle);
            Glide.with(getContext()).load(videoImageURL).into(videoImage);
            channelVideoName.setText(channelName);
            return view;
        }
    }
    public void getVideData(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String base = "https://www.googleapis.com/youtube/v3/channels?part=snippet%2CcontentDetails%2Cstatistics&id=";
        String channelid = channelURL;
        String link = base + channelid+"&maxResults=25&key=AIzaSyB43vfEpUD3NFH5ZV3l1UXcJOZ5aZ7SIZQ";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(link,null,this,this);
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray items = response.getJSONArray("items");
            for (int i = 0; i <items.length() ; i++) {
                JSONObject object = items.getJSONObject(i);
                videoID = object.getString("id");
                videoImageURL = object.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("maxres").getString("url");
                videoTitle = object.getJSONObject("snippet").getString("title");


            }
            CustomfragmntAdapter customfragmntAdapter = new CustomfragmntAdapter(this,R.layout.custom_playlist);
            fragmntList.setAdapter(customfragmntAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Volley Error",error.getMessage());
    }
}