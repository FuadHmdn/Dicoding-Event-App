package com.fuad.dicoding_event.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fuad.dicoding_event.data.remote.ApiConfig
import com.fuad.dicoding_event.data.remote.Event
import com.fuad.dicoding_event.data.remote.ResponseEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private val _item = MutableLiveData<Event?>()
    val item get() = _item

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _failure = MutableLiveData<Boolean>()
    val failure: LiveData<Boolean> = _failure

    private var _failureMessage = MutableLiveData<String>()
    val failureMessage: LiveData<String> = _failureMessage

    fun getDataById(id: Int){
        _isLoading.value = true
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
                _failureMessage.value = t.message.toString()
                _failure.value = true
            }
        })
    }

}