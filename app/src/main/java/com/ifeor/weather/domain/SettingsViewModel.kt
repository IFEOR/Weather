package com.ifeor.weather.domain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    // Example liveData
    val liveData = MutableLiveData<String>()
}
