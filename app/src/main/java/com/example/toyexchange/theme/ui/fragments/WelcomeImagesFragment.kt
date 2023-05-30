package com.example.toyexchange.theme.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.toyexchange.Presentation.ToysViewModel.ToysViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.FeedToysFragmentBinding
import com.example.toyexchange.databinding.WelcomeImagesSliderBinding
import com.example.toyexchange.theme.ui.MainActivity

class WelcomeImagesFragment : Fragment(R.layout.welcome_images_slider) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = WelcomeImagesSliderBinding.inflate(inflater, container, false)
        // create instance of viewmodel , the life cycle library creates it for us so if the viewmodel destroyed we don't need to recreated
        (activity as MainActivity).setSlideNavigaton(false)
        (activity as MainActivity).setBottomNavigation(false)
        (activity as MainActivity).setToolbar(false)
        //notification

        //image slider
        val imageList = ArrayList<SlideModel>()
        imageList.clear()
        imageList.add(SlideModel(R.drawable.slide1))
        imageList.add(SlideModel(R.drawable.slide2))
        imageList.add(SlideModel(R.drawable.slide3))
        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
        binding.skip.setOnClickListener{
            findNavController().navigate(R.id.action_welcomeImagesFragment_to_signInFragment)
        }
        return binding.root

    }
}