package com.example.monpfe

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.getSystemService
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.monpfe.Profile.ProfileFragment
import com.example.monpfe.ui.Deplacement.DeplacementFragment
import com.example.monpfe.ui.Deplacement.OnReplaceFragment
import com.example.monpfe.ui.Urgence.UrgenceFragment
import com.example.monpfe.ui.conseil.ConseilFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseUser


class HomeActivity : AppCompatActivity(), OnReplaceFragment {
    lateinit var user: FirebaseUser
    override fun onReplacefragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, fragment, fragment.javaClass.getSimpleName())
            .addToBackStack(null)
            .commit()

    }


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navView: NavigationView
    lateinit var fragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        add.setOnClickListener {
            val intent = Intent(this, Ajouter_MedicamentActivity::class.java)
            startActivity(intent)
        }

        tool.setNavigationIcon(ResourcesCompat.getDrawable(resources,R.drawable.ic_nav,null))
        tool.setNavigationOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }
       //setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        //val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_conseil,
                R.id.nav_urgence, R.id.nav_deplacement
            ), drawerLayout
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        //navView.setupWithNavController(navController)


        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        if (savedInstanceState == null) {
            fragment = HomeFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                .commit()
        }

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_conseil -> {
                    fragment = ConseilFragment(this)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                        .commit()

                }

                R.id.nav_urgence -> {
                    fragment = UrgenceFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                }
                R.id.nav_deplacement -> {
                    fragment = DeplacementFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                }
            }
            drawerLayout.closeDrawers()
            return@setNavigationItemSelectedListener true
        }
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    fragment = HomeFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.notification -> {
                    fragment = NotificationFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    fragment = ProfileFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                    return@OnNavigationItemSelectedListener true // request permission l android 6+
                }
                R.id.evenement -> {
                    fragment = AgendaFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }

            }
            false
        }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }



}


