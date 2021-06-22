package com.example.otp_broadcaster.CustomExceptions;

public class OTPBroadcasterException extends  Exception {

    private String message;

    public OTPBroadcasterException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage() {
        this.message = message;
    }

}
