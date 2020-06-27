package com.example.monpfe.ui.conseil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monpfe.ui.Deplacement.OnReplaceFragment
import com.example.monpfe.R
import kotlinx.android.synthetic.main.item_conseil.view.*

class Adapter(val conseils : ArrayList<Conseil>,val onReplaceFragment: OnReplaceFragment?) : RecyclerView.Adapter<Adapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_conseil,parent,false)
        return Holder(v)
    }

    override fun getItemCount(): Int {
        return conseils.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.title.text = conseils.get(position).title
    }

    inner class Holder(view : View) : RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener {
                val conseil = conseils.get(adapterPosition)
                val fragment = ConseiDetailFragment(conseil)
                onReplaceFragment?.onReplacefragment(fragment)
            }
        }
    }


}