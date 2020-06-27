package com.example.monpfe.Register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData


class RegisterViewModel  (application: Application) : AndroidViewModel(application){
    private val repository= RegisterRepository(application)
    val isSuccesful : LiveData<Boolean>
    init {
        isSuccesful = repository.isSuccesful
    }
    fun requestRegister(email:String, name:String,phone:String,password:String,confpass:String)
    {
        repository.requestRegister(email,name,phone, password,confpass)
    }
}