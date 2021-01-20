package com.example.widerstandscode;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ImageView in_Ring1;
    ImageView in_Ring2;
    ImageView in_Ring3;
    ImageView in_Ring4;
    ImageView in_Ring5;
    private String[] colortable = {"black", "brown", "red", "orange", "yellow", "green", "blue", "violet", "grey", "white", "gold", "silver"};
    private String[] colors = new String[5];
    private HashMap<String, Double> generalToleranz = new HashMap<String, Double>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        in_Ring1 = findViewById(R.id.iv_Ring1);
        in_Ring2 = findViewById(R.id.iv_Ring2);
        in_Ring3 = findViewById(R.id.iv_Ring3);
        in_Ring4 = findViewById(R.id.iv_Ring4);
        in_Ring5 = findViewById(R.id.iv_Ring5);
        ToleranzTable();
    }
    public void SelectColor(View view){
        String id = view.getTag().toString();
        int index = Integer.parseInt(Character.toString(id.charAt(id.length() - 1)));
        id = id.substring(0, id.length() - 1);
        colors[index]=id;
        Log.d("Color Index", colors[index]);
        ChangeColor(index);
    }
    private void ChangeColor(int index){
        switch (index){
            case(0):
                in_Ring1.setImageResource(getResources().getIdentifier(colors[index] , "drawable", getPackageName()));
                break;
            case(1):
                in_Ring2.setImageResource(getResources().getIdentifier(colors[index] , "drawable", getPackageName()));
                break;
            case(2):
                in_Ring3.setImageResource(getResources().getIdentifier(colors[index] , "drawable", getPackageName()));
                break;
            case(3):
                in_Ring4.setImageResource(getResources().getIdentifier(colors[index] , "drawable", getPackageName()));
                break;
            case(4):
                in_Ring5.setImageResource(getResources().getIdentifier(colors[index] , "drawable", getPackageName()));
                break;
        }
    }
    public void SubmitRun(View view){
        colors = Values();//macht void mehr sinn?
        double toleranz = Toleranz(colors);
        Log.d("Toleranz: ", Double.toString(toleranz));
    }
    private String[] Values(){
        String values = "";
        String multiplikator = "1";
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
    private void ToleranzTable(){
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
}