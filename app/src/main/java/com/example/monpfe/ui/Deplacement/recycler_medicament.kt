package com.example.monpfe
import android.content.Context
import android.content.Intent
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import kotlinx.android.synthetic.main.reycler_deplacement.view.name
import androidx.recyclerview.widget.RecyclerView
import com.example.monpfe.ui.Deplacement.DeplacementFragment
import com.example.monpfe.ui.Deplacement.deplacementActivity
import com.example.monpfe.ui.Deplacement.deplacement_data
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener


class recycler_medicament(var f:DeplacementFragment,var c: Context, var array:ArrayList<String>) :
    RecyclerView.Adapter<recycler_medicament.myhold>() {
    var mypos=-1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recycler_medicament.myhold {
        return myhold(LayoutInflater.from(c).inflate(R.layout.reycler_deplacement,parent,false))
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder:myhold, position: Int) {
        holder.name.text=array[position]

        holder.itemView.setOnLongClickListener (object:View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {

                var popup = PopupMenu(c, holder.itemView)

                popup.inflate(R.menu.mymenu)
                val firebaseDatabase = FirebaseDatabase.getInstance().getReference("deplacement")
                popup.setOnMenuItemClickListener(object:PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(item: MenuItem?): Boolean {
                        when(item!!.itemId){

                            R.id.del ->
                            {
                                val name = array[position]
                                firebaseDatabase.addListenerForSingleValueEvent(object :
                                ValueEventListener{
                                    override fun onDataChange(p0: DataSnapshot) {
                                        for(ds in p0.children){
                                            if (ds.child("nom").value == name ){
                                                    ds.ref.removeValue()
                                                array.removeAt(position)
                                                notifyDataSetChanged()
                                            }
                                        }
                                    }

                                    override fun onCancelled(p0: DatabaseError) {

                                    }
                                })
                            }

                            R.id.upd ->{
                                var i= Intent(c,deplacementActivity::class.java)
                                i.putExtra("update",array[position])
                                i.putExtra("data",array[position])
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

    inner class myhold(v:View):RecyclerView.ViewHolder(v){
        var name=v.name

    }



    fun rem(old:String,new:String){

        val reference = FirebaseDatabase.getInstance().getReference("deplacement")
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    if (ds.child("deplacement").child("nom").getValue()!!.equals(old)) {
                        ds.child("deplacement").child("nom").getRef().removeValue()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }


        })
    }


}