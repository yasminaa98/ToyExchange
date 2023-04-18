package com.example.toyexchange.theme.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.toyexchange.Presentation.ToysViewModel.ToysViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.FeedToysFragmentBinding
import com.example.toyexchange.theme.ui.MainActivity
import com.example.trypostrequest.ui.adapter.ToysRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedToysFragment : Fragment(R.layout.feed_toys_fragment){

    private lateinit var toysViewModel: ToysViewModel
    lateinit var toysRecyclerViewAdapter: ToysRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FeedToysFragmentBinding.inflate(inflater, container, false)
        // create instance of viewmodel , the life cycle library creates it for us so if the viewmodel destroyed we don't need to recreated
        toysViewModel = ViewModelProvider(this).get(ToysViewModel::class.java)
        (activity as MainActivity).setBottomNavigation(true)
        (activity as MainActivity).setToolbar(false)
        //image slider
        val imageList=ArrayList<SlideModel>()
        imageList.clear()
        imageList.add(SlideModel(R.drawable.img1))
        imageList.add(SlideModel(R.drawable.img2))
        imageList.add(SlideModel(R.drawable.img3))
        imageList.add(SlideModel(R.drawable.img4))
        imageList.add(SlideModel(R.drawable.img1))

        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)

        /* toysRecyclerViewAdapter = ToysRecyclerViewAdapter(emptyList(),ToysRecyclerViewAdapter.OnClickListener{ photo ->
            Toast.makeText(context, "${photo.name}", Toast.LENGTH_SHORT).show() }) */
        //get the stored token
        val sharedPreferences =
            requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val token =sharedPreferences.getString("authToken",null)

        binding.toysList.apply {
            layoutManager = LinearLayoutManager(this.context)

            //when ever the data changes this code below is called
            //it's the observer of the live data
            toysViewModel.toys.observe(viewLifecycleOwner, { toys ->

                toysRecyclerViewAdapter = ToysRecyclerViewAdapter(toys,
                    ToysRecyclerViewAdapter.OnClickListener{
                        clickedItem->
                        val bundle = bundleOf("id" to clickedItem.id ,
                            "name" to clickedItem.name, "description" to clickedItem.description,
                        "price" to clickedItem.price,"category" to clickedItem.category,
                            "image_url" to clickedItem.image_url)
                        findNavController().navigate(R.id.action_feedToysFragment_to_detailsToysFragment,bundle)
                })
                binding.toysList.adapter = toysRecyclerViewAdapter
            })
            toysViewModel.fetchToys(token.toString())
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
            //filter by category
            val checkboxListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                val category = when (buttonView.tag) {
                    "musical" -> "musical"
                    "learning" -> "learning"
                    "electronic" -> "electronic"
                    else -> ""
                }

                if (isChecked) {
                    if (toysViewModel.toys.value?.isEmpty() == true) {

                        Toast.makeText(context, "this toy is unavailable", Toast.LENGTH_LONG).show()
                    } else {
                        toysViewModel.searchToysByCategory(category)

                    }
                }

            }

            binding.musical.setOnCheckedChangeListener(checkboxListener)
            binding.puzzel.setOnCheckedChangeListener(checkboxListener)
            binding.electronic.setOnCheckedChangeListener(checkboxListener)
            binding.learning.setOnCheckedChangeListener(checkboxListener)



            /*binding.musical.setOnCheckedChangeListener{ _,isChecked ->
                if(isChecked) {
                    if (toysViewModel.toys.value?.isEmpty() == true) {
                        Toast.makeText(context, "this toy is unavailable", Toast.LENGTH_LONG).show()
                    } else {
                        toysViewModel.searchToysByCategory("musical")
                    }
                }
                else{
                    toysViewModel.fetchToys()

                }
            } */


            return binding.root
        }
    }


}





