package com.fuad.dicoding_event.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("events")
    fun getEvents(@Query("active") active : Int, @Query("limit") limit: Int = 40): Call<EventResponse>

}