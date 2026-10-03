package com.qa.opencart.utils;

public class StringUtils {

    public static String generateRandomEmailId()
    {
        String email="UIAutomation"+Math.random()+"@gmail.com";
        return email;
    }
}
