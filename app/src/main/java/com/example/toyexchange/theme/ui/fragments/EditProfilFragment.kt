package com.example.toyexchange.theme.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.toyexchange.R
import com.example.toyexchange.databinding.FeedToysFragmentBinding
import com.example.toyexchange.databinding.ProfilEditFragmentBinding
import com.example.toyexchange.theme.ui.MainActivity

class EditProfilFragment : Fragment(R.layout.profil_edit_fragment) {

    private lateinit var binding: ProfilEditFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ProfilEditFragmentBinding.inflate(inflater, container, false)
        (activity as MainActivity).setBottomNavigation(true)


        return binding.root
    }

}

