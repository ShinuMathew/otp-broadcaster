package com.example.otp_broadcaster.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.otp_broadcaster.helper.BroadcastHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OTPReceiver extends BroadcastReceiver {


    private static EditText otpText;
    private static String otp;
    private BroadcastHelper broadcastHelper;

    public void setOtpText(EditText editOTPText) {
        OTPReceiver.otpText = editOTPText;
    }

    public String getOtp() {
        return OTPReceiver.otp;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for(SmsMessage smsMessage : messages) {
            try {
                String messageBody = smsMessage.getMessageBody();
                String otp = _fetchOTPFromMessage(messageBody);
                Log.d("##### onReceive : OTPReceiver #####", "Found OTP: "+otp);

                //  Display OTP on screen
                Toast.makeText(context.getApplicationContext(), "Found OTP: "+otp, Toast.LENGTH_LONG).show();
                otpText.setText(otp);

                //  Broadcast message to remote
                broadcastHelper = new BroadcastHelper(context);
                broadcastHelper.broadcastOTP(otp);
            } catch (Exception ex) {
                Log.e("onReceive: OTPReceiver", "OTP roadcasting failed due to :\n"+ex.getMessage());
            }
        }
    }

    private String _fetchOTPFromMessage(String messageBody) {
        String otp = "";
        try {
            Pattern p = Pattern.compile("\\d{4,7}");
            Matcher m = p.matcher(messageBody);
            while (m.find())  otp = m.group();
            if(otp.length() == 0)
                Log.d("_fetchOTPFromMessage", String.format("Unable to fetch OTP from message %s", messageBody));
        } catch (Exception ex) {
            Log.e("_fetchOTPFromMessage", String.format("Unable to fetch OTP. Following exception occured: %s \n", ex.getMessage()));
        }
        OTPReceiver.otp = otp;
        Log.d("_fetchOTPFromMessage:", OTPReceiver.otp);
        return otp;
    }
}
