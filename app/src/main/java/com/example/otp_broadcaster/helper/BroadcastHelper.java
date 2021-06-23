package com.example.otp_broadcaster.helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicInteger;

public class BroadcastHelper {

    private Context context;
    public static Boolean broadcastSuccess;
    private static AtomicInteger counter = new AtomicInteger(0);

    public BroadcastHelper(Context context) {
        counter.set(0);
        this.context = context;
        BroadcastHelper.broadcastSuccess = false;
    }

    public void broadcastOTP(String otp) {
        RequestQueue request = Volley.newRequestQueue(this.context);
        String url = "https://otpbroadcaster-webhook.herokuapp.com/otp-broadcaster/send-otp";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("otp", otp);
            final String requestBody = jsonObject.toString();
            Log.d("broadcastOTP", requestBody);

            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("broadcastOTP : onResponse", response);
                    Toast.makeText(context.getApplicationContext(), "OTP broadcast successfully", Toast.LENGTH_LONG).show();
                    BroadcastHelper.broadcastSuccess = true;
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("broadcastOTP : onErrorResponse", error.getMessage() == null ? "null" : error.getMessage());
                    BroadcastHelper.broadcastSuccess = false;
                    if(BroadcastHelper.counter.getAndIncrement() <  2){
                        Toast.makeText(context.getApplicationContext(), "OTP broadcast Failed.\nRetrying otp broadcast : " + counter.get(), Toast.LENGTH_LONG).show();
                        broadcastOTP(otp);
                    }
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        Log.e("broadcastOTP : getBody", "Unsupported Encoding while trying to get the bytes of %s using " + requestBody);
                        return null;
                    }
                }
            };
            request.add(stringRequest);
        } catch (Exception ex) {
            Log.e("broadcastOTP", "Failed to Broadcast otp due to following exception :\n"+ex.getMessage());
        }
    }
}
