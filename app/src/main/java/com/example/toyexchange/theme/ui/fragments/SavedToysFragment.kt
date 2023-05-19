package com.example.toyexchange.theme.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.toyexchange.Presentation.ToysViewModel.RoomViewModel
import com.example.toyexchange.Presentation.ToysViewModel.RoomViewModelFactory
import com.example.toyexchange.Presentation.ToysViewModel.ToysViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.FeedToysFragmentBinding
import com.example.toyexchange.databinding.SavedToysFragmentBinding
import com.example.toyexchange.db.ToyDatabase
import com.example.toyexchange.theme.ui.MainActivity
import com.example.trypostrequest.ui.adapter.ToysRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@Suppress("UNREACHABLE_CODE")
class SavedToysFragment: Fragment(R.layout.saved_toys_fragment) {
    private lateinit var roomViewModel: RoomViewModel
    lateinit var toysRecyclerViewAdapter: ToysRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = SavedToysFragmentBinding.inflate(inflater, container, false)
        // create instance of viewmodel , the life cycle library creates it for us so if the viewmodel destroyed we don't need to recreated
        val toyDatabase = ToyDatabase.getInstance(requireContext())
        val roomViewModelFactory = RoomViewModelFactory(toyDatabase)
        roomViewModel =
            ViewModelProvider(this, roomViewModelFactory)[RoomViewModel::class.java]
        (activity as MainActivity).setBottomNavigation(true)
        (activity as MainActivity).setToolbar(true)
        return binding.root

        binding.savedToys.apply {
            layoutManager = LinearLayoutManager(this.context)
            //when ever the data changes this code below is called
            //it's the observer of the live data
            roomViewModel.toys.observe(viewLifecycleOwner, { toys ->

                toysRecyclerViewAdapter = ToysRecyclerViewAdapter(toys,
                    ToysRecyclerViewAdapter.OnClickListener { clickedItem ->
                        val bundle = bundleOf(
                            "id" to clickedItem.id,
                            "name" to clickedItem.name, "description" to clickedItem.description,
                            "price" to clickedItem.price, "category" to clickedItem.category,
                            "image_url" to clickedItem.picturePath
                        )
                        findNavController().navigate(
                            R.id.action_savedToysFragment_to_detailsToysFragment,
                            bundle
                        )
                    }, lifecycleScope
                )
                binding.savedToys.adapter = toysRecyclerViewAdapter
            })
            roomViewModel.getAllToys()
            Log.i("toys fetched", "toys fetched")

        }
    }
}