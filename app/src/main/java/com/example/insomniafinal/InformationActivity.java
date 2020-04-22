package com.example.insomniafinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class InformationActivity extends AppCompatActivity {

    String newString;
    TextView informationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        informationText = findViewById(R.id.informationTextView);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {
                newString = extras.getString("g");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("g");
        }

        informationText.setText(newString);

    }
}
