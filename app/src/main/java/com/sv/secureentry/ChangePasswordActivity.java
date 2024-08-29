package com.sv.secureentry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sv.secureentry.models.ProjConstants;
import com.sv.secureentry.models.UpdatePassReqBody;
import com.sv.secureentry.models.UpdatePassResBody;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.leo.simplearcloader.SimpleArcLoader;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText newPassword, confirmPassword;
    private Button updatePasswordBtn;
    private String email;
    private ApiInterface apiInterface;
    private SimpleArcDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        newPassword = findViewById(R.id.etUpdateNewPassword);
        confirmPassword = findViewById(R.id.etUpdateConfirmPassword);
        updatePasswordBtn = findViewById(R.id.updatePasswordBtn);

        mDialog = new SimpleArcDialog(this);
        ArcConfiguration configuration = new ArcConfiguration(this);
        configuration.setLoaderStyle(SimpleArcLoader.STYLE.SIMPLE_ARC);
        configuration.setColors(new int[]{Color.parseColor("#D8533FD3")});
        configuration.setText("Please wait..");
        mDialog.setConfiguration(configuration);
        mDialog.setCancelable(false);

        Retrofit retrofit = RetrofitClient.getInstance();
        apiInterface = retrofit.create(ApiInterface.class);

        email = getIntent().getStringExtra(ProjConstants.USER_EMAIL);

        updatePasswordBtn.setOnClickListener(v -> {
            mDialog.show();
            String password = newPassword.getText().toString();
            String cPassword = confirmPassword.getText().toString();
            if (!checkValidation(password, cPassword)) {
                mDialog.cancel();
                return;
            }
            updatePassword(email, password);
        });
    }

    private void updatePassword(String email, String password) {
        UpdatePassReqBody updatePassReqBody = new UpdatePassReqBody(email, password);
        Call<UpdatePassResBody> call = apiInterface.updatePassword(updatePassReqBody);

        call.enqueue(new Callback<UpdatePassResBody>() {
            @Override
            public void onResponse(@NonNull Call<UpdatePassResBody> call, @NonNull Response<UpdatePassResBody> response) {
                UpdatePassResBody updatePassResBody = response.body();
                if (response.isSuccessful() && Objects.requireNonNull(updatePassResBody).isSuccess) {
                    mDialog.cancel();
                    Toast.makeText(ChangePasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                    if (Objects.equals(getIntent().getStringExtra(ProjConstants.CLIENT_ROLE), ProjConstants.MEMBER_ROLE)) {
                        startActivity(new Intent(ChangePasswordActivity.this, MemberHomeActivity.class));
                    } else if (Objects.equals(getIntent().getStringExtra(ProjConstants.CLIENT_ROLE), ProjConstants.SECRETARY_ROLE)) {
                        Intent intent = new Intent(ChangePasswordActivity.this, SecretaryGuardHomeActivity.class);
                        intent.putExtra(ProjConstants.CLIENT_ROLE, ProjConstants.SECRETARY_ROLE);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(ChangePasswordActivity.this, SecretaryGuardHomeActivity.class);
                        intent.putExtra(ProjConstants.CLIENT_ROLE, ProjConstants.GUARD_ROLE);
                        startActivity(intent);
                    }
                } else {
                    mDialog.cancel();
                    Toast.makeText(ChangePasswordActivity.this, "Password update failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdatePassResBody> call, @NonNull Throwable t) {
                mDialog.cancel();
                Toast.makeText(ChangePasswordActivity.this, "API Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkValidation(String password, String cPassword) {
        boolean validation = true;
        if (password.trim().isEmpty()) {
            newPassword.setError("Required");
            validation = false;
        }
        if (cPassword.trim().isEmpty()) {
            confirmPassword.setError("Required");
            validation = false;
        }
        if (!password.equals(cPassword)) {
            confirmPassword.setError("Password does not match");
            validation = false;
        }
        return validation;
    }

    public void goBack(View view) {
        onBackPressed();
    }
}