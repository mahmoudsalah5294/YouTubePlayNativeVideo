package com.mahmoudsalah.youtubeplaynativevideo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoPlayer extends AppCompatActivity {
YouTubePlayerView youTubePlayerView;
YouTubePlayer.OnInitializedListener onInitializedListener;
TextView videoTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        youTubePlayerView = findViewById(R.id.youtubePlayer);
        String playlistId = getIntent().getStringExtra("playlistId");
        videoTitle = findViewById(R.id.videoTitlelast);
        videoTitle.setText(getIntent().getStringExtra("videoName"));

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadPlaylist(playlistId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        youTubePlayerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youTubePlayerView.initialize("AIzaSyB43vfEpUD3NFH5ZV3l1UXcJOZ5aZ7SIZQ",onInitializedListener);
            }
        });

    }
}