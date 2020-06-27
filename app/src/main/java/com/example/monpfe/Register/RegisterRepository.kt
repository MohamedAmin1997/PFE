package com.example.monpfe.Register

import android.app.Application
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import com.example.monpfe.Ajouter_MedicamentActivity
import com.example.monpfe.Profile.utilisateur_data
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RegisterRepository(val application: Application) {
    private var firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()
    val isSuccesful = MutableLiveData<Boolean>()

    fun databaseUpload( email : String,  id:String , name:String ,phone:String, password:String,confpass:String ){
        val ref = FirebaseDatabase.getInstance().getReference("Utilisateurs")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                try{
                    for (i in p0.children){
                        Log.i("myapp",i.child("email").getValue().toString())
                    }
                }catch (e: java.lang.Exception){
                    Log.i("myapp",e.message.toString())
                }
            }
        })

        if(firebaseAuth.currentUser != null){
            val utilisateurID = id
            val utilisateur = utilisateur_data(
                email,
                utilisateurID,
                name,
                phone,
                password,
                confpass
            )
            utilisateurID.let { ref.child(utilisateurID).setValue(utilisateur).addOnCompleteListener {
                if (it.isSuccessful){

                    Log.d("TAG", "Bienvenue")
                    return@addOnCompleteListener
                }
                Log.d("TAG", "Erreur.")
            } }
        }

        else{
            Log.i("TAG","firebase user = null")
        }
    }



    fun requestRegister(email : String, name:String,phone:String, password:String, confpass:String){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val id = firebaseAuth.getUid()!!
                    databaseUpload(email,id,name,phone,password,confpass)
                    isSuccesful.value=it.isSuccessful

                } else {
                    Log.d("TAG", "Authentification échouée.")

                    isSuccesful.value=false
                }
            }.addOnFailureListener {

            }
    }
}