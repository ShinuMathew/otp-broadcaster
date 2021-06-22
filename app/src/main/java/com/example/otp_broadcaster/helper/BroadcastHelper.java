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

    public boolean broadcastOTP1(String otp) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n    \"otp\" : "+otp+"\n}");
            Request request = new Request.Builder()
                    .url("https://otpbroadcaster-webhook.herokuapp.com/otp-broadcaster/send-otp")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (Exception ex) {
            Log.e("broadcastOTP", "broadcastOTP: failed due to the following error: \n"+ex.getMessage());
            return false;
        }
    }

    public boolean broadcastOTPXX(String otp) {
        boolean[] isPublished = {false};
        OkHttpClient client = new OkHttpClient();
        String url = "https://otpbroadcaster-webhook.herokuapp.com/otp-broadcaster/send-otp";
        Log.d("##### broadcastOTP #####", "Initializing request params to be sent to : "+url);
        MediaType mediaType = MediaType.parse("application/json");
        String reqBody = "{\"otp\":"+otp+"}";
        Log.d("##### broadcastOTP ##### ", " reqBody : \n"+reqBody);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .method("POST", RequestBody.create(reqBody, mediaType))
                .build();
        Log.d("##### broadcastOTP #####", "Sending request : "+url+"....");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("##### broadcastOTP: Callback #####", "broadcastOTP: failed due to the following error: \n"+e.getMessage());
//                Toast.makeText(context.getApplicationContext(), "Found OTP: "+otp+" broadcast failed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String myResponse = response.body().string();
                    Log.d("##### myResponse #####", myResponse );
                    Log.d("##### broadcastOTP: Callback #####", "broadcastOTP: SUCCESS!!");
                    isPublished[0] = true;
//                    Toast.makeText(context.getApplicationContext(), "OTP : "+otp+" broadcast successful", Toast.LENGTH_LONG).show();
                } else {
                    System.out.println(response.body().string());
                    //  Toast.makeText(context.getApplicationContext(), "OTP : "+otp+" broadcast failed : \n"+response.body().string(), Toast.LENGTH_LONG).show();
                }

            }
        });
        return isPublished[0];
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
