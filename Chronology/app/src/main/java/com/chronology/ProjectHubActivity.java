package com.chronology;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Jordan on 1/9/2017.
 */
public class ProjectHubActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_hub);

        TextView textView = (TextView) findViewById(R.id.hubTitle);
        textView.setText(getIntent().getStringExtra(
                "title"
        ));

        ListView listView = (ListView) findViewById(R.id.eventList);
        

    }

    public void newEvent (View view){

    }
}
