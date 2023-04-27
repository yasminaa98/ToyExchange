package com.example.toyexchange.theme.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.toyexchange.R
import com.example.toyexchange.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

private lateinit var binding: ActivityMainBinding
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
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
        binding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item1 -> Toast.makeText(applicationContext,
                "cliqued item1",Toast.LENGTH_SHORT).show()
                R.id.auctionFragment -> Toast.makeText(applicationContext,
                    "cliqued item2",Toast.LENGTH_SHORT).show()
                R.id.item3 -> Toast.makeText(applicationContext,
                    "cliqued item3",Toast.LENGTH_SHORT).show()
                R.id.item4 -> Toast.makeText(applicationContext,
                    "cliqued item4",Toast.LENGTH_SHORT).show()
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
    }






