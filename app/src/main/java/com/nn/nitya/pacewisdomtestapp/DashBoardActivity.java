package com.nn.nitya.pacewisdomtestapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DashBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        Button kycButton = findViewById(R.id.button_kyc);
        kycButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent startMainActivityIntent = new Intent(DashBoardActivity.this,MainActivity.class);
                startActivity(startMainActivityIntent);
            }
        });
    }
}
