package com.example.monpfe

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.monpfe.Login.LoginActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.changer_mdp.*

class Change_Password : DialogFragment() {
    lateinit var v:View
    lateinit var Auth:FirebaseAuth
   var user:FirebaseUser?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v=LayoutInflater.from(context).inflate(R.layout.changer_mdp,container,false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v.apply {
            changer_mdp2.setOnClickListener {
                changerMDP()
            }
        }

    }
    private fun changerMDP() {
        v.apply {
            if (ancien_mdp.text.toString().trim().isNotEmpty() && nouveau_mdp.text.toString().trim().isNotEmpty() && verifier_mdp.text.toString().trim().isNotEmpty())
            {
                if (nouveau_mdp.text.toString().equals(verifier_mdp.text.toString()))
                {
                    Auth=FirebaseAuth.getInstance()
                    user=Auth.currentUser
                    }
                    if (user != null && user?.email != null){
                        val credential = EmailAuthProvider
                            .getCredential(user?.email!!, ancien_mdp.text.toString())
                        user?.reauthenticate(credential)
                            ?.addOnCompleteListener{
                                if(it.isSuccessful){
                                    Toast.makeText(requireContext(),"Re-Authentification avec succes", Toast.LENGTH_SHORT).show()

                                    user?.updatePassword(nouveau_mdp.text.toString())
                                        ?.addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Toast.makeText(requireContext(),"Mot de passe est changé avec succés",
                                                    Toast.LENGTH_SHORT).show()
                                                dismiss()


                                                Auth.signOut()
                                                startActivity(Intent(requireContext(),
                                                    LoginActivity::class.java))
                                            }
                                        }
                                }else{
                                    Toast.makeText(requireContext(),"Re-Authentification est échoué", Toast.LENGTH_SHORT).show()
                                }
                            }


                    }else {
                        startActivity(Intent(requireContext(), LoginActivity::class.java))
                    }

                }else {
                    Toast.makeText(requireContext(),"Erreur de correspondance", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
