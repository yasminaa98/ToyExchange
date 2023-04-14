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
import com.example.toyexchange.Presentation.ToysViewModel.AddToyViewModel
import com.example.toyexchange.Presentation.ToysViewModel.GetUserInfoViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.FeedToysFragmentBinding
import com.example.toyexchange.databinding.ProfilEditFragmentBinding
import com.example.toyexchange.theme.ui.MainActivity

class EditProfilFragment : Fragment(R.layout.profil_edit_fragment) {
    private lateinit var getUserInfoViewModel: GetUserInfoViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ProfilEditFragmentBinding.inflate(inflater, container, false)
        (activity as MainActivity).setBottomNavigation(true)
        getUserInfoViewModel= ViewModelProvider(this).get(GetUserInfoViewModel::class.java)
        val sharedPreferences =
            requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val username =sharedPreferences.getString("username",null)
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

        return binding.root
    }

}

