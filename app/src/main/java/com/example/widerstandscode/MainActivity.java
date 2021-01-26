package com.example.widerstandscode;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageView in_Ring1;
    ImageView in_Ring2;
    ImageView in_Ring3;
    ImageView in_Ring4;
    ImageView in_Ring5;
    TextView tv_color;
    TextView tv_color2;
    TextView tv_color3;
    TextView tv_color4;
    TextView tv_color5;
    private String[] colortable = {"black", "brown", "red", "orange", "yellow", "green", "blue", "violet", "grey", "white", "gold", "silver"};
    private String[] colors;
    private List<String> colorlist = new ArrayList<String>();
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
        tv_color = findViewById(R.id.tv_color);
        tv_color2 = findViewById(R.id.tv_color2);
        tv_color3 = findViewById(R.id.tv_color3);
        tv_color4 = findViewById(R.id.tv_color4);
        tv_color5 = findViewById(R.id.tv_color5);
        ToleranzTable();
    }
    public void SelectColor(View view){
        String id = view.getTag().toString();
        int index = Integer.parseInt(Character.toString(id.charAt(id.length() - 1)));
        id = id.substring(0, id.length() - 1);
        if(index >= colorlist.size()) {
            colorlist.add(index , id);
        }
        else{
            colorlist.set(index, id);
        }
        ChangeColor(index);
    }
    public void ColorSelection(View view) {
        String id = view.getTag().toString();
        int index = Integer.parseInt(id);
        //die Layouts sind durchnummeriert von 1-5, der switch-case prüft im welchen Layout die Farbe ausgewählt wurde
        //um die richtige Farbe zu wechseln
        switch(index){
            case 1:
                findViewById(R.id.layoutRing1).setVisibility(View.VISIBLE);
                break;
            case 2:
                findViewById(R.id.layoutRing2).setVisibility(View.VISIBLE);
                break;
            case 3:
                findViewById(R.id.layoutRing3).setVisibility(View.VISIBLE);
                break;
            case 4:
                findViewById(R.id.layoutRing4).setVisibility(View.VISIBLE);
                break;
            case 5:
                findViewById(R.id.layoutRing5).setVisibility(View.VISIBLE);
                break;
        }
    }
    private void ChangeColor(int index){
        switch (index){
            case(0):
                in_Ring1.setImageResource(getResources().getIdentifier(colorlist.get(index) , "drawable", getPackageName()));
                findViewById(R.id.layoutRing1).setVisibility(View.INVISIBLE); //Layout wird wieder unsichtbar
                ColorResistor(index, tv_color); //Wählt die Farbe je nach der Liste aus
                break;
            case(1):
                in_Ring2.setImageResource(getResources().getIdentifier(colorlist.get(index) , "drawable", getPackageName()));
                findViewById(R.id.layoutRing2).setVisibility(View.INVISIBLE);
                ColorResistor(index, tv_color2);
                break;
            case(2):
                in_Ring3.setImageResource(getResources().getIdentifier(colorlist.get(index) , "drawable", getPackageName()));
                findViewById(R.id.layoutRing3).setVisibility(View.INVISIBLE);
                ColorResistor(index, tv_color3);
                break;
            case(3):
                in_Ring4.setImageResource(getResources().getIdentifier(colorlist.get(index) , "drawable", getPackageName()));
                findViewById(R.id.layoutRing4).setVisibility(View.INVISIBLE);
                ColorResistor(index, tv_color4);
                break;
            case(4):
                in_Ring5.setImageResource(getResources().getIdentifier(colorlist.get(index) , "drawable", getPackageName()));
                findViewById(R.id.layoutRing5).setVisibility(View.INVISIBLE);
                ColorResistor(index, tv_color5);
                break;
        }
    }
    public void SubmitRun(View view){
        colors = colorlist.toArray(new String[colorlist.size()]);
        Values();//macht void mehr sinn?
        if(colors.length > 3) {
            double toleranz = Toleranz(colors, 4);
            Log.d("Toleranz: ", Double.toString(toleranz));
        }
    }
    private void Values(){
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
    }
    private double Toleranz(String[] colors, int pos){
        return generalToleranz.get(colors[pos]);
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
    private void ColorResistor(int index, TextView tv)
    {
        switch (colorlist.get(index))
        {
            case "black":
                tv.setBackgroundColor(Color.rgb(0, 0, 0));
                break;
            case "brown":
                tv.setBackgroundColor(Color.rgb(196, 106, 60));
                break;
            case "red":
                tv.setBackgroundColor(Color.rgb(243, 65, 65));
                break;
            case "orange":
                tv.setBackgroundColor(Color.rgb(255, 172, 7));
                break;
            case "yellow":
                tv.setBackgroundColor(Color.rgb(241, 226, 18));
                break;
            case "green":
                tv.setBackgroundColor(Color.rgb(45, 189, 40));
                break;
            case "blue":
                tv.setBackgroundColor(Color.rgb(72, 194, 232));
                break;
            case "violet":
                tv.setBackgroundColor(Color.rgb(203, 51, 209));
                break;
            case "grey":
                tv.setBackgroundColor(Color.rgb(138, 135, 130));
                break;
            case "white":
                tv.setBackgroundColor(Color.rgb(255, 255, 255));
                break;
            case "gold":
                tv.setBackgroundColor(Color.rgb(222, 148, 1));
                break;
            case "silver":
                tv.setBackgroundColor(Color.rgb(200, 199, 197));
                break;
        }
    }


}