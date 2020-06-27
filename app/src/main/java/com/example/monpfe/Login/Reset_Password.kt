package com.example.monpfe.Login

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.monpfe.R
import com.example.monpfe.custome_application
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.reset_password.*
import kotlinx.android.synthetic.main.reset_password.view.*

class Reset_Password: DialogFragment() {
    lateinit var v: View
    private var mAuth: FirebaseAuth? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = LayoutInflater.from(context).inflate(R.layout.reset_password, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reset_mdp.setOnClickListener {


            mAuth = FirebaseAuth.getInstance()

            val email = login_User.text.toString().trim()

            if (!email.isNotEmpty()) {
                Toast.makeText(requireContext(), "Entrer votre email!", Toast.LENGTH_SHORT).show()
            } else {
                mAuth!!.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "Vérifiez l'email pour réinitialiser votre mot de passe!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Impossible d'envoyer un e-mail de réinitialisation du mot de passe!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }

        }
    }
}