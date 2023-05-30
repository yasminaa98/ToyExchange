package com.example.toyexchange.theme.ui.fragments.AuthenticationFragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
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
import com.example.toyexchange.Common.PICK_IMAGE_REQUEST
import com.example.toyexchange.Common.PicturesConverter
import com.example.toyexchange.Presentation.ToysViewModel.GetUserInfoViewModel
import com.example.toyexchange.Presentation.ToysViewModel.ToysViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.ProfilEditFragmentBinding
import com.example.toyexchange.theme.ui.MainActivity
import com.example.toyexchange.theme.ui.fragments.AnnoncesFragment.AddToyFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.*
import java.util.*

@AndroidEntryPoint
class EditProfilFragment : Fragment(R.layout.profil_edit_fragment) {

    private lateinit var getUserInfoViewModel: GetUserInfoViewModel
    private lateinit var selectedImage: ImageView
    private lateinit var toysViewModel: ToysViewModel
    private lateinit var selectedPhotoUri: Uri


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ProfilEditFragmentBinding.inflate(inflater, container, false)
        toysViewModel = ViewModelProvider(this).get(ToysViewModel::class.java)

        (activity as MainActivity).setBottomNavigation(true)
        getUserInfoViewModel = ViewModelProvider(this).get(GetUserInfoViewModel::class.java)
        //decode profile image
        binding.profileImage.setOnClickListener {
            openGallery()
            /*sendImage()*/
        }
        selectedImage = binding.profileImage
        //get current user

        val sharedPreferences =
            requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", null)
        val idUser = sharedPreferences.getLong("idUser", 0L)
        val token = sharedPreferences.getString("authToken", null)
        Log.i("idUser", idUser.toString())
        getUserInfoViewModel.getUserInfo(username.toString())
        getUserInfoViewModel.info.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.firstname.setText(it.firstname.toString())
                binding.address.setText(it.homeAddress.toString())
                binding.lastname.setText(it.lastname.toString())
                binding.response.setText(it.avgResponseTime.toString())
                binding.userPhone.setText(it.phone.toString())
                Glide.with(requireActivity())
                    .load(IMAGE_URL +it.profile_picture_path)
                    .apply(RequestOptions.circleCropTransform()) // Apply circular crop transformation
                    .into(binding.profileImage)

                Toast.makeText(requireContext(), "info got successfully", Toast.LENGTH_LONG).show()
                Log.i("msg", it.toString())


            } else {
                Toast.makeText(requireContext(), "getting info failed", Toast.LENGTH_LONG).show()
            }
        })

        // update firstname
        binding.save.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Update profile information")
                .setMessage("are you sure you want to update information ?")
                .setPositiveButton("Update") { dialog, which ->

                    var newFirstName = binding.firstname.text.toString()
                    getUserInfoViewModel.updateFirstName(idUser.toLong(), newFirstName)
                    var newHomeAddress = binding.address.text.toString()
                    getUserInfoViewModel.updateHomeAddress(idUser.toLong(), newHomeAddress)
                    var newLastName = binding.lastname.text.toString()
                    getUserInfoViewModel.updateLastName(idUser.toLong(), newLastName)
                    val photoPart=PicturesConverter.managePicture(requireContext(),"profilePicture",selectedPhotoUri)
                    getUserInfoViewModel.updatePicture(token.toString(),photoPart!!)
                }
                .setNegativeButton("Cancel") { dialog, which ->
                }

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()

        }
        getUserInfoViewModel.msg.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Toast.makeText(
                    requireContext(),
                    "firstname Updated successfully",
                    Toast.LENGTH_LONG
                ).show()
                Log.i("msg", it.toString())
            } else {
                Toast.makeText(requireContext(), "firstname update failed", Toast.LENGTH_LONG)
                    .show()
            }
        })
        /* var newHomeAddress=binding.address.text.toString()
            getUserInfoViewModel.updateHomeAddress(idUser.toLong(),newHomeAddress)
            getUserInfoViewModel.msgHomeAdd.observe(viewLifecycleOwner, Observer {
                if(it!=null){
                    Toast.makeText(requireContext(),"home address Updated successfully", Toast.LENGTH_LONG).show()
                    Log.i("msg",it.toString())
                }
                else{
                    Toast.makeText(requireContext(),"home address update failed", Toast.LENGTH_LONG).show()
                }
            })

            var newLastName=binding.lastname.text.toString()
            getUserInfoViewModel.updateLastName(idUser.toLong(),newLastName)
            getUserInfoViewModel.msgLastName.observe(viewLifecycleOwner, Observer {
                if(it!=null){
                    Toast.makeText(requireContext(),"lastname Updated successfully", Toast.LENGTH_LONG).show()
                    Log.i("msg",it.toString())
                }
                else{
                    Toast.makeText(requireContext(),"lastname update failed", Toast.LENGTH_LONG).show()
                }
            }) */



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



