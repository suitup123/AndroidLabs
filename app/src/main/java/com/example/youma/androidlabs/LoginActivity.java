package com.example.youma.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    protected static final String TAG2 = "LoginActivity";

    SharedPreferences sharePref;
    private Button loginButton;
    private EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Reference login button
        loginButton = findViewById(R.id.button2);
        //Reference emailEditText
        emailEditText = findViewById(R.id.email);
        //Initial or get SharedPreferences
        sharePref = getSharedPreferences("User_Data",MODE_PRIVATE);
        emailEditText.setText(sharePref.getString("DefaultEmail", "email@domain.com"));

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String string = emailEditText.getText().toString();
                SharedPreferences.Editor editor = sharePref.edit();
                editor.putString("DefaultEmail", string);
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
                }
            });
        Log.i(TAG2, "In onCreate");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(TAG2, "In onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(TAG2, "In onResume");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(TAG2, "In onPause");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(TAG2, "In onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG2, "In onDestroy");
    }
}
