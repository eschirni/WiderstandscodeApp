package com.example.widerstandscode;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    boolean[] b_selected = new boolean[5];
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
    TextView tv_Resistor;
    TextView tv_Tolerance;
    private String[] colortable = {"black", "brown", "red", "orange", "yellow", "green", "blue", "violet", "grey", "white", "gold", "silver"};
    private String[] colors;
    private List<String> colorlist = new ArrayList<String>();
    private HashMap<String, Double> generalToleranz = new HashMap<String, Double>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetLocale();
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
        tv_Resistor = findViewById(R.id.tv_Resistor);
        tv_Tolerance = findViewById(R.id.tv_Tolerance);
        for (int i = 0; i < 5; i++) {
            b_selected[i] = false;
        }
        Generate_Tolerance_Table();
    }
    public void Run_Calculation(View view){
        if(Valid() == true)
        {
            colors = colorlist.toArray(new String[colorlist.size()]);
            Resistor_Values_and_Multiplicator();
            if(colors.length > 3) {
                double toleranz = Get_Tolerance(colors, 4);
                String strToleranz = Double.toString(toleranz);
                strToleranz += " %";
                tv_Tolerance.setText(strToleranz);
            }
        }
    }
    private void Resistor_Values_and_Multiplicator(){
        String values = "";
        String multiplikator = "1";
        long longValue = 0;
        double doubleValue = 0;
        for(int i = 0; i < colors.length; i++)
        {
            for(int x = 0; x < this.colortable.length; x++)
            {
                if(colors[i].equals(colortable[x])) {
                    if(i<=2){
                        values += Integer.toString(x);
                        x = this.colortable.length;
                    }
                    if(i == 3){
                        if(colors[i].equals("white") || colors[i].equals("grey")){
                            multiplikator = "1";
                        }
                        else if(colors[i].equals("gold")){
                            multiplikator = "0.1";
                        }
                        else if(colors[i].equals("silver")){
                            multiplikator = "0.01";
                        }
                        else {
                            for (int y = 0; y < x; y++)
                                multiplikator += "0";
                        }
                    }
                }
            }
        }
        double doubleMulti = Double.parseDouble(multiplikator);
        if(doubleMulti < 1){
            doubleValue = doubleMulti * Double.parseDouble(values);
            BigDecimal bd = new BigDecimal(doubleValue).setScale(2, RoundingMode.HALF_EVEN);
            doubleValue = bd.doubleValue();
            values = Double.toString(doubleValue);
        }
        else{
            longValue = Long.parseLong(multiplikator) * Long.parseLong(values);
            values = Long.toString(longValue);
        }
        values += " Ω";
        tv_Resistor.setText(values);
    }
    private void Generate_Tolerance_Table(){
        for (int i = 0; i < colortable.length; i++)
        {
            generalToleranz.put(colortable[i], 0.0);
        }
        generalToleranz.put("brown", 1.0);
        generalToleranz.put("red", 2.0);
        generalToleranz.put("green", 0.5);
        generalToleranz.put("blue", 0.25);
        generalToleranz.put("violet", 0.1);
        generalToleranz.put("grey", 0.05);
        generalToleranz.put("gold", 5.0);
        generalToleranz.put("silver", 10.0);
    }
    private double Get_Tolerance(String[] colors, int pos){
        return generalToleranz.get(colors[pos]);
    }
    public void SelectColor(View view){
        String id = view.getTag().toString();
        int index = Integer.parseInt(Character.toString(id.charAt(id.length() - 1)));
        id = id.substring(0, id.length() - 1);
        int pos = (index - colorlist.size())+1;
        if(pos > 1){
            for(int i = colorlist.size()-1;i<pos-1;i++){
                colorlist.add("null");
            }
            colorlist.add(index, id);
        }
        else if(pos == 1 || colorlist.size() == 0){
            colorlist.add(index, id);
        }
        else{
            colorlist.set(index, id);
        }
        ChangeColor(index);
        b_selected[index] = true;
    }
    private void ChangeColor(int index){
        switch (index){
            case(0):
                in_Ring1.setImageResource(getResources().getIdentifier(colorlist.get(index) , "drawable", getPackageName()));
                findViewById(R.id.layoutRing1).setVisibility(View.INVISIBLE); //Layout wird wieder unsichtbar
                Change_Color_on_Resistor(index, tv_color); //Wählt die Farbe je nach der Liste aus
                break;
            case(1):
                in_Ring2.setImageResource(getResources().getIdentifier(colorlist.get(index) , "drawable", getPackageName()));
                findViewById(R.id.layoutRing2).setVisibility(View.INVISIBLE);
                Change_Color_on_Resistor(index, tv_color2);
                break;
            case(2):
                in_Ring3.setImageResource(getResources().getIdentifier(colorlist.get(index) , "drawable", getPackageName()));
                findViewById(R.id.layoutRing3).setVisibility(View.INVISIBLE);
                Change_Color_on_Resistor(index, tv_color3);
                break;
            case(3):
                in_Ring4.setImageResource(getResources().getIdentifier(colorlist.get(index) , "drawable", getPackageName()));
                findViewById(R.id.layoutRing4).setVisibility(View.INVISIBLE);
                Change_Color_on_Resistor(index, tv_color4);
                break;
            case(4):
                in_Ring5.setImageResource(getResources().getIdentifier(colorlist.get(index) , "drawable", getPackageName()));
                findViewById(R.id.layoutRing5).setVisibility(View.INVISIBLE);
                Change_Color_on_Resistor(index, tv_color5);
                break;
        }
        All_Elements_Visible();
    }
    private void Change_Color_on_Resistor(int index, TextView tv)
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
    public void Show_Color_Selection(View view) {
        String id = view.getTag().toString();
        int index = Integer.parseInt(id);
        All_Elements_Invisible();
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
    private void All_Elements_Invisible(){
        findViewById(R.id.btn_Submit).setVisibility(View.INVISIBLE);
        findViewById(R.id.btn_Cpy1).setVisibility(View.INVISIBLE);
        findViewById(R.id.btn_Cpy2).setVisibility(View.INVISIBLE);
        findViewById(R.id.btn_Reset).setVisibility(View.INVISIBLE);
        tv_Resistor.setVisibility(View.INVISIBLE);
        tv_Tolerance.setVisibility(View.INVISIBLE);
        findViewById(R.id.layoutRing1).setVisibility(View.INVISIBLE);
        findViewById(R.id.layoutRing2).setVisibility(View.INVISIBLE);
        findViewById(R.id.layoutRing3).setVisibility(View.INVISIBLE);
        findViewById(R.id.layoutRing4).setVisibility(View.INVISIBLE);
        findViewById(R.id.layoutRing5).setVisibility(View.INVISIBLE);
        findViewById(R.id.copyright).setVisibility(View.INVISIBLE);
    }
    private void All_Elements_Visible(){
        findViewById(R.id.btn_Submit).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_Cpy1).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_Cpy2).setVisibility(View.VISIBLE);
        tv_Resistor.setVisibility(View.VISIBLE);
        tv_Tolerance.setVisibility(View.VISIBLE);
        findViewById(R.id.btn_Reset).setVisibility(View.VISIBLE);
        findViewById(R.id.copyright).setVisibility(View.VISIBLE);
    }
    public void Reset_Strings_and_Colors(View view) {
        colorlist = new ArrayList<String>();
        tv_Resistor.setText(R.string.resistorDefault);
        tv_Tolerance.setText(R.string.toleranceDefault);
        in_Ring1.setImageResource(R.drawable.gruppe_4);
        in_Ring2.setImageResource(R.drawable.gruppe_4);
        in_Ring3.setImageResource(R.drawable.gruppe_4);
        in_Ring4.setImageResource(R.drawable.gruppe_4);
        in_Ring5.setImageResource(R.drawable.gruppe_4);
        tv_color.setBackgroundColor(Color.argb(0, 255, 255, 255));
        tv_color2.setBackgroundColor(Color.argb(0, 255, 255, 255));
        tv_color3.setBackgroundColor(Color.argb(0, 255, 255, 255));
        tv_color4.setBackgroundColor(Color.argb(0, 255, 255, 255));
        tv_color5.setBackgroundColor(Color.argb(0, 255, 255, 255));
        for (int i = 0; i < 5; i++)
            b_selected[i] = false;
    }
    private void SetLocale(){
        String language = Locale.getDefault().getDisplayLanguage();
        Locale sysLocale = new Locale(language);
        Locale.setDefault(sysLocale);
    }
    private boolean Valid() {
        if(b_selected[4] == true && b_selected[3] == true && (b_selected[1] == true || b_selected[2] == true || b_selected[3] == true)) //Überprüft ob Toleranz, Multiplier und midestens eine Farbe ausgewählt ist
            return true;
        else
            return false;
    }
}