package com.example.widerstandscode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView pt_Input1;
    TextView pt_Input2;
    TextView pt_Input3;
    TextView pt_Input4;
    TextView pt_Input5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pt_Input1 = findViewById(R.id.pt_Input);
        pt_Input2 = findViewById(R.id.pt_Input2);
        pt_Input3 = findViewById(R.id.pt_Input3);
        pt_Input4 = findViewById(R.id.pt_Input4);
        pt_Input5 = findViewById(R.id.pt_Input5);
    }
    void SubmitRun(View view){
        //Variablen
        String[] colors = new String[5];
        colors[0] = pt_Input1.getText().toString();
        colors[1] = pt_Input2.getText().toString();
        colors[2] = pt_Input3.getText().toString();
        colors[3] = pt_Input4.getText().toString();
        colors[4] = pt_Input5.getText().toString();
        //Algorithmus
        
    }
}