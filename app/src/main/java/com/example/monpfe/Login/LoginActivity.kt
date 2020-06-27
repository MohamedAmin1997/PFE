package com.example.monpfe.Login

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.monpfe.HomeActivity
import com.example.monpfe.HomeFragment
import com.example.monpfe.MainActivity
import com.example.monpfe.R
import com.example.monpfe.Register.RegisterActivity
import com.google.android.datatransport.runtime.backends.BackendFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.create_btn
import kotlinx.android.synthetic.main.activity_register.*


class LoginActivity  : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        login_btn.setOnClickListener {
            Log.d("login", "login here")



            if (login_User.text.toString().isNullOrEmpty()) {
                Toast.makeText(this , "Entrer votre votre email ", Toast.LENGTH_SHORT).show()

                //viewModel.requestLogin(login_User.text.toString(), login_password.text.toString())
            } else if (login_password.text.toString().isNullOrEmpty()) {
                Toast.makeText(this, "mot de passe inccorect", Toast.LENGTH_SHORT).show()
            }
            else {
                viewModel.requestLogin(login_User.text.toString(), login_password.text.toString())
            }

            val pref = getPreferences(Context.MODE_PRIVATE)
            val editor = pref.edit()

            editor.putString("email",login_User.text.toString() )
            editor.putString("password",login_password.text.toString())
            editor.commit()

        }
        viewModel.isSuccesful.observe(this, Observer {
            var message = ""
            if (it == true) {
                message = "Bienvenue"
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                message = "Erreur"
            }
            Toast.makeText(application, message, Toast.LENGTH_SHORT).show()
        })

        create_btn.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        for_pass.setOnClickListener {
            var dialogue2=Reset_Password()
            dialogue2.show((this
                    ).supportFragmentManager,null)


        }

    }


    private fun isValidData(): Boolean {

        val email = login_User.text.toString()
        val password = login_password.text.toString()

        if (!email.isNullOrEmpty()) {
            Toast.makeText(application, "Entrer votre Email", Toast.LENGTH_SHORT).show()
        }
        if (!password.isNullOrEmpty()) {
            Toast.makeText(application, "Mot de passe incorrect", Toast.LENGTH_SHORT).show()
        }
        return true

    }


}
/*

        mProgressbar = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()



        if (user != null) {
            val startIntent = Intent(applicationContext, HomeActivity::class.java)
            finish()
        }
        create_btn.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        login_btn.setOnClickListener {
            val email = login_User.text.toString().trim()
            val password = login_password.text.toString().trim()

            if (TextUtils.isEmpty(email)) {
                login_User.error = "Entrer votre Email"
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                login_User.error = "Entrer votre Mot de Passe"
                return@setOnClickListener
            }

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        mProgressbar.dismiss()
                        val startIntent = Intent(applicationContext, HomeActivity::class.java)
                        startActivity(startIntent)
                        finish()
                    } else {
                        Toast.makeText(this, "Authentification échouée.", Toast.LENGTH_SHORT).show()
                    }


                }
        }
        for_pass.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Mot de passe oublié")
            val view = layoutInflater.inflate(R.layout.dialog_forgot_password, null)
            val username = view.findViewById<EditText>(R.id.et_username)
            builder.setView(view)
            builder.setPositiveButton("Réinitialiser", DialogInterface.OnClickListener { _, _ ->
                forgotPassword(username)
            })
            builder.setNegativeButton("Fermer", DialogInterface.OnClickListener { _, _ -> })
            builder.show()
        }
    }

    private  fun forgotPassword(username : EditText) {

        if (username.text.toString().isEmpty()) {

            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()) {
            return
        }

        mAuth.sendPasswordResetEmail(username.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,"Email envoyer.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

*/



