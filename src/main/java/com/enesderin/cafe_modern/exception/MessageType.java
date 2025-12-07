package com.enesderin.cafe_modern.exception;

import lombok.Getter;

@Getter
public enum MessageType {
    NO_RECORD_EXIST("1004","Kayıt bulunamadı"),
    TOKEN_IS_EXPIRED("1005","Tokenın süresi dolmuştur"),
    USERNAME_NOT_FOUND("1006","Kullanıcı bulunamadı"),
    USERNAME_OR_PASSWORD_INVALID("1007","Kullanıcı adı ve ya şifre hatalı"),
    REFRESH_TOKEN_NOT_FOUND("1008","Refresh token bulunamadı"),
    REFRESH_TOKEN_IS_EXPIRED("1009","Refresh token süresi dolmuştur"),
    GENERAL_EXCEPTION("9999","Genel bir hata oluştu");

    private String code;
    private String message;

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
