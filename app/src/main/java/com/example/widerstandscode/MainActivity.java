package com.example.widerstandscode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    TextView tv_issues;
    boolean b_Visible = false;
    private String[] colortable = {"black", "brown", "red", "orange", "yellow", "green", "blue", "violet", "grey", "white", "gold", "silver"};
    private String[] colors;
    private List<String> colorlist = new ArrayList<String>();
    private HashMap<String, Double> generalTolerance = new HashMap<String, Double>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetLocale();    //Überprüft die Sprache des Android-Systems
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
        tv_issues = findViewById(R.id.tv_issue);
        for (int i = 0; i < 5; i++) {
            b_selected[i] = false;
        }
        GenerateToleranceTable(); //Erstellt ein "Hash Map" (Dictionary) mit Key, Value für die Toleranz
    }
    public void RunCalculation(View view){
        if(Valid() == true)
        {
            tv_issues.setText("");
            colors = colorlist.toArray(new String[colorlist.size()]);
            ResistorValuesMultiplicator();  //Rechnet Multiplikator, die Werte aus und gibt das Ergebnis aus
            if(colors.length > 3) {
                double tolerance = GetTolerance(colors, 4); //Ruft Toleranz von der Farbe aus der Liste ab
                String strTolerance = Double.toString(tolerance);
                strTolerance += " %";
                tv_Tolerance.setText(strTolerance);
            }
        }
        else {
            Issue();    //Gibt aus, dass nicht alle wichtigen Werte ausgewählt wurden
        }
    }
    private void ResistorValuesMultiplicator(){
        String values = "";
        String multiplikator = "1";
        long l_Value = 0;
        double d_Value = 0.0; //falls Multiplikator gold oder silverf
        for(int i = 0; i < colors.length; i++) //das ganze Array wird durchgegangen und mit colortable verglichen
        {
            for(int x = 0; x < this.colortable.length; x++)
            {
                if(colors[i].equals(colortable[x])) {
                    if(i<=2){
                        values += Integer.toString(x); //wenn übereinstimmung wird die Position an values angefügt
                        x = this.colortable.length; //Abbruchbedingung
                    }
                    if(i == 3){ //wenn Multiplikator/Toleranz
                        if(colors[i].equals("white") || colors[i].equals("grey")){
                            multiplikator = "1";
                        } //Sonderfälle gold und silver
                        else if(colors[i].equals("gold")){
                            multiplikator = "0.1";
                        }
                        else if(colors[i].equals("silver")){
                            multiplikator = "0.01";
                        }
                        else {
                            for (int y = 0; y < x; y++) //Die Anzahl der 0 entspricht der Position in colortable
                                multiplikator += "0";
                        }
                    }
                }
            }
        }
        double doubleMulti = Double.parseDouble(multiplikator);
        if(doubleMulti < 1){ //ob Multiplikator gold o. silver -> wenn ja brauchen wir den Datentyp Double
            d_Value = doubleMulti * Double.parseDouble(values);
            BigDecimal bd = new BigDecimal(d_Value).setScale(2, RoundingMode.HALF_EVEN);
            d_Value = bd.doubleValue();
            values = Double.toString(d_Value);
        }
        else{ //wenn nicht nehmen wir long, weil es in der Ausgabe schöner ausschaut und bei mir das Runden irgenwie nicht geklappt hat
            l_Value = Long.parseLong(multiplikator) * Long.parseLong(values);
            values = Long.toString(l_Value);
        }
        values += " Ω";
        tv_Resistor.setText(values);
    }
    private void GenerateToleranceTable(){
        for (int i = 0; i < colortable.length; i++)
        {
            generalTolerance.put(colortable[i], 0.0);
        }
        generalTolerance.put("brown", 1.0);
        generalTolerance.put("red", 2.0);
        generalTolerance.put("green", 0.5);
        generalTolerance.put("blue", 0.25);
        generalTolerance.put("violet", 0.1);
        generalTolerance.put("grey", 0.05);
        generalTolerance.put("gold", 5.0);
        generalTolerance.put("silver", 10.0);
    }
    private double GetTolerance(String[] colors, int pos){
        return generalTolerance.get(colors[pos]);
    }
    public void SelectColor(View view){
        String id = view.getTag().toString(); //die ID hat das Format: farbe0
        int index = Integer.parseInt(Character.toString(id.charAt(id.length() - 1))); //das letzte Zeichen ist eine Nummer welche das Band angibt
        id = id.substring(0, id.length() - 1); //die nummer wird entfernt
        int pos = (index - colorlist.size())+1; //an welcher Position die Farbe gesetzt wird
        if(pos > 1){
            for(int i = colorlist.size()-1;i<index-1;i++){ // wenn groesser 1 sind die dazwischen liegenden Farben nicht ausgewählt, sie werden im Array mit "null" befüllt
                colorlist.add("null");
            }
            colorlist.add(index, id);
        }
        else if(pos == 1 || colorlist.size() == 0){
            colorlist.add(index, id); //wenn an dieser Position keine Farbe ausgewählt wurde
        }
        else{
            colorlist.set(index, id); //wenn an dieser Position eine Farbe ausgewählt wurde, dann wird sie ersetzt.
        }
        ChangeColor(index); //die Farben in der Anzeige werden geändert
        b_selected[index] = true;
    }
    private void ChangeColor(int index){
        switch (index){
            case(0):
                in_Ring1.setImageResource(getResources().getIdentifier(colorlist.get(index) , "drawable", getPackageName()));
                findViewById(R.id.lyt_Colors1).setVisibility(View.INVISIBLE); //Layout wird wieder unsichtbar
                findViewById(R.id.sv_Color1).setVisibility(View.INVISIBLE);
                ChangeColorResistor(index, tv_color); //Ändert die Farbe auf dem Resistor
                break;
            case(1):
                in_Ring2.setImageResource(getResources().getIdentifier(colorlist.get(index) , "drawable", getPackageName()));
                findViewById(R.id.lyt_Colors2).setVisibility(View.INVISIBLE);
                findViewById(R.id.sv_Color2).setVisibility(View.INVISIBLE);
                ChangeColorResistor(index, tv_color2);
                break;
            case(2):
                in_Ring3.setImageResource(getResources().getIdentifier(colorlist.get(index) , "drawable", getPackageName()));
                findViewById(R.id.lyt_Colors3).setVisibility(View.INVISIBLE);
                findViewById(R.id.sv_Color3).setVisibility(View.INVISIBLE);
                ChangeColorResistor(index, tv_color3);
                break;
            case(3):
                in_Ring4.setImageResource(getResources().getIdentifier(colorlist.get(index) , "drawable", getPackageName()));
                findViewById(R.id.lyt_Colors4).setVisibility(View.INVISIBLE);
                findViewById(R.id.sv_Color4).setVisibility(View.INVISIBLE);
                ChangeColorResistor(index, tv_color4);
                break;
            case(4):
                in_Ring5.setImageResource(getResources().getIdentifier(colorlist.get(index) , "drawable", getPackageName()));
                findViewById(R.id.lyt_Colors5).setVisibility(View.INVISIBLE);
                findViewById(R.id.sv_Color5).setVisibility(View.INVISIBLE);
                ChangeColorResistor(index, tv_color5);
                break;
        }
        AllElementsVisible();
    }
    private void ChangeColorResistor(int index, TextView tv)
    {
        switch (colorlist.get(index))   //Überprüft die Farbe am übergebenen Index und passt sie am Resistor an
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
    public void ShowColorSelection(View view) {
        String id = view.getTag().toString();
        int index = Integer.parseInt(id);
        AllElementsInvisible(); //die Ergebnisausgabe, Buttons, usw. werden unsichtbar
        View colorLayout = GetColorSelectionLayout(index); //Layout und ScrollView werden anhand von der ID ermittelt.
        View scroll = GetScrollView(index);
        if(this.b_Visible){ //Versteckt die Farben wenn man auf den Knopf drückt
            colorLayout.setVisibility(View.INVISIBLE);
            scroll.setVisibility(View.INVISIBLE);
            AllElementsVisible();
        }
        else{   //Zeigt die Farben wenn man auf den Knopf drückt
            colorLayout.setVisibility(View.VISIBLE);
            scroll.setVisibility(View.VISIBLE);
            this.b_Visible = true;
        }
    }
    private View GetColorSelectionLayout(int index){
        switch(index){
            case 1:
                return findViewById(R.id.lyt_Colors1);
            case 2:
                return findViewById(R.id.lyt_Colors2);
            case 3:
                return findViewById(R.id.lyt_Colors3);
            case 4:
                return findViewById(R.id.lyt_Colors4);
            case 5:
                return findViewById(R.id.lyt_Colors5);
        }
        return null;
    }
    private View GetScrollView(int index){
        switch(index){
            case 1:
                return findViewById(R.id.sv_Color1);
            case 2:
                return findViewById(R.id.sv_Color2);
            case 3:
                return findViewById(R.id.sv_Color3);
            case 4:
                return findViewById(R.id.sv_Color4);
            case 5:
                return findViewById(R.id.sv_Color5);
        }
        return null;
    }
    private void AllElementsInvisible(){
        findViewById(R.id.btn_Submit).setVisibility(View.INVISIBLE);
        findViewById(R.id.btn_Cpy1).setVisibility(View.INVISIBLE);
        findViewById(R.id.btn_Cpy2).setVisibility(View.INVISIBLE);
        findViewById(R.id.btn_Reset).setVisibility(View.INVISIBLE);
        tv_Resistor.setVisibility(View.INVISIBLE);
        tv_Tolerance.setVisibility(View.INVISIBLE);
        findViewById(R.id.lyt_Colors1).setVisibility(View.INVISIBLE);
        findViewById(R.id.lyt_Colors2).setVisibility(View.INVISIBLE);
        findViewById(R.id.lyt_Colors3).setVisibility(View.INVISIBLE);
        findViewById(R.id.lyt_Colors4).setVisibility(View.INVISIBLE);
        findViewById(R.id.lyt_Colors5).setVisibility(View.INVISIBLE);
        findViewById(R.id.tv_Copyright).setVisibility(View.INVISIBLE);
        findViewById(R.id.tv_issue).setVisibility(View.INVISIBLE);
    }
    private void AllElementsVisible(){
        findViewById(R.id.btn_Submit).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_Cpy1).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_Cpy2).setVisibility(View.VISIBLE);
        tv_Resistor.setVisibility(View.VISIBLE);
        tv_Tolerance.setVisibility(View.VISIBLE);
        findViewById(R.id.btn_Reset).setVisibility(View.VISIBLE);
        findViewById(R.id.tv_Copyright).setVisibility(View.VISIBLE);
        findViewById(R.id.tv_issue).setVisibility(View.VISIBLE);
        this.b_Visible = false;
    }
    public void ResetStringsAndColors(View view) {  //Setzt alle wichtigen Variablen und visuellen Elemente zurück
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
        if(b_selected[4] == true && b_selected[3] == true && (b_selected[0] == true || b_selected[1] == true || b_selected[2] == true)) //Überprüft ob Toleranz, Multiplier und midestens eine Farbe ausgewählt sind
            return true;
        else
            return false;
    }

    public void CopyToClipboard(View view) {
        ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("Zero", "Zero");
        switch (view.getTag().toString()){
            case "res":
                data = ClipData.newPlainText("Resistor", tv_Resistor.getText());
                break;
            case "tol":
                data = ClipData.newPlainText("Tolerance", tv_Tolerance.getText());
                break;
        }
        clipboard.setPrimaryClip(data);
        Toast.makeText(getApplicationContext(), R.string.copiedMsg, Toast.LENGTH_SHORT).show();
    }
    public void Issue() {
        tv_issues.setText(R.string.issue);
    }
}