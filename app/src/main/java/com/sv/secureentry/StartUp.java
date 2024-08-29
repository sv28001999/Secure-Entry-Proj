package com.sv.secureentry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sv.secureentry.models.ProjConstants;

public class StartUp extends AppCompatActivity {

    private Button signInBtn, signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        signInBtn = findViewById(R.id.signInBtn);
        signUpBtn = findViewById(R.id.signUpBtn);

        SharedPreferences sharedPreferences = getSharedPreferences(ProjConstants.USER_DATA_SF, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String role = sharedPreferences.getString("roleType", "");

        if (token.length() > 0) {
            Intent intent = new Intent(this, SecretaryGuardHomeActivity.class);
            intent.putExtra(ProjConstants.CLIENT_ROLE, role);
            startActivity(intent);
        }

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartUp.this, LoginActivity.class));
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartUp.this, SignUpRoleActivity.class));
            }
        });
    }
}