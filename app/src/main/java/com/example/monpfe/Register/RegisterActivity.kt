package com.example.monpfe.Register

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.view.Window
import android.view.WindowManager

import android.widget.Toast

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.monpfe.Ajouter_MedicamentActivity
import com.example.monpfe.R

import kotlinx.android.synthetic.main.activity_register.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.monpfe.Login.LoginActivity
import com.example.monpfe.SplashScreen.SplashState


class RegisterActivity: AppCompatActivity() {
    private lateinit var viewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(com.example.monpfe.R.layout.activity_register)

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        create_btn.setOnClickListener {

            if (user_name.text.toString().trim().isNullOrEmpty()) {
                Toast.makeText(this, "Entrer votre votre nom ", Toast.LENGTH_SHORT).show()
            } else if (user_email.text.toString().trim().isNullOrEmpty()) {
                Toast.makeText(this, "Entrer votre votre email ", Toast.LENGTH_SHORT).show()
            } else if (user_phone.text.toString().trim().isNullOrEmpty()) {
                Toast.makeText(this, "Entrer votre votre numero du téléphone ", Toast.LENGTH_SHORT)
                    .show()
            } else if (user_pass.text.toString().trim().isNullOrEmpty()) {
                Toast.makeText(this, "Entrer votre votre mot de passe  ", Toast.LENGTH_SHORT).show()
            } else if (user_pass.text.toString().trim().length < 6) {
                Toast.makeText(
                    this,
                    "mot de passe doit etre superieur a 6 caractères et contient au minimum un chiffre ",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (conf_pass.text.toString().trim().isNullOrEmpty() && (!user_pass.text.toString().trim().equals(
                    conf_pass.text.toString().trim()
                ))
            ) {
                Toast.makeText(this, "verifier votre mot de passe   ", Toast.LENGTH_SHORT)
                    .show()
            } else {
                viewModel.requestRegister(

                    user_email.text.toString(),
                    user_name.text.toString(),
                    user_phone.text.toString(),
                    user_pass.text.toString(),
                    conf_pass.text.toString()
                )
            }

            viewModel.isSuccesful.observe(this, Observer {
                var message = ""
                if (it == true) {
                    message = "welcome"
                    startActivity(Intent(this, Ajouter_MedicamentActivity::class.java))
                    finish()
                } else {
                    message = "Authentification échouée."
                }
                Toast.makeText(application, message, Toast.LENGTH_SHORT).show()
            })

        }

    }
    override fun onBackPressed() {
        startActivity(Intent(this, LoginActivity::class.java))
        return
    }
}


/*

class RegisterActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var mProgressbar: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        mAuth = FirebaseAuth.getInstance()
        mProgressbar = ProgressDialog(this)
        try {

            create_btn.setOnClickListener {
                val email = user_email.text.toString().trim()
                val password = user_pass.text.toString().trim()
                val name = user_name.text.toString().trim()
                val confpass = conf_pass.text.toString().trim()
                val phone = user_phone.text.toString().trim()

                if (TextUtils.isEmpty(email)) {
                    user_email.error = "Entrer votre Email"
                    return@setOnClickListener
                }
                if (TextUtils.isEmpty(password)) {
                    user_pass.error = "Mot de passe doit etre superieure a 6 chiffres "
                    return@setOnClickListener
                }
                if (TextUtils.isEmpty(name)) {
                    user_name.error = "Entrer votre Prénom"
                    return@setOnClickListener
                }
                if (TextUtils.isEmpty(confpass) || (!confpass.equals(password))) {
                    conf_pass.error = "Vérifiez votre mot de passe "
                    return@setOnClickListener
                } else if (TextUtils.isEmpty(phone)) {
                    user_phone.error = "Entrer votre Numéro de Téléphone"
                    return@setOnClickListener
                }

                val ref = FirebaseDatabase.getInstance().getReference("Utilisateurs")
                ref.addValueEventListener(object:ValueEventListener{
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
                val utilisateurID = ref.push().key
                val utilisateur = utilisateur_data(
                    utilisateurID!!,
                    name,
                    email,
                    phone,
                    password,
                    confpass
                )
                utilisateurID?.let { ref.child(it).setValue(utilisateur).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(applicationContext,"Bienvenue", Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }
                    Toast.makeText(applicationContext,"Erreur", Toast.LENGTH_SHORT).show()
                } }

                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {

                            startActivity(Intent(this, Ajouter_MedicamentActivity::class.java))
                            finish()
                        } else {
                            Log.d("TAG", "Authentification échouée.")
                            Toast.makeText(
                                baseContext,
                                "Authentification échouée.",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                    }.addOnFailureListener(OnFailureListener {
                        Log.e("FAILURE", it.message)
                    })
            }

        }catch (e:Exception){
            Toast.makeText(this@RegisterActivity,e.message.toString(),Toast.LENGTH_SHORT).show()
        }
    }
}*/