package com.practice.mysource.utils;

public class EmailUtils {

    public static String getEmailMessage(String name, String host, String key){
        return "Hello " + name + ",\n\nYour new account has been created. Please clickon the link bellow to verify your account. \n\n" +
                getVerificationUrl(host,key) + "\n\nThe Support Team";
    }

    public static String getResetPasswordMessage(String name,String host, String token){
        return "Hello " + name + ",\n\nYour new account has been created. Please click on the link bellow to verify your account.\n\n" +
                getResetPasswordUrl(host, token) + "\n\nThe Support Team";
    }

    private static String getVerificationUrl(String host, String key) {
        return host + "/user/verify/account?key="+ key;
    }

    private static String getResetPasswordUrl(String host, String token) {
        return host + "/user/verify/password?token="+ token;
    }


}
