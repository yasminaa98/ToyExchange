package com.example.toyexchange.theme.ui.fragments.AnnoncesFragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.toyexchange.Common.PicturesConverter
import com.example.toyexchange.Presentation.ToysViewModel.AddAuctionViewModel
import com.example.toyexchange.Presentation.ToysViewModel.DetailsToyViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.MyAnnonceDetailsBinding
import com.example.toyexchange.theme.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyAnnoncedetailsFragment: Fragment(R.layout.my_annonce_details) {
    private lateinit var addAuctionViewModel:AddAuctionViewModel
    private lateinit var detailsToyViewModel: DetailsToyViewModel


    private lateinit var selectedImage: ImageView
    @SuppressLint("SuspiciousIndentation", "ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = MyAnnonceDetailsBinding.inflate(inflater, container, false)

        addAuctionViewModel = ViewModelProvider(this).get(AddAuctionViewModel::class.java)
        detailsToyViewModel = ViewModelProvider(this).get(DetailsToyViewModel::class.java)

        selectedImage=binding.toyImage
        val sharedPreferences =
            requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val token =sharedPreferences.getString("authToken",null)
        Log.i("token",token!!)

        //addToyViewModel.token=token
        val idAnnonce = arguments?.getLong("id")
        Log.i("id annonce",idAnnonce.toString())
        (activity as MainActivity).setBottomNavigation(false)
        (activity as MainActivity).setToolbar(true)

        // get item selected
        val name = arguments?.getString("name").toString()
        val description = arguments?.getString("description").toString()
        val _price = arguments?.getString("price").toString()
        val _category = arguments?.getString("category").toString()
        val age_child = arguments?.getString("age_child").toString()
        val age_toy = arguments?.getString("age_toy").toString()
        val _state = arguments?.getString("state").toString()
        val _image= arguments?.getString("image").toString()
        val estArchive=arguments?.getBoolean("estArchive")
        Log.i("archivage",estArchive.toString())

            //Log.i("image arrived", image!!)
            binding.apply {

                toyName.setText(name)
                toyDescription.setText(description)
                childAge.setText(age_child)
                category.setText(_category)
                state.setText(_state)
                toyAge.setText(age_toy)
                /*val sh =
                    requireActivity().getSharedPreferences("myAnnonceImage", Context.MODE_PRIVATE)
                val image =sh.getString("myAnnonceImage",null)*/

                lifecycleScope.launch {
                    toyImage.setImageBitmap(PicturesConverter.base64ToBitmap(_image.toString()))
                    Log.i("image set", _image.toString())
                }
            }

        //archive
        if (estArchive==true){
            binding.archive.setImageResource(R.drawable.archived)
            binding.archive.isEnabled=false
        }
        else {
            binding.archive.setImageResource(R.drawable.vue)
            binding.archive.setOnClickListener {
                binding.archive.setImageResource(R.drawable.archived)
                detailsToyViewModel.archiveAnnonce(idAnnonce!!, token.toString())
                detailsToyViewModel.response.observe(viewLifecycleOwner, Observer {
                    if (it != null) {

                    }
                })
            }
        }
        //get owner
        detailsToyViewModel.getAnnonceOwner(idAnnonce!!)
        detailsToyViewModel.annonceOwner.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                binding.apply {
                    lifecycleScope.launch {
                        val image=PicturesConverter.base64ToBitmap(it.profile_picture_path)
                        ownerImage.setImageBitmap(PicturesConverter.getRoundedBitmap(image!!,200)) }
                }
            }
        })
        val bundle = bundleOf("id" to idAnnonce)

        binding.Modifiy.setOnClickListener {
            findNavController().navigate(R.id.action_myAnnoncedetailsFragment_to_modifyAnnonceFragment,bundle)
        }
        binding.addToAuction.setOnClickListener {
            findNavController().navigate(
                R.id.action_myAnnoncedetailsFragment_to_addAuctionFragment,
                bundle
            )
        }

        addAuctionViewModel.checkExistentAuction(idAnnonce!!,token.toString())
        addAuctionViewModel.msg.observe(viewLifecycleOwner, Observer {
            if (it==null){
                binding.addToAuction.visibility=View.VISIBLE
            }
        })
        return binding.root

    }
}