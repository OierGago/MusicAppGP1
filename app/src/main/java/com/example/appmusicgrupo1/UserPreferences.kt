package com.example.appmusicgrupo1


import android.content.Context
import android.content.SharedPreferences

class UserPreferences() {

    private val sharedPreferences: SharedPreferences by lazy {
        MyApp.context.getSharedPreferences(MyApp.context.getString(R.string.app_name), Context.MODE_PRIVATE)
    }

    companion object {
        const val USER_TOKEN = "user_token"
        const val USER_ID =  "id"
        const val USER_LOGIN="login"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String, id : Int, login :String) {
        val editor = sharedPreferences.edit()
        editor.putString(USER_TOKEN, token)
        editor.putInt(USER_ID, id)
        editor.putString(USER_LOGIN, login)
        editor.apply()
    }
    /*
    fun saveAuthId(id : Int){
        val editor = sharedPreferences.edit()

        editor.apply()
    }
    fun saveAuthLogin(login :String){
        val editor = sharedPreferences.edit()

        editor.apply()
    }

     */

    fun fetchAuthId(): Int? {
        return sharedPreferences.getInt(USER_ID, 0)
    }
    fun fetchAuthLogin(): String? {
        return sharedPreferences.getString(USER_LOGIN, null)
    }
    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return sharedPreferences.getString(USER_TOKEN, null)
    }
    /*fun getUserId(token: String): Int {
        val claims = parseClaims(token)
        return claims["userId"] as Int
    }

     */
   /* fun parseClaims(token: String): Claims {
        val jwtParser = Jwts.parser()
        return jwtParser.parseClaimsJws(token).body
    }*/
}