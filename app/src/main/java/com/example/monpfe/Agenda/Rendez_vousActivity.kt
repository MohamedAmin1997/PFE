package com.example.monpfe

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.monpfe.Agenda.rendezv
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.rendez_vous.*
import java.lang.Exception
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.util.*

class Rendez_vousActivity : AppCompatActivity() {
    private var firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var titre : TextView
    lateinit var array:ArrayList<rendezv>
    lateinit var typer:String
    lateinit var dater:String
    lateinit var horairer:String
    lateinit var rendez_vousID:String
    var update=false
    var value:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rendez_vous)

        titre = findViewById(R.id.titre_rdv)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        try{
            value=intent.extras!!.getString("update")
            value?.apply {
                val r=intent.getSerializableExtra("data") as rendezv
                type_r.setText(r.type)
                date_r.setText(r.dater)
                horaire_r.setText(r.horairer)
                valider_rdv.text="Modifier"
            }
        }catch (e:NullPointerException){

        }

        rendez_vousID= FirebaseAuth.getInstance().currentUser!!.uid
        date_rdv.setOnClickListener {

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                date_r.setText("" + dayOfMonth + " " + month + ", " + year)
            }, year, month, day)
            dpd.show()

        }



        horaire_rdv.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                horaire_r.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }
        valider_rdv.setOnClickListener {
            try {
                if (value!=null){
                    Grab(value!!)
                }
                else {
                    valider()
                }
            }catch (e:Exception){
                Log.i("myapp",e.message.toString())
            }

        }
    }
    private fun valider(){

        if (type_r.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, "Entrer le Type du Rendez-vous ", Toast.LENGTH_SHORT).show()
        }
        if (date_r.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, "Entrer le Date du Rendez-vous ", Toast.LENGTH_SHORT).show()
        }
        if (horaire_r.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, "Entrer l'heure du Rendez-vous ", Toast.LENGTH_SHORT).show()
        }


        val email= FirebaseAuth.getInstance().currentUser!!.email.toString()
        val ref = FirebaseDatabase.getInstance().getReference("rendez-vous")
        val currentUser = firebaseAuth.currentUser



        var hash = HashMap<String, Any>()
        var key=ref.push().key
        ref.updateChildren(hash)
        var child=ref.child(key!!)
        var hash2=HashMap<String, Any>().also {
            it.apply {
               put("email",FirebaseAuth.getInstance().currentUser!!.email!!)
               put("rendez-vousID",rendez_vousID)
               put("typer",type_r.text.toString())
               put("dater",date_r.text.toString())
               put("horairer",horaire_r.text.toString())

            }


        }

                child.updateChildren(hash2).addOnCompleteListener {
                   if (it.isSuccessful)
                       startActivity(Intent(baseContext,AgendaFragment::class.java))

                    else Toast.makeText(baseContext,it.exception!!.message.toString(),Toast.LENGTH_LONG).show()

                }
            }





    fun Grab(aa:String) {

        val ref = FirebaseDatabase.getInstance()
            .getReference().ref.child("rendez-vous")
        var hash = HashMap<String, Any>()
        var key=ref.push().key
        ref.updateChildren(hash)
        var child=ref.child(key!!)
        var hash2=HashMap<String, Any>().also {

            it.apply {
                put("email",FirebaseAuth.getInstance().currentUser!!.email!!)
                put("rendez-vousID","  ")
                put("typer","  ")
                put("dater", "  ")
                put("horairer","  ")
            }

        }
        child.updateChildren(hash2)

        ref.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children) {
                    if (i.child("typer").getValue().toString() == "  ") {
                        i.ref.removeValue()
                    }
                    if (i.child("typer").getValue().toString()==aa){
                        var hash3=HashMap<String, Any>().also {
                            it.apply {
                                put("email",FirebaseAuth.getInstance().currentUser!!.email!!)
                                put("rendez-vousID",rendez_vousID)
                                put("typer",type_r.text.toString())
                                put("dater", date_r.text.toString())
                                put("horairer",horaire_r.text.toString())
                            }
                        }
                        i.ref.updateChildren(hash3).addOnCompleteListener {
                            if (it.isSuccessful){
                                Toast.makeText(baseContext,"updated !",Toast.LENGTH_LONG).show()
                                startActivity(Intent(baseContext,AgendaFragment::class.java))
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


