package com.example.monpfe
import android.content.Context
import android.content.Intent
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.monpfe.Agenda.rendezv
import kotlinx.android.synthetic.main.recycler_rdv.view.*
import kotlinx.android.synthetic.main.recycler_rdv.view.date_rdv


class recycler_rdv(

    var h:AgendaFragment,
    var c: Context, var array: ArrayList<rendezv>
) :
    RecyclerView.Adapter<recycler_rdv.myhold>() {
    var mypos=-1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myhold {
        return myhold(LayoutInflater.from(c).inflate(R.layout.recycler_rdv,parent,false))
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder:myhold, position: Int) {
        holder.apply {
            name.setText("Titre : "+array[position].type)
            date.setText("Date: "+array[position].dater)
            heure.setText("Heure : "+array[position].horairer)

        }


        holder.itemView.setOnLongClickListener (object:View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {

                var popup = PopupMenu(c, holder.itemView)

                popup.inflate(R.menu.mymenu)

                popup.setOnMenuItemClickListener(object:PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(item: MenuItem?): Boolean {
                        when(item!!.itemId){
                            R.id.del -> h.Grab(array[position].type)
                            R.id.upd ->{
                                var i=Intent(c,Rendez_vousActivity::class.java)
                                i.putExtra("update",array[position].type)
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
        var name=v.nom_rdv
        var date = v.date_rdv
        var heure = v.heure_rdv

    }

}
