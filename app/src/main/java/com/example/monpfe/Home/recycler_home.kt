package com.example.monpfe.Home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.monpfe.Agenda.remove
import com.example.monpfe.Ajouter_MedicamentActivity
import com.example.monpfe.HomeFragment
import com.example.monpfe.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.recycler_home.view.*

class recycler_home(var h: HomeFragment, var c: Context, var array:ArrayList<medicament>) :
    RecyclerView.Adapter<recycler_home.secondhold>() {
    var mypos = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): secondhold {
        return secondhold(LayoutInflater.from(c).inflate(R.layout.recycler_home, parent, false))
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: secondhold, position: Int) {
        holder.apply {

            nom.setText("nom : " + array[position].nom)
            forme.setText("Forme : " + array[position].forme)
            utilisateur.setText("Utilisateur : " + array[position].utilisateur)
            horairet.setText("Horaire de prise : " + array[position].horairet)
            moment.setText("Moment de prise : " + array[position].moment)


        }

        holder.itemView.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {

                var popup = PopupMenu(c, holder.itemView)

                popup.inflate(R.menu.mymenu)
                val firebaseDatabase = FirebaseDatabase.getInstance().getReference("medicaments")
                popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(item: MenuItem?): Boolean {
                        when (item!!.itemId) {
                            R.id.del -> {
                                val nom = array[position]
                                firebaseDatabase.addListenerForSingleValueEvent(object :
                                    ValueEventListener {
                                    override fun onDataChange(p0: DataSnapshot) {
                                        for(ds in p0.children){
                                            if (ds.child("nom").value == nom ){
                                                ds.ref.removeValue()
                                                array.clear()
                                                notifyDataSetChanged()
                                            }

                                        }

                                    }

                                    override fun onCancelled(p0: DatabaseError) {

                                    }
                                })
                            }
                           R.id.upd ->{
                           var i= Intent(c,Ajouter_MedicamentActivity::class.java)
                               i.putExtra("update",array[position].nom)
                               i.putExtra("data_m",array[position])
                               c.startActivity(i)}

                        }
                        return true
                    }
                })

                popup.show()
                return true
            }

        })
    }

    inner class secondhold(v: View) : RecyclerView.ViewHolder(v) {

        var nom = v.nom_rec
        var forme = v.forme_rec
        var utilisateur = v.utilisation_rec
        var horairet = v.horaire_rec
        var moment = v.momoent_p_rec

    }

  /*  fun upd(name: String) {

        val ref = FirebaseDatabase.getInstance()
            .getReference().ref.child("medicaments")

        var hash = HashMap<String, Any>()
        var key = ref.push().key
        ref.updateChildren(hash)
        var child = ref.child(key!!)
        var hash2 = HashMap<String, Any>().also {

            it.apply {
                put("email", FirebaseAuth.getInstance().currentUser!!.email!!)
                put(" medicamentId", "  ")
                put("nom", "  ")
                put("forme", "  ")
                put("utilisateur", "  ")
                put("nbr", "  ")
                put("duret", "  ")
                put("horairet", "  ")
                put("moment", "  ")

            }
        }
        child.updateChildren(hash2)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children) {
                    if (i.child("nom").getValue().toString() == "  ") {
                        i.ref.removeValue()
                    }
                    if (i.child("nom").getValue().toString() == name) {
                        i.ref.removeValue().addOnCompleteListener {
                            Toast.makeText(
                                c,
                                if (it.isSuccessful) "deleted ! " else "erreur",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        })

    }*/

   /* fun del(name: String) {

        val ref = FirebaseDatabase.getInstance()
            .getReference().ref.child("medicaments")

        var hash = HashMap<String, Any>()
        var key = ref.push().key
        ref.updateChildren(hash)
        var child = ref.child(key!!)
        var hash2 = HashMap<String, Any>().also {

            it.apply {
                put("email", FirebaseAuth.getInstance().currentUser!!.email!!)
                put(" medicamentId", "  ")
                put("nom", "  ")
                put("forme", "  ")
                put("utilisateur", "  ")
                put("nbr", "  ")
                put("duret", "  ")
                put("horairet", "  ")
                put("moment", "  ")

            }
        }
        child.updateChildren(hash2)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children) {
                    if (i.child("nom").getValue().toString() == "  ") {
                        i.ref.removeValue()
                    }

                }
            }
        })

    }

*/
}


