package com.chronology;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Array;

/**
 * Created by Jordan on 1/20/2017.
 */
public class OpenProjectActivity extends AppCompatActivity {

    String suffix = ".chrn";

    @Override
    protected void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_open_project);

        ListView listView = (ListView) findViewById(R.id.projectListView);

        String[] projectList = getProjectList();
        if(projectList == null){
            Toast.makeText(getApplicationContext(), "No Projects Exist", Toast.LENGTH_SHORT).show();
        }

        else {
            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, projectList);
            listView.setAdapter(itemsAdapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


    }

    public String[] getProjectList(){
        int curr = 0;
        int numOfFiles = 0;
        File dir = getFilesDir();
        File[] filesList = dir.listFiles();
        if(filesList.length == 0) return null;

        for(int i = 0; i < filesList.length; ++i){
            String s = filesList[i].getName();
            if(s.endsWith(this.suffix)) ++numOfFiles;
        }
        if(numOfFiles == 0) return null;

        String[] retArray = new String[numOfFiles];
        int j = 0;
        for(int i = 0; i < filesList.length; ++i) {
            String s = filesList[i].getName();
            if(s.endsWith(this.suffix)){
                retArray[j] = s;
                ++j;
            }
        }

        return removeSuffixes(retArray);
    }

    public String[] removeSuffixes(String[] array){
        String[] retArray = new String[array.length];
        for(int i =0; i < array.length; ++i){
            int start = 0;
            int end = array[i].length() - this.suffix.length();
            retArray[i] = array[i].substring(start, end);
        }
        return retArray;
    }
}
