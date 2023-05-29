package com.example.toyexchange.theme.ui.fragments.AnnoncesFragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
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
import com.example.toyexchange.theme.ui.MainActivity
import com.example.toyexchange.theme.ui.fragments.AuthenticationFragment.EditProfilFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.util.*

@AndroidEntryPoint
class AddToyFragment: Fragment(R.layout.add_toy_fragment) {
    private lateinit var addToyViewModel: AddToyViewModel
    private lateinit var selectedImage: ImageView
    private lateinit var selectedPhotoUri: Uri



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
        (activity as MainActivity).setBottomNavigation(true)
        (activity as MainActivity).setToolbar(false)
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
            val nameRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
            val childAgeRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), child_age)
            val toyAgeRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), toy_age)
            val priceRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), price)
            val categoryRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), category)
            val stateRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), state)
            val descriptionRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), description)


            //val annonce= Annonce(1,category,description,ImageString!!,name,price,state,child_age,toy_age)
            val photoPart=PicturesConverter.managePicture(requireContext(),"picture",selectedPhotoUri)
            addToyViewModel.addToy(photoPart!!, nameRequestBody,priceRequestBody,
                stateRequestBody,childAgeRequestBody,toyAgeRequestBody,categoryRequestBody,
                descriptionRequestBody,token.toString())
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
        startActivityForResult(intent, PICK_IMAGE_REQUEST.REQUEST_CODE_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_IMAGE_REQUEST.REQUEST_CODE_PICK_IMAGE -> {
                    val uri = data?.data
                    uri?.let {
                        selectedPhotoUri = it
                        selectedImage.setImageURI(it)
                    }
                }
            }
        }
    }

}
