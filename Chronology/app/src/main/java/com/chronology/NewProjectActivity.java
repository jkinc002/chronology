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

/**
 * Created by Jordan on 1/9/2017.
 */
public class NewProjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);
    }

    public void approveNew(View view){
        Intent intent = new Intent(this, ProjectHubActivity.class);
        EditText et = (EditText) findViewById(R.id.TitleField);
        String s = et.getText().toString();
        if(s.isEmpty()) {
            Context context = getApplicationContext();
            CharSequence message = "Title Required";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, message, duration).show();
            return;
        }
        intent.putExtra("title", s);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.newProjectContainer);

        InputMethodManager inputMethodManager =
                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                linearLayout.getApplicationWindowToken(),
                InputMethodManager.RESULT_HIDDEN, 0);

        startActivity(intent);
    }

    public void cancelNewProject(View view){
    }
}
