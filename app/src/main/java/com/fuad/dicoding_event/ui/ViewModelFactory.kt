package com.fuad.dicoding_event.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fuad.dicoding_event.data.EventRepository
import com.fuad.dicoding_event.di.Injection
import com.fuad.dicoding_event.ui.favoritfragment.FavoriteViewModel

class ViewModelFactory private constructor(private val eventRepository: EventRepository)
    :ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(eventRepository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object{

        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance?: synchronized(this){
                instance?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}