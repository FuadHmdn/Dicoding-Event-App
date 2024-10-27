package com.fuad.dicoding_event.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("events")
    fun getEvents(@Query("active") active : Int, @Query("limit") limit: Int = 40): Call<EventResponse>

    @GET("events/{id}")
    fun getEventById(@Path("id") id : Int): Call<ResponseEvent>

    @GET("events")
    fun getEventsByName(@Query("active") active: Int = 0, @Query("q") q: String): Call<EventResponse>

}