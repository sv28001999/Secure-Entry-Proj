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
import com.sv.secureentry.models.SendOtpReqBody;
import com.sv.secureentry.models.SendOtpResBody;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.leo.simplearcloader.SimpleArcLoader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ForgetPasswordActivity extends AppCompatActivity {

    private Button sendForgetPasswordOtp;
    private EditText etForgotPassEmail;
    private ApiInterface apiInterface;
    private SimpleArcDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        sendForgetPasswordOtp = findViewById(R.id.sendForgetPasswordOtp);
        etForgotPassEmail = findViewById(R.id.etForgotPassEmail);

        Retrofit retrofit = RetrofitClient.getInstance();
        apiInterface = retrofit.create(ApiInterface.class);

        mDialog = new SimpleArcDialog(this);
        ArcConfiguration configuration = new ArcConfiguration(this);
        configuration.setLoaderStyle(SimpleArcLoader.STYLE.SIMPLE_ARC);
        configuration.setColors(new int[]{Color.parseColor("#D8533FD3")});
        configuration.setText("Please wait..");
        mDialog.setConfiguration(configuration);
        mDialog.setCancelable(false);

        sendForgetPasswordOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
                if (!checkValidation(etForgotPassEmail.getText().toString())) {
                    mDialog.cancel();
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
                    mDialog.cancel();
                    Toast.makeText(ForgetPasswordActivity.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                    ProjConstants.isForgetPasswordNav = true;
                    Intent intent = new Intent(ForgetPasswordActivity.this, VerifyOtpActivity.class);
                    intent.putExtra("userEmail", email);
                    startActivity(intent);
                } else {
                    mDialog.cancel();
                    Toast.makeText(ForgetPasswordActivity.this, "OTP send failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SendOtpResBody> call, @NonNull Throwable t) {
                mDialog.cancel();
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