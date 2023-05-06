package com.example.toyexchange.theme.ui.fragments.Exchange

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.toyexchange.Presentation.ToysViewModel.DetailsToyViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.ExchangeAnnonceDetailsBinding
import com.example.toyexchange.databinding.ToyDetailsFragmentBinding
import com.example.toyexchange.theme.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangeAnnonceDetailsFragment:Fragment(R.layout.exchange_annonce_details) {


    private lateinit var detailsToyViewModel: DetailsToyViewModel


    @SuppressLint("ResourceAsColor", "ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ExchangeAnnonceDetailsBinding.inflate(inflater, container, false)
        detailsToyViewModel = ViewModelProvider(this).get(DetailsToyViewModel::class.java)

        //get current user
        val sharedPreferences =
            requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("authToken", null)
        val username = sharedPreferences.getString("username", null)
        val annonceToExchange = arguments?.getLong("id")

        //get annonce
        val name = arguments?.getString("name").toString()
        val description = arguments?.getString("description").toString()
        val price = arguments?.getString("price").toString()
        val age_child = arguments?.getString("age_child").toString()
        val age_toy = arguments?.getString("age_toy").toString()
        val _state = arguments?.getString("state").toString()
        binding.apply {
            annonceName.text = name
            annonceDescription.text = description
            estimatedPrice.text = price
            ageToy.text=age_toy
            ageChild.text=age_child
            state.text=_state
            (activity as MainActivity).setBottomNavigation(false)
            (activity as MainActivity).setToolbar(true)
        }

        // getAnnonceOwner
        detailsToyViewModel.getAnnonceOwner(annonceToExchange!!)
        detailsToyViewModel.annonceOwner.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                binding.apply {
                    userName.setText(it.username)
                    firstname.setText(it.firstname)
                    lastname.setText(it.lastname)
                    email.setText(it.email)
                    homeaddress.setText(it.homeAddress)
                    phone.setText(it.phone.toString())
                    avgResponse.setText(it.avgResponseTime) }
            }
        })
        binding.ownerImage.setOnClickListener {
            if (binding.profileOwner.visibility== View.GONE) {
                Log.i("button clicked", "")
                binding.profileOwner.apply {
                    visibility = View.VISIBLE
                    translationY = height.toFloat()
                    // Animate from off-screen to original position
                    animate().apply {
                        translationY(-1400f)
                    }
                }
            }
        }
        binding.annonceDetailsFragment.setOnClickListener {
            if (binding.profileOwner.visibility == View.VISIBLE) {
                Log.i("button clicked", "")
                binding.profileOwner.apply {
                    visibility = View.GONE
                    translationY = height.toFloat()
                    // Animate from off-screen to original position
                    animate().apply {
                        translationY(1400f)
                    }
                }
            }
        }

        return binding.root
    }
}