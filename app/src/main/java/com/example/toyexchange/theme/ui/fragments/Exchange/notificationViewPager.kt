package com.example.toyexchange.theme.ui.fragments.AnnoncesFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.toyexchange.R
import com.example.toyexchange.databinding.ModifyAnnonceFragmentBinding
import com.example.toyexchange.databinding.NotifcationFragmentBinding
import com.example.toyexchange.theme.ui.MainActivity
import com.example.toyexchange.theme.ui.adapter.ViewPagerAdapter
import com.example.toyexchange.theme.ui.fragments.Exchange.NotificationFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class notificationViewPager:Fragment(R.layout.notifcation_fragment) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = NotifcationFragmentBinding.inflate(inflater, container, false)
        binding.viewPager.adapter=ViewPagerAdapter(childFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        return binding.root
    }

}