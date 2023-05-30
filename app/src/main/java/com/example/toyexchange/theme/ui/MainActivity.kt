package com.example.toyexchange.theme.ui

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.toyexchange.Common.Constants.IMAGE_URL
import com.example.toyexchange.Presentation.ToysViewModel.GetUserInfoViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.ActivityMainBinding
import com.example.toyexchange.theme.ui.fragments.ResetPasswordFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var getUserInfoViewModel: GetUserInfoViewModel

    lateinit var toggle: ActionBarDrawerToggle
    private var job: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // drawer menu
        toggle= ActionBarDrawerToggle(this, binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // get user





       startUserInfoUpdate()

        //notification
        binding.notification.setOnClickListener{
            val navController = Navigation.findNavController(this, R.id.toysNavHostFragment)
            navController.navigate(R.id.notificationViewPager)
        }
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            val navController = Navigation.findNavController(this, R.id.toysNavHostFragment)

            when (menuItem.itemId) {
                R.id.profile -> {
                    navController.navigate(R.id.editProfilFragment)
                }
                R.id.auctions -> {
                    navController.navigate(R.id.myAuctionFragment)
                }
                R.id.messages -> {
                    navController.navigate(R.id.chatMainFragment)
                }
                R.id.myAnnonces -> {
                    navController.navigate(R.id.myAnnoncesFragment)
                }
                R.id.explore -> {
                    navController.navigate(R.id.feedToysFragment)
                }
                R.id.logout -> {
                    navController.popBackStack(R.id.signInFragment, false)
                    navController.navigate(R.id.signInFragment)

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
           val appBarConfiguration = AppBarConfiguration(setOf(R.id.feedToysFragment, R.id.savedToysFragment, R.id.editProfilFragment,R.id.addToysFragment))
           binding.toolbar.setupWithNavController(this, appBarConfiguration)

    }
        //reset password
        val data: Uri? = intent.data
        if (data != null) {
            val path: String? = data.path
            if (path == "/resetpassword") {
                // Navigate to the reset password fragment
                val resetPasswordFragment = ResetPasswordFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.flFragment, resetPasswordFragment)
                    .commit()
            }
        }
        /*new notification
        job = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                val sharedPreferences = getSharedPreferences("notif", Context.MODE_PRIVATE)
                val notif = sharedPreferences.getString("notif", null)
                Log.i("notif", notif.toString())
                if (notif == "new") {
                    binding.redDot.visibility = View.VISIBLE
                }
                delay(1000) // wait for 1 second before checking the shared preferences again
            }
        }*/
    }
    private val mainScope = CoroutineScope(Dispatchers.Main)

    private fun startUserInfoUpdate() {
        mainScope.launch {
            while (true) {
                // Run the code block
                val sharedPreferences =
                    applicationContext.getSharedPreferences("authToken", Context.MODE_PRIVATE)
                val username = sharedPreferences.getString("username", null)
                val idUser = sharedPreferences.getLong("idUser", 0L)
                val token = sharedPreferences.getString("authToken", null)

                getUserInfoViewModel = ViewModelProvider(this@MainActivity).get(GetUserInfoViewModel::class.java)
                getUserInfoViewModel.getUserInfo(username.toString())
                getUserInfoViewModel.info.observe(this@MainActivity, Observer {
                    if (it != null) {
                        val headerView = binding.navigationView.getHeaderView(0)
                        val usernameTextView = headerView.findViewById<TextView>(R.id.username)
                        val userImageView = headerView.findViewById<ImageView>(R.id.user_photo)
                        usernameTextView.text = username
                        Glide.with(applicationContext)
                            .load(IMAGE_URL + it.profile_picture_path)
                            .apply(RequestOptions.circleCropTransform()) // Apply circular crop transformation
                            .into(userImageView)
                    } else {
                        Toast.makeText(applicationContext, "getting info failed", Toast.LENGTH_LONG)
                            .show()
                    }
                })

                // Delay for 1 second before running the code block again
                delay(1000)
            }
        }
    }

    override fun onBackPressed() {
        val navController = Navigation.findNavController(this, R.id.toysNavHostFragment)

        // Check if the current destination is the splash screen fragment
        if (navController.currentDestination?.id == R.id.signInFragment) {
            // Handle the back button press, such as displaying a dialog or taking any other action
            // You can choose to do nothing or exit the app if desired
            return
        }

        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
        job?.cancel() // cancel the coroutine when the activity is destroyed
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
    fun setSlideNavigaton(isSliding: Boolean) {
        val drawerLayout = binding.drawerLayout
        if (isSliding) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            binding.navigationView.visibility = View.VISIBLE
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            binding.navigationView.visibility = View.GONE
        }
    }

    }






