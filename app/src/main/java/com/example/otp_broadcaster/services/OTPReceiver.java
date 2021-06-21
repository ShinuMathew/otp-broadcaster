package com.example.otp_broadcaster.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OTPReceiver extends BroadcastReceiver {


    private static EditText otpText;

    public void setOtpText(EditText editOTPText) {
        OTPReceiver.otpText = editOTPText;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for(SmsMessage smsMessage : messages) {
            String messageBody = smsMessage.getMessageBody();
            otpText.setText(_fetchOTPFromMessage(messageBody));
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
        return otp;
    }
}
