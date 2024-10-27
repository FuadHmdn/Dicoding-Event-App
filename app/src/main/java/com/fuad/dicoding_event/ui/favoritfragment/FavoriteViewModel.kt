package com.fuad.dicoding_event.ui.favoritfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fuad.dicoding_event.data.EventRepository
import com.fuad.dicoding_event.data.local.entity.EventEntity

class FavoriteViewModel(private val eventRepository: EventRepository): ViewModel() {

    fun getAllFavoriteEvent(): LiveData<List<EventEntity>> = eventRepository.dao.getEventFavorite()

}