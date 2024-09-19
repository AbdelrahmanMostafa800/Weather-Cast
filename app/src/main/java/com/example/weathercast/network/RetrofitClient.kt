package com.example.lab1.retrofitapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    const val BASE_URL="api.openweathermap.org/data/2.5/"
    val retrofitInstance = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

object API{
    val retrofitService:ApiInterface by lazy {
        RetrofitClient.retrofitInstance.create(ApiInterface::class.java)
    }
}

