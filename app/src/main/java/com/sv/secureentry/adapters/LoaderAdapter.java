package com.sv.secureentry.adapters;

import android.app.ProgressDialog;

import com.sv.secureentry.R;

public class LoaderAdapter {
    public static void startLoader( ProgressDialog progressDialog){
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
}
