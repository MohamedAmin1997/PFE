package com.example.monpfe

import android.app.Application
import com.firebase.client.Firebase

class custome_application : Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.setAndroidContext(this)
    }
}