package com.fuad.dicoding_event.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuad.dicoding_event.data.EventRepository
import com.fuad.dicoding_event.data.local.entity.EventEntity
import kotlinx.coroutines.launch

class DetailViewModel(private val eventRepository: EventRepository): ViewModel() {

    val item = eventRepository.item
    val failure = eventRepository.failure
    val isLoading = eventRepository.isLoading
    val failureMessage = eventRepository.failureMessage

    fun getDataById(id: Int) = eventRepository.getDataById(id)

    fun geFavoriteEvent(id: Int) = eventRepository.geFavoriteEvent(id)

    fun insertFavoriteEvent(event: EventEntity){
        viewModelScope.launch {
            eventRepository.dao.insertFavoriteEvent(event)
        }
    }

    fun deleteFavoriteEvent(event: EventEntity){
        viewModelScope.launch {
            eventRepository.dao.deleteFavoriteEvent(event)
        }
    }
}