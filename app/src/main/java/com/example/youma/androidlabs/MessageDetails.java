package com.example.youma.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MessageDetails extends Activity {

    //Running on the phone.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
//Passing the Bundle MessageFragment will take care the rest
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new MessageFragment();
        Bundle data = new Bundle();
        data.putString("message", getIntent().getStringExtra("message"));
        data.putDouble("id", getIntent().getDoubleExtra("id", -100));
        data.putBoolean("messageFragment",getIntent().getBooleanExtra("messageFragment", true));
        fragment.setArguments(data);
        fragmentTransaction.add(R.id.frameHolder,fragment);
        fragmentTransaction.commit();
    }
}