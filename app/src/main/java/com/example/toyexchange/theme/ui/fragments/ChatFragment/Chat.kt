package com.example.toyexchange.theme.ui.fragments.ChatFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toyexchange.R
import com.example.toyexchange.databinding.ChatFragmentBinding
import com.example.toyexchange.databinding.FeedToysFragmentBinding
import com.example.toyexchange.theme.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Chat:Fragment(R.layout.chat_fragment) {

    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDbRef: DatabaseReference
    private lateinit var messageListener: ValueEventListener

    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = ChatFragmentBinding.inflate(inflater, container, false)
        (activity as MainActivity).setBottomNavigation(false)
        (activity as MainActivity).setToolbar(true)

        val name = arguments?.getString("username")
        val receiverUid = arguments?.getString("uid")
        Log.i("reciever uid",receiverUid.toString())
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDbRef = FirebaseDatabase.getInstance().reference

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        //supportActionBar?.title = name


        messageList = ArrayList()
        messageAdapter = MessageAdapter(requireContext(), messageList)

        binding.charRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.charRecyclerView.adapter = messageAdapter

        // logic for adding data to recyclerView
        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for(postSnapshot in snapshot.children){

                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)

                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        // adding the message to database
        binding.sentButton.setOnClickListener{

            val message = binding.messageBox.text.toString()
            val messageObject = Message(message, senderUid)

            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                   mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            binding.messageBox.setText("")
        }

        return binding.root
    }

}