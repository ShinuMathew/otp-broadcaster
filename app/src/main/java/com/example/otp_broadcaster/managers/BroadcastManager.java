package com.example.otp_broadcaster.managers;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;

import com.example.otp_broadcaster.R;
import com.example.otp_broadcaster.services.OTPReceiver;
import com.example.otp_broadcaster.utils.CommonUtils;

import androidx.appcompat.app.AppCompatActivity;

public class BroadcastManager extends AppCompatActivity {

    EditText otpText;
    CommonUtils commonUtils;
    Context context;
    Activity activity;

    public BroadcastManager(Context context, Activity activity, EditText otpText) {
        this.context = context;
        this.activity = activity;
        commonUtils = new CommonUtils();
        this.otpText = otpText;
    }

    public void init() {
        this.getOTP();
    }

    public void getOTP() {
        //  Find the otp edit text view
        commonUtils._requestPermission(this.context, this.activity);
        new OTPReceiver().setOtpText(otpText);
    }
}
