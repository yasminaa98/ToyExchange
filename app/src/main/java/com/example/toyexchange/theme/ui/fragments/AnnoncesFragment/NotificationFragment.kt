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
import com.example.toyexchange.Presentation.ToysViewModel.ExchangeViewModel
import com.example.toyexchange.Presentation.ToysViewModel.UserAnnoncesViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.MyAnnoncesFragmentBinding
import com.example.toyexchange.databinding.NotifcationFragmentBinding
import com.example.toyexchange.theme.ui.MainActivity
import com.example.toyexchange.theme.ui.adapter.NotificationAdapter
import com.example.toyexchange.theme.ui.adapter.UserAnnoncesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment: Fragment(R.layout.notifcation_fragment) {

    private lateinit var exchangeViewModel: ExchangeViewModel
    lateinit var notificationAdapter: NotificationAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = NotifcationFragmentBinding.inflate(inflater, container, false)
        // create instance of viewmodel , the life cycle library creates it for us so if the viewmodel destroyed we don't need to recreated
        exchangeViewModel = ViewModelProvider(this).get(ExchangeViewModel::class.java)
        (activity as MainActivity).setBottomNavigation(true)
        (activity as MainActivity).setToolbar(true)
        /* toysRecyclerViewAdapter = ToysRecyclerViewAdapter(emptyList(),ToysRecyclerViewAdapter.OnClickListener{ photo ->
            Toast.makeText(context, "${photo.name}", Toast.LENGTH_SHORT).show() }) */
        //get the stored token
        val sharedPreferences =
            requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val token =sharedPreferences.getString("authToken",null)
        val username = sharedPreferences.getString("username", null)


        binding.notificationList.apply {
            layoutManager = LinearLayoutManager(this.context)

            //when ever the data changes this code below is called
            //it's the observer of the live data
            exchangeViewModel.exchange.observe(viewLifecycleOwner, Observer {

                notificationAdapter = NotificationAdapter(it,
                    NotificationAdapter.OnClickListener{
                            clickedItem->
                    })
                binding.notificationList.adapter = notificationAdapter
            })
            exchangeViewModel.getUserExchange(username.toString())
            Log.i("notifications fetched", "notifications fetched")

        }

        return binding.root
    }


}
