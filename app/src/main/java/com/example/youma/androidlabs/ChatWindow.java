package com.example.youma.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    ListView chatView;
    Button sendBtn;
    EditText editText;
    static ArrayList<String> message = new ArrayList<>();
    ChatDataBaseHelper dbManagement;
    static ChatAdapter messageAdapter;
    boolean isTablet;
    static SQLiteDatabase database;
    static Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbManagement = new ChatDataBaseHelper(this);
        database = dbManagement.getWritableDatabase();
        cursor = database.rawQuery("SELECT " + ChatDataBaseHelper.KEY_MESSAGE + ", " + ChatDataBaseHelper.KEY_ID + " FROM " + ChatDataBaseHelper.getTableName(), new String[]{});
        cursor.moveToFirst();
        int column = cursor.getColumnIndex(ChatDataBaseHelper.KEY_MESSAGE);
        while (!cursor.isAfterLast()) {
            Log.i("ChatWindow", "SQL Message:" + cursor.getString(cursor.getColumnIndex(ChatDataBaseHelper.KEY_MESSAGE)));
            message.add(cursor.getString(column));
            cursor.moveToNext();
        }

        Log.i("ChatWindow", "Cursor's column count =" + cursor.getColumnCount());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        chatView = findViewById(R.id.chat_view);
        sendBtn = findViewById(R.id.sendButton);
        editText = findViewById(R.id.editChat);
        messageAdapter = new ChatAdapter(this);
        chatView.setAdapter(messageAdapter);
        final ContentValues cValues = new ContentValues();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempString = editText.getText().toString();
                message.add(tempString);
                messageAdapter.notifyDataSetChanged();
                editText.setText("");
                cValues.put(ChatDataBaseHelper.KEY_MESSAGE, tempString);
                database.insert(ChatDataBaseHelper.getTableName(), ChatDataBaseHelper.KEY_MESSAGE, cValues);
                cursor = database.rawQuery("SELECT " + ChatDataBaseHelper.KEY_MESSAGE + ", " + ChatDataBaseHelper.KEY_ID + " FROM " + ChatDataBaseHelper.getTableName(), new String[]{});
            }

        });
        isTablet = (findViewById(R.id.isTablet) != null);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManagement.close();
        database.close();
        cursor.close();
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return (message.size());
        }

        public String getItem(int position) {
            return message.get(position);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null;

            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }
            TextView message = result.findViewById(R.id.message_text);
            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isTablet) {
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        Fragment fragment = new MessageFragment();
                        Bundle data = new Bundle();
                        data.putString("message", getItem(position));
                        data.putDouble("id", getItemId(position));
                        data.putBoolean("isTablet", isTablet);
                        fragment.setArguments(data);
                        fragmentTransaction.add(R.id.isTablet, fragment);
                        fragmentTransaction.commit();
                    } else {
                        Intent intent = new Intent(ChatWindow.this, MessageDetails.class);
                        intent.putExtra("message", getItem(position));
                        intent.putExtra("id", Double.valueOf(getItemId(position)));
                        intent.putExtra("isTablet", isTablet);
                        startActivityForResult(intent, position);
                    }
                }
            });
            message.setText(getItem(position));
            return result;
        }

        public long getItemId(int position) {
            cursor.moveToPosition(position);
            long temp = cursor.getLong(cursor.getColumnIndex(ChatDataBaseHelper.KEY_ID));
            return temp;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String delete;
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            delete = String.valueOf(extras.getDouble("delete"));
            deleteMessage(delete);
        }
    }

    public static void deleteMessage(String delete){
        database.delete(ChatDataBaseHelper.getTableName(),ChatDataBaseHelper.KEY_ID+"=?", new String[]{delete});
        message = new ArrayList<>();
        cursor=database.rawQuery("SELECT "+ChatDataBaseHelper.KEY_MESSAGE+", "+ChatDataBaseHelper.KEY_ID+ " FROM "+ChatDataBaseHelper.getTableName(), new String[]{});
        cursor.moveToFirst();
        int column = cursor.getColumnIndex(ChatDataBaseHelper.KEY_MESSAGE);
        while(!cursor.isAfterLast()){
            Log.i("ChatWindow", "SQL Message:" + cursor.getString(cursor.getColumnIndex(ChatDataBaseHelper.KEY_MESSAGE)));
            message.add(cursor.getString(column));
            cursor.moveToNext();
        }
        messageAdapter.notifyDataSetChanged();
    }
}