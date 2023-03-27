package com.example.toyexchange.theme.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.toyexchange.Presentation.ToysViewModel.DetailsToyViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.ToyDetailsFragmentBinding
import com.example.toyexchange.databinding.ToyItemBinding
import com.example.trypostrequest.ui.adapter.ToysRecyclerViewAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class DetailsToysFragment:Fragment(R.layout.toy_item){
    private lateinit var detailsToyViewModel: DetailsToyViewModel
    lateinit var toys_recycler: RecyclerView
    lateinit var toysRecyclerViewAdapter: ToysRecyclerViewAdapter
    private lateinit var binding: ToyDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ToyDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
        detailsToyViewModel = ViewModelProvider(this).get(DetailsToyViewModel::class.java)
            detailsToyViewModel.toysDetails.observe(viewLifecycleOwner, { toyDetails ->
            val toyId= arguments?.getInt("id")
            if (toyId != null) {
                detailsToyViewModel.getToyDetailsById(toyId)
                binding.toyName.text = toyDetails.name
                binding.toyDescription.text = toyDetails.description
                binding.toyPrice.text = toyDetails.price.toString()
            }
        })

        //binding.toyPrice.text = arguments?.getString("price")

            //Log.i("viewmodel created","vm Created" )


    }




}
