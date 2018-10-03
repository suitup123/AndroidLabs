package com.example.youma.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends Activity {

    protected static final String TAG3 = "ListItemsActivity";

    //ImageButton imgButton;
    //Switch switchButton;
    //CheckBox checkBox;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(TAG3, "In onCreate");
        ImageButton imgButton = (ImageButton)findViewById(R.id.imageButton);
        Switch switchButton = (Switch)findViewById(R.id.switch1);
        CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox);

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    CharSequence text = "Switch is On";
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(ListItemsActivity.this, text, duration).show();
                } else {
                    CharSequence text = "Switch is Off";
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(ListItemsActivity.this, text, duration).show();
                }
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.dialog_message) //Add a dialog message to strings.xml

                        .setTitle(R.string.dialog_title)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("Response", "Here is my response");
                                setResult(Activity.RESULT_OK, resultIntent);

                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageButton imgButton = (ImageButton)findViewById(R.id.imageButton);
            imgButton.setImageBitmap(imageBitmap);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(TAG3, "In onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(TAG3, "In onResume");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(TAG3, "In onPause");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(TAG3, "In onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG3, "In onDestroy");
    }
}
