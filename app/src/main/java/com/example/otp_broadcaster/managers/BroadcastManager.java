package com.example.otp_broadcaster.managers;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;

import com.example.otp_broadcaster.R;
import com.example.otp_broadcaster.helper.BroadcastHelper;
import com.example.otp_broadcaster.services.OTPReceiver;
import com.example.otp_broadcaster.utils.CommonUtils;

import androidx.appcompat.app.AppCompatActivity;

public class BroadcastManager extends AppCompatActivity {

    EditText otpText;
    CommonUtils commonUtils;
    Context context;
    Activity activity;
    BroadcastHelper bcHelper;
    OTPReceiver otpReceiver;

    public BroadcastManager(Context context, Activity activity, EditText otpText) {
        this.context = context;
        this.activity = activity;
        commonUtils = new CommonUtils();
        this.otpText = otpText;
        this.otpReceiver = new OTPReceiver();
    }

    public void init() {
        commonUtils._requestPermission(this.context, this.activity);
        this.otpReceiver.setOtpText(otpText);
    }

//    public void fetchOTP() {
//        //  Find the otp edit text view
//        commonUtils._requestPermission(this.context, this.activity);
//        this.otpReceiver.setOtpText(otpText);
//        return this.otpReceiver.getOtp();
//    }
//
//    public boolean sendOTP(String otp) {
//        return this.bcHelper.broadcastOTP(otp);
//    }
}
