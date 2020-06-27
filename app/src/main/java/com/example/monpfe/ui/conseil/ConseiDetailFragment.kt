package com.example.monpfe.ui.conseil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.monpfe.R
import kotlinx.android.synthetic.main.fragment_conseil_detail.*

class ConseiDetailFragment(val conseil: Conseil) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_conseil_detail,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title.text = conseil.title
        description.text = conseil.description

    }
}