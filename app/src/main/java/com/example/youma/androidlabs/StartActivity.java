package com.example.youma.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {

    protected static final String TAG1 = "StartActivity";

    //Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button btn = (Button)findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, 50);
            }
        });

        Button btn2 = (Button)findViewById(R.id.chat_button);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(StartActivity.this, ChatWindow.class);
                startActivity(intent);
                Log.i(TAG1, "User clicked Start Chat");
            }
        });
        Log.i(TAG1, "In onCreate()");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 50){
            String messagePassed = data.getStringExtra("Response")+" ListItemsActivity passed: My information to share";
            Log.i(TAG1, "Returned to StartActivity.onActivityResult");
            Toast.makeText(this, messagePassed, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(TAG1, "In onStart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(TAG1, "In onResume()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(TAG1, "In onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(TAG1, "In onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG1, "In onDestroy()");
    }
}
