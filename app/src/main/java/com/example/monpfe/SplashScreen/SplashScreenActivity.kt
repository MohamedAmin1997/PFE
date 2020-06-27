package com.example.monpfe.SplashScreenActivity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.monpfe.Login.LoginActivity
import com.example.monpfe.R
import com.example.monpfe.SplashScreen.SplashState
import com.example.monpfe.SplashScreen.SplashViewModel
import com.google.android.gms.common.api.Response

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val splashViewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        splashViewModel.liveData.observe(this, Observer {
            when (it) {
                is SplashState.LoginActivity -> {
                    goToLoginActivity()

            }

            }

        })
    }

    private fun goToLoginActivity() {
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
    }

}
/*
class SplashScreenActivity : AppCompatActivity() {
    val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        GlobalScope.launch(Dispatchers.Main) {
            delay(3000)

            var intent = Intent(this@SplashScreenActivity,LoginActivity::class.java)
            startActivity(intent)
            //  finish()
        }
    }
}
*/