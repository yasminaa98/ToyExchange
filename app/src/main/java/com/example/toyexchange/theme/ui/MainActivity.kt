package com.example.toyexchange.theme.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toyexchange.Data.Repository.ToysRepositoryImpl
import com.example.toyexchange.Data.remote.RetrofitClient
import com.example.toyexchange.Data.remote.ToysApi
import com.example.toyexchange.Domain.model.Toy
import com.example.toyexchange.Presentation.ToysViewModel.ToysViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.ActivityMainBinding
import com.example.toyexchange.databinding.FeedToysFragmentBinding
import com.example.trypostrequest.ui.adapter.ToysRecyclerViewAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView


private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toysNavHostFragment =
            supportFragmentManager.findFragmentById(R.id.toysNavHostFragment) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(toysNavHostFragment.findNavController())
        toysNavHostFragment.findNavController().run {
            binding.toolbar.setupWithNavController(this, AppBarConfiguration(graph))
           /*  to hide the back button
           val appBarConfiguration = AppBarConfiguration(setOf(R.id.feedToysFragment, R.id.savedToysFragment, R.id.editProfilFragment))
           binding.toolbar.setupWithNavController(this, appBarConfiguration)*/

    }
    }
        fun setBottomNavigation(isNavigation: Boolean) {
            if (isNavigation) {
                binding.bottomNavigationView.visibility = View.VISIBLE
            } else {
                binding.bottomNavigationView.visibility = View.GONE


            }
        }

        fun setToolbar(backToPreviousFragment: Boolean) {
            if (backToPreviousFragment) {
                binding.toolbar.visibility = View.VISIBLE
            } else {
                binding.toolbar.visibility = View.GONE
            }
        }
    }





