package com.example.monpfe.Login

import android.app.Application
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import com.example.monpfe.HomeFragment
import com.example.monpfe.MainActivity
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivityRepository (val application: Application) {
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val isSuccesful = MutableLiveData<Boolean>()
    fun requestLogin(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("login", "login success")
                    isSuccesful.value = it.isSuccessful
                } else {
                    Log.d("login", "login failed")
                    isSuccesful.value = false
                }
            }.addOnFailureListener {

                if (!email.isNullOrEmpty()) {
                    Toast.makeText(application, "Entrer votre Email", Toast.LENGTH_SHORT).show()
                }
                if (!password.isNullOrEmpty()) {
                    Toast.makeText(application, "Mot de passe incorrect", Toast.LENGTH_SHORT).show()
                }

            }


    }


}