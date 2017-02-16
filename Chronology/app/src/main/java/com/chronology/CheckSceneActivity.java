package com.chronology;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jordan on 1/27/2017.
 */
public class CheckSceneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_scene);



        TextView textView = (TextView) findViewById(R.id.sceneCheck);
        textView.setText(getIntent().getStringExtra("content"));
    }
}
