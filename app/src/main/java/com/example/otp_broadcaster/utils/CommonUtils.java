package com.example.otp_broadcaster.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import com.example.otp_broadcaster.MainActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CommonUtils {
    public void _requestPermission(Context context, Activity activity) {
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[] {
                    Manifest.permission.RECEIVE_SMS
            }, 100);
        }
    }
}
