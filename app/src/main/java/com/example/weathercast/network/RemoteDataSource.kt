package com.example.mvvm.network


import com.example.lab1.retrofitapi.ApiInterface
import com.example.lab1.retrofitapi.RetrofitClient
import com.example.weathercast.data.pojo.CurrentWeatherData
import com.example.weathercast.data.pojo.ForcastWeatherData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource() : RemoteDataSourceInterface {
    private val apiService = RetrofitClient.retrofitInstance.create(ApiInterface::class.java)

    override suspend fun getForecastData(
        latitude: String,
        longitude: String,
        measurementUnit: String,language:String
    ): Flow<ForcastWeatherData> =flow{
            val response = apiService.getForecastData(latitude, longitude,measurementUnit,appId="ed06dd80726ae839deb870c92c41bb01",language)
            emit(response)
    }
    override suspend fun getCurrentData(
        latitude: String,
        longitude: String,
        measurementUnit: String,language:String
    ): CurrentWeatherData {
        try {
            val response = apiService.getCurrentWeathertData(latitude, longitude,measurementUnit,appId="ed06dd80726ae839deb870c92c41bb01",language)
            return response
        }catch (e:Exception){
            throw e
        }
    }
}