package com.example.otp_broadcaster;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.otp_broadcaster.managers.BroadcastManager;

public class MainActivity extends AppCompatActivity {
    EditText otpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        otpText = findViewById(R.id.otp_text);

        BroadcastManager bcm = new BroadcastManager(MainActivity.this, MainActivity.this, otpText);
        Log.d("##### InitBroadcastManager #####", "onCreate: BroadcastManager initialized.");
        bcm.init();
    }
}