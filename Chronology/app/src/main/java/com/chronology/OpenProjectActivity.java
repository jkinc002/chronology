package com.chronology;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        String[] projectList = getProjectList();
        if(projectList == null){
            Toast.makeText(getApplicationContext(), "size = 0", Toast.LENGTH_SHORT).show();
        }

        else {
            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, projectList);
            ListView listView = (ListView) findViewById(R.id.projectListView);
            listView.setAdapter(itemsAdapter);
        }

    }

    public String[] getProjectList(){
        int curr = 0;
        int numOfFiles = 0;
        File dir = getFilesDir();
        File[] filesList = dir.listFiles();
        if(filesList.length == 0) return null;

        for(int i = 0; i < filesList.length; ++i){
            String s = filesList[i].getName();
            if(s.endsWith(".chrn")) ++numOfFiles;
        }
        if(numOfFiles == 0) return null;

        String[] retArray = new String[numOfFiles];
        int j = 0;
        for(int i = 0; i < filesList.length; ++i) {
            String s = filesList[i].getName();
            if(s.endsWith(".chrn")){
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
