package com.fuad.dicoding_event.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fuad.dicoding_event.data.ApiConfig
import com.fuad.dicoding_event.data.Event
import com.fuad.dicoding_event.data.EventResponse
import com.fuad.dicoding_event.data.ListEventsItem
import com.fuad.dicoding_event.data.ResponseEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private val _item = MutableLiveData<Event?>()
    val item get() = _item

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDataById(id: Int){
        val client = ApiConfig.getApiService().getEventById(id)
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
            }
        })
    }

}