package com.example.foodanywhere

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.foodanywhere.view.apply.CuisineApplyFragment
import com.example.foodanywhere.view.nation.NationListFragment
import com.example.foodanywhere.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val userViewModel: UserViewModel by viewModels()

    companion object Info {
        lateinit var loginText: TextView
        lateinit var manageCook: MenuItem
        lateinit var manageCuisine: MenuItem
        lateinit var applyCuisine: MenuItem
        lateinit var applyCook: MenuItem
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
                setOf(
                        R.id.nav_home,
                        R.id.nav_login,
                        R.id.nav_recommendR,
                        R.id.nav_manageCook,
                        R.id.nav_manageCuisine,
                        R.id.nav_applyCook,
                        R.id.nav_applyCuisine
                ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        manageCook = navView.menu.findItem(R.id.nav_manageCook)
        manageCuisine = navView.menu.findItem(R.id.nav_manageCuisine)
        applyCook = navView.menu.findItem(R.id.nav_applyCook)
        applyCuisine = navView.menu.findItem(R.id.nav_applyCuisine)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        if (currentFragment == null) {
            val fragment =
                    NationListFragment()
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .addToBackStack(null)
                    .commit()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        loginText = findViewById(R.id.text_login)

        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}