package com.sv.secureentry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sv.secureentry.adapters.LoaderAdapter;
import com.sv.secureentry.models.LoginReqBody;
import com.sv.secureentry.models.LoginResBody;
import com.sv.secureentry.models.ProjConstants;
import com.sv.secureentry.models.SendOtpReqBody;
import com.sv.secureentry.models.SendOtpResBody;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private EditText etLoginEmail, etLoginPassword;
    private ApiInterface apiInterface;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageButton loginBackBtn = findViewById(R.id.loginBackBtn);
        TextView navigateToForgetPassword = findViewById(R.id.navigateToForgetPassword);
        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        Button loginBtn = findViewById(R.id.loginBtn);

        Retrofit retrofit = RetrofitClient.getInstance();
        apiInterface = retrofit.create(ApiInterface.class);

        mDialog = new ProgressDialog(this);

        loginBackBtn.setOnClickListener(v -> onBackPressed());

        navigateToForgetPassword.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class)));

        loginBtn.setOnClickListener(v -> {
            LoaderAdapter.startLoader(mDialog);
            String email = etLoginEmail.getText().toString();
            String password = etLoginPassword.getText().toString();

            if (checkValidation(email, password)) {
                LoginReqBody loginReqBody = new LoginReqBody(email, password);

                Call<LoginResBody> call = apiInterface.loginUser(loginReqBody);

                call.enqueue(new Callback<LoginResBody>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginResBody> call, @NonNull Response<LoginResBody> response) {
                        LoginResBody loginResBody = response.body();
                        if (response.isSuccessful() && response.code() == 200 && Objects.requireNonNull(loginResBody).isSuccess) {
                            if (!loginResBody.getData().isActive) {
                                sendOtp(loginResBody.getData().getEmail());
                            } else {
                                mDialog.dismiss();
                                SharedPreferences sharedPreferences = getSharedPreferences(ProjConstants.USER_DATA_SF, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putString("email", loginResBody.getData().getEmail());
                                editor.putString("username", loginResBody.getData().getUsername());
//                                editor.putBoolean("isActive", loginResBody.getData().isActive());
                                editor.putString("roleType", loginResBody.getData().getRoleType());
                                editor.putString("orgUniqueCode", loginResBody.getData().getOrgUniqueCode());
                                editor.putString("token", loginResBody.getData().getToken());

                                editor.apply();

                                Log.d("userData",loginResBody.getData().getEmail());
                                Log.d("userData",loginResBody.getData().getUsername());
                                Log.d("userData",loginResBody.getData().getRoleType());
                                Log.d("userData",loginResBody.getData().getOrgUniqueCode());
                                Log.d("userData",loginResBody.getData().getToken());
                                if (Objects.equals(loginResBody.getData().getRoleType(), ProjConstants.MEMBER_ROLE)) {
                                    startActivity(new Intent(LoginActivity.this, MemberHomeActivity.class));
                                } else if (Objects.equals(loginResBody.getData().getRoleType(), ProjConstants.SECRETARY_ROLE)) {
                                    Intent intent = new Intent(LoginActivity.this, SecretaryGuardHomeActivity.class);
                                    intent.putExtra(ProjConstants.CLIENT_ROLE, ProjConstants.SECRETARY_ROLE);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, SecretaryGuardHomeActivity.class);
                                    intent.putExtra(ProjConstants.CLIENT_ROLE, ProjConstants.GUARD_ROLE);
                                    startActivity(intent);
                                }
                            }
                        } else {
                            mDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<LoginResBody> call, @NonNull Throwable t) {
                        mDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "API Failed", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                mDialog.dismiss();
            }
        });
    }

    private boolean checkValidation(String email, String password) {
        boolean validation = true;
        if (email.trim().isEmpty()) {
            etLoginEmail.setError("Required");
            validation = false;
        }
        if (password.trim().isEmpty()) {
            etLoginPassword.setError("Required");
            validation = false;
        }
        return validation;
    }

    public void backToRoleSelectionPage(View view) {
        startActivity(new Intent(LoginActivity.this, SignUpRoleActivity.class));
    }

    private void sendOtp(String email) {
        SendOtpReqBody sendOtpReqBody = new SendOtpReqBody(email);
        Call<SendOtpResBody> call = apiInterface.sendOtp(sendOtpReqBody);
        call.enqueue(new Callback<SendOtpResBody>() {
            @Override
            public void onResponse(@NonNull Call<SendOtpResBody> call, @NonNull Response<SendOtpResBody> response) {
                if (response.isSuccessful()) {
                    mDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, VerifyOtpActivity.class);
                    intent.putExtra(ProjConstants.USER_EMAIL, email);
                    startActivity(intent);
                } else {
                    mDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "OTP send failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SendOtpResBody> call, @NonNull Throwable t) {
                mDialog.dismiss();
                Toast.makeText(LoginActivity.this, "API Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}