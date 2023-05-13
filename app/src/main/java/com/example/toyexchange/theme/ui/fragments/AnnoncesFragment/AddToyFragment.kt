package com.example.toyexchange.theme.ui.fragments.AnnoncesFragment

import android.app.Activity
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
import com.example.toyexchange.Common.PICK_IMAGE_REQUEST
import com.example.toyexchange.Common.PicturesConverter
import com.example.toyexchange.Domain.model.Annonce
import com.example.toyexchange.Presentation.ToysViewModel.AddToyViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.AddToyFragmentBinding
import com.example.toyexchange.theme.ui.fragments.AuthenticationFragment.EditProfilFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddToyFragment: Fragment(R.layout.add_toy_fragment) {
    private lateinit var addToyViewModel: AddToyViewModel
    private lateinit var selectedImage: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = AddToyFragmentBinding.inflate(inflater, container, false)
        addToyViewModel= ViewModelProvider(this).get(AddToyViewModel::class.java)
        val sharedPreferences =
            requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val token =sharedPreferences.getString("authToken",null)
        //addToyViewModel.token=token
        selectedImage=binding.annoncePicture
        selectedImage.setOnClickListener{ openGallery()}
        binding.addButton.setOnClickListener {
            val name=binding.toyName.text.toString()
            val child_age=binding.childAge.text.toString()
            val toy_age=binding.toyAge.text.toString()
            val price=binding.price.text.toString()
            val category=binding.toyCategory.text.toString()
            val state=binding.toyState.text.toString()
            val description=binding.toyDescription.text.toString()
            lifecycleScope.launch{
            val ImageString=PicturesConverter.sendImage(selectedImage)
            val annonce= Annonce(1,category,description,ImageString!!,name,price,state,child_age,toy_age)
            addToyViewModel.addToy(token.toString(),annonce) }
        }
        addToyViewModel.adding_msg.observe(viewLifecycleOwner , Observer {
            if(it!=null){
                Toast.makeText(requireContext(),"toy added successfully", Toast.LENGTH_LONG).show()
                Log.i("msg",it.toString())
            }
            else{
                Toast.makeText(requireContext(),"toy adding failed", Toast.LENGTH_LONG).show()
            }
        })
        return binding.root

    }
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST.PICK_IMAGE_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST.PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data
            val imageStream = activity?.contentResolver?.openInputStream(imageUri!!)
            val selectedBitmap = BitmapFactory.decodeStream(imageStream)
            selectedImage.setImageBitmap(selectedBitmap)
        }
    }
}