package com.example.widerstandscode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    TextView pt_Input1;
    TextView pt_Input2;
    TextView pt_Input3;
    TextView pt_Input4;
    TextView pt_Input5;
    String[] colortable = {"black", "brown", "red", "orange", "yellow", "green", "blue", "violet", "grey", "white", "gold", "silver"};
    HashMap<String, Double> generalToleranz = new HashMap<String, Double>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pt_Input1 = findViewById(R.id.pt_Input);
        pt_Input2 = findViewById(R.id.pt_Input2);
        pt_Input3 = findViewById(R.id.pt_Input3);
        pt_Input4 = findViewById(R.id.pt_Input4);
        pt_Input5 = findViewById(R.id.pt_Input5);
        for (int i = 0; i < colortable.length; i++)
        {
            generalToleranz.put(colortable[i], 0.0);
        }
        generalToleranz.put("brown", 1.0);
        generalToleranz.put("red", 2.0);
        generalToleranz.put("green", 0.5);
        generalToleranz.put("blue", 0.25);
        generalToleranz.put("violet", 0.1);
        generalToleranz.put("gold", 5.0);
        generalToleranz.put("silver", 10.0);
    }
    public void SubmitRun(View view)
    {
        String[] colors = Values();
        double toleranz = Toleranz(colors);
    }
    private String[] Values()
    {
        String[] colors = new String[5];
        String values = "";
        String multiplikator = "1";
        colors[0] = pt_Input1.getText().toString();
        colors[1] = pt_Input2.getText().toString();
        colors[2] = pt_Input3.getText().toString();
        colors[3] = pt_Input4.getText().toString();
        colors[4] = pt_Input5.getText().toString();
        for(int i = 0; i < colors.length; i++)
        {
            for(int x = 0; x < this.colortable.length; x++)
            {
                if(colors[i].equals(colortable[x]) && x < 10) {
                    if(i<=2){
                        values += Integer.toString(x);
                        x = this.colortable.length;
                    }
                    if(i == 3){
                        for(int y = 0; y <x ; y++)
                            multiplikator += "0";
                    }
                }
            }
        }
        Log.d("Values Werte: ", values);
        Log.d("Multiplikator: ", multiplikator);
        return colors;
    }
    private double Toleranz(String[] colors){
        return generalToleranz.get(colors[4]);
    }
}