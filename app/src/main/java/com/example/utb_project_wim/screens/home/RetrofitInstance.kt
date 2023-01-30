package com.example.utb_project_wim.screens.home

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api : ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl("https://csrng.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}

fun convertRandomNumberToWeather(randomNumberResponse: RandomNumber?):String{
    if (randomNumberResponse!=null && randomNumberResponse.size == 1){
        val myRandomNumber = randomNumberResponse[0].random
        println(myRandomNumber)
        return if (myRandomNumber==1){
            "SUNNY"
        } else {
            "RAINY"
        }
    }
    return "UNKNOWN"
}