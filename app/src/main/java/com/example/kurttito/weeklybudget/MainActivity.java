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
import android.widget.TextView;
import android.view.*;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button zero, one, two, three, four, five,
            six, seven, eight, nine, clear, enter, decimal;

    private TextView InputFeed;
    private TextView TotalBudget_Feed;
    private TextView TotalWeeks_Feed;
    private TextView WeeklyBudget_Feed;

    private String InputFeed_str;
    private String totalWeeks;

    private float spent;
    private float totalBudget_flt;
    private float weeklyBudget_float;
    private float newtotal;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

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

        if (sharedPref.getString("weeklyBudget", "") == "") {
            weeklyBudget_float = Float.parseFloat(totalBudget) / Float.parseFloat(totalWeeks); // WEEKLY BUDGET
            editor.putString("weeklyBudget", String.valueOf(weeklyBudget_float));
            editor.apply();
        } else {
            String weeklyBudget = sharedPref.getString("weeklyBudget", "");
            weeklyBudget_float = Float.parseFloat((weeklyBudget));
        }

        totalBudget_flt = Float.parseFloat(sharedPref.getString("totalBudget", ""));

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

        boolean initApp = sharedPref.getBoolean("InitApplication", false);

        if(!initApp){
            setAlarm();

            editor.putBoolean("InitApplication", true);
            editor.apply();
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        MyService.ONPAUSE = true;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        MyService.ONPAUSE = false;

        boolean restart = sharedPref.getBoolean("restart", false);

        if(restart == true)
        {
            startActivity(getIntent());
        }

        editor.putBoolean("restart", false);
        editor.apply();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

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

        InputFeed = (TextView) findViewById(R.id.TextView_InputFeed);
        TotalBudget_Feed = (TextView) findViewById(R.id.textView_totalBudget);
        TotalWeeks_Feed = (TextView)findViewById(R.id.textView_totalWeeks);
        WeeklyBudget_Feed = (TextView)findViewById(R.id.textView_weeklyBudget);

        //next = (Button)findViewById(R.id.btn_next);
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

    public void setAlarm()
    {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if(calendar.getTimeInMillis() >= System.currentTimeMillis())
        {
            System.out.println("NEXT WEEK");
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);

        }
        else if(calendar.getTimeInMillis() < System.currentTimeMillis())
        {
            calendar.add(Calendar.DAY_OF_WEEK ,7);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, pendingIntent);

        }
        else
        {
            System.out.println("COULD NOT GET DATE/TIME ");
        }
    }

}