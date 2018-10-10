package com.example.youma.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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

    ListView lv;
    Button sendBtn;
    EditText editText;
    ArrayList<String> message;

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


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = editText.getText().toString();
                message.add(data);
                messageAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });
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