package com.example.monpfe.ui.conseil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConseilViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Conseil Fragment"
    }
    val text: LiveData<String> = _text
}