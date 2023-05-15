package com.example.toyexchange.theme.ui.fragments.ChatFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.toyexchange.R

class UserFirebaseAdapter (val context: FragmentActivity, val userList: ArrayList<UserFirebase>):
    RecyclerView.Adapter<UserFirebaseAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return UserViewHolder(view)
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        // bind with text
        val currentUser = userList[position]
        holder.textName.text = currentUser.name
        holder.itemView.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("username", currentUser.name)
            bundle.putString("uid", currentUser.uid)
            val navController = Navigation.findNavController(context, R.id.toysNavHostFragment)
            navController.navigate(R.id.chat,bundle)
            /*val chatFragment = Chat()
            chatFragment.arguments = bundle
            val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, chatFragment)
            transaction.addToBackStack(null)
            transaction.commit()*/
        }

    }
    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName = itemView.findViewById<TextView>(R.id.txt_name)

    }
}