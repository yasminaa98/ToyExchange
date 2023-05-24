package com.example.toyexchange.theme.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.toyexchange.R
import com.example.toyexchange.databinding.SavedToysFragmentBinding
import com.example.toyexchange.databinding.SplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Splash_Fragment: Fragment(R.layout.splash_screen) {
    private val splashScreenDuration: Long = 2000


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = SplashScreenBinding.inflate(inflater, container, false)

        Handler().postDelayed({
            findNavController().navigate(R.id.action_splash_Fragment_to_signInFragment)
        }, splashScreenDuration)
        return binding.root

    }
}