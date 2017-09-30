package com.example.hafiz.instagramclone.Utils;

/**
 * Created by hafiz on 9/30/2017.
 */

public class StringManipulation {

    public static String expandUsername (String username) {
        return username.replace("."," ");
    }

    public  static String condenseUsername (String usernaem) {
        return usernaem.replace(" ", ".");
    }
}
