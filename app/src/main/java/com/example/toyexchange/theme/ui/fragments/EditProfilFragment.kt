package com.example.toyexchange.theme.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.toyexchange.Presentation.ToysViewModel.GetUserInfoViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.ProfilEditFragmentBinding
import com.example.toyexchange.theme.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfilFragment : Fragment(R.layout.profil_edit_fragment) {
    private lateinit var getUserInfoViewModel: GetUserInfoViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ProfilEditFragmentBinding.inflate(inflater, container, false)
        (activity as MainActivity).setBottomNavigation(true)
        getUserInfoViewModel= ViewModelProvider(this).get(GetUserInfoViewModel::class.java)
        val sharedPreferences =
        requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val username =sharedPreferences.getString("username",null)
        val idUser =sharedPreferences.getLong("idUser",0L)
        Log.i("idUser",idUser.toString())
        getUserInfoViewModel.getUserInfo(username.toString())
        getUserInfoViewModel.info.observe(viewLifecycleOwner , Observer {
            if(it!=null){
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
            var newHomeAddress=binding.address.text.toString()
            var newLastName=binding.lastname.text.toString()
            getUserInfoViewModel.updateFirstName(idUser.toLong(),newFirstName)
            getUserInfoViewModel.updateLastName(idUser.toLong(),newLastName)
            getUserInfoViewModel.updateHomeAddress(idUser.toLong(),newHomeAddress)
            getUserInfoViewModel.msg.observe(viewLifecycleOwner, Observer {
                if(it!=null){
                    Toast.makeText(requireContext(),"firstname Updated successfully", Toast.LENGTH_LONG).show()
                    Log.i("msg",it.toString())
                }
                else{
                    Toast.makeText(requireContext(),"firstname update failed", Toast.LENGTH_LONG).show()
                }
            })
        }

        return binding.root
    }

}

