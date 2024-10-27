package com.fuad.dicoding_event.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingsViewModel(val settingsPreferences: SettingsPreferences): ViewModel() {

    fun getTheme(): LiveData<Boolean>{
        return settingsPreferences.getTheme().asLiveData()
    }

    fun setTheme(isDarkMode: Boolean){
        viewModelScope.launch {
            settingsPreferences.saveTheme(isDarkMode)
        }
    }
}