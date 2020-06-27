package com.example.monpfe


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.monpfe.Agenda.rendezv
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_agenda.*
import kotlinx.android.synthetic.main.fragment_agenda.view.*



class AgendaFragment : Fragment() {

    lateinit var v: View
    lateinit var array: ArrayList<rendezv>
    lateinit var layoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_agenda, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rdv.setOnClickListener {

            val intent = Intent(context, Rendez_vousActivity::class.java)
            startActivity(intent)
        }


        array = ArrayList()
        layoutManager =  LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        v.recycler_rdv.layoutManager = layoutManager
        Grab()
        v.recycler_rdv.adapter = recycler_rdv(this,requireContext(), array)
        val divider = DividerItemDecoration(context, LinearLayout.HORIZONTAL)
        var drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.recycler_divider, null)
        divider.setDrawable(drawable!!)
        v.recycler_rdv.addItemDecoration(divider)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recycler_rdv)


    }

     fun Grab(fn:String?=null) {

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
                        i.ref.removeValue().addOnCompleteListener {
                            if (it.isSuccessful)
                                add(p0)
                        }

                    }
                    fn?.apply {
                        if (i.child("typer").getValue().toString() ==this) {
                            i.ref.removeValue().addOnCompleteListener {
                                Toast.makeText(requireContext(),if (it.isSuccessful) "Rendez-vous Supprimer".also {
                                    array.forEach { if (it.type==this) array.remove(it) }
                                    v.recycler_rdv.adapter?.notifyDataSetChanged()
                                } else "nope",
                                    Toast.LENGTH_LONG).show()


                            }
                        }
                    }
                }

            }
        })

    }

    fun add(d: DataSnapshot) {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val id =currentUser!!.uid
        for (i in d.children){
            FirebaseAuth.getInstance().currentUser?.let {
                if (i.child("email").getValue().toString()==it.email.toString()){
                    var type = i.child("typer").getValue().toString()
                    var dater = i.child("dater").getValue().toString()
                    var horairer = i.child("horairer").getValue().toString()
                    val r=rendezv(type,dater,horairer)
                    Log.i("myapp","Yours : "+r.toString())
                    if (!array.contains(r)){
                        array.add(r)
                        v.recycler_rdv.adapter?.notifyDataSetChanged()
                    }

                }
                Log.i("myapp","All : "+i.child("email").getValue().toString())
            }
        }

        array.forEach {
            if (it.dater=="  ")
                array.remove(it)
            v.recycler_rdv.adapter?.notifyDataSetChanged()
        }

    }
}
