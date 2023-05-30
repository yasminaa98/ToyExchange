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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.toyexchange.Common.Constants.IMAGE_URL
import com.example.toyexchange.Common.PicturesConverter
import com.example.toyexchange.Presentation.ToysViewModel.DetailsToyViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.ExchangeAnnonceDetailsBinding
import com.example.toyexchange.databinding.ToyDetailsFragmentBinding
import com.example.toyexchange.theme.ui.MainActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
        val image = arguments?.getString("image").toString()
        val _category=arguments?.getString("category").toString()
        binding.apply {
            annonceName.text = name
            annonceDescription.text = description
            estimatedPrice.text = price
            ageToy.text=age_toy
            ageChild.text=age_child
            state.text=_state
            annonceCategory.text=_category
            Glide.with(requireActivity())
                .load(IMAGE_URL+image)
                //.apply(RequestOptions.circleCropTransform()) // Apply circular crop transformation
                .into(binding.toyImage)
            (activity as MainActivity).setBottomNavigation(false)
            (activity as MainActivity).setToolbar(true)
        }

        // getAnnonceOwner
        detailsToyViewModel.getAnnonceOwner(annonceToExchange!!)
        detailsToyViewModel.annonceOwner.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                binding.apply {
                    userName.setText("Username: "+it.username)
                    firstname.setText("Firstname: "+it.firstname)
                    lastname.setText("Lastname: "+it.lastname)
                    email.setText("Email: "+it.email)
                    homeaddress.setText("Home address: "+it.homeAddress)
                    phone.setText("Phone: " + it.phone.toString())
                    avgResponse.setText("I respond every "+it.avgResponseTime+" hours")
                    Glide.with(requireActivity())
                        .load(IMAGE_URL+it.profile_picture_path)
                        .apply(RequestOptions.circleCropTransform()) // Apply circular crop transformation
                        .into(binding.ownerImage)
                    Glide.with(requireActivity())
                        .load(IMAGE_URL+it.profile_picture_path)
                        .apply(RequestOptions.circleCropTransform()) // Apply circular crop transformation
                        .into(binding.ownerimage)
                }
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
        binding.sendMessage.setOnClickListener{
            val database = FirebaseDatabase.getInstance()
            val userRef = database.getReference("user")
            val nameToFind = detailsToyViewModel.annonceOwner.value?.username.toString()

            userRef.orderByChild("name").equalTo(nameToFind).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (userSnapshot in dataSnapshot.children) {
                        val uid = userSnapshot.child("uid").getValue(String::class.java)
                        Log.i("uid clicked is ", uid.toString())

                        val bundle= bundleOf("uid" to uid)
                        findNavController().navigate(R.id.action_exchangeAnnonceDetailsFragment_to_chat,bundle)
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.i("uid clicked is ", "error")

                }
            })

        }

        return binding.root
    }
}