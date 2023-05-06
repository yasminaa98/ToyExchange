package com.example.toyexchange.theme.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.toyexchange.R
import com.example.toyexchange.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // drawer menu
        toggle= ActionBarDrawerToggle(this, binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //notification
        binding.notification.setOnClickListener{
            val navController = Navigation.findNavController(this, R.id.toysNavHostFragment)
            navController.navigate(R.id.notificationViewPager)
        }
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            val navController = Navigation.findNavController(this, R.id.toysNavHostFragment)

            when (menuItem.itemId) {
                R.id.profile -> {
                    Toast.makeText(applicationContext, "Clicked item1", Toast.LENGTH_SHORT).show()
                    // Navigate to Fragment1
                    navController.navigate(R.id.editProfilFragment)
                }
                R.id.auctions -> {
                    Toast.makeText(applicationContext, "Clicked auctionFragment", Toast.LENGTH_SHORT).show()
                    // Navigate to AuctionFragment
                    navController.navigate(R.id.auctionFragment)
                }
            }
            true
        }


        val toysNavHostFragment =
            supportFragmentManager.findFragmentById(R.id.toysNavHostFragment) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(toysNavHostFragment.findNavController())
        toysNavHostFragment.findNavController().run {
            binding.toolbar.setupWithNavController(this, AppBarConfiguration(graph))
           //to hide the back button
           val appBarConfiguration = AppBarConfiguration(setOf(R.id.feedToysFragment, R.id.savedToysFragment, R.id.editProfilFragment))
           binding.toolbar.setupWithNavController(this, appBarConfiguration)

    }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun setToolbar(backToPreviousFragment: Boolean) {
            if (backToPreviousFragment) {
                binding.toolbar.visibility = View.VISIBLE
            } else {
                binding.toolbar.visibility = View.GONE
            }
        }
    fun setBottomNavigation(isNavigation: Boolean) {
        if (isNavigation) {
            binding.bottomNavigationView.visibility = View.VISIBLE
        } else {
            binding.bottomNavigationView.visibility = View.GONE
        }
    }
    fun setSlideNavigaton(isSliding:Boolean){
        if(isSliding){
            binding.navigationView.visibility=View.VISIBLE
        }
        else{
            binding.navigationView.visibility=View.GONE
        }
    }
    }






