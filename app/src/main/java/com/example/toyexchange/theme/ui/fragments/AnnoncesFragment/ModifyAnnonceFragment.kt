package com.example.toyexchange.theme.ui.fragments.AnnoncesFragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.toyexchange.Common.Constants
import com.example.toyexchange.Common.Constants.IMAGE_URL
import com.example.toyexchange.Common.PicturesConverter
import com.example.toyexchange.Domain.model.Annonce
import com.example.toyexchange.Presentation.ToysViewModel.DetailsToyViewModel
import com.example.toyexchange.Presentation.ToysViewModel.ModifyAnnonceViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.ModifyAnnonceFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ModifyAnnonceFragment: Fragment(R.layout.modify_annonce_fragment) {
    private lateinit var modifyAnnonceViewModel: ModifyAnnonceViewModel
    private lateinit var detailsToyViewModel:DetailsToyViewModel

    private lateinit var selectedImage: ImageView


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ModifyAnnonceFragmentBinding.inflate(inflater, container, false)
        modifyAnnonceViewModel= ViewModelProvider(this).get(ModifyAnnonceViewModel::class.java)
        detailsToyViewModel= ViewModelProvider(this).get(DetailsToyViewModel::class.java)
        selectedImage=binding.toyImage
        binding.toyImage.setOnClickListener{
            Log.i("1","1")


            Log.i("2","2")
        }

        val sharedPreferences =
            requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val token =sharedPreferences.getString("authToken",null)
        //addToyViewModel.token=token
        val idAnnonce = arguments?.getLong("id")
        Log.i("id annonce",idAnnonce.toString())
        // get item selected


       /* val name = arguments?.getString("name").toString()
        val description = arguments?.getString("description").toString()
        val _price = arguments?.getString("price").toString()
        val _image= arguments?.getString("image").toString()
        val category = arguments?.getString("category").toString()
        val age_child = arguments?.getString("age_child").toString()
        val age_toy = arguments?.getString("age_toy").toString()
        val _state = arguments?.getString("state").toString()*/
        detailsToyViewModel.getAnnonce1ById(idAnnonce!!)
        detailsToyViewModel.annonce1.observe(viewLifecycleOwner, Observer {
            if(it!=null){
           binding.apply {

                   toyName.setText(it.name)
                   toyDescription.setText(it.description)
                   childAge.setText(it.age_child)
                   toyCategory.setText(it.category)
                   price.setText(it.price)
                   toyAge.setText(it.age_toy)
                   toyState.setText(it.state)
               Glide.with(requireActivity())
                   .load(IMAGE_URL +it.picturePath)
                   .apply(RequestOptions.circleCropTransform()) // Apply circular crop transformation
                   .into(binding.toyImage)

                   Toast.makeText(requireContext(), "info got successfully", Toast.LENGTH_LONG)
                       .show()
               }}else {
                Toast.makeText(requireContext(), "getting info failed", Toast.LENGTH_LONG).show()
            }



        })

        binding.addButton.setOnClickListener {
            var name = binding.toyName.text.toString()
            var child_age = binding.childAge.text.toString()
            var toy_age = binding.toyAge.text.toString()
            var price = binding.price.text.toString()
            var category = binding.toyCategory.text.toString()
            var state = binding.toyState.text.toString()
            var description = binding.toyDescription.text.toString()

                //Log.i("id annonce2",idAnnonce.toString())

                val annonce = Annonce(
                    idAnnonce!!,
                    category,
                    description,
                    "image!!",
                    name,
                    price,
                    state,
                    child_age,
                    toy_age
                )
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Modify toy")
                .setMessage("are you sure you want to modify this toy ?")
                .setPositiveButton("Modify") { dialog, which ->
                    modifyAnnonceViewModel.modifyAnnonce(token.toString(), idAnnonce.toLong(), annonce)
                    findNavController().navigate(R.id.action_modifyAnnonceFragment_to_myAnnoncesFragment)
                }
                .setNegativeButton("Cancel") { dialog, which ->
                }

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()

        }

        modifyAnnonceViewModel.msg.observe(viewLifecycleOwner , Observer {
            if(it!=null){
                Toast.makeText(requireContext(),"toy modified successfully", Toast.LENGTH_LONG).show()
                Log.i("msg",it.toString())
            }
            else{
                Toast.makeText(requireContext(),"toy modification failed", Toast.LENGTH_LONG).show()
            }
        })
        return binding.root

    }

}