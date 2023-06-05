package com.example.toyexchange.theme.ui.fragments.AuctionFragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.toyexchange.Common.Constants
import com.example.toyexchange.Common.Constants.IMAGE_URL
import com.example.toyexchange.Common.CountDownManager
import com.example.toyexchange.Common.PicturesConverter
import com.example.toyexchange.Presentation.ToysViewModel.AuctionDetailsViewModel
import com.example.toyexchange.Presentation.ToysViewModel.DetailsToyViewModel
import com.example.toyexchange.R
import com.example.toyexchange.databinding.AuctionDetailsBinding
import com.example.toyexchange.theme.ui.MainActivity
import com.example.toyexchange.theme.ui.adapter.BidsAdapter
import com.example.trypostrequest.ui.adapter.ToysRecyclerViewAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AuctionDetailsFragment:Fragment(R.layout.auction_details) {
    private lateinit var auctionDetailsViewModel: AuctionDetailsViewModel
    private lateinit var detailsToyViewModel: DetailsToyViewModel
    private lateinit var selectedImage: ImageView
    lateinit var bidsAdapter:BidsAdapter

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = AuctionDetailsBinding.inflate(inflater, container, false)

        auctionDetailsViewModel = ViewModelProvider(this).get(AuctionDetailsViewModel::class.java)
        detailsToyViewModel = ViewModelProvider(this).get(DetailsToyViewModel::class.java)
        selectedImage = binding.toyImage
        selectedImage.setOnClickListener { enlargeImage(binding.toyImage.drawable) }
        (activity as MainActivity).setBottomNavigation(false)
        (activity as MainActivity).setToolbar(true)
        val sharedPreferences =
            requireActivity().getSharedPreferences("authToken", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("authToken", null)
        val username = sharedPreferences.getString("username", null)
        Log.i("token1", token.toString())

        val auctionId = arguments?.getLong("id_auction")
        val name = arguments?.getString("name")
        val description = arguments?.getString("description")
        val endDateTime = arguments?.getString("end_dateTime")
        val startDateTime = arguments?.getString("start_datetime")
        val initial_price = arguments?.getString("initial_price")
        val image = arguments?.getString("image")
        val bundle = bundleOf(
            "idAuction" to auctionId,
            "end_dateTime" to endDateTime,
            "image" to image,
            "current" to initial_price
        )
        Log.i("bundle", bundle.toString())
        Log.i("auctionId", auctionId.toString())

        binding.toyName.text = name
        binding.toyDescription.text = description
        binding.endDateTime.text = endDateTime
        binding.toyPrice.text=initial_price
        Log.i("endDateTime", endDateTime.toString())
    // get owner
        Log.i("owner get it", "")

        binding.ownerImage.setOnClickListener {
            if (binding.profileOwner.visibility==View.GONE) {
                Log.i("button clicked", "")
                binding.profileOwner.apply {
                    visibility = View.VISIBLE
                    translationY = height.toFloat()
                    // Animate from off-screen to original position
                    animate().apply {
                        translationY(-1400f)
                    }
                }
            }
        }
            binding.auctionDetailsFragment.setOnClickListener {
                if (binding.profileOwner.visibility == View.VISIBLE) {
                    Log.i("button clicked", "")
                    binding.profileOwner.apply {
                        visibility = View.GONE
                        translationY = height.toFloat()
                        // Animate from off-screen to original position
                        animate().apply {
                            translationY(1400f)
                        }
                    }
                }
            }
        auctionDetailsViewModel.getAuctionOwner(auctionId!!)
        auctionDetailsViewModel.auctionOwner.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                if (it.username==username){
                    binding.place.setBackgroundColor(R.color.grey)
                    binding.place.isEnabled=false
                    binding.place.setText("Bid unavailable")
                    binding.sendMessage.visibility=View.GONE
                    Log.i("username", "username")
                }
                binding.apply {
                    userName.setText("Username: "+it.username)
                    firstname.setText("Firstname: "+it.firstname)
                    lastname.setText("Lastname:"+it.lastname)
                    email.setText("Email: "+it.email)
                    homeaddress.setText("Home address: "+it.homeAddress)
                    phone.setText("Phone: "+it.phone.toString())
                    avgResponse.setText("I respond every "+it.avgResponseTime+" hours")
                    Glide.with(requireActivity())
                        .load(IMAGE_URL +it.profile_picture_path)
                        .apply(RequestOptions.circleCropTransform()) // Apply circular crop transformation
                        .into(binding.ownerimage)
                    Glide.with(requireActivity())
                        .load(IMAGE_URL+it.profile_picture_path)
                        .apply(RequestOptions.circleCropTransform()) // Apply circular crop transformation
                        .into(binding.ownerImage)

                }


            }
        })
        detailsToyViewModel.getAnnonceByAuction(auctionId!!)
        detailsToyViewModel.annonce.observe(viewLifecycleOwner, Observer {
            Log.i("1", "1")

            if(it!=null){
                Glide.with(requireActivity())
                    .load(IMAGE_URL+it.picturePath)
                    //.apply(RequestOptions.circleCropTransform()) // Apply circular crop transformation
                    .into(binding.toyImage)

                    }else {
                Toast.makeText(requireContext(), "getting picture failed", Toast.LENGTH_LONG).show()
            }
        })

        /* val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val currentDateTime = dateFormat.format(calendar.time)
        Log.i("currentDateTime", currentDateTime.toString())*/

        val endDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val futureDateTime: Date? = endDateTime?.let { endDateFormat.parse(it) }
        if (futureDateTime != null) {
            val calendar = Calendar.getInstance()
            calendar.time = futureDateTime
            val now = Calendar.getInstance()

            if (calendar.before(now)) {
                Log.i("before", "before")
                binding.place.isEnabled = true
                binding.place.text = "you can't place a bid !"
                binding.endDateTime.text = "Time is over !"


            } else if (calendar.after(now)) {
                Log.i("after", "after")

                CountDownManager.startCountDown(endDateTime.toString(), binding.endDateTime,{
                    findNavController().navigate(
                        R.id.action_auctionDetailsFragment_to_auctionFragment
                    )
                })
                auctionDetailsViewModel.getAuctionPrice(auctionId!!, token.toString())

                    auctionDetailsViewModel.price.observe(viewLifecycleOwner, Observer {
                        if (it != null) {
                            //auctionDetailsViewModel.getAuctionPrice(auctionId!!,token.toString())
                            val jsonObject = Gson().fromJson(it, JsonObject::class.java);
                            val messageValue = jsonObject.get("message").asString
                            binding.toyPrice.text = messageValue.toString()
                            Log.i("last price", it.toString())
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "getting info failed",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    })
                binding.place.setOnClickListener {
                    Log.i("clicked", "clicked")
                    findNavController().navigate(
                        R.id.action_auctionDetailsFragment_to_addBidFragment, bundle
                    )
                }
            }
        }
                // get bids list
                binding.bidsList.apply {
                    layoutManager = LinearLayoutManager(this.context)
                    auctionDetailsViewModel.bids.observe(viewLifecycleOwner, { priceProposed ->
                        bidsAdapter = BidsAdapter(priceProposed,
                            BidsAdapter.OnClickListener { clickedItem ->
                                val database = FirebaseDatabase.getInstance()
                                val userRef = database.getReference("user")
                                val nameToFind = clickedItem.username

                                userRef.orderByChild("name").equalTo(nameToFind).addListenerForSingleValueEvent(object :
                                    ValueEventListener {
                                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                                        for (userSnapshot in dataSnapshot.children) {
                                            val uid = userSnapshot.child("uid").getValue(String::class.java)
                                            Log.i("uid clicked is ", uid.toString())

                                            val bundle= bundleOf("uid" to uid)
                                            findNavController().navigate(R.id.action_auctionDetailsFragment_to_chat,bundle)
                                        }
                                    }
                                    override fun onCancelled(databaseError: DatabaseError) {
                                        Log.i("uid clicked is ", "error")

                                        // Handle error
                                    }
                                })
                            }, lifecycleScope
                        )
                        binding.bidsList.adapter = bidsAdapter
                    })

                    auctionDetailsViewModel.getAuctionBids(auctionId!!, token.toString())
                    Log.i("bids fetched", "bids fetched")


                }
        binding.sendMessage.setOnClickListener{
            val database = FirebaseDatabase.getInstance()
            val userRef = database.getReference("user")
            val nameToFind = auctionDetailsViewModel.auctionOwner.value?.username.toString()

            userRef.orderByChild("name").equalTo(nameToFind).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (userSnapshot in dataSnapshot.children) {
                        val uid = userSnapshot.child("uid").getValue(String::class.java)
                        Log.i("uid clicked is ", uid.toString())
                        val bundle= bundleOf("uid" to uid)
                        findNavController().navigate(R.id.action_auctionDetailsFragment_to_chat,bundle)
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.i("uid clicked is ", "error")

                    // Handle error
                }
            })

        }
        return binding.root
        }
    private fun enlargeImage(drawable: Drawable?) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_enlarged_image, null)
        val imageView = dialogView.findViewById<ImageView>(R.id.enlargedImage)

        imageView.setImageDrawable(drawable)

        dialogBuilder.setView(dialogView)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }




    }