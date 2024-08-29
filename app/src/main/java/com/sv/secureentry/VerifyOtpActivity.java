package com.sv.secureentry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sv.secureentry.models.ProjConstants;
import com.sv.secureentry.models.SendOtpReqBody;
import com.sv.secureentry.models.SendOtpResBody;
import com.sv.secureentry.models.VerifyOtpReqBody;
import com.sv.secureentry.models.VerifyOtpResBody;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.leo.simplearcloader.SimpleArcLoader;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VerifyOtpActivity extends AppCompatActivity {

    private Button verifyOtpBtn;
    private ImageButton emailVerificationBackBtn;
    private TextView tvResendOtp;
    private EditText[] editTexts;
    private ApiInterface apiInterface;
    private int currentField = 0;
    private String email;
    private SimpleArcDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        verifyOtpBtn = findViewById(R.id.verifyOtpBtn);
        emailVerificationBackBtn = findViewById(R.id.emailVerificationBackBtn);
        tvResendOtp = findViewById(R.id.tvResendOtp);

        editTexts = new EditText[]{
                findViewById(R.id.etOtpDigit1),
                findViewById(R.id.etOtpDigit2),
                findViewById(R.id.etOtpDigit3),
                findViewById(R.id.etOtpDigit4)
        };

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

        verifyOtpBtn.setOnClickListener(v -> {
            mDialog.show();
            verifyOtpAndNav(ProjConstants.isForgetPasswordNav);
        });
        setEditTextListeners();

        emailVerificationBackBtn.setOnClickListener(v -> onBackPressed());

        tvResendOtp.setOnClickListener(v -> {
            mDialog.show();
            reSendOtp(email);
        });
    }

    private void verifyOtpAndNav(boolean isForgetPassword) {
        if (!editTexts[0].getText().toString().trim().isEmpty() && !editTexts[1].getText().toString().trim().isEmpty() && !editTexts[2].getText().toString().trim().isEmpty() && !editTexts[3].getText().toString().trim().isEmpty()) {
            String enteredOTP = editTexts[0].getText().toString() + editTexts[1].getText().toString() + editTexts[2].getText().toString() + editTexts[3].getText().toString();

            Log.d("enteredOTP",enteredOTP);
            Log.d("enteredEmail",email);
            Log.d("isForgetPassword",isForgetPassword+"  ");
            VerifyOtpReqBody verifyOtpReqBody = new VerifyOtpReqBody(email, enteredOTP, isForgetPassword);
            Call<VerifyOtpResBody> call = apiInterface.verifyOtp(verifyOtpReqBody);

            call.enqueue(new Callback<VerifyOtpResBody>() {
                @Override
                public void onResponse(@NonNull Call<VerifyOtpResBody> call, @NonNull Response<VerifyOtpResBody> response) {
                    VerifyOtpResBody verifyOtpResBody = response.body();
                    Log.d("responseCode", response.code() + " code");
                    if (response.isSuccessful()) {
                        mDialog.cancel();
                        Log.d("isFailed", "false");
                        Toast.makeText(VerifyOtpActivity.this, "Verification Successful", Toast.LENGTH_SHORT).show();
                        if (isForgetPassword) {
                            Intent intent = new Intent(VerifyOtpActivity.this, ChangePasswordActivity.class);
                            intent.putExtra(ProjConstants.USER_EMAIL, email);
                            intent.putExtra(ProjConstants.CLIENT_ROLE, getIntent().getStringExtra(ProjConstants.CLIENT_ROLE));
                            startActivity(intent);
                        } else {
                            if (Objects.equals(getIntent().getStringExtra(ProjConstants.CLIENT_ROLE), ProjConstants.MEMBER_ROLE)) {
                                startActivity(new Intent(VerifyOtpActivity.this, MemberHomeActivity.class));
                            } else if (Objects.equals(getIntent().getStringExtra(ProjConstants.CLIENT_ROLE), ProjConstants.SECRETARY_ROLE)) {
                                Intent intent = new Intent(VerifyOtpActivity.this, SecretaryGuardHomeActivity.class);
                                intent.putExtra(ProjConstants.CLIENT_ROLE, ProjConstants.SECRETARY_ROLE);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(VerifyOtpActivity.this, SecretaryGuardHomeActivity.class);
                                intent.putExtra(ProjConstants.CLIENT_ROLE, ProjConstants.GUARD_ROLE);
                                startActivity(intent);
                            }
                        }
                    } else {
                        mDialog.cancel();
                        Log.d("isFailed", "true");
                        Log.d("otpVerify", response.code() + " code");
                        Toast.makeText(VerifyOtpActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<VerifyOtpResBody> call, @NonNull Throwable t) {
                    mDialog.cancel();
                    Toast.makeText(VerifyOtpActivity.this, "API Failed", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            mDialog.cancel();
        }
    }

    private void reSendOtp(String email) {
        SendOtpReqBody sendOtpReqBody = new SendOtpReqBody(email);
        Call<SendOtpResBody> call = apiInterface.sendOtp(sendOtpReqBody);
        call.enqueue(new Callback<SendOtpResBody>() {
            @Override
            public void onResponse(@NonNull Call<SendOtpResBody> call, @NonNull Response<SendOtpResBody> response) {
                if (response.isSuccessful()) {
                    mDialog.cancel();
                    Toast.makeText(VerifyOtpActivity.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                } else {
                    mDialog.cancel();
                    Toast.makeText(VerifyOtpActivity.this, "OTP send failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SendOtpResBody> call, @NonNull Throwable t) {
                mDialog.cancel();
                Toast.makeText(VerifyOtpActivity.this, "API Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setEditTextListeners() {
        for (final EditText editText : editTexts) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.length() == 1) {
                        moveToNextField(editText);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            editText.setOnKeyListener((v, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    moveToPreviousField(editText);
                    return true;
                }
                return false;
            });
        }
    }

    private void moveToNextField(EditText currentEditText) {
        currentField++;
        if (currentField < editTexts.length) {
            editTexts[currentField].requestFocus();
        } else {
            currentEditText.clearFocus();
        }
    }

    private void moveToPreviousField(EditText currentEditText) {
        if (currentField > 0) {
            currentField--;
            editTexts[currentField].requestFocus();
            editTexts[currentField].setText("");
        }
    }
}