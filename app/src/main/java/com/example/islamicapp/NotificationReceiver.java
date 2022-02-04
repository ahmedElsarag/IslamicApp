package com.example.islamicapp;

import static com.example.islamicapp.ApplicationClass.ACTION_NEXT;
import static com.example.islamicapp.ApplicationClass.ACTION_PLAY;
import static com.example.islamicapp.ApplicationClass.ACTION_PREV;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context,AudioService.class);
        if (intent.getAction()!=null){

            switch (intent.getAction()){
                case ACTION_PLAY:
                    Toast.makeText(context,"play",Toast.LENGTH_LONG).show();
                    intent1.putExtra("myActionName",intent.getAction());
                    context.startService(intent1);
                    break;
                case ACTION_NEXT:
                    Toast.makeText(context,"next",Toast.LENGTH_LONG).show();
                    intent1.putExtra("myActionName",intent.getAction());
                    context.startService(intent1);
                    break;
                case ACTION_PREV:
                    Toast.makeText(context,"prev",Toast.LENGTH_LONG).show();
                    intent1.putExtra("myActionName",intent.getAction());
                    context.startService(intent1);
                    break;
            }
        }

    }
}
