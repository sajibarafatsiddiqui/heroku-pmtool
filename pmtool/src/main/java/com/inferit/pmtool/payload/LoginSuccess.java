package com.inferit.pmtool.payload;

import lombok.Data;
@Data

public class LoginSuccess {
    private boolean success;
    private String token;

    public LoginSuccess(boolean success, String token) {
        this.success = success;
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginSuccess{" +
                "success=" + success +
                ", token='" + token + '\'' +
                '}';
    }
}
