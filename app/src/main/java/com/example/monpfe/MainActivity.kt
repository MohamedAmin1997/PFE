package com.example.monpfe

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity(){
lateinit var firebaseAuth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fun getConnectionType(context: Context): Int {
            var result = 0 // Returns connection type. 0: none; 1: mobile data; 2: wifi
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cm?.run {
                    cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                        if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                            result = 2
                        } else if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                            result = 1
                        } else if (hasTransport(NetworkCapabilities.TRANSPORT_VPN)){
                            result = 3
                        }
                    }
                }
            } else {
                cm?.run {
                    cm.activeNetworkInfo?.run {
                        if (type == ConnectivityManager.TYPE_WIFI) {
                            result = 2
                        } else if (type == ConnectivityManager.TYPE_MOBILE) {
                            result = 1
                        } else if(type == ConnectivityManager.TYPE_VPN) {
                            result = 3
                        }
                    }
                }
            }
            return result
        }



           }

}
