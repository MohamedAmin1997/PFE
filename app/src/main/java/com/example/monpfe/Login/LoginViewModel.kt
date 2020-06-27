package com.example.monpfe.Login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class LoginViewModel(application: Application) : AndroidViewModel(application){
    private val repository= LoginActivityRepository(application)
    val isSuccesful : LiveData<Boolean>
    init {
        isSuccesful = repository.isSuccesful
    }
    fun requestLogin(email:String, password:String)
    {
        repository.requestLogin(email, password)

    }

}