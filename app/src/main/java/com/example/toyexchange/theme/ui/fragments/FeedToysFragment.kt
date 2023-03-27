package com.example.toyexchange.theme.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toyexchange.Domain.model.Toy
import com.example.toyexchange.Presentation.ToysViewModel.DetailsToyViewModel
import com.example.toyexchange.Presentation.ToysViewModel.ToysViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.FeedToysFragmentBinding
import com.example.trypostrequest.ui.adapter.ToysRecyclerViewAdapter


class FeedToysFragment : Fragment(R.layout.feed_toys_fragment){

    private lateinit var toysViewModel: ToysViewModel
    private lateinit var detailsToyViewModel: DetailsToyViewModel
    lateinit var toys_recycler: RecyclerView
    lateinit var toysRecyclerViewAdapter: ToysRecyclerViewAdapter
    private lateinit var searchView: SearchView
    private lateinit var binding: FeedToysFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FeedToysFragmentBinding.inflate(inflater, container, false)
        // create instance of viewmodel , the life cycle library creates it for us so if the viewmodel destroyed we don't need to recreated
        toysViewModel = ViewModelProvider(this).get(ToysViewModel::class.java)
        detailsToyViewModel = ViewModelProvider(this).get(DetailsToyViewModel::class.java)

        /* toysRecyclerViewAdapter = ToysRecyclerViewAdapter(emptyList(),ToysRecyclerViewAdapter.OnClickListener{ photo ->
            Toast.makeText(context, "${photo.name}", Toast.LENGTH_SHORT).show() }) */
        binding.toysList.apply {
            layoutManager = LinearLayoutManager(this.context)

            //when ever the data changes this code below is called
            //it's the observer of the live data
            toysViewModel.toys.observe(viewLifecycleOwner, { toys ->

                toysRecyclerViewAdapter = ToysRecyclerViewAdapter(toys,
                    ToysRecyclerViewAdapter.OnClickListener{
                        clickedItem->
                        val bundle = bundleOf("id" to clickedItem.id)
                        findNavController().navigate(R.id.action_feedToysFragment_to_detailsToysFragment,bundle)
                })
                binding.toysList.adapter = toysRecyclerViewAdapter
            })
            toysViewModel.fetchToys()
            Log.i("toys fetched", "toys fetched")


            // search in the recycler view
            binding.etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if (toysViewModel.toys.value?.isEmpty() == true) {
                        Toast.makeText(context, "this toy is unavailable", Toast.LENGTH_LONG).show()
                    }
                    Log.i("onQueryTextSubmit", "onQueryTextSubmit")
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    toysViewModel.searchToys(newText)
                    Log.i("onQueryTextChange", "onQueryTextChange")
                    return false
                }
            })

            return binding.root
        }
    }


}





