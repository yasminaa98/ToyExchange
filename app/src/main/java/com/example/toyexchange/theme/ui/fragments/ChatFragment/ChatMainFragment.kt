package com.example.toyexchange.theme.ui.fragments.ChatFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toyexchange.R
import com.example.toyexchange.databinding.ChatFragmentBinding
import com.example.toyexchange.databinding.ChatMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatMainFragment:Fragment(R.layout.chat_main) {
    private lateinit var userList: ArrayList<UserFirebase>
    private lateinit var adapter: UserFirebaseAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ChatMainBinding.inflate(inflater, container, false)
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference //getReference()

        userList = ArrayList()
        adapter = UserFirebaseAdapter(requireActivity() as FragmentActivity, userList)

        binding.userRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.userRecyclerView.adapter = adapter

        // realtime database load
        mDbRef.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()
                for (postSnapshot in snapshot.children) {
                    // get one user
                    val currentUser = postSnapshot.getValue(UserFirebase::class.java)
                    // add user to list
                    if(mAuth.currentUser?.uid != currentUser?.uid){
                        userList.add(currentUser!!)
                    }

                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return binding.root
    }
}