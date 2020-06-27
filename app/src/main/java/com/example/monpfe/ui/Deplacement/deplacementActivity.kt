package com.example.monpfe.ui.Deplacement

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil.setContentView
import com.example.monpfe.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_deplacement.*
import kotlinx.android.synthetic.main.medicament_deplacement.*
import kotlinx.android.synthetic.main.medicament_deplacement.view.*

class deplacementActivity : AppCompatActivity() {
    var value:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.medicament_deplacement)

        try {
            value = intent.extras!!.getString("data")
            val r = intent.getSerializableExtra("data") as deplacement_data
            value?.apply {
                medicament_en_deplacement.setText(r.nom)
                val builder = AlertDialog.Builder(this@deplacementActivity)
                builder.setTitle("Nom du médicament")
                val view = layoutInflater.inflate(R.layout.medicament_deplacement, null)
                builder.setView(view)
                builder.setPositiveButton("modifier", DialogInterface.OnClickListener { _, _ ->
                    Grab( view.medicament_en_deplacement.text.toString())
                })
                builder.setNegativeButton("Annuler", DialogInterface.OnClickListener { _, _ ->

                })
                builder.show()

            }
        } catch (e: NullPointerException) {

        }
    }

    fun Grab(aa:String) {

        val ref = FirebaseDatabase.getInstance()
            .getReference().ref.child("deplacement")
        var hash = HashMap<String, Any>()
        var key=ref.push().key
        ref.updateChildren(hash)
        var child=ref.child(key!!)
        var hash2=HashMap<String, Any>().also {

            it.apply {
                put("email", FirebaseAuth.getInstance().currentUser!!.email!!)
                put("nom","  ")

            }

        }
        child.updateChildren(hash2)

        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children) {
                    if (i.child("nom").getValue().toString() == "  ") {
                        i.ref.removeValue()
                    }
                    if (i.child("nom").getValue().toString()==aa){
                        var hash3=HashMap<String, Any>().also {
                            it.apply {
                                put("email",FirebaseAuth.getInstance().currentUser!!.email!!)
                                put("nom",medicament_en_deplacement.text.toString())

                            }
                        }
                        i.ref.updateChildren(hash3).addOnCompleteListener {
                            if (it.isSuccessful){
                                Toast.makeText(baseContext,"updated !",Toast.LENGTH_LONG).show()
                                startActivity(Intent(baseContext,DeplacementFragment::class.java))
                                return@addOnCompleteListener
                            }
                            Toast.makeText(baseContext,it.exception?.message.toString(),Toast.LENGTH_LONG).show()
                        }

                    }

                }

            }
        })

    }


}