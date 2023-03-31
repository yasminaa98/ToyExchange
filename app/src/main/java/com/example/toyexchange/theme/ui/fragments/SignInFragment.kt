package com.example.toyexchange.theme.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.toyexchange.Presentation.ToysViewModel.DetailsToyViewModel
import com.example.toyexchange.Presentation.ToysViewModel.ToysViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.FeedToysFragmentBinding
import com.example.toyexchange.databinding.SignInFragmentBinding
import com.example.toyexchange.theme.ui.MainActivity
import com.example.trypostrequest.ui.adapter.ToysRecyclerViewAdapter

class SignInFragment: Fragment(R.layout.sign_in_fragment) {

    private lateinit var binding: SignInFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = SignInFragmentBinding.inflate(inflater, container, false)
        binding.signInButton.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_feedToysFragment)
        }
        return binding.root

    }
}

