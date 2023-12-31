package com.example.appmusicgrupo1


import android.content.Context
import android.content.SharedPreferences
import com.example.appmusicgrupo1.data.User


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
    fun saveAuthTokenWithPs( id: Int,contrasenya:String,login:String,token: String) {
        val editor = sharedPreferences.edit()
        editor.putInt(USER_ID, id)
        editor.putString(USER_CONTRASENYA, contrasenya)
        editor.putString(USER_LOGIN, login)
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }
    fun restartPreference (){
        val  editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
    fun saveAuthToken( id: Int,login:String,token: String) {
        val editor = sharedPreferences.edit()
        editor.putInt(USER_ID, id)
        editor.putString(USER_LOGIN, login)
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }


    fun fetchAuthId():Int? {
        return sharedPreferences.getInt(USER_ID,0)
    }
    fun fetchAuthLogin():String?{
        return sharedPreferences.getString(USER_LOGIN, null)
    }
    fun fetchAuthPassword():String?{
        return sharedPreferences.getString(USER_CONTRASENYA, null)
    }
    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return sharedPreferences.getString(USER_TOKEN, null)
    }
}