package com.sv.secureentry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.sv.secureentry.models.ProjConstants;

public class SignUpRoleActivity extends AppCompatActivity {

    private ImageButton signUpRoleBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_role);

        signUpRoleBackBtn = findViewById(R.id.signUpRoleBackBtn);

        signUpRoleBackBtn.setOnClickListener(v -> onBackPressed());
    }

    public void startSecretarySignUp(View view) {
        Intent intent = new Intent(SignUpRoleActivity.this, SignUpActivity.class);
        intent.putExtra(ProjConstants.CLIENT_ROLE, ProjConstants.SECRETARY_ROLE);
        startActivity(intent);
    }

    public void startMemberSignUp(View view) {
        Intent intent = new Intent(SignUpRoleActivity.this, SignUpActivity.class);
        intent.putExtra(ProjConstants.CLIENT_ROLE, ProjConstants.MEMBER_ROLE);
        startActivity(intent);
    }

    public void startGuardSignUp(View view) {
        Intent intent = new Intent(SignUpRoleActivity.this, SignUpActivity.class);
        intent.putExtra(ProjConstants.CLIENT_ROLE, ProjConstants.GUARD_ROLE);
        startActivity(intent);
    }
}