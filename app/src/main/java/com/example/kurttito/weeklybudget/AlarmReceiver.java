package com.example.kurttito.weeklybudget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by TITO on 6/3/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){

        System.out.println("ALARM RECEIVED");

        Intent weeklyUpdater = new Intent(context, MyService.class);
        context.startService(weeklyUpdater);

    }
}
