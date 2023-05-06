package com.example.toyexchange.theme.ui.fragments.AnnoncesFragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toyexchange.Presentation.ToysViewModel.DetailsToyViewModel
import com.example.toyexchange.Presentation.ToysViewModel.ExchangeViewModel
import com.example.toyexchange.Presentation.ToysViewModel.UserAnnoncesViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.NotificationExchangeFragmentBinding
import com.example.toyexchange.databinding.NotificationExchangeResultFragmentBinding
import com.example.toyexchange.theme.ui.MainActivity
import com.example.toyexchange.theme.ui.adapter.ExchangeResultAdapter
import com.example.toyexchange.theme.ui.adapter.NotificationAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangeResultFragment:Fragment(R.layout.notification_exchange_result_fragment) {
    private lateinit var exchangeViewModel: ExchangeViewModel
    private lateinit var exchangeResultAdapter: ExchangeResultAdapter
    private lateinit var detailsToyViewModel: DetailsToyViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = NotificationExchangeResultFragmentBinding.inflate(inflater, container, false)
        // create instance of viewmodel , the life cycle library creates it for us so if the viewmodel destroyed we don't need to recreated
        exchangeViewModel = ViewModelProvider(this).get(ExchangeViewModel::class.java)
        detailsToyViewModel = ViewModelProvider(this).get(DetailsToyViewModel::class.java)
        (activity as MainActivity).setBottomNavigation(true)
        (activity as MainActivity).setToolbar(true)
        /* toysRecyclerViewAdapter = ToysRecyclerViewAdapter(emptyList(),ToysRecyclerViewAdapter.OnClickListener{ photo ->
            Toast.makeText(context, "${photo.name}", Toast.LENGTH_SHORT).show() }) */
        //get the stored token
        val sharedPreferences =
            requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val token =sharedPreferences.getString("authToken",null)
        val username = sharedPreferences.getString("username", null)
        Log.i("sender username",username.toString())

        binding.exchangeNotificationResultList.apply {
            layoutManager = LinearLayoutManager(this.context)

            exchangeViewModel.sender.observe(viewLifecycleOwner, Observer {
                exchangeResultAdapter = ExchangeResultAdapter(it,
                    ExchangeResultAdapter.OnClickListener{
                            clickedItem->
                        val bundle=bundleOf("id_exchange" to clickedItem.id,"id_sender_annonce" to clickedItem.id_sender_annonce,
                            "id_receiver_annonce" to clickedItem.id_receiver_annonce)
                        findNavController().navigate(R.id.action_notificationViewPager_to_exchangeDecisionFragment,bundle)

                    })
                binding.exchangeNotificationResultList.adapter = exchangeResultAdapter
                Log.i("adapter senderrr", "adapter senderrr")

            })
            exchangeViewModel.getSenderRequests(username.toString())
            Log.i("notifications fetched", "notifications fetched")

        }

        return binding.root
    }


}
