package com.example.youma.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
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

import static com.example.youma.androidlabs.ChatDataBaseHelper.TABLE_NAME;
import static com.example.youma.androidlabs.LoginActivity.TAG2;

public class ChatWindow extends Activity {

    ListView lv;
    Button sendBtn;
    EditText editText;
    ArrayList<String> message;
    ChatDataBaseHelper dbManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        ListView lv = (ListView)findViewById(R.id.chat_view);
        Button sendBtn = (Button)findViewById(R.id.sendButton);
        final EditText editText = (EditText)findViewById(R.id.editChat);
        message = new ArrayList<>();

        //in this case, “this” is the ChatWindow, which is-A Context object
        final ChatAdapter messageAdapter = new ChatAdapter(this);
        lv.setAdapter(messageAdapter);


        dbManagement = new ChatDataBaseHelper(this);

        final SQLiteDatabase db = dbManagement.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery,null);

        while(cursor.moveToNext()){
            String newMessage = cursor.getString(cursor.getColumnIndex(dbManagement.KEY_MESSAGE));
            message.add(newMessage);
            Log.i(TAG2, "SQL_MESSAGE " + newMessage);
        }

        //while(!cursor.isAfterLast()) {
          //  log.i(ACTIVITY_NAME, "SQL_MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDataBaseHelper.KEY_MESSAGE)));
        //}

        for (int columnIndex = 0; columnIndex < cursor.getColumnCount(); columnIndex++){
            cursor.getColumnName(columnIndex);
            Log.i(TAG2, "Cursor column count = " +cursor.getColumnCount());
        }

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = editText.getText().toString();
                message.add(data);
                messageAdapter.notifyDataSetChanged();
                ContentValues contentValues = new ContentValues();
                contentValues.put(dbManagement.KEY_MESSAGE,editText.getText().toString());

                long insertCheck = db.insert(dbManagement.TABLE_NAME,null,contentValues);
                editText.setText("");
            }
        });
    }

        @Override
        protected void onDestroy(){
        super.onDestroy();
        dbManagement.close();
        }

        private class ChatAdapter extends ArrayAdapter<String>{

            public ChatAdapter(Context ctx){
                super(ctx, 0);
            }

            @Override
            public int getCount(){
                return message.size();
            }

            @Override
            public String getItem(int position){
                return message.get(position);
            }

            @Override
            public long getItemId(int position){
                return super.getItemId(position);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
                View result = null ;
                if(position%2 == 0)
                    result = inflater.inflate(R.layout.chat_row_incoming, null);
                else
                    result = inflater.inflate(R.layout.chat_row_outgoing, null);
                TextView message = (TextView)result.findViewById(R.id.message_text);
                message.setText(   getItem(position)  ); // get the string at position
                return result;
            }
        }
    }