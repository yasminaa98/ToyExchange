package com.example.toyexchange.theme.ui.fragments.AnnoncesFragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toyexchange.Domain.model.Exchange
import com.example.toyexchange.Presentation.ToysViewModel.ExchangeViewModel
import com.example.toyexchange.Presentation.ToysViewModel.UserAnnoncesViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.ChooseExchangeAnnonceFragmentBinding
import com.example.toyexchange.databinding.MyAnnoncesFragmentBinding
import com.example.toyexchange.theme.ui.MainActivity
import com.example.toyexchange.theme.ui.adapter.SelectExchangeAnnonceAdapter
import com.example.toyexchange.theme.ui.adapter.UserAnnoncesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseExchangeAnnonceFragment:Fragment(R.layout.choose_exchange_annonce_fragment) {
    private lateinit var userAnnoncesViewModel: UserAnnoncesViewModel
    private lateinit var exchangeViewModel: ExchangeViewModel
    lateinit var selectExchangeAnnonceAdapter: SelectExchangeAnnonceAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = ChooseExchangeAnnonceFragmentBinding.inflate(inflater, container, false)
        userAnnoncesViewModel = ViewModelProvider(this).get(UserAnnoncesViewModel::class.java)
        exchangeViewModel=ViewModelProvider(this).get(ExchangeViewModel::class.java)
        (activity as MainActivity).setBottomNavigation(false)
        (activity as MainActivity).setToolbar(true)
        val sharedPreferences =
            requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val token =sharedPreferences.getString("authToken",null)
        val id_HisAnnonce = arguments?.getLong("id_HisAnnonce")
        val username = arguments?.getString("username")
        binding.annoncesList.apply {
            layoutManager = LinearLayoutManager(this.context)
            userAnnoncesViewModel.annonces.observe(viewLifecycleOwner, { annonces ->

                selectExchangeAnnonceAdapter = SelectExchangeAnnonceAdapter(annonces,
                    SelectExchangeAnnonceAdapter.OnClickListener{
                            clickedItem->
                        val id_AnnonceToExchange = clickedItem.id
                        val exchange= Exchange(0,username!!,
                            id_HisAnnonce!!,id_AnnonceToExchange,false)
                        binding.swap.visibility=View.VISIBLE
                        binding.swap.setOnClickListener{
                        exchangeViewModel.addExchangeOffer(exchange,token.toString())
                            findNavController().navigate(R.id.action_chooseExchangeAnnonceFragment_to_feedToysFragment)}
                    })
                binding.annoncesList.adapter = selectExchangeAnnonceAdapter
            })
            userAnnoncesViewModel.getUserAnnonces(token.toString())
            Log.i("toys fetched", "toys fetched")

        }

        return binding.root
    }


}
