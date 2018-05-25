package com.example.kurttito.weeklybudget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InitiateActivity extends AppCompatActivity {

    private Button next;
    public static EditText input_total_budget;
    public static EditText input_total_weeks;

    public static String totalBudget;
    public static String totalWeeks;

    public static float totalBudget_float, totalWeek_float, weeklyBudget_float;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate);

        SharedPreferences load = getSharedPreferences("userInput", Context.MODE_PRIVATE);
        totalBudget = load.getString("totalBudget", "");
        totalWeeks = load.getString("totalWeeks", "");

        setUP();

        if(totalBudget == "" || totalWeeks == "")
        {
            //startActivity(new Intent(InitiateActivity.this, MainActivity.class));
            Toast.makeText(InitiateActivity.this, "Data NOT found", Toast.LENGTH_LONG);
        }
        else{
            startActivity(new Intent(InitiateActivity.this, MainActivity.class));
        }

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                try {

                    totalBudget_float = Float.parseFloat(input_total_budget.getText().toString()); // TOTAL BUDGET
                    totalWeek_float = Float.parseFloat(input_total_weeks.getText().toString()); // TOTAL WEEKS
                    weeklyBudget_float = totalBudget_float/totalWeek_float;

                    /**
                     * Save and Write Preferences Here
                     */

                    SharedPreferences sharedPref = getSharedPreferences("userInput", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("totalBudget", input_total_budget.getText().toString());
                    editor.putString("totalWeeks", input_total_weeks.getText().toString());
                    editor.apply();

                    Toast.makeText(InitiateActivity.this, "-" +String.format("%.2f", weeklyBudget_float), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(InitiateActivity.this, MainActivity.class));

                }catch (Exception e){
                    AlertDialog.Builder builder = new AlertDialog.Builder(InitiateActivity.this);

                    builder.setTitle("Invalid Amount");
                    builder.setMessage("Please enter an amount. ");
                    builder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    builder.show();

//                    System.out.println(totalBudget +"& " +totalWeeks);
                }

            }
        });

    }

    public void setUP(){
        next = (Button)findViewById(R.id.btn_next);
        input_total_budget = (EditText) findViewById(R.id.editText_totalBudget);
        input_total_weeks = (EditText) findViewById(R.id.editText_totalWeeks);
    }
}
