package com.example.widerstandscode;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView pt_Input1;
    TextView pt_Input2;
    TextView pt_Input3;
    TextView pt_Input4;
    TextView pt_Input5;
    ImageView iv_Ring;
    String[] colortable = new String[12];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WertZuweisung();
        /*pt_Input1 = findViewById(R.id.pt_Input);
        pt_Input2 = findViewById(R.id.pt_Input2);
        pt_Input3 = findViewById(R.id.pt_Input3);
        pt_Input4 = findViewById(R.id.pt_Input4);
        pt_Input5 = findViewById(R.id.pt_Input5);*/
    }
    public void SelectColor(View view){
    }
    public void SubmitRun(View view){
        //Variablen
        String[] colors = new String[5];
        String values = "";
        String multiplikator = "1";
        colors[0] = pt_Input1.getText().toString();
        colors[1] = pt_Input2.getText().toString();
        colors[2] = pt_Input3.getText().toString();
        colors[3] = pt_Input4.getText().toString();
        colors[4] = pt_Input5.getText().toString();
        //Algorithmus
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
    void WertZuweisung()
    {
        colortable[0] = "black";
        colortable[1] = "brown";
        colortable[2] = "red";
        colortable[3] = "orange";
        colortable[4] = "yellow";
        colortable[5] = "green";
        colortable[6] = "blue";
        colortable[7] = "violet";
        colortable[8] = "grey";
        colortable[9] = "white";
        colortable[10] = "gold";
        colortable[11] = "silver";
    }
}