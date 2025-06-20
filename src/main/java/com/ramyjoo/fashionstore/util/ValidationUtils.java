package com.ramyjoo.fashionstore.util;

public class ValidationUtils {
    // TO FIX: possibly cause thread safety issues use Synchronized and volatile
    private static ValidationUtils instance;

    private ValidationUtils(){}

    public static ValidationUtils getInstance(){
        if(instance == null){
            instance = new ValidationUtils();
        }
        return instance;
    }

    public String trimAndLowerCase(String input){
        return input == null ? null : input.trim().toLowerCase();
    }
}
