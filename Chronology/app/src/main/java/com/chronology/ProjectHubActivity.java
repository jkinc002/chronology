package com.chronology;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Jordan on 1/9/2017.
 */
public class ProjectHubActivity extends AppCompatActivity {

    String title;
    String filename;
    private int arraySize;
    public String[] myStringArray;
    public String[] copyArray;
    public GridView gridView;
    int lastClick;

    boolean sceneSelected;

    @Override protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_hub);

        TextView textView = (TextView) findViewById(R.id.hubTitle);
        this.title = getIntent().getStringExtra("title");
        this.filename = getIntent().getStringExtra("filename");
        assert textView != null;
        textView.setText(title);

        int arraySize = 0;
        final String[] myStringArray = new String[arraySize];

        gridView = (GridView) findViewById(R.id.sceneList);
        sceneSelected = false;

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    toggleSceneSelect(position, view);
                    //toggleSceneSelect(position, view);

            }
        });
    }
    public void toggleAddSceneMenu (View view){
        Button b = (Button) findViewById(R.id.addSceneButton);
        RelativeLayout addSceneMenu = (RelativeLayout) findViewById(R.id.sceneEntryContainer);
        EditText sceneContent = (EditText) findViewById(R.id.editSceneText);

        //closing
        if(b.getVisibility() == View.GONE){
            InputMethodManager inputMethodManager =
                    (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInputFromWindow(
                    addSceneMenu.getApplicationWindowToken(),
                    InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            b.setVisibility(View.VISIBLE);
            addSceneMenu.setVisibility(View.GONE);
            sceneContent.setText("");

        }
        //opening
        else{
            b.setVisibility(View.GONE);
            addSceneMenu.setVisibility(View.VISIBLE);
            InputMethodManager inputMethodManager =
                    (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInputFromWindow(
                    addSceneMenu.getApplicationWindowToken(),
                    InputMethodManager.SHOW_IMPLICIT, 0);

            sceneContent.requestFocus();
        }
    }

    public void approveScene (View view){
        EditText sceneContent = (EditText) findViewById(R.id.editSceneText);

        String[] newStringArray = new String[arraySize + 1];
        if(arraySize > 0) {
            System.arraycopy(myStringArray, 0, newStringArray, 0, arraySize);
            myStringArray = newStringArray;
            arraySize += 1;
        }
        else{
            myStringArray = new String[1];
            arraySize = 1;
        }
        writeToFile(sceneContent.getText().toString());
        myStringArray[arraySize - 1] = sceneContent.getText().toString();
        myAdapter adapter2 = new myAdapter(getBaseContext(), myStringArray);
        gridView.setAdapter(adapter2);
        toggleAddSceneMenu(findViewById(R.id.sceneEntryContainer));
    }

    public void toggleSceneSelect(int position, View view){
        RelativeLayout editBar = (RelativeLayout) findViewById(R.id.sceneOptionBar);
        if(!sceneSelected){
            for(int i = 0; i < gridView.getChildCount(); ++i){
                View child = gridView.getChildAt(i);
                if(i != position)
                    child.setAlpha((float) 0.6);
            }
            view.setBackgroundResource(R.drawable.square_white);
            editBar.setVisibility(View.VISIBLE);
            sceneSelected = true;
        }
        else if(sceneSelected){
            if (position == lastClick) {
                for (int i = 0; i < gridView.getChildCount(); ++i) {
                    View child = gridView.getChildAt(i);
                    child.setAlpha(1);
                }
                editBar.setVisibility(View.GONE);
                sceneSelected = false;
            }
            else{
                for(int i = 0; i < gridView.getChildCount(); ++i){
                    View child = gridView.getChildAt(i);
                    if(i != position)
                        child.setAlpha((float) 0.6);
                    else
                        child.setAlpha(1);
                }
                view.setBackgroundResource(R.drawable.square_white);
            }
        }
        lastClick = position;
        return;
    }

    public void writeToFile(String s){
        String message = s + "\n\n";
        try {
            FileOutputStream fos = new FileOutputStream(filename, true);
            fos.write(message.getBytes());
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
