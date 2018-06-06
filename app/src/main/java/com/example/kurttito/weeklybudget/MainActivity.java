package com.example.kurttito.weeklybudget;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.*;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button zero;
    private Button one;
    private Button two;
    private Button three;
    private Button four;
    private Button five;
    private Button six;
    private Button seven;
    private Button eight;
    private Button nine;
    private Button clear;
    private Button enter;
    private Button decimal;

    //private Button next;

    private TextView InputFeed;

    private TextView TotalBudget_Feed;
    private TextView TotalWeeks_Feed;
    private TextView WeeklyBudget_Feed;

    String InputFeed_str;
    float spent;

    float totalBudget_flt;
    public static float weeklyBudget_float;
    public static float newtotal;

    public SharedPreferences sharedPref;
    public SharedPreferences.Editor editor;

    String totalWeeks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setupUIViews();

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputFeed.setText(InputFeed.getText().toString() + "0");
            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputFeed.setText(InputFeed.getText().toString() + "1");
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputFeed.setText(InputFeed.getText().toString() + "2");
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputFeed.setText(InputFeed.getText().toString() + "3");
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputFeed.setText(InputFeed.getText().toString() + "4");
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputFeed.setText(InputFeed.getText().toString() + "5");
            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputFeed.setText(InputFeed.getText().toString() + "6");
            }
        });

        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputFeed.setText(InputFeed.getText().toString() + "7");
            }
        });

        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputFeed.setText(InputFeed.getText().toString() + "8");
            }
        });

        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputFeed.setText(InputFeed.getText().toString() + "9");
            }
        });

        decimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputFeed.setText(InputFeed.getText().toString() + ".");
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputFeed.setText("");
            }
        });

        /**
         * Load SharedPref Data Here
         */

        sharedPref = getSharedPreferences("userInput", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        String totalBudget = sharedPref.getString("totalBudget", "");
        totalWeeks = sharedPref.getString("totalWeeks", "");

        //String weeklyBudget;

        if (sharedPref.getString("weeklyBudget", "") == "") {
            weeklyBudget_float = Float.parseFloat(totalBudget) / Float.parseFloat(totalWeeks); // WEEKLY BUDGET
            editor.putString("weeklyBudget", String.valueOf(weeklyBudget_float));
            editor.apply();
        } else {
            String weeklyBudget = sharedPref.getString("weeklyBudget", "");
            weeklyBudget_float = Float.parseFloat((weeklyBudget));
        }

        totalBudget_flt = Float.parseFloat(sharedPref.getString("totalBudget", ""));
        //Toast.makeText(MainActivity.this, "-" +String.format("%.2f", weeklyBudget_float), Toast.LENGTH_SHORT).show();

        TotalBudget_Feed.setText(String.format("$ %.2f", totalBudget_flt)); //how to get public variable of another activity
        TotalWeeks_Feed.setText(totalWeeks);

        WeeklyBudget_Feed.setText(String.format("$ %.2f", weeklyBudget_float));

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputFeed_str = InputFeed.getText().toString();

                try {

                    spent = Float.parseFloat(InputFeed_str);
                    newtotal = Spend(weeklyBudget_float, spent);
                    totalBudget_flt = Spend(totalBudget_flt, spent);

                    WeeklyBudget_Feed.setText(String.format("$ %.2f", newtotal));
                    TotalBudget_Feed.setText(String.format("$ %.2f", totalBudget_flt));

                    weeklyBudget_float = newtotal;


                    editor.putString("totalBudget", String.valueOf(totalBudget_flt));
                    editor.putString("weeklyBudget", String.valueOf(weeklyBudget_float));
                    editor.apply();

                    InputFeed.setText("");

                } catch (Exception e) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setTitle("Invalid Amount");
                    builder.setMessage("Please enter an amount. ");
                    builder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    builder.show();

                }
            }
        });

//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View w) {
//                nextWeek();
//            }
//        });

        Calendar calendar = Calendar.getInstance();
//        int day = calendar.get(Calendar.DAY_OF_WEEK);
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);

        calendar.set(Calendar.DAY_OF_WEEK, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

//        if(day == Calendar.SUNDAY && hour == 23 && minute == 59)
//        {
//            nextWeek();
//        }

        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24 * 7, pendingIntent);

        if(calendar.getTimeInMillis() >= System.currentTimeMillis())
        {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);

        }
        else if(calendar.getTimeInMillis() <= System.currentTimeMillis())
        {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, pendingIntent);

        }
        else
        {
            System.out.println("COULD NOT GET DATE/TIME ");
        }

        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void onBackPressed()
    {
        ActivityCompat.finishAffinity(this);
    }

//    @Override
//    public void onResume() {
//
//        super.onResume();
//
//        //finish();
//        //startActivity(new Intent(this, MainActivity.class));
//
//        TotalBudget_Feed.setText(String.format("$ %.2f", totalBudget_flt));
//        TotalWeeks_Feed.setText(totalWeeks);
//        WeeklyBudget_Feed.setText(String.format("$ %.2f", weeklyBudget_float));
//    }

//    @Override
//    public void onStart() {
//
//        super.onStart();
//
//        TotalBudget_Feed.setText(String.format("$ %.2f", totalBudget_flt));
//        TotalWeeks_Feed.setText(totalWeeks);
//        WeeklyBudget_Feed.setText(String.format("$ %.2f", weeklyBudget_float));
//
//        //startActivity(getIntent());
//    }

    @Override
    public void onPause(){
        System.out.println("ON PAUSE");
        super.onPause();

        ActivityCompat.finishAffinity(MainActivity.this);

//        TotalBudget_Feed.invalidate();
//        TotalWeeks_Feed.invalidate();
//        WeeklyBudget_Feed.invalidate();
    }
//
    @Override
    public void onResume(){
        super.onResume();

        //System.out.println("ON PAUSE");
    }

//    @Override
//    public void onStart(){
//        super.onStart();
//
//        TotalBudget_Feed.invalidate();
//        TotalWeeks_Feed.invalidate();
//        WeeklyBudget_Feed.invalidate();
//    }

    private void setupUIViews(){
        zero = (Button)findViewById(R.id.btn_0);
        one = (Button)findViewById(R.id.btn_1);
        two = (Button)findViewById(R.id.btn_2);
        three = (Button)findViewById(R.id.btn_3);
        four = (Button)findViewById(R.id.btn_4);
        five = (Button)findViewById(R.id.btn_5);
        six = (Button)findViewById(R.id.btn_6);
        seven = (Button)findViewById(R.id.btn_7);
        eight = (Button)findViewById(R.id.btn_8);
        nine = (Button)findViewById(R.id.btn_9);
        clear = (Button)findViewById(R.id.btn_clear);
        decimal = (Button)findViewById(R.id.btn_decimal);
        enter = (Button)findViewById(R.id.btn_enter);

        //next = (Button)findViewById(R.id.btn_next);

        InputFeed = (TextView) findViewById(R.id.TextView_InputFeed);
        TotalBudget_Feed = (TextView) findViewById(R.id.textView_totalBudget);
        TotalWeeks_Feed = (TextView)findViewById(R.id.textView_totalWeeks);
        WeeklyBudget_Feed = (TextView)findViewById(R.id.textView_weeklyBudget);
    }

    //Float Version of Spend Method
    private float Spend(float intotal, float insubamount){
        intotal = intotal - insubamount;
        return intotal;
    }

    /**
     * String Version of Spend Method
     * This will be more useful for this application
     *  because TextViews reads only strings and the combined
     *   use of SharedPreferences
     **/

    private String Str_Spend(String intotal, String insubamount){

        float total = Float.parseFloat(intotal);
        float subamount = Float.parseFloat(insubamount);
        total = total - subamount;

        return String.format("$ %.2f", total);
    }

    public void nextWeek()
    {
        String newTotalBudget = sharedPref.getString("totalBudget", "");
        String newTotalWeeks = sharedPref.getString("totalWeeks", "");
        String newWeeklyBudget = sharedPref.getString("weeklyBudget", "");

        float newTotalBudget_flt = Float.parseFloat(newTotalBudget);
        float newTotalWeeks_flt = Float.parseFloat(newTotalWeeks);
        float newWeeklyBudget_flt = Float.parseFloat(newWeeklyBudget);

        //Displays the remaining amount of the weekly spent to add to Total Budget
        //Toast.makeText(MainActivity.this, "+" +String.format("%.2f", newWeeklyBudget_flt), Toast.LENGTH_SHORT).show();

        /**
         * Fix math calculations
         * If you press nextWeek without spending, it adds on or subracts wrong amount
         */

        //newTotalBudget_flt = newWeeklyBudget_flt;
        newTotalWeeks_flt--;
        newWeeklyBudget_flt = newTotalBudget_flt/newTotalWeeks_flt;

        //float nextTotalBudget = newTotalBudget_flt - newWeeklyBudget_flt;

        editor.putString("totalBudget", String.format("%.2f", newTotalBudget_flt));
        editor.putString("totalWeeks", String.format("%.0f", newTotalWeeks_flt));
        editor.putString("weeklyBudget", String.format("%.2f", newWeeklyBudget_flt));
        editor.apply();

        recreate();

//        TotalBudget_Feed.setText(String.format("$ %.2f", newTotalBudget_flt));
//        TotalWeeks_Feed.setText(String.format("%.0f", newTotalWeeks_flt));
//        WeeklyBudget_Feed.setText(String.format("%.2f", newWeeklyBudget_flt));

//        finish();
//        startActivity(getIntent());
    }

    public class BackgroundService{
        public void nextWeek(){
            String newTotalBudget = sharedPref.getString("totalBudget", "");
            String newTotalWeeks = sharedPref.getString("totalWeeks", "");
            String newWeeklyBudget = sharedPref.getString("weeklyBudget", "");

            float newTotalBudget_flt = Float.parseFloat(newTotalBudget);
            float newTotalWeeks_flt = Float.parseFloat(newTotalWeeks);
            float newWeeklyBudget_flt = Float.parseFloat(newWeeklyBudget);

            //Displays the remaining amount of the weekly spent to add to Total Budget
            //Toast.makeText(MainActivity.this, "+" +String.format("%.2f", newWeeklyBudget_flt), Toast.LENGTH_SHORT).show();

            /**
             * Fix math calculations
             * If you press nextWeek without spending, it adds on or subracts wrong amount
             */

            //newTotalBudget_flt = newWeeklyBudget_flt;
            newTotalWeeks_flt--;
            newWeeklyBudget_flt = newTotalBudget_flt/newTotalWeeks_flt;

            //float nextTotalBudget = newTotalBudget_flt - newWeeklyBudget_flt;

            editor.putString("totalBudget", String.format("%.2f", newTotalBudget_flt));
            editor.putString("totalWeeks", String.format("%.0f", newTotalWeeks_flt));
            editor.putString("weeklyBudget", String.format("%.2f", newWeeklyBudget_flt));
            editor.apply();

            ActivityCompat.finishAffinity(MainActivity.this);
            //finish();
            //startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }

}