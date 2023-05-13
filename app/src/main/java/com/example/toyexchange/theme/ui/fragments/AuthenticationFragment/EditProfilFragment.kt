package com.example.toyexchange.theme.ui.fragments.AuthenticationFragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
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
import com.example.toyexchange.Common.PICK_IMAGE_REQUEST.Companion.PICK_IMAGE_REQUEST
import com.example.toyexchange.Common.PicturesConverter
import com.example.toyexchange.Presentation.ToysViewModel.GetUserInfoViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.ProfilEditFragmentBinding
import com.example.toyexchange.theme.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

@AndroidEntryPoint
class EditProfilFragment : Fragment(R.layout.profil_edit_fragment) {

    private lateinit var getUserInfoViewModel: GetUserInfoViewModel
    private lateinit var selectedImage: ImageView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ProfilEditFragmentBinding.inflate(inflater, container, false)
        (activity as MainActivity).setBottomNavigation(true)
        getUserInfoViewModel= ViewModelProvider(this).get(GetUserInfoViewModel::class.java)
        //decode profile image
        binding.profileImage.setOnClickListener { openGallery()
            /*sendImage()*/}
        selectedImage=binding.profileImage
        //get current user
        val sharedPreferences =
        requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val username =sharedPreferences.getString("username",null)
        val idUser =sharedPreferences.getLong("idUser",0L)
        Log.i("idUser",idUser.toString())
        getUserInfoViewModel.getUserInfo(username.toString())
        getUserInfoViewModel.info.observe(viewLifecycleOwner , Observer {
            if(it!=null){
                lifecycleScope.launch {
                binding.profileImage.setImageBitmap(PicturesConverter.base64ToBitmap(it.profile_picture_path))}
                binding.firstname.setText(it.firstname.toString())
                binding.address.setText(it.homeAddress.toString())
                binding.lastname.setText(it.lastname.toString())
                binding.response.setText(it.avgResponseTime.toString())
                binding.userPhone.setText(it.phone.toString())
                Toast.makeText(requireContext(),"info got successfully", Toast.LENGTH_LONG).show()
                Log.i("msg",it.toString())
            }
            else{
                Toast.makeText(requireContext(),"getting info failed", Toast.LENGTH_LONG).show()
            }
        })
        // update firstname
        binding.save.setOnClickListener{
            var newFirstName=binding.firstname.text.toString()
            getUserInfoViewModel.updateFirstName(idUser.toLong(),newFirstName)
            var newHomeAddress=binding.address.text.toString()
            getUserInfoViewModel.updateHomeAddress(idUser.toLong(),newHomeAddress)
            var newLastName=binding.lastname.text.toString()
            getUserInfoViewModel.updateLastName(idUser.toLong(),newLastName)
            lifecycleScope.launch {
            getUserInfoViewModel.updatePicture(idUser.toLong(),PicturesConverter.sendImage(selectedImage)!!)}
            getUserInfoViewModel.msg.observe(viewLifecycleOwner, Observer {
                if(it!=null){
                    Toast.makeText(requireContext(),"firstname Updated successfully", Toast.LENGTH_LONG).show()
                    Log.i("msg",it.toString())
                }
                else{
                    Toast.makeText(requireContext(),"firstname update failed", Toast.LENGTH_LONG).show()
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

        }

        return binding.root
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data
            val imageStream = activity?.contentResolver?.openInputStream(imageUri!!)
            val selectedBitmap = BitmapFactory.decodeStream(imageStream)
            selectedImage.setImageBitmap(selectedBitmap)
        }
    }


}



