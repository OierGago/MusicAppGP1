package com.example.appmusicgrupo1


import android.content.Context
import android.content.SharedPreferences


class UserPreferences() {

    private val sharedPreferences: SharedPreferences by lazy {
        MyApp.context.getSharedPreferences(MyApp.context.getString(R.string.app_name), Context.MODE_PRIVATE)
    }

    companion object {
        const val USER_TOKEN = "user_token"
        const val USER_ID="id"
        const val USER_CONTRASENYA="contrasenya"
        const val USER_LOGIN="login"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken( id: Int,contrasenya:String,login:String,token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(USER_TOKEN, token)
        editor.putInt(USER_ID, id)
        editor.putString(USER_CONTRASENYA, contrasenya)
        editor.putString(USER_LOGIN, login)

        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return sharedPreferences.getString(USER_TOKEN, null)
    }
}