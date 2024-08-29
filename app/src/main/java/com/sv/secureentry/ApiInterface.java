package com.sv.secureentry;

import com.sv.secureentry.models.AddEntryReqBody;
import com.sv.secureentry.models.AddEntryResBody;
import com.sv.secureentry.models.GetEntriesReqBody;
import com.sv.secureentry.models.GetEntriesResBody;
import com.sv.secureentry.models.GetSocietyInfoReqBody;
import com.sv.secureentry.models.GetSocietyInfoResBody;
import com.sv.secureentry.models.LoginReqBody;
import com.sv.secureentry.models.LoginResBody;
import com.sv.secureentry.models.RegistrationReqBody;
import com.sv.secureentry.models.RegistrationResBody;
import com.sv.secureentry.models.SendOtpReqBody;
import com.sv.secureentry.models.SendOtpResBody;
import com.sv.secureentry.models.UpdatePassReqBody;
import com.sv.secureentry.models.UpdatePassResBody;
import com.sv.secureentry.models.VerifyOtpReqBody;
import com.sv.secureentry.models.VerifyOtpResBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("api/v1/createAccount")
    Call<RegistrationResBody> createAccount(@Body RegistrationReqBody registrationReqBody);

    @POST("api/v1/sendOtp")
    Call<SendOtpResBody> sendOtp(@Body SendOtpReqBody sendOtpReqBody);

    @POST("api/v1/verifyOtp")
    Call<VerifyOtpResBody> verifyOtp(@Body VerifyOtpReqBody verifyOtpReqBody);

    @POST("api/v1/login")
    Call<LoginResBody> loginUser(@Body LoginReqBody loginReqBody);

    @POST("api/v1/updatePassword")
    Call<UpdatePassResBody> updatePassword(@Body UpdatePassReqBody updatePassReqBody);

    @POST("api/v1/getSocietyInfo")
    Call<GetSocietyInfoResBody> getSocietyInfo(@Body GetSocietyInfoReqBody getSocietyInfoReqBody);

    @POST("api/v1/uploadEntry")
    Call<AddEntryResBody> uploadEntry(@Header("Authorization") String token, @Body AddEntryReqBody addEntryReqBody);

    @POST("api/v1/getEntryDetails")
    Call<GetEntriesResBody> getEntryDetails(@Header("Authorization") String token, @Body GetEntriesReqBody getEntriesReqBody);

}
