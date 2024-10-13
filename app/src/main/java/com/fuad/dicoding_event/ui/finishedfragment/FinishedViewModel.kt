package com.fuad.dicoding_event.ui.finishedfragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fuad.dicoding_event.data.ApiConfig
import com.fuad.dicoding_event.data.EventResponse
import com.fuad.dicoding_event.data.ListEventsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedViewModel: ViewModel() {

    companion object{
        const val FINISHED_EVENT = 0
        const val TAG = "FinishedViewModel"
    }

    private var _listEventItemFinished = MutableLiveData<List<ListEventsItem?>?>()
    val listEventItemFinished: LiveData<List<ListEventsItem?>?> = _listEventItemFinished

    private var _searchEventItemFinished = MutableLiveData<List<ListEventsItem?>?>()
    val searchEventItemFinished: LiveData<List<ListEventsItem?>?> = _searchEventItemFinished

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        findFinishedEvent()
    }

    fun searchEvent(q: String){
        searchFinishedEvent(q)
    }

    private fun findFinishedEvent(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvents(FINISHED_EVENT)
        client.enqueue(object : Callback<EventResponse> {
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
            }

        })
    }

    private fun searchFinishedEvent(q: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEventsByName(FINISHED_EVENT, q)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _searchEventItemFinished.value = response.body()?.listEvents
                } else {
                    Log.e(TAG, "Failure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Failure : ${t.message.toString()}")
            }

        })
    }
}