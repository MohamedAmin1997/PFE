package com.example.monpfe.ui.Deplacement


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monpfe.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_deplacement.*
import kotlinx.android.synthetic.main.fragment_deplacement.view.*
import kotlinx.android.synthetic.main.medicament_deplacement.*
import kotlinx.android.synthetic.main.medicament_deplacement.view.*
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseReference
import org.json.JSONArray
import org.json.JSONObject


@Suppress("UNREACHABLE_CODE")
class DeplacementFragment : Fragment() {
    lateinit var v: View
    var mypos=-1
    lateinit var array: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_deplacement, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        array = ArrayList()
        v.recycler.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        Deplacement("  ")
        v.recycler.adapter = recycler_medicament(this,requireContext(), array)

        v.btn_deplacement.setOnClickListener {
            onclick()
        }
    }

    fun onclick() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Nom du médicament")
        val view = layoutInflater.inflate(R.layout.medicament_deplacement, null)
        builder.setView(view)
        builder.setPositiveButton("Ajouter", DialogInterface.OnClickListener { _, _ ->

                Deplacement(view.medicament_en_deplacement.text.toString())


        })
        builder.setNegativeButton("Annuler", DialogInterface.OnClickListener {_, _ ->

        })
        builder.show()
    }

     fun Deplacement(nom: String) {

        val ref = FirebaseDatabase.getInstance()
            .getReference().ref.child("deplacement")
        var hash = HashMap<String, Any>()
        var key=ref.push().key
        ref.updateChildren(hash)
        var child=ref.child(key!!)
        var hash2=HashMap<String, Any>().also {
            it.put("email",FirebaseAuth.getInstance().currentUser!!.email!!)
            it.put("nom",nom)
        }
        child.updateChildren(hash2)





        ref.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children) {

                    if (i.child("nom").getValue().toString() ==" ") {
                        i.ref.removeValue().addOnCompleteListener {
                            if (it.isSuccessful)
                                add(p0)
                        }
                    }
                }
                add(p0)

            }
        })

    }

    fun add(d: DataSnapshot) {
        for (i in d.children) {
            FirebaseAuth.getInstance().currentUser?.let {
                if (i.child("email").getValue().toString()==it.email.toString()){
                    var nom = i.child("nom").getValue().toString()
                    if (!array.contains(nom)){
                        array.add(nom)
                     if (array.contains("  ")){
                         array.remove("  ")
                     }
                        v.recycler.adapter?.notifyDataSetChanged()
                    }

                }
                Log.i("myapp","All : "+i.child("email").getValue().toString())
            }

        }
    }

}


