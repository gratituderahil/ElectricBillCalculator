package com.example.electricbillcalculator;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CalculatorActivity extends AppCompatActivity {
    Spinner spinnerMonth;
    EditText editUnits, editRebate;
    TextView textTotalCharges, textFinalCost;
    ListView listView;
    Button btnCalculate;

    MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        spinnerMonth = findViewById(R.id.spinnerMonth);
        editUnits = findViewById(R.id.editUnits);
        editRebate = findViewById(R.id.editRebate);
        textTotalCharges = findViewById(R.id.textTotalCharges);
        textFinalCost = findViewById(R.id.textFinalCost);
        listView = findViewById(R.id.listView);
        btnCalculate = findViewById(R.id.btnCalculate);

        dbHelper = new MyDatabaseHelper(this);

        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December"};
        spinnerMonth.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, months));

        btnCalculate.setOnClickListener(v -> {
            String month = spinnerMonth.getSelectedItem().toString();
            int units = Integer.parseInt(editUnits.getText().toString());
            float rebate = Float.parseFloat(editRebate.getText().toString());

            float total = calculateCharges(units);
            float finalCost = total - (total * rebate / 100);

            textTotalCharges.setText("Total Charges: RM " + String.format("%.2f", total));
            textFinalCost.setText("Final Cost: RM " + String.format("%.2f", finalCost));

            dbHelper.insertRecord(month, units, rebate, total, finalCost);
            loadList();
        });

        loadList();
    }

    private float calculateCharges(int units) {
        float charges = 0;
        if (units <= 200) charges = units * 0.218f;
        else if (units <= 300) charges = (200 * 0.218f) + ((units - 200) * 0.334f);
        else if (units <= 600) charges = (200 * 0.218f) + (100 * 0.334f) + ((units - 300) * 0.516f);
        else charges = (200 * 0.218f) + (100 * 0.334f) + (300 * 0.516f) + ((units - 600) * 0.546f);
        return charges;
    }

    private void loadList() {
        Cursor cursor = dbHelper.getAllRecords();
        ArrayList<String> data = new ArrayList<>();
        while (cursor.moveToNext()) {
            String month = cursor.getString(1);
            float finalCost = cursor.getFloat(5);
            data.add(month + ": RM " + String.format("%.2f", finalCost));
        }
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data));
    }
}
