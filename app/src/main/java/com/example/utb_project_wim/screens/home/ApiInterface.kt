package com.example.utb_project_wim.screens.home

import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("/csrng/csrng.php?min=1&max=2")
    suspend fun getWeather(): Response<RandomNumber>
}