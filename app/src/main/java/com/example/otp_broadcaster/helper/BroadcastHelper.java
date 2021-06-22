package com.example.otp_broadcaster.helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.otp_broadcaster.CustomExceptions.OTPBroadcasterException;
import com.example.otp_broadcaster.MainActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.MainThread;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BroadcastHelper {

    private Context context;

    public BroadcastHelper(Context context) {
        this.context = context;
    }

    public void broadcastOTP(String otp) throws JSONException {
        RequestQueue request = Volley.newRequestQueue(this.context);
        String url = "https://otpbroadcaster-webhook.herokuapp.com/otp-broadcaster/send-otp";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("otp", otp);
        final String requestBody = jsonObject.toString();
        Log.d("broadcastOTP", requestBody);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("broadcastOTP : onResponse", response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("broadcastOTP : onErrorResponse", error.getMessage() == null ? "null" : error.getMessage());
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
    }
}
