package com.example.monpfe
import android.os.Bundle

import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

import com.example.monpfe.Home.medicament
import com.example.monpfe.Home.recycler_home
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*

import kotlinx.android.synthetic.main.fragment_home.view.*


import java.lang.IllegalStateException
import java.text.ParseException
import java.text.SimpleDateFormat

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.absoluteValue


class HomeFragment: Fragment() {

    lateinit var v: View

    lateinit var array: ArrayList<medicament>
    var medicTime = ""
    lateinit var layoutManager: LinearLayoutManager
    var timer : Timer? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_home, container, false)
        return v


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        array = ArrayList()
        layoutManager =  LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        v.recycler_h.layoutManager = layoutManager
        Grab()
        v.recycler_h.adapter = recycler_home(this, requireContext(), array)
        val divider = DividerItemDecoration(context, LinearLayout.HORIZONTAL)
        divider.setDrawable(ResourcesCompat.getDrawable(resources,R.drawable.recycler_divider,null)!!)
        v.recycler_h.addItemDecoration(divider)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recycler_h)

        v.recycler_h.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val itemCountVisible = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                Log.e("Recycler",firstVisibleItem.toString())
                if (firstVisibleItem >= 0 ) {
                    medicTime = array.get(firstVisibleItem).horairet
                    showDifTime()
                }
            }
        })

        try{
            if(timer!=null)
                timer?.cancel()
        }catch (e : IllegalStateException){}


        if(timer == null)
            timer = Timer()
        val timerTask = object : TimerTask(){
            override fun run() {
                showDifTime()
            }
        }
        timer?.scheduleAtFixedRate(timerTask,0,60000)
    }

    fun Grab(fn:String?=null){

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
                put("forme", "  ")
                put("utilisateur","  ")
                put("nbr","  ")
                put("duret","  ")
                put("horairet","  ")
                put("moment","  ")

            }
        }
        child.updateChildren(hash2)

        ref.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children) {
                    if (i.child("nom").getValue().toString() == "  ") {
                        i.ref.removeValue().addOnCompleteListener {
                            if (it.isSuccessful)
                                add(p0)
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
        for (i in d.children) {
            FirebaseAuth.getInstance().currentUser?.let {
                if (i.child("email").getValue().toString()==it.email.toString()){
                    var nom = i.child("nom").getValue().toString()
                    var forme = i.child("forme").getValue().toString()
                    var utilisateur = i.child("utilisateur").getValue().toString()
                    var horairet = i.child("horairet").getValue().toString()
                    var moment = i.child("moment").getValue().toString()
                    val m=medicament(nom,forme,utilisateur,horairet,moment)
                    Log.i("myapp","Yours : "+m.toString())
                    medicTime = horairet
                    showDifTime()
                    if (!array.contains(m)){
                        array.add(m)
                        v.recycler_h.adapter?.notifyDataSetChanged()
                    }
                    v.recycler_h.adapter?.notifyDataSetChanged()
                }
                Log.i("myapp","All : "+i.child("email").getValue().toString())

            }

        }
        array.forEach {
            if (it.nom=="  ")
                array.remove(it)
            v.recycler_h.adapter?.notifyDataSetChanged()
        }
    }

    fun showDifTime(){
        val t = medicTime
        try{
            val fakeDateNow = SimpleDateFormat("dd/MM/yyyy").format(Date())


            var medicDateTimeSt = "$fakeDateNow $t"
            val sf = SimpleDateFormat("dd/MM/yyyy hh:mm")
            val medicDateTime = sf.parse(medicDateTimeSt)
            val nowDateTime = Date()

            if(nowDateTime.after(medicDateTime)){
                val diff = (nowDateTime.time - medicDateTime.time).absoluteValue

                val seconds = diff / 1000
                val minutes = seconds / 60


                var h = minutes /60
                var min = minutes % 60

                h =  ((24 * 60) - (h*60 + min) )/ 60
                min = ((24 * 60) - (h*60 + min) ) % 60
                v.text_view_countdown.setText("$h h : $min min")

            }else {
                val diff = (nowDateTime.time - medicDateTime.time).absoluteValue

                val seconds = diff / 1000
                val minutes = seconds / 60


                val h = minutes /60
                val min = minutes % 60
                v.text_view_countdown.setText("$h h : $min min ")
            }
        }catch (e : ParseException){
            Log.e("Time converter","Invalid time")
        }
    }
}
