package com.example.toyexchange.theme.ui.fragments.Exchange

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
import com.example.toyexchange.Presentation.ToysViewModel.ExchangeViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.NotificationExchangeFragmentBinding
import com.example.toyexchange.theme.ui.MainActivity
import com.example.toyexchange.theme.ui.adapter.NotificationAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment: Fragment(R.layout.notification_exchange_fragment) {

    private lateinit var exchangeViewModel: ExchangeViewModel
    lateinit var notificationAdapter: NotificationAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = NotificationExchangeFragmentBinding.inflate(inflater, container, false)
        // create instance of viewmodel , the life cycle library creates it for us so if the viewmodel destroyed we don't need to recreated
        exchangeViewModel = ViewModelProvider(this).get(ExchangeViewModel::class.java)
        (activity as MainActivity).setBottomNavigation(true)
        (activity as MainActivity).setToolbar(false)
        /* toysRecyclerViewAdapter = ToysRecyclerViewAdapter(emptyList(),ToysRecyclerViewAdapter.OnClickListener{ photo ->
            Toast.makeText(context, "${photo.name}", Toast.LENGTH_SHORT).show() }) */
        //get the stored token
        val sharedPreferences =
            requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val token =sharedPreferences.getString("authToken",null)
        val username = sharedPreferences.getString("username", null)


        binding.exchangeNotificationList.apply {
            layoutManager = LinearLayoutManager(this.context)

            //when ever the data changes this code below is called
            //it's the observer of the live data
            exchangeViewModel.exchange.observe(viewLifecycleOwner, Observer {
                notificationAdapter = NotificationAdapter(requireActivity(),it,
                    NotificationAdapter.OnClickListener{
                            clickedItem->
                        val bundle=bundleOf("id_exchange" to clickedItem.id,"id_sender_annonce" to clickedItem.id_sender_annonce,
                            "id_receiver_annonce" to clickedItem.id_receiver_annonce)
                        Log.i("bundle sender", "bundle sender")

                        findNavController().navigate(R.id.action_notificationViewPager_to_exchangeDecisionFragment,bundle)
                    })
                binding.exchangeNotificationList.adapter = notificationAdapter

            })
            exchangeViewModel.getUserExchanges(username.toString())

        }

        return binding.root
    }


}
