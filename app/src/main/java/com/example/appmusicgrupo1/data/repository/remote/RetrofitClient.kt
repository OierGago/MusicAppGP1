package com.example.appmusicgrupo1.data.repository.remote

import android.util.Log
import com.example.appmusicgrupo1.MyApp
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    const val API_URI = "http://10.5.7.162:8080/api/"

    //const val API_URI = "http://10.5.7.162:8080/api/"
    //const val API_URI = "http://10.5.7.129:8080/api/"


    var client = OkHttpClient.Builder().addInterceptor { chain ->
        Log.i("prueba", "client")
        val authToken = MyApp.userPreferences.fetchAuthToken()
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $authToken")
            .build()
        chain.proceed(newRequest)
    }.build()

    val retrofitClient: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .client(client)
            .baseUrl(API_URI)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: APIInterface by lazy {
        retrofitClient
            .build()
            .create(APIInterface::class.java)
    }

}