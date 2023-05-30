package com.example.toyexchange.theme.ui.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.example.toyexchange.Common.Constants.IMAGE_URL
import com.example.toyexchange.Common.PicturesConverter
import com.example.toyexchange.Domain.model.Toy
import com.example.toyexchange.Domain.model.ToysInformation
import com.example.toyexchange.Presentation.ToysViewModel.DetailsToyViewModel
import com.example.toyexchange.Presentation.ToysViewModel.RoomViewModel
import com.example.toyexchange.Presentation.ToysViewModel.RoomViewModelFactory
import com.example.toyexchange.R
import com.example.toyexchange.databinding.ToyDetailsFragmentBinding
import com.example.toyexchange.db.ToyDatabase
import com.example.toyexchange.theme.ui.MainActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class DetailsToysFragment : Fragment(R.layout.toy_details_fragment) {
    private lateinit var detailsToyViewModel: DetailsToyViewModel
    private lateinit var roomViewModel: RoomViewModel
    private lateinit var selectedImage: ImageView


    @SuppressLint("ResourceAsColor", "ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = ToyDetailsFragmentBinding.inflate(inflater, container, false)
        val toyDatabase = ToyDatabase.getInstance(requireContext())
        val roomViewModelFactory = RoomViewModelFactory(toyDatabase)
        roomViewModel =
            ViewModelProvider(
                this,
                roomViewModelFactory
            )[RoomViewModel::class.java] //onFavoriteClick()
        detailsToyViewModel = ViewModelProvider(this).get(DetailsToyViewModel::class.java)
//select image
        selectedImage = binding.toyImage
        selectedImage.setOnClickListener { enlargeImage(binding.toyImage.drawable) }


        //get current user
        val sharedPreferences =
            requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("authToken", null)
        val username = sharedPreferences.getString("username", null)
        val toyId = arguments?.getLong("id")

        // getAnnonceOwner
        detailsToyViewModel.getAnnonceOwner(toyId!!)

        binding.ownerImage.setOnClickListener {
            if (binding.profileOwner.visibility == View.GONE) {
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
        binding.toyDetailsFragment.setOnClickListener {
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
        detailsToyViewModel.annonceOwner.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.username == username) {
                    binding.sendMessage.visibility = View.GONE
                    binding.exchange.setBackgroundColor(R.color.grey)
                    binding.exchange.isEnabled = false
                    binding.exchange.setText("Exchange unavailable")
                }

                binding.apply {
                    userName.setText("Username: " + it.username)
                    firstname.setText("Firstname: " + it.firstname)
                    lastname.setText("Lastname: " + it.lastname)
                    email.setText("Email: " + it.email)
                    homeaddress.setText("Home address: " + it.homeAddress)
                    phone.setText("Phone: " + it.phone.toString())
                    avgResponse.setText("I respond every " + it.avgResponseTime + " hours")
                    Glide.with(requireActivity())
                        .load(IMAGE_URL + it.profile_picture_path)
                        .apply(RequestOptions.circleCropTransform()) // Apply circular crop transformation
                        .into(binding.ownerimage)
                    Glide.with(requireActivity())
                        .load(IMAGE_URL + it.profile_picture_path)
                        .apply(RequestOptions.circleCropTransform()) // Apply circular crop transformation
                        .into(binding.ownerImage)

                }

                val bundle = bundleOf("reciever" to it.username, "id_receiver_annonce" to toyId)
                binding.exchange.setOnClickListener {
                    findNavController().navigate(
                        R.id.action_detailsToysFragment_to_chooseExchangeAnnonceFragment,
                        bundle
                    )
                }


            }
        })
        //maintain checkbox state :
        val sharedPreferencesRoom =
            requireActivity().getSharedPreferences("my_checkbox_${toyId}", Context.MODE_PRIVATE)
        val checkboxState = sharedPreferencesRoom.getBoolean("checkbox_state", false)
        binding.checkBox.isChecked = checkboxState

        // get item selected
        val name = arguments?.getString("name").toString()
        val description = arguments?.getString("description").toString()
        val price = arguments?.getString("price").toString()
        val _category = arguments?.getString("category").toString()
        val image = arguments?.getString("image_url").toString()
        val ageToy = arguments?.getString("age_toy").toString()
        val ageChild = arguments?.getString("age_child").toString()
        val _state = arguments?.getString("state").toString()

        binding.apply {
            toyName.text = name
            toyDescription.text = description
            toyPrice.text = price
            toyAge.text = "Toy age: " + ageToy
            childAge.text = "Child age: " + ageChild
            category.text = "Category: " + _category
            state.text = "State: " + _state

            Glide.with(requireActivity())
                .load(IMAGE_URL + image)
                .into(binding.toyImage)
            (activity as MainActivity).setBottomNavigation(false)
            (activity as MainActivity).setToolbar(true)
        }
        val toyToSave =
            ToysInformation(toyId!!, _category, description, image, name, price, _state, ageChild, ageToy, false)
        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferencesRoom.edit().putBoolean("checkbox_state", isChecked).apply()
            if (isChecked) {
                roomViewModel.insertToy(toyToSave)
                Toast.makeText(requireContext(), "Toy saved", Toast.LENGTH_SHORT).show()
            } else {
                roomViewModel.deleteToy(toyToSave)
                Toast.makeText(requireContext(), "Toy deleted", Toast.LENGTH_SHORT).show()
            }

        }
        binding.sendMessage.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val userRef = database.getReference("user")
            val nameToFind = detailsToyViewModel.annonceOwner.value?.username.toString()

            userRef.orderByChild("name").equalTo(nameToFind).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (userSnapshot in dataSnapshot.children) {
                        val uid = userSnapshot.child("uid").getValue(String::class.java)
                        Log.i("uid clicked is ", uid.toString())

                        val bundle = bundleOf("uid" to uid)
                        findNavController().navigate(
                            R.id.action_detailsToysFragment_to_chat,
                            bundle
                        )
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.i("uid clicked is ", "error")

                    // Handle error
                }
            })

        }

        return binding.root
    }

    private fun enlargeImage(drawable: Drawable?) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_enlarged_image, null)
        val imageView = dialogView.findViewById<ImageView>(R.id.enlargedImage)

        imageView.setImageDrawable(drawable)

        dialogBuilder.setView(dialogView)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

}




