package com.example.newstation;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class OfflineDetails extends AppCompatActivity {
TextView title;
TextView author;
TextView description;
String mTitle;
String mAuthor;
String mDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_details);
        Intent i =getIntent();
        title = findViewById(R.id.article_heading);
        //author = findViewById(R.id.article_subheading);
        description = findViewById(R.id.article);
        mAuthor = i.getStringExtra("author");
        mTitle = i.getStringExtra("title");
        mDescription = i.getStringExtra("description");
        title.setText(mTitle);
       // author.setText(mAuthor);
        description.setText(mDescription);



    }

}
