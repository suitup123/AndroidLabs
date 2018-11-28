package com.example.youma.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MessageFragment extends Fragment {

    TextView textView1;
    TextView textView2;
    Button deleteFragmentBtn;
    String idString = "-10";
    String textString = "Test";
    boolean state = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            textString = savedInstanceState.getString("message", "Error");
            idString = (Double.toString(savedInstanceState.getDouble("id", -100)));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_layout, container, false);
        textView1 = view.findViewById(R.id.textView1);
        textView2 = view.findViewById(R.id.textView2);
        if (getArguments() != null) {
            textString = getArguments().getString("message", "Error");
            idString = (Double.toString(getArguments().getDouble("id", -100)));
            state = !getArguments().getBoolean("messageFragment", false);
        }
        textView1.setText(textString);
        textView2.setText(idString);
        deleteFragmentBtn = view.findViewById(R.id.deleteFragment);
        Log.i("testing", "Before button clicked " + state);
        deleteFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View t) {
                if (state) {
                    Log.i("DeleteFragment", "Delete Button Pressed from Fragment");
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("delete", getArguments().getDouble("id", -100));
                    getActivity().setResult(Activity.RESULT_OK, resultIntent);
                    getActivity().finish();
                }
                Log.i("DeleteFragment", "Delete Button Pressed");
            }
        });
        return view;
    }
}