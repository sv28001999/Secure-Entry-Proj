package com.sv.secureentry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.sv.secureentry.adapters.LoaderAdapter;
import com.sv.secureentry.models.ProjConstants;
import com.sv.secureentry.models.RegistrationReqBody;
import com.sv.secureentry.models.RegistrationResBody;
import com.sv.secureentry.models.SendOtpReqBody;
import com.sv.secureentry.models.SendOtpResBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddSocietyActivity extends AppCompatActivity {

    private EditText etSocietyName, etCity, etState, etStreet, etPinCode;
    private String fullName, mobileNumber, email, username, password;
    private ApiInterface apiInterface;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_society);

        ImageButton addSocietyBackBtn = findViewById(R.id.addSocietyBackBtn);
        Button submitSignUpBtn = findViewById(R.id.submitSignUpBtn);
        etSocietyName = findViewById(R.id.etSocietyName);
        etCity = findViewById(R.id.etCity);
        etStreet = findViewById(R.id.etStreet);
        etState = findViewById(R.id.etState);
        etPinCode = findViewById(R.id.etPinCode);

        mDialog = new ProgressDialog(this);

        fullName = getIntent().getStringExtra("fullName");
        mobileNumber = getIntent().getStringExtra("mobileNumber");
        email = getIntent().getStringExtra("email");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        Retrofit retrofit = RetrofitClient.getInstance();
        apiInterface = retrofit.create(ApiInterface.class);

        addSocietyBackBtn.setOnClickListener(v -> onBackPressed());

        submitSignUpBtn.setOnClickListener(v -> {
            Log.d("signUpBtn", "clicked");
            LoaderAdapter.startLoader(mDialog);
            String societyName = etSocietyName.getText().toString();
            String city = etCity.getText().toString();
            String street = etStreet.getText().toString();
            String state = etState.getText().toString();
            int pinCode = Integer.parseInt(etPinCode.getText().toString());

            if (!checkValidity(societyName, city, street, state, pinCode)) {
                Log.d("signUpBtn", "validation error");
                mDialog.dismiss();
                return;
            }

            RegistrationReqBody.SocietyAddress societyAddress = new RegistrationReqBody.SocietyAddress(state, city, street, societyName, pinCode);
            RegistrationReqBody registrationReqBody = new RegistrationReqBody(fullName, email, username, "SC", societyAddress, mobileNumber, password);

            Call<RegistrationResBody> call = apiInterface.createAccount(registrationReqBody);

            call.enqueue(new Callback<RegistrationResBody>() {
                @Override
                public void onResponse(@NonNull Call<RegistrationResBody> call, @NonNull Response<RegistrationResBody> response) {
                    RegistrationResBody registrationResBody = response.body();
                    if (response.isSuccessful()) {
                        Log.d("signUpBtn", "successful registration");
                        Toast.makeText(AddSocietyActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        sendOtp(email, registrationResBody.getData().roleType);
                    } else {
                        Log.d("signUpBtn", "unsuccessful registration");
                        mDialog.dismiss();
                        Toast.makeText(AddSocietyActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<RegistrationResBody> call, @NonNull Throwable t) {
                    mDialog.dismiss();
                    Log.d("signUpBtn", "api issue");
                    Toast.makeText(AddSocietyActivity.this, "API Failure", Toast.LENGTH_SHORT).show();
                }
            });
        });


    }

    private boolean checkValidity(String societyName, String city, String street, String state, int pinCode) {
        boolean validity = true;
        if (societyName.trim().isEmpty()) {
            etSocietyName.setError("Required");
            validity = false;
        }
        if (city.trim().isEmpty()) {
            etSocietyName.setError("Required");
            validity = false;
        }
        if (street.trim().isEmpty()) {
            etSocietyName.setError("Required");
            validity = false;
        }
        if (state.trim().isEmpty()) {
            etSocietyName.setError("Required");
            validity = false;
        }
        if (String.valueOf(pinCode).trim().isEmpty()) {
            etSocietyName.setError("Required");
            validity = false;
        }
        return validity;
    }

    private void sendOtp(String email, String roleType) {
        SendOtpReqBody sendOtpReqBody = new SendOtpReqBody(email);
        Call<SendOtpResBody> call = apiInterface.sendOtp(sendOtpReqBody);
        call.enqueue(new Callback<SendOtpResBody>() {
            @Override
            public void onResponse(@NonNull Call<SendOtpResBody> call, @NonNull Response<SendOtpResBody> response) {
                if (response.isSuccessful()) {
                    mDialog.dismiss();
                    Toast.makeText(AddSocietyActivity.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddSocietyActivity.this, VerifyOtpActivity.class);
                    intent.putExtra(ProjConstants.USER_EMAIL, email);
                    assert roleType != null;
                    intent.putExtra(ProjConstants.CLIENT_ROLE, roleType);
                    startActivity(intent);
                } else {
                    mDialog.dismiss();
                    Toast.makeText(AddSocietyActivity.this, "OTP send failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SendOtpResBody> call, @NonNull Throwable t) {
                mDialog.dismiss();
                Toast.makeText(AddSocietyActivity.this, "API Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}