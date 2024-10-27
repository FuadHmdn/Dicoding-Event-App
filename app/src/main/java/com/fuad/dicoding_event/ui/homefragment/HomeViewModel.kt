package com.fuad.dicoding_event.ui.homefragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fuad.dicoding_event.data.remote.ApiConfig
import com.fuad.dicoding_event.data.remote.EventResponse
import com.fuad.dicoding_event.data.remote.ListEventsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {

    companion object{
        const val FINISHED_EVENT = 0
        const val UPCOMING_EVENT = 1
        const val LIMIT = 5
        const val TAG = "HomeViewModel"
    }

    private var _listEventItemUpcoming = MutableLiveData<List<ListEventsItem?>?>()
    val listEventItemUpcoming: LiveData<List<ListEventsItem?>?> = _listEventItemUpcoming

    private var _listEventItemFinished = MutableLiveData<List<ListEventsItem?>?>()
    val listEventItemFinished: LiveData<List<ListEventsItem?>?> = _listEventItemFinished

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _failure = MutableLiveData<Boolean>()
    val failure: LiveData<Boolean> = _failure

    private var _failureMessage = MutableLiveData<String>()
    val failureMessage: LiveData<String> = _failureMessage

    init {
        findUpcomingEvent()
        findFinishedEvent()
    }

    private fun findUpcomingEvent(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvents(UPCOMING_EVENT, LIMIT)
        client.enqueue(object : Callback<EventResponse>{
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listEventItemUpcoming.value = response.body()?.listEvents
                } else {
                    Log.e(TAG, "Failure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Failure : ${t.message.toString()}")
                _failureMessage.value = t.message.toString()
                _failure.value = true
            }

        })
    }

    private fun findFinishedEvent(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvents(FINISHED_EVENT, LIMIT)
        client.enqueue(object : Callback<EventResponse>{
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listEventItemFinished.value = response.body()?.listEvents
                } else {
                    Log.e(TAG, "Failure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Failure : ${t.message.toString()}")
                _failureMessage.value = t.message.toString()
                _failure.value = true
            }

        })
    }

}