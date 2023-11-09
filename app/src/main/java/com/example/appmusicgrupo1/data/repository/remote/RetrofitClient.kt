package com.example.appmusicgrupo1.data.repository.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

<<<<<<< HEAD
    const val API_URI = "http://10.0.2.2:8080/api/"
=======
    const val API_URI = "http://10.5.7.129:8080/api/"
>>>>>>> 3e2642bc0539cac309cf19316aa9ffc15c049549

    val retrofitClient: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(API_URI)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: APIInterface by lazy {
        retrofitClient
            .build()
            .create(APIInterface::class.java)
    }

}