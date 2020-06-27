package com.example.monpfe.ui.Urgence



import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.monpfe.R
import kotlinx.android.synthetic.main.fragment_urgence.*




class UrgenceFragment : Fragment() {

    val NumeroUrgence = "190"
    val REQUEST_PHONE_CALL = 1
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_urgence, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appel_urgence.setOnClickListener{

            if (ContextCompat.checkSelfPermission(
                    view.context,
                    android.Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(view.context as Activity,arrayOf(android.Manifest.permission.CALL_PHONE),REQUEST_PHONE_CALL)

            } else {
                startcall()
            }

        }
    }
    private fun startcall(){
        val callIntent = Intent(Intent.ACTION_CALL)

        callIntent.data  = Uri.parse("tel:" + NumeroUrgence)

        startActivity(callIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PHONE_CALL)startcall()
    }

}