package com.teenvan.newstartup.Activities;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

/**
 * Created by navneet on 07/02/16.
 */
public class BackgroundSubscribeIntentService extends IntentService {

    // Declaration of member variables
    private final String TAG = BackgroundSubscribeIntentService.class.getSimpleName();
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BackgroundSubscribeIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Nearby.Messages.handleIntent(intent, new MessageListener() {
            @Override
            public void onFound(Message message) {
                String content = new String(message.getContent());
                Log.i(TAG, "Found message via PendingIntent: " + message);
                Log.i(TAG, " Found Message Content " + content +
                        " of type " + message.getType());
                Log.i(TAG, " Found Message namespace" + message.getNamespace());
            }

            @Override
            public void onLost(Message message) {
                Log.i(TAG, "Lost message via PendingIntent: " + message);
            }
        });
    }
}
