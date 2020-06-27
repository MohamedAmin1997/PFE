package com.example.monpfe.ui.Urgence

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UrgenceViewModel: ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is urgence Fragment"
    }
    val text: LiveData<String> = _text
}