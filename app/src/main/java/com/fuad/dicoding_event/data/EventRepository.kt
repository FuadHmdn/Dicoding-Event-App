package com.fuad.dicoding_event.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fuad.dicoding_event.data.local.entity.EventEntity
import com.fuad.dicoding_event.data.local.room.EventDao
import com.fuad.dicoding_event.data.remote.ApiService
import com.fuad.dicoding_event.data.remote.Event
import com.fuad.dicoding_event.data.remote.ResponseEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventRepository(
    private val apiService: ApiService,
    val dao: EventDao
) {

    private val _item = MutableLiveData<Event?>()
    val item get() = _item

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _failure = MutableLiveData<Boolean>()
    val failure: LiveData<Boolean> = _failure

    private var _failureMessage = MutableLiveData<String>()
    val failureMessage: LiveData<String> = _failureMessage

    companion object{
        @Volatile
        private var instance: EventRepository? = null

        fun getInstance(
            apiService: ApiService,
            dao: EventDao
        ): EventRepository = instance?: synchronized(this){
            instance?: EventRepository(apiService, dao)
        }.also { instance = it }
    }

    fun getDataById(id: Int){
        _isLoading.value = true
        val client = apiService.getEventById(id)
        client.enqueue(object : Callback<ResponseEvent> {
            override fun onResponse(call: Call<ResponseEvent>, response: Response<ResponseEvent>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _item.value = response.body()?.event
                } else {
                    Log.e("DetailViewModel", "Failure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseEvent>, t: Throwable) {
                _isLoading.value = false
                Log.e("DetailViewModel", "Failure : ${t.message.toString()}")
                _failureMessage.value = t.message.toString()
                _failure.value = true
            }
        })
    }

    fun geFavoriteEvent(id: Int): LiveData<EventEntity>{
        return dao.getFavoriteEventById(id.toString())
    }

}