package com.example.e_commerce2.activity

import android.animation.ArgbEvaluator
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.e_commerce2.*
import com.example.e_commerce2.Fragment.FavouriteFragment
import com.example.e_commerce2.Fragment.HomeFragment
import com.example.e_commerce2.Fragment.NotificationFragment
import com.example.e_commerce2.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle


class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var client: GoogleSignInClient
    lateinit var drawerLayout : DuoDrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setStatusBarColor(this, "#F8F9FA", true)

        auth = Firebase.auth


        val  options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            // dont worry about this error
            .requestEmail()
            .build()
        client = GoogleSignIn.getClient(this,options)

        if (auth.currentUser!!.photoUrl != null) {
            Picasso.get().load(auth.currentUser!!.photoUrl).into(binding.profileImageMainActivity)
        }
        binding.nameMainActivity.setText(auth.currentUser!!.displayName)

        replaceFragment(HomeFragment(),false)

        binding.bottomNavBar.onItemSelected = {
            when (it) {
                0 -> replaceFragment(HomeFragment(),false)
                1 -> replaceFragment(FavouriteFragment())
                2 -> replaceFragment(NotificationFragment())
//                3 -> replaceFragment(ProfileFragment())
            }
        }

        var toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        drawerLayout = binding.drawer
        val drawerToggle = DuoDrawerToggle(
            this, drawerLayout, toolbar,
            nl.psdcompany.psd.duonavigationdrawer.R.string.navigation_drawer_open,
            nl.psdcompany.psd.duonavigationdrawer.R.string.navigation_drawer_close)

        drawerLayout.setDrawerListener(drawerToggle)

        binding.backButton.setOnClickListener {
            drawerLayout.closeDrawer()
        }

        drawerLayout.setDrawerListener(object : DrawerLayout.DrawerListener {

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

                val colorFrom = Color.parseColor("#F8F9FA") // starting color (default)
                val colorTo = Color.parseColor("#1A2530") // ending color (when drawer is fully open)
                val color = ArgbEvaluator().evaluate(slideOffset, colorFrom, colorTo) as Int // interpolate between colors
                window.statusBarColor = color

                animateStatusBarColorChange(this@MainActivity, slideOffset < 0.5f)

                val fragment = supportFragmentManager.findFragmentById(R.id.fragment) as? HomeFragment
                fragment?.startSlider(false)
            }

            override fun onDrawerOpened(drawerView: View) {
//                var contentView = findViewById<View>(R.id.myContent)
//                contentView.setClickable(true)
                val fragment = supportFragmentManager.findFragmentById(R.id.fragment) as? HomeFragment
                fragment?.startSlider(false)
            }

            override fun onDrawerClosed(drawerView: View) {
//                setStatusBarColor(this@MainActivity, "#F8F9FA", true)
//                animateStatusBarColorChange("#F8F9FA")
                val fragment = supportFragmentManager.findFragmentById(R.id.fragment) as? HomeFragment
                fragment?.startSlider(true)
            }

            override fun onDrawerStateChanged(newState: Int) {
                // Optional: do something when the drawer state changes
            }
        })
        drawerToggle.syncState()

        binding.menuSignOut.setOnClickListener {
            auth.signOut()
            client.signOut().addOnCompleteListener {
                startActivity(Intent(this, LoginActivity::class.java))
            }

        }

        binding.menuFavourite.setOnClickListener {
            drawerLayout.closeDrawer()
            binding.bottomNavBar.itemActiveIndex = 1
            replaceFragment(FavouriteFragment(),true)
        }

        binding.menuProfile.setOnClickListener {
            drawerLayout.closeDrawer()
            startActivity(Intent(this@MainActivity,ProfileActivity::class.java))
        }

        binding.menuCart.setOnClickListener {
            drawerLayout.closeDrawer()
            startActivity(Intent(this,CartActivity::class.java))
        }

        binding.menuOrder.setOnClickListener {
            drawerLayout.closeDrawer()
            startActivity(Intent(this,OrderActivity::class.java))
        }

    }

    fun setStatusBarColor(activity: Activity, color: String, DarkText: Boolean) {
        val window = activity.window
        window.statusBarColor = Color.parseColor(color)
        if (DarkText) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    fun animateStatusBarColorChange(activity: Activity,isLightStatusBar: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = activity.window.decorView.systemUiVisibility
            flags = if (isLightStatusBar) {
                flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
            activity.window.decorView.systemUiVisibility = flags
        }
    }

    fun customProgressBar(context: Context, show: Boolean) {
        val progressDialog = Dialog(context)
        progressDialog.setContentView(R.layout.custom_progress_dialog)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        if (show) {
            progressDialog.show()
        } else {
            progressDialog.dismiss()
        }
    }

    private fun replaceFragment(fragment: Fragment, hidetoolbar : Boolean = true) {
        val myFragment = supportFragmentManager
        val fragmentTransaction = myFragment.beginTransaction()
        fragmentTransaction.replace(R.id.fragment, fragment)
        fragmentTransaction.commit()

        if (hidetoolbar)
        {
            binding.toolbar.visibility = View.GONE
        }
        else
        {
            binding.toolbar.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    override fun onRestart() {
        super.onRestart()
        binding.nameMainActivity.text = auth.currentUser!!.displayName
        Picasso.get().load(auth.currentUser!!.photoUrl).into(binding.profileImageMainActivity)
    }
}


