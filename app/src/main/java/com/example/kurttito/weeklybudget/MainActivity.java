package com.example.kurttito.weeklybudget;
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

    private Button next;

    private TextView InputFeed;

    private TextView TotalBudget_Feed;
    private TextView TotalWeeks_Feed;
    private TextView WeeklyBudget_Feed;

    String InputFeed_str;
    float spent;

    public static float weeklyBudget_float;
    public static float newtotal;

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

        zero.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                InputFeed.setText(InputFeed.getText().toString() + "0");
            }
        });

        one.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                InputFeed.setText(InputFeed.getText().toString() + "1");
            }
        });

        two.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                InputFeed.setText(InputFeed.getText().toString() + "2");
            }
        });

        three.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                InputFeed.setText(InputFeed.getText().toString() + "3");
            }
        });

        four.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                InputFeed.setText(InputFeed.getText().toString() + "4");
            }
        });

        five.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                InputFeed.setText(InputFeed.getText().toString() + "5");
            }
        });

        six.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                InputFeed.setText(InputFeed.getText().toString() + "6");
            }
        });

        seven.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                InputFeed.setText(InputFeed.getText().toString() + "7");
            }
        });

        eight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                InputFeed.setText(InputFeed.getText().toString() + "8");
            }
        });

        nine.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                InputFeed.setText(InputFeed.getText().toString() + "9");
            }
        });

        decimal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                InputFeed.setText(InputFeed.getText().toString() + ".");
            }
        });

        clear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                InputFeed.setText("");
            }
        });

        /**
         * Load SharedPref Data Here
         */

        sharedPref = getSharedPreferences("userInput", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        String totalBudget = sharedPref.getString("totalBudget", "");
        String totalWeeks = sharedPref.getString("totalWeeks", "");

        //String weeklyBudget;

        if(sharedPref.getString("weeklyBudget", "") == "")
        {
            weeklyBudget_float = Float.parseFloat(totalBudget) / Float.parseFloat(totalWeeks); // WEEKLY BUDGET
            editor.putString("weeklyBudget", String.valueOf(weeklyBudget_float));
            editor.apply();
        }
        else
        {
            String weeklyBudget = sharedPref.getString("weeklyBudget", "");
            weeklyBudget_float = Float.parseFloat((weeklyBudget));
        }

        float totalBudget_flt = Float.parseFloat(sharedPref.getString("totalBudget", "")) - weeklyBudget_float;
        Toast.makeText(MainActivity.this, "-" +String.format("%.2f", weeklyBudget_float), Toast.LENGTH_SHORT).show();

        TotalBudget_Feed.setText(String.format("$ %.2f", totalBudget_flt)); //how to get public variable of another activity
        TotalWeeks_Feed.setText(totalWeeks);

        WeeklyBudget_Feed.setText(String.format("$ %.2f", weeklyBudget_float));

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputFeed_str = InputFeed.getText().toString();

                try{

                    spent = Float.parseFloat(InputFeed_str);
                    newtotal = Spend(weeklyBudget_float, spent);
                    WeeklyBudget_Feed.setText(String.format("$ %.2f", newtotal));
                    weeklyBudget_float = newtotal;

                    editor.putString("weeklyBudget", String.valueOf(weeklyBudget_float));
                    editor.apply();

                    InputFeed.setText("");

                }catch (Exception e){

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

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {
                nextWeek();
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        ActivityCompat.finishAffinity(this);
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

        next = (Button)findViewById(R.id.btn_next);

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

    private void nextWeek()
    {
        String newTotalBudget = sharedPref.getString("totalBudget", "");
        String newTotalWeeks = sharedPref.getString("totalWeeks", "");
        String newWeeklyBudget = sharedPref.getString("weeklyBudget", "");

        float newTotalBudget_flt = Float.parseFloat(newTotalBudget);
        float newTotalWeeks_flt = Float.parseFloat(newTotalWeeks);
        float newWeeklyBudget_flt = Float.parseFloat(newWeeklyBudget);

        /**
         * Fix math calculations
         * If you press nextWeek without spending, it adds on or subracts wrong amount
         */

        newTotalBudget_flt += newWeeklyBudget_flt;
        newTotalWeeks_flt--;
        //newWeeklyBudget_flt = newTotalBudget_flt/newTotalWeeks_flt;

        float nextTotalBudget = newTotalBudget_flt - newWeeklyBudget_flt;
        //Toast.makeText(MainActivity.this, "+" +String.format("%.2f", newWeeklyBudget_flt), Toast.LENGTH_SHORT).show();

        editor.putString("totalBudget", String.format("%.2f", nextTotalBudget));
        editor.putString("totalWeeks", String.format("%.0f", newTotalWeeks_flt));
        editor.putString("weeklyBudget", String.format("%.2f", newWeeklyBudget_flt));
        editor.apply();

        finish();
        startActivity(getIntent());
    }

}