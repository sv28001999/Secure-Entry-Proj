package com.sv.secureentry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sv.secureentry.adapters.LoaderAdapter;
import com.sv.secureentry.models.ProjConstants;
import com.sv.secureentry.models.SendOtpReqBody;
import com.sv.secureentry.models.SendOtpResBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ForgetPasswordActivity extends AppCompatActivity {

    private Button sendForgetPasswordOtp;
    private EditText etForgotPassEmail;
    private ApiInterface apiInterface;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        sendForgetPasswordOtp = findViewById(R.id.sendForgetPasswordOtp);
        etForgotPassEmail = findViewById(R.id.etForgotPassEmail);

        Retrofit retrofit = RetrofitClient.getInstance();
        apiInterface = retrofit.create(ApiInterface.class);

        mDialog = new ProgressDialog(this);

        sendForgetPasswordOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaderAdapter.startLoader(mDialog);

                if (!checkValidation(etForgotPassEmail.getText().toString())) {
                    mDialog.dismiss();
                    return;
                }

                sendOtp(etForgotPassEmail.getText().toString());
            }
        });
    }

    private void sendOtp(String email) {
        SendOtpReqBody sendOtpReqBody = new SendOtpReqBody(email);
        Call<SendOtpResBody> call = apiInterface.sendOtp(sendOtpReqBody);
        call.enqueue(new Callback<SendOtpResBody>() {
            @Override
            public void onResponse(@NonNull Call<SendOtpResBody> call, @NonNull Response<SendOtpResBody> response) {
                if (response.isSuccessful()) {
                    mDialog.dismiss();
                    Toast.makeText(ForgetPasswordActivity.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                    ProjConstants.isForgetPasswordNav = true;
                    Intent intent = new Intent(ForgetPasswordActivity.this, VerifyOtpActivity.class);
                    intent.putExtra("userEmail", email);
                    startActivity(intent);
                } else {
                    mDialog.dismiss();
                    Toast.makeText(ForgetPasswordActivity.this, "OTP send failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SendOtpResBody> call, @NonNull Throwable t) {
                mDialog.dismiss();
                Toast.makeText(ForgetPasswordActivity.this, "API Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkValidation(String email) {
        if (email.trim().isEmpty()) {
            etForgotPassEmail.setError("Required");
            return false;
        }
        return true;
    }

    public void goBack(View view) {
        onBackPressed();
    }
}