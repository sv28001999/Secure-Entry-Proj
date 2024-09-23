package com.sv.secureentry;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.sv.secureentry.models.AddEntryReqBody;
import com.sv.secureentry.models.AddEntryResBody;
import com.sv.secureentry.models.ProjConstants;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
//import com.leo.simplearcloader.ArcConfiguration;
//import com.leo.simplearcloader.SimpleArcDialog;
//import com.leo.simplearcloader.SimpleArcLoader;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddEntriesActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;

    private Uri imageUri;
    private ImageView imageView;
    private StorageReference storageReference;
    private ApiInterface apiInterface;
//    private SimpleArcDialog mDialog;
    private EditText personName, personMobile, personPlace, personWork, memberRoomNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entries);

        imageView = findViewById(R.id.personImg);
        ImageButton buttonSelectImage = findViewById(R.id.buttonSelectImage);
        ImageButton buttonCaptureImage = findViewById(R.id.buttonCaptureImage);
        Button buttonUploadImage = findViewById(R.id.personEntrySubmitBtn);
        personName = findViewById(R.id.etPersonName);
        personMobile = findViewById(R.id.etPersonMobile);
        personPlace = findViewById(R.id.etPersonPlace);
        personWork = findViewById(R.id.etPersonWork);
        memberRoomNo = findViewById(R.id.etPersonVisitingInfo);

//        mDialog = new SimpleArcDialog(this);
//        ArcConfiguration configuration = new ArcConfiguration(this);
//        configuration.setLoaderStyle(SimpleArcLoader.STYLE.SIMPLE_ARC);
//        configuration.setColors(new int[]{Color.parseColor("#D8533FD3")});
//        configuration.setText("Please wait..");
//        mDialog.setConfiguration(configuration);
//        mDialog.setCancelable(false);

        Retrofit retrofit = RetrofitClient.getInstance();
        apiInterface = retrofit.create(ApiInterface.class);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        buttonSelectImage.setOnClickListener(v -> openFileChooser());

        buttonCaptureImage.setOnClickListener(v -> captureImage());

        buttonUploadImage.setOnClickListener(v -> uploadImage());
    }

    private void captureImage() {
        checkAndRequestPermissions();
    }

    private void checkAndRequestPermissions() {
        Log.d("isPermissionMethodCall", "true");
        Dexter.withContext(this)
                .withPermission(android.Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Log.d("dexterMethod", "onPermissionGranted");
                        openCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Log.d("dexterMethod", "onPermissionDenied");
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        Log.d("dexterMethod", "onPermissionRationaleShouldBeShown");
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    private void openCamera() {
        Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(iCamera, REQUEST_IMAGE_CAPTURE);
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Log.d("onActivityResult", "fileRequest");
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
            Log.d("onActivityResult", "cameraRequest");
            Bitmap img = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            assert img != null;
            imageUri = getImageUri(this, img);
            imageView.setImageURI(imageUri);
        } else {
            Log.d("onActivityResult", "else");
            Log.d("onActivityResult", resultCode + "  " + requestCode + "  " + data + "  ");
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void uploadImage() {
        if (imageUri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        String url = uri.toString();
                        String name = personName.getText().toString();
                        String mobile = personMobile.getText().toString();
                        String work = personWork.getText().toString();
                        String place = personPlace.getText().toString();
                        String memberRoom = memberRoomNo.getText().toString();
                        SharedPreferences sharedPreferences = getSharedPreferences(ProjConstants.USER_DATA_SF, Context.MODE_PRIVATE);
                        String token = sharedPreferences.getString("token", "");
                        String secretCode = sharedPreferences.getString("orgUniqueCode", "");

                        AddEntryReqBody addEntryReqBody = new AddEntryReqBody(name, mobile, work, place, memberRoom, url, secretCode);
                        Call<AddEntryResBody> call = apiInterface.uploadEntry("Bearer " + token, addEntryReqBody);

                        call.enqueue(new Callback<AddEntryResBody>() {
                            @Override
                            public void onResponse(Call<AddEntryResBody> call, Response<AddEntryResBody> response) {
                                AddEntryResBody resBody = response.body();
                                Log.d("responseCode", response.isSuccessful() + "   responseCode:" + response.code() + "    secretCode:" + secretCode);
                                Log.d("responseToken", token);
                                if (response.isSuccessful() && response.code() == 200) {
                                    Toast.makeText(AddEntriesActivity.this, "Entry added successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(AddEntriesActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<AddEntryResBody> call, Throwable t) {
                                Toast.makeText(AddEntriesActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }))
                    .addOnFailureListener(e -> Toast.makeText(AddEntriesActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri) {
        return Objects.requireNonNull(getContentResolver().getType(uri)).split("/")[1];
    }

}