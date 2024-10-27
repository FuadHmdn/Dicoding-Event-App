package com.fuad.dicoding_event.di

import android.content.Context
import com.fuad.dicoding_event.data.EventRepository
import com.fuad.dicoding_event.data.local.room.EventDatabase
import com.fuad.dicoding_event.data.remote.ApiConfig

object Injection {
    fun provideRepository(context: Context): EventRepository{
        val api = ApiConfig.getApiService()
        val database = EventDatabase.getInstance(context)
        val dao = database.eventDao()
        return EventRepository(api, dao)
    }
}