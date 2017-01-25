package com.chronology;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by Jordan on 1/9/2017.
 */
public class NewProjectActivity extends AppCompatActivity {

    String suffix = ".chrn";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);
    }

    public void approveNew(View view){
        Intent intent = new Intent(this, ProjectHubActivity.class);
        EditText et = (EditText) findViewById(R.id.TitleField);

        String s = et.getText().toString();
        intent.putExtra("title", s);
        String filename = s.concat(this.suffix);

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        File file = new File(getApplicationContext().getFilesDir(), filename);

        if(s.isEmpty()) {
            CharSequence message = "Title Required";
            Toast.makeText(context, message, duration).show();
            return;
        }
        else if(file.exists()){
            CharSequence message = "File Already Exists";
            Toast.makeText(context, message, duration).show();
            return;
        }

        /*Create file in internal storage*/
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        intent.putExtra("filename", filename);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.newProjectContainer);

        InputMethodManager inputMethodManager =
                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                linearLayout.getApplicationWindowToken(),
                InputMethodManager.RESULT_HIDDEN, 0);

        startActivity(intent);
    }

    public void cancelNewProject(View view){
        Intent intent = new Intent(this, NewProjectActivity.class);
        startActivity(intent);
    }
}
