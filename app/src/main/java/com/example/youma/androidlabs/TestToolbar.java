package com.example.youma.androidlabs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {

    Toolbar lab8_toolbar;
    Button snackbar;
    String optionMessage = "You select option 1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        lab8_toolbar = findViewById(R.id.lab8_toolbar);
        setSupportActionBar(lab8_toolbar);
        snackbar = findViewById(R.id.snackBar);
        snackbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(findViewById(R.id.snackBar), "You clicked SnackBar button", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();

        switch (id) {
            case R.id.action_one:
                Log.d("Toolbar", "Option 1 selected");
                Snackbar.make(findViewById(R.id.action_one), optionMessage, Snackbar.LENGTH_LONG).show();
                break;
            case R.id.action_two:
                Log.d("Toolbar", "Option 2 selected");
                AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
                builder.setTitle(R.string.box);
// Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        // User clicked OK button
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
// Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
                break;

            case R.id.action_three:
                Log.d("Toolbar", "Option 3 selected");
                Dialog alertDialog = createDialog();
                alertDialog.show();
                break;

            case R.id.action_four:
                Log.d("Toolbar", "Option 4 selected");
                Toast toast = Toast.makeText(TestToolbar.this, "Version 1.0, by Ethan Nguyen", Toast.LENGTH_LONG);
                toast.show();
                break;
        }
        return true;
    }

    public Dialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View inflated = inflater.inflate(R.layout.custom_layout, null);
        builder.setView(inflated).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView text = inflated.findViewById(R.id.option3_input);
                if (text != null) {
                    optionMessage = text.getText().toString();
                }
            }
        });
        builder.setView(inflated).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
