package com.chronology;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
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
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jordan on 1/9/2017.
 */
public class ProjectHubActivity extends AppCompatActivity {

    String title;
    String filename;
    File file;
    private int arraySize;
    public String[] sceneArray;
    public GridView gridView;
    int lastClick;
    boolean sceneSelected;

    @Override protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_hub);

        Intent intent = getIntent();
        this.title = intent.getStringExtra("title");
        this.filename = intent.getStringExtra("filename");
        this.file = new File(getFilesDir(), this.filename);
        this.arraySize = intent.getIntExtra("arraySize", 0);
        this.sceneArray = new String[arraySize];
        this.gridView = (GridView) findViewById(R.id.sceneList);
        this.sceneSelected = false;

        TextView textView = (TextView) findViewById(R.id.hubTitle);
        textView.setText(title);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    toggleSceneSelect(position, view);
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    checkScene(view, parent.getItemAtPosition(position).toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
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
        String scene = sceneContent.getText().toString();

        String[] newStringArray = new String[arraySize + 1];
        if(arraySize > 0) {
            System.arraycopy(sceneArray, 0, newStringArray, 0, arraySize);
            sceneArray = newStringArray;
            arraySize += 1;
        }
        else{
            sceneArray = new String[1];
            arraySize = 1;
        }
        writeToFile(scene);
        sceneArray[arraySize - 1] = scene;
        myAdapter adapter2 = new myAdapter(getApplicationContext(), sceneArray);
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
        String message = "<";
        message.concat(s.concat(">"));
        try {
            FileOutputStream fos = openFileOutput(filename, MODE_APPEND);
            byte[] b = message.getBytes();
            Toast.makeText(this, "bytes: ".concat(String.valueOf(b.length)), Toast.LENGTH_SHORT);
            fos.write(b);
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Write Failed", Toast.LENGTH_SHORT);

        }
    }

    public void checkScene(View view, String s) throws IOException {
        Intent intent = new Intent(this, CheckSceneActivity.class);

        FileInputStream input = new FileInputStream(file);
        int size = input.available();
        String a = "size: ";
        byte[] buffer = new byte[size];
        input.read(buffer);
        input.close();
        String text = new String(buffer);

        Toast.makeText(this, a.concat(String.valueOf(size)), Toast.LENGTH_SHORT).show();
        intent.putExtra("content", text);
        intent.putExtra("size", size);
        startActivity(intent);
    }
}
