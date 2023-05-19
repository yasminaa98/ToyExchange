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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toyexchange.Presentation.ToysViewModel.UserAnnoncesViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.MyAnnoncesFragmentBinding
import com.example.toyexchange.theme.ui.MainActivity
import com.example.toyexchange.theme.ui.adapter.UserAnnoncesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyAnnoncesFragment: Fragment(R.layout.my_annonces_fragment) {

    private lateinit var userAnnoncesViewModel:UserAnnoncesViewModel

    lateinit var userAnnoncesAdapter: UserAnnoncesAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = MyAnnoncesFragmentBinding.inflate(inflater, container, false)
        // create instance of viewmodel , the life cycle library creates it for us so if the viewmodel destroyed we don't need to recreated
        userAnnoncesViewModel = ViewModelProvider(this).get(UserAnnoncesViewModel::class.java)

        (activity as MainActivity).setBottomNavigation(true)
        (activity as MainActivity).setToolbar(false)
        /* toysRecyclerViewAdapter = ToysRecyclerViewAdapter(emptyList(),ToysRecyclerViewAdapter.OnClickListener{ photo ->
            Toast.makeText(context, "${photo.name}", Toast.LENGTH_SHORT).show() }) */
        //get the stored token
        val sharedPreferences =
            requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val token =sharedPreferences.getString("authToken",null)
        binding.annoncesList.apply {
            layoutManager = LinearLayoutManager(this.context)

            //when ever the data changes this code below is called
            //it's the observer of the live data
            userAnnoncesViewModel.annonces.observe(viewLifecycleOwner, { annonces ->

                userAnnoncesAdapter = UserAnnoncesAdapter(annonces,
                    UserAnnoncesAdapter.OnClickListener{
                            clickedItem->
                        val bundle = bundleOf("id" to clickedItem.id ,
                            "name" to clickedItem.name, "description" to clickedItem.description,
                            "price" to clickedItem.price,"category" to clickedItem.category,
                            "age_child" to clickedItem.age_child,"age_toy" to clickedItem.age_toy,
                        "state" to clickedItem.state,"image" to clickedItem.picturePath,
                        "estArchive" to clickedItem.estArchive)
                        //val image=clickedItem.picturePath
                        Log.i("bundel sent",bundle.toString())
                        findNavController().navigate(R.id.action_myAnnoncesFragment_to_myAnnoncedetailsFragment,bundle)
                    },lifecycleScope)
                binding.annoncesList.adapter = userAnnoncesAdapter
            })
            userAnnoncesViewModel.getUserAnnonces(token.toString())
            Log.i("toys fetched", "toys fetched")

        }

        return binding.root
    }


}
