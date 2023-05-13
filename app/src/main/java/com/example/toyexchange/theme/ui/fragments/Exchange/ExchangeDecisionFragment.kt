package com.example.toyexchange.theme.ui.fragments.Exchange

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.toyexchange.Presentation.ToysViewModel.DetailsToyViewModel
import com.example.toyexchange.Presentation.ToysViewModel.ExchangeViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.ExchangeDecisionFragmentBinding
import com.example.toyexchange.theme.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangeDecisionFragment:Fragment(R.layout.exchange_decision_fragment) {
    private lateinit var detailsToyViewModel: DetailsToyViewModel
    private lateinit var exchangeViewModel: ExchangeViewModel



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ExchangeDecisionFragmentBinding.inflate(inflater, container, false)
        detailsToyViewModel = ViewModelProvider(this).get(DetailsToyViewModel::class.java)
        exchangeViewModel = ViewModelProvider(this).get(ExchangeViewModel::class.java)
        (activity as MainActivity).setBottomNavigation(false)
        (activity as MainActivity).setToolbar(true)
        //get current user
        val sharedPreferences =
            requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("authToken", null)
        val username = sharedPreferences.getString("username", null)
        val id_exchange = arguments?.getLong("id_exchange")
        Log.i("id exchange1",id_exchange.toString())

        val my_annonce_id = arguments?.getLong("id_receiver_annonce")
        val annonce_to_exchange=arguments?.getLong("id_sender_annonce")
        Log.i("id recu ", my_annonce_id.toString()+annonce_to_exchange.toString())


        // getAnnonceById
        detailsToyViewModel.getAnnonce1ById(annonce_to_exchange!!)
        detailsToyViewModel.annonce1.observe(viewLifecycleOwner, Observer {
            if (it!= null){
                binding.annonceName.setText(it.name)
                binding.annonceDescription.setText(it.description)
                binding.ageToy.setText(it.age_toy)
                binding.ageChild.setText(it.age_child)
                binding.state.setText(it.state)
                binding.estimatedPrice.setText(it.price)
                it.id=annonce_to_exchange
                Toast.makeText(requireContext(),"annonce got successfully", Toast.LENGTH_LONG).show()
                Log.i("annonce",it.toString())
                val bundle=bundleOf("id" to annonce_to_exchange,"name" to it.name,
                    "description" to it.description,
                    "age_toy" to it.age_toy,
                    "age_child" to it.age_child,"state" to it.state,"price" to it.price,
                "image" to it.picturePath)
                Log.i("bundle in the decision",bundle.toString())
                //annonce details
                binding.hisannonce.setOnClickListener{
                    findNavController().navigate(R.id.action_exchangeDecisionFragment_to_exchangeAnnonceDetailsFragment,bundle)
                }
            }
            else{
                Toast.makeText(requireContext(),"getting annonce failed", Toast.LENGTH_LONG).show()
            }
        })

        detailsToyViewModel.getAnnonce2ById(my_annonce_id!!)
        detailsToyViewModel.annonce2.observe(viewLifecycleOwner, Observer {
            if (it!= null){
                binding.myAnnonceName.setText(it.name)
                binding.myAnnonceDescription.setText(it.description)
                binding.myAgeToy.setText(it.age_toy)
                binding.myAgeChild.setText(it.age_child)
                binding.myAnnonceState.setText(it.state)
                binding.myEstimatedPrice.setText(it.price)
                it.id=my_annonce_id
                Toast.makeText(requireContext(),"annonce got successfully", Toast.LENGTH_LONG).show()
                Log.i("annonce",it.toString())
            }
            else{
                Toast.makeText(requireContext(),"getting annonce failed", Toast.LENGTH_LONG).show()
            }
        })

        exchangeViewModel.getExchangeById(id_exchange!!)
        Log.i("id exchange",id_exchange.toString())

        exchangeViewModel.exchangeById.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                Log.i("it",it.toString())

                if (it.status=="accepted"){
                    binding.statusFixed.visibility=View.VISIBLE
                    binding.statusFixed.setText("Accepted")
                    binding.accept.visibility=View.GONE
                    binding.decline.visibility=View.GONE
                }
                else if (it.status=="declined"){
                    binding.statusFixed.visibility=View.VISIBLE
                    binding.statusFixed.setText("Declined")
                    binding.accept.visibility=View.GONE
                    binding.decline.visibility=View.GONE
                }

            }
        })

        //update exchange status
        binding.accept.visibility=View.VISIBLE
        binding.decline.visibility=View.VISIBLE
        binding.accept.setOnClickListener{
            Log.i("id exchange2",id_exchange.toString())
            exchangeViewModel.updateStatus(id_exchange!!,"accepted",token.toString())
        }
        binding.decline.setOnClickListener{
            Log.i("id exchange3",id_exchange.toString())

            exchangeViewModel.updateStatus(id_exchange!!,"declined",token.toString())
        }

        return binding.root
    }
}


