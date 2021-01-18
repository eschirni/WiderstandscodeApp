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

public class MainActivity extends AppCompatActivity {
    ImageView in_Ring1;
    ImageView in_Ring2;
    ImageView in_Ring3;
    ImageView in_Ring4;
    ImageView in_Ring5;
    String[] colortable = new String[12];
    String s_Ring1Col = "";
    String s_Ring2Col = "";
    String s_Ring3Col = "";
    String s_Ring4Col = "";
    String s_Ring5Col = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WertZuweisung();
        in_Ring1 = findViewById(R.id.iv_Ring1);
        in_Ring2 = findViewById(R.id.iv_Ring2);
        in_Ring3 = findViewById(R.id.iv_Ring3);
        in_Ring4 = findViewById(R.id.iv_Ring4);
        in_Ring5 = findViewById(R.id.iv_Ring5);
    }
    public void ChangeColor(View view){
        switch (view.getId()){
            case(R.id.iv_Ring1):
                s_Ring1Col = in_Ring1.getBackground().toString();
                in_Ring1.setImageResource(getResources().getIdentifier(s_Ring1Col, "drawable", getPackageName()));
                break;
            case(R.id.iv_Ring2):
                s_Ring2Col = in_Ring2.getBackground().toString();
                in_Ring2.setImageResource(getResources().getIdentifier(s_Ring1Col, "drawable", getPackageName()));
                break;
            case(R.id.iv_Ring3):
                s_Ring3Col = in_Ring3.getBackground().toString();
                in_Ring3.setImageResource(getResources().getIdentifier(s_Ring3Col, "drawable", getPackageName()));
                break;
            case(R.id.iv_Ring4):
                s_Ring4Col = in_Ring4.getBackground().toString();
                in_Ring4.setImageResource(getResources().getIdentifier(s_Ring4Col, "drawable", getPackageName()));
                break;
        }
    }
    public void SubmitRun(View view){
        //Variablen
        String[] colors = new String[5];
        String values = "";
        String multiplikator = "1";
        colors[0] = s_Ring1Col;
        colors[1] = s_Ring2Col;
        colors[2] = s_Ring3Col;
        colors[3] = s_Ring4Col;
        colors[4] = s_Ring5Col;
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