package com.example.monpfe

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.monpfe.Home.ajouter_medicament_data
import com.example.monpfe.Home.medicament
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.ajouter_medicament.*
import java.text.SimpleDateFormat
import java.util.*

class Ajouter_MedicamentActivity : AppCompatActivity() {
    private var firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var titlem : TextView
    lateinit var grouper : RadioGroup
    lateinit var radio1b : RadioButton
    lateinit var radio2b : RadioButton
    lateinit var radio3b : RadioButton
    lateinit var radio4b : RadioButton

    lateinit var array:ArrayList<medicament>
    lateinit var nom:String
    lateinit var utilisateur:String
    lateinit var nbr:String
    lateinit var duret:String
    lateinit var forme:String
    lateinit var moment:String
    lateinit var horairet:String
    lateinit var medicamentId:String
    var update=false

    var value:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ajouter_medicament)
        val forme_ph = resources.getStringArray(R.array.forme_ph)
        val user_med = resources.getStringArray(R.array.user_med)

        try{
            value=intent.extras!!.getString("update")
            value?.apply {
                val ms=intent.getSerializableExtra("data_m") as medicament
                nom_medi.setText(ms.nom)
                forme_m.setText(ms.forme)
                utilisateur_m.setText(ms.utilisateur)
                horaire.setText(ms.horairet)
                moment_m.setText(findViewById<RadioButton>(group.checkedRadioButtonId).text.toString())
                ajouter_btn.text="Modifier"
            }
        }catch (e:NullPointerException){

        }
        medicamentId = FirebaseAuth.getInstance().currentUser!!.uid


        titlem = findViewById(R.id.title)
        grouper = findViewById(R.id.group)
        radio1b = findViewById(R.id.radio1)
        radio2b = findViewById(R.id.radio2)
        radio3b = findViewById(R.id.radio3)
        radio4b = findViewById(R.id.radio4)






        if (spinner1 != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, forme_ph
            )
            spinner1.adapter = adapter
        }


        if (spinner2 != null) {
            val adapter2 = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, user_med
            )
            spinner2.adapter = adapter2
        }




            horairep.setOnClickListener {
                val cal = Calendar.getInstance()
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                        cal.set(Calendar.HOUR_OF_DAY, hour)
                        cal.set(Calendar.MINUTE, minute)
                        horaire.text = SimpleDateFormat("HH:mm").format(cal.time)?:"Null here"
                    }
                TimePickerDialog(
                    this,
                    timeSetListener,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    true
                ).show()
            }


            group.setOnCheckedChangeListener { group, checkedId ->
                Toast.makeText(
                    this, when (group.checkedRadioButtonId) {
                        R.id.radio1 -> radio1.text.toString()
                        R.id.radio2 -> radio2.text.toString()
                        R.id.radio3 -> radio3.text.toString()
                        R.id.radio4 -> radio4.text.toString()
                        else -> ""
                    }, Toast.LENGTH_SHORT
                ).show()
            }

            ajouter_btn.setOnClickListener {

                 try {
                if (value!=null){
                    Grab(value!!)
                }
                else {
                   enregistrer()
                    Log.i("myapp","desolé"
                    )
                }
            }catch (e:Exception){
                Log.i("myapp",e.message.toString())
            }

            }

    }
    private fun enregistrer(){




                if (nom_medi.text.toString().isNullOrEmpty()) {
                    Toast.makeText(this, "Entrer le nom du médicament ", Toast.LENGTH_SHORT).show()
                }
                if (forme_m.text.toString().isNullOrEmpty()) {
                    Toast.makeText(
                        this,
                        "Entrer la forme pharmacetique du médicament",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                if (utilisateur_m.text.toString().isNullOrEmpty()) {
                    Toast.makeText(this, "Entrer l'utilisateur du médicament", Toast.LENGTH_SHORT)
                        .show()
                }

                if (horaire.text.toString().isNullOrEmpty()) {
                    Toast.makeText(
                        this,
                        "Entrer l'heure du prise du médicament",
                        Toast.LENGTH_SHORT
                    ).show()
                }

        val email=FirebaseAuth.getInstance().currentUser!!.email.toString()

        val ref = FirebaseDatabase.getInstance().getReference("medicaments")

        val currentUser = firebaseAuth.currentUser



        var hash = HashMap<String, Any>()
        var key=ref.push().key
        ref.updateChildren(hash)
        var child=ref.child(key!!)
        var hash2=HashMap<String, Any>().also {

            it.apply {
                put("email",FirebaseAuth.getInstance().currentUser!!.email!!)
                put("medicamentId",medicamentId)
                put("nom",nom_medi.text.toString())
                put("forme",spinner1.selectedItem.toString())
                put("utilisateur",spinner2.selectedItem.toString())
                put("nbr", nmbrp.text.toString())
                put("duret",duret_m.text.toString())
                put("horairet",horaire.text.toString())
                put("moment",(findViewById<RadioButton>(group.checkedRadioButtonId).text.toString()))

            }
        }

            child.updateChildren(hash2).addOnCompleteListener {
                if (it.isSuccessful) {
                    startActivity(Intent(baseContext,HomeFragment::class.java))
                   /* supportFragmentManager.beginTransaction()
                        .replace(R.id.container, HomeFragment())
                        .commit()
                    return@addOnCompleteListener*/
                }
                else Toast.makeText(baseContext,it.exception!!.message.toString(),Toast.LENGTH_LONG).show()

            }


    }

    fun Grab(aa:String) {

        val ref = FirebaseDatabase.getInstance()
            .getReference().ref.child("medicaments")
        var hash = HashMap<String, Any>()
        var key=ref.push().key
        ref.updateChildren(hash)
        var child=ref.child(key!!)
        var hash2=HashMap<String, Any>().also {
            it.apply {
                put("email",FirebaseAuth.getInstance().currentUser!!.email!!)
                put("medicamentId","  ")
                put("nom","  ")
                put("forme","  ")
                put("utilisateur","  ")
                put("nbr","  ")
                put("duret","  ")
                put("horairet","  ")
                put("moment","  ")
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
                    if (i.child("nom").getValue().toString() ==aa){
                        var hash3=HashMap<String, Any>().also {

                            it.apply {
                                put("email",FirebaseAuth.getInstance().currentUser!!.email!!)
                                put(" medicamentId",medicamentId)
                                put("nom",nom_medi.text.toString())
                                put("forme",spinner1.selectedItem.toString())
                                put("utilisateur",spinner2.selectedItem.toString())
                                put("nbr",nmbrp.text.toString())
                                put("duret",duret_m.text.toString())
                                put("horairet",horaire.text.toString())
                                put("moment",findViewById<RadioButton>(group.checkedRadioButtonId).text.toString())

                            }
                        }
                        i.ref.updateChildren(hash3).addOnCompleteListener {
                            if (it.isSuccessful){
                                Toast.makeText(baseContext,"updated",Toast.LENGTH_LONG).show()
                                startActivity(Intent(baseContext, HomeFragment::class.java))
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