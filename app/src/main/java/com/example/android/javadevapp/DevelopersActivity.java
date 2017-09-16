package com.example.android.javadevapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DevelopersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);
        TextView usernameTextView = (TextView)findViewById(R.id.githu_user_name);
        TextView profileUrlTextView = (TextView)findViewById(R.id.github_profile_url);
        ImageView userImageImageView = (ImageView) findViewById(R.id.github_user_image);

        Intent developerIntent = getIntent();
        final String githubProfileUrl = developerIntent.getStringExtra("profile_url");
        profileUrlTextView.setText(githubProfileUrl);
        final String username = developerIntent.getStringExtra("username");
        usernameTextView.setText(username);

        String userImageUrl = developerIntent.getStringExtra("image_url");
        Picasso.with(getApplicationContext())
                .load(userImageUrl)
                .into(userImageImageView);


        profileUrlTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gitHubUri = Uri.parse(githubProfileUrl);
                Intent githubUerIntent = new Intent(Intent.ACTION_VIEW, gitHubUri);
                startActivity(githubUerIntent);
            }
        });

        Button showProfile = (Button)findViewById(R.id.btn_show_profile);
        showProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTextUrl(githubProfileUrl, username);
            }
        });
        


    }

    private void shareTextUrl( String githubProfileUrl, String username){
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_TEXT, "Check out this awesome developer@"+username+","+githubProfileUrl);
        startActivity(Intent.createChooser(share, "Share link!"));
    }

}
