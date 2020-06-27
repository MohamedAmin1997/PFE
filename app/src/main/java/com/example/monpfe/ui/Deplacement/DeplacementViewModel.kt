package com.example.monpfe.ui.Deplacement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DeplacementViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This isdeplacement Fragment"
    }
    val text: LiveData<String> = _text
}