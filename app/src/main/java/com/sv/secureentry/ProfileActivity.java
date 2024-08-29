package com.sv.secureentry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {

    private ImageButton profileBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileBackBtn = findViewById(R.id.profileBackBtn);

        profileBackBtn.setOnClickListener(v -> onBackPressed());
    }
}