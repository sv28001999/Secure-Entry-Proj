package com.sv.secureentry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sv.secureentry.adapters.LoaderAdapter;
import com.sv.secureentry.models.GetSocietyInfoReqBody;
import com.sv.secureentry.models.GetSocietyInfoResBody;
import com.sv.secureentry.models.ProjConstants;
import com.sv.secureentry.models.RegistrationReqBody;
import com.sv.secureentry.models.RegistrationResBody;
import com.sv.secureentry.models.SendOtpReqBody;
import com.sv.secureentry.models.SendOtpResBody;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity {

    private String role;
    private EditText etFullName, etMobileNumber, etEmail, etSocietySecretCode, etUsername, etPassword, etConfirmPassword;
    private ApiInterface apiInterface;
    private ProgressDialog mDialog;
    private String fullName, mobileNumber, email, societySecretCode, username, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etFullName = findViewById(R.id.etFullName);
        etMobileNumber = findViewById(R.id.etMobileNum);
        etEmail = findViewById(R.id.etEmail);
        etSocietySecretCode = findViewById(R.id.etSocietySecretCode);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        Button continueSignUpBtn = findViewById(R.id.continueSignUpBtn);
        LinearLayout secretCodeLayout = findViewById(R.id.secretCodeLayout);

        Retrofit retrofit = RetrofitClient.getInstance();
        apiInterface = retrofit.create(ApiInterface.class);

        mDialog = new ProgressDialog(this);


        Intent intent = getIntent();
        role = intent.getStringExtra(ProjConstants.CLIENT_ROLE);
        Log.d("sign_up_role", role);
        if (Objects.equals(role, ProjConstants.SECRETARY_ROLE)) {
            secretCodeLayout.setVisibility(View.GONE);
        }

        continueSignUpBtn.setOnClickListener(v -> {
            ProjConstants.isForgetPasswordNav = false;

            fullName = etFullName.getText().toString();
            mobileNumber = etMobileNumber.getText().toString();
            email = etEmail.getText().toString();
            username = etUsername.getText().toString();
            societySecretCode = etSocietySecretCode.getText().toString();
            password = etPassword.getText().toString();
            confirmPassword = etConfirmPassword.getText().toString();

            if (checkValidation(fullName, mobileNumber, email, username, societySecretCode, password, confirmPassword)) {
                Log.d("isValidation", "called");
                return;
            }

            if (Objects.equals(role, ProjConstants.SECRETARY_ROLE)) {
                Intent navigateWithDetailIntent = new Intent(SignUpActivity.this, AddSocietyActivity.class);
                navigateWithDetailIntent.putExtra("fullName", fullName);
                navigateWithDetailIntent.putExtra("mobileNumber", mobileNumber);
                navigateWithDetailIntent.putExtra("email", email);
                navigateWithDetailIntent.putExtra("username", username);
                navigateWithDetailIntent.putExtra("password", password);
                startActivity(navigateWithDetailIntent);
            } else {
                LoaderAdapter.startLoader(mDialog);
                if (checkValidation(fullName, mobileNumber, email, username, societySecretCode, password, confirmPassword)) {
                    mDialog.dismiss();
                    Log.d("isValidation", "called");
                    return;
                }
                validateSecretCode(societySecretCode);
            }
        });
    }

    private void signUp(String fullName, String mobileNumber, String email, String username, String societySecretCode, String password, String confirmPassword, GetSocietyInfoResBody societyInfoResBody) {
        RegistrationReqBody.SocietyAddress societyAddress = new RegistrationReqBody.SocietyAddress(societyInfoResBody.data.societyAddress.state, societyInfoResBody.data.societyAddress.city, societyInfoResBody.data.societyAddress.street, societyInfoResBody.data.societyAddress.societyName, societyInfoResBody.data.societyAddress.pinCode);
        RegistrationReqBody registrationReqBody = new RegistrationReqBody(fullName, email, username, role, societyAddress, mobileNumber, password, societySecretCode);

        Call<RegistrationResBody> call = apiInterface.createAccount(registrationReqBody);

        call.enqueue(new Callback<RegistrationResBody>() {
            @Override
            public void onResponse(@NonNull Call<RegistrationResBody> call, @NonNull Response<RegistrationResBody> response) {
                RegistrationResBody registrationResBody = response.body();
                if (response.isSuccessful() && response.code() == 200) {
                    Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    sendOtp(email, role);
                } else {
                    mDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegistrationResBody> call, @NonNull Throwable t) {
                mDialog.dismiss();
                Toast.makeText(SignUpActivity.this, "API Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validateSecretCode(String secretaryCode) {
        GetSocietyInfoReqBody getSocietyInfoReqBody = new GetSocietyInfoReqBody(secretaryCode);
        Call<GetSocietyInfoResBody> call = apiInterface.getSocietyInfo(getSocietyInfoReqBody);

        call.enqueue(new Callback<GetSocietyInfoResBody>() {
            @Override
            public void onResponse(@NonNull Call<GetSocietyInfoResBody> call, @NonNull Response<GetSocietyInfoResBody> response) {
                GetSocietyInfoResBody getSocietyInfoResBody = response.body();
                if (response.isSuccessful() && Objects.requireNonNull(getSocietyInfoResBody).isSuccess) {
                    signUp(fullName, mobileNumber, email, username, secretaryCode, password, confirmPassword, getSocietyInfoResBody);
                } else {
                    etSocietySecretCode.setError("Invalid code");
                    Toast.makeText(SignUpActivity.this, "Invalid code found", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GetSocietyInfoResBody> call, Throwable t) {
                mDialog.dismiss();
                Toast.makeText(SignUpActivity.this, "API failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendOtp(String email, String roleType) {
        SendOtpReqBody sendOtpReqBody = new SendOtpReqBody(email);
        Call<SendOtpResBody> call = apiInterface.sendOtp(sendOtpReqBody);
        call.enqueue(new Callback<SendOtpResBody>() {
            @Override
            public void onResponse(@NonNull Call<SendOtpResBody> call, @NonNull Response<SendOtpResBody> response) {
                if (response.isSuccessful()) {
                    mDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                    Log.d("sign_up_role", role);
                    Intent intent = new Intent(SignUpActivity.this, VerifyOtpActivity.class);
                    intent.putExtra(ProjConstants.USER_EMAIL, email);
                    assert roleType != null;
                    intent.putExtra(ProjConstants.CLIENT_ROLE, roleType);
                    startActivity(intent);
                } else {
                    mDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "OTP send failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SendOtpResBody> call, @NonNull Throwable t) {
                mDialog.dismiss();
                Toast.makeText(SignUpActivity.this, "API Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkValidation(String fullName, String mobileNumber, String email, String username, String secretaryCode, String password, String confirmPassword) {
        boolean validation = true;
        if (fullName.trim().isEmpty()) {
            etFullName.setError("Required");
            validation = false;
        }

        if (mobileNumber.trim().isEmpty()) {
            etMobileNumber.setError("Required");
            validation = false;
        }

        if (email.trim().isEmpty()) {
            etEmail.setError("Required");
            validation = false;
        }

        if (username.trim().isEmpty()) {
            etUsername.setError("Required");
            validation = false;
        }

        if (secretaryCode.trim().isEmpty() && !Objects.equals(role, ProjConstants.SECRETARY_ROLE)) {
            etSocietySecretCode.setError("Required");
            validation = false;
        }

        if (password.trim().isEmpty()) {
            etPassword.setError("Required");
            validation = false;
        }

        if (confirmPassword.trim().isEmpty()) {
            etConfirmPassword.setError("Required");
            validation = false;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Password does not match");
            validation = false;
        }
        return !validation;
    }

    public void goBack(View view) {
        onBackPressed();
    }

    public void backToLoginPage(View view) {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mDialog.dismiss();
    }
}