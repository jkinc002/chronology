package com.chronology;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void newProject(View view){
        Intent intent = new Intent(this, NewProjectActivity.class);
        startActivity(intent);
    }

    public void openProject(View view){
        Intent intent = new Intent (this, OpenProjectActivity.class);
        startActivity(intent);
    }

    public void deleteProject(View view){
        Intent intent = new Intent(this, DeleteProjectActivity.class);
        startActivity(intent);
    }
}
