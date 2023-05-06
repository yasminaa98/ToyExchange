package com.example.toyexchange.theme.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.toyexchange.Domain.model.Toy
import com.example.toyexchange.Presentation.ToysViewModel.DetailsToyViewModel
import com.example.toyexchange.Presentation.ToysViewModel.RoomViewModel
import com.example.toyexchange.Presentation.ToysViewModel.RoomViewModelFactory
import com.example.toyexchange.R
import com.example.toyexchange.databinding.ToyDetailsFragmentBinding
import com.example.toyexchange.db.ToyDatabase
import com.example.toyexchange.theme.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsToysFragment : Fragment(R.layout.toy_item) {
    private lateinit var detailsToyViewModel: DetailsToyViewModel
    private lateinit var roomViewModel: RoomViewModel


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
            ViewModelProvider(this, roomViewModelFactory)[RoomViewModel::class.java] //onFavoriteClick()
        detailsToyViewModel = ViewModelProvider(this).get(DetailsToyViewModel::class.java)

        //get current user
        val sharedPreferences =
            requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("authToken", null)
        val username = sharedPreferences.getString("username", null)
        val toyId = arguments?.getLong("id")

        // getAnnonceOwner
        detailsToyViewModel.getAnnonceOwner(toyId!!)

        binding.ownerImage.setOnClickListener {
            if (binding.profileOwner.visibility==View.GONE) {
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
            if (it!=null){
                if (it.username==username){
                    binding.exchange.setBackgroundColor(R.color.grey)
                    binding.exchange.isEnabled=false
                    binding.exchange.setText("Exchange unavailable")
                }

                binding.apply {
                    userName.setText(it.username)
                    firstname.setText(it.firstname)
                    lastname.setText(it.lastname)
                    email.setText(it.email)
                    homeaddress.setText(it.homeAddress)
                    phone.setText(it.phone.toString())
                    avgResponse.setText(it.avgResponseTime) }

                    val bundle= bundleOf("reciever" to it.username,"id_receiver_annonce" to toyId)
                    binding.exchange.setOnClickListener{
                        findNavController().navigate(R.id.action_detailsToysFragment_to_chooseExchangeAnnonceFragment,bundle)
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
        val category = arguments?.getString("category").toString()
        val image = arguments?.getString("image_url").toString()
        binding.apply {
            toyName.text = name
            toyDescription.text = description
            toyPrice.text = price
            (activity as MainActivity).setBottomNavigation(false)
            (activity as MainActivity).setToolbar(true)
        }
        val toyToSave = Toy(toyId!!, category, description, image, name, price,"","","")
        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferencesRoom.edit().putBoolean("checkbox_state", isChecked).apply()
            if (isChecked){
            roomViewModel.insertToy(toyToSave)
            Toast.makeText(requireContext(), "Toy saved", Toast.LENGTH_SHORT).show()}
            else{
                roomViewModel.deleteToy(toyToSave)
                Toast.makeText(requireContext(), "Toy deleted", Toast.LENGTH_SHORT).show()}

        }

        return binding.root
    }
}






