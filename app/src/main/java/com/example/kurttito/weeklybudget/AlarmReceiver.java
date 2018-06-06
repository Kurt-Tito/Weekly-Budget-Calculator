package com.example.kurttito.weeklybudget;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by TITO on 6/3/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {

    public SharedPreferences sharedPref;
    public SharedPreferences.Editor editor;

    private MainActivity.BackgroundService bService;

    @Override
    public void onReceive(Context context, Intent intent){

        System.out.println("ALARM RECEIVED");

        Intent weeklyUpdater = new Intent(context, MyService.class);
        context.startService(weeklyUpdater);

    }
}
