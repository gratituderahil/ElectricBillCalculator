package com.example.electricbillcalculator;

import android.content.Intent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.electricbillcalculator.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnCalculator).setOnClickListener(v ->
                startActivity(new Intent(this, CalculatorActivity.class))
        );

        findViewById(R.id.btnAbout).setOnClickListener(v ->
                startActivity(new Intent(this, com.example.electricbillcalculator.AboutActivity.class))
        );
    }
}
