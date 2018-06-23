package com.example.kurttito.weeklybudget;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

//import android.os.AsyncTask;
//import android.os.Build;

/**
 * Created by TITO on 6/4/2018.
 */

public class MyService extends IntentService{

    public static boolean ONPAUSE = false;
    public SharedPreferences sharedPref;
    public SharedPreferences.Editor editor;//private MainActivity mainActivity;

    public MyService() {
        super("MyServiceName");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        System.out.println("SERVICE RECEIVED");

        sharedPref = getSharedPreferences("userInput", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        String newTotalBudget = sharedPref.getString("totalBudget", "");
        String newTotalWeeks = sharedPref.getString("totalWeeks", "");
        String newWeeklyBudget = sharedPref.getString("weeklyBudget", "");

        float newTotalBudget_flt = Float.parseFloat(newTotalBudget);
        float newTotalWeeks_flt = Float.parseFloat(newTotalWeeks);
        float newWeeklyBudget_flt = Float.parseFloat(newWeeklyBudget);

        newTotalWeeks_flt--;
        newWeeklyBudget_flt = newTotalBudget_flt/newTotalWeeks_flt;

        editor.putString("totalBudget", String.format("%.2f", newTotalBudget_flt));
        editor.putString("totalWeeks", String.format("%.0f", newTotalWeeks_flt));
        editor.putString("weeklyBudget", String.format("%.2f", newWeeklyBudget_flt));
        editor.apply();

        intent = new Intent(getBaseContext(), MainActivity.class);

        if(ONPAUSE == false) {
            startActivity(intent);
        }
        else
        {
            editor.putBoolean("restart", true);
            editor.apply();
        }

    }

    /**
     ** AsyncTask can be used to update the UI thread in the background
     *
    @Override
    protected void onHandleIntent(@Nullable Intent intent){
        new MyTask().execute();
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            mainActivity.nextWeek();
            return null;
        }
    }
    *
    **/
}
