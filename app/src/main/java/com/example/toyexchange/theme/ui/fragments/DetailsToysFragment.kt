package com.example.toyexchange.theme.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.toyexchange.Domain.model.Toy
import com.example.toyexchange.Presentation.ToysViewModel.DetailsToyViewModel
import com.example.toyexchange.Presentation.ToysViewModel.DetailsToyViewModelFactory
import com.example.toyexchange.R
import com.example.toyexchange.databinding.ToyDetailsFragmentBinding
import com.example.toyexchange.db.ToyDatabase
import com.example.toyexchange.theme.ui.MainActivity
import com.example.trypostrequest.ui.adapter.ToysRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsToysFragment : Fragment(R.layout.toy_item) {
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
        val toyDatabase = ToyDatabase.getInstance(requireContext())

        val detailsViewModelFactory = DetailsToyViewModelFactory(toyDatabase)
        detailsToyViewModel =
            ViewModelProvider(this, detailsViewModelFactory)[DetailsToyViewModel::class.java] //onFavoriteClick()
        //maintain checkbox state :
        val toyId = arguments?.getInt("id")
        val sharedPreferences =
            requireActivity().getSharedPreferences("my_checkbox_${toyId}", Context.MODE_PRIVATE)
        val checkboxState = sharedPreferences.getBoolean("checkbox_state", false)
        binding.checkBox.isChecked = checkboxState

        // get item selected
        val name = arguments?.getString("name").toString()
        val description = arguments?.getString("description").toString()
        val price = arguments?.getString("price").toString()
        val category = arguments?.getString("category").toString()
        val image = arguments?.getString("image_url").toString()
        binding.apply {
            toyName.text = name
            toyDescription.text = description
            toyPrice.text = price.toString()
            (activity as MainActivity).setBottomNavigation(false)
            (activity as MainActivity).setToolbar(true)
        }

        val toyToSave = Toy(toyId!!, category, description, image, name, price,"","","")
        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("checkbox_state", isChecked).apply()
            if (isChecked){
            detailsToyViewModel.insertToy(toyToSave)
            Toast.makeText(requireContext(), "Toy saved", Toast.LENGTH_SHORT).show()}
            else{
                detailsToyViewModel.deleteToy(toyToSave)
                Toast.makeText(requireContext(), "Toy deleted", Toast.LENGTH_SHORT).show()}

        }
        return binding.root
    }
}

/* private fun onFavoriteClick() {
     binding.button.setOnClickListener{
         detailsToyViewModel.insertToy(getToyClicked())
         Toast.makeText(requireContext(),"Toy saved",Toast.LENGTH_SHORT).show()

     } */

/* override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
     super.onViewCreated(view, savedInstanceState)
     detailsToyViewModel.toysDetails.observe(viewLifecycleOwner, { toyDetails ->
         binding.toyName.text = toyDetails.name
         binding.toyDescription.text = toyDetails.description
         binding.toyPrice.text = toyDetails.price.toString()
 })

 //binding.toyPrice.text = arguments?.getString("price")

     //Log.i("viewmodel created","vm Created" )


}*/





