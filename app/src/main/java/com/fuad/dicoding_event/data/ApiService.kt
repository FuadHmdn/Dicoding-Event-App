package com.fuad.dicoding_event.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("events")
    fun getUpcomingEvents(@Query("active") active : Int): Call<Response>

}