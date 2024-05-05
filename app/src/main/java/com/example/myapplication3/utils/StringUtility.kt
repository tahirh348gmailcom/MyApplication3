package com.example.myapplication3.utils

import android.util.Patterns
import java.util.regex.Pattern

object StringUtility {


    fun stringNotNull(str: String?): Boolean {
        return str != null
    }

    fun stringNotEmpty(str: String): Boolean {
        return !str.isEmpty()
    }


    /*
    * Validate input string for null object and not empty string.
    * */
    @JvmStatic
    fun validateString(str: String?): Boolean {
        return stringNotNull(str) && stringNotEmpty(str!!.trim())
    }

    @JvmStatic
    fun validateInt(value: Int?): Boolean {
        if(value != null)
        {
            return true
        }else
        {
            return false
        }
    }

    @JvmStatic
    fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePassword(password: String): Boolean {
            val passwordRegex = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$")
            return passwordRegex.matcher(password).matches()
    }

}
