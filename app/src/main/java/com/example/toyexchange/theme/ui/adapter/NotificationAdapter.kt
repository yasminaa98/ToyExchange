package com.example.toyexchange.theme.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.toyexchange.Domain.model.Exchange
import com.example.toyexchange.databinding.NotificationItemBinding
class NotificationAdapter (
    private val context: Context,
    private var exchanges:List<Exchange>,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<NotificationAdapter.ToysViewHolder>(){


    class ToysViewHolder(private val binding: NotificationItemBinding) : RecyclerView.ViewHolder(binding.root){
        //var toy_image=itemView.findViewById<ImageView>(R.id.toy_image)

        fun bind(exchange: Exchange) {
            binding.hisAnnonce.text = exchange.id_receiver_annonce.toString()
            binding.annonceToExchange.text = exchange.id_sender_annonce.toString()

        }
    }
    private val acceptedExchanges = mutableListOf<Exchange>()
    private val declinedExchanges = mutableListOf<Exchange>()
    private val otherExchanges = mutableListOf<Exchange>()



    init {
        exchanges.forEach { exchange ->
            when (exchange.status) {
                "accepted" -> acceptedExchanges.add(exchange)
                "declined" -> declinedExchanges.add(exchange)
                else -> otherExchanges.add(exchange)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToysViewHolder {
        val binding= NotificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToysViewHolder(binding)
        //traja3li view lkol, nemchi lil view holder lfou9 w declari jme3a
    }
    override fun getItemCount(): Int {
        return otherExchanges.size + acceptedExchanges.size + declinedExchanges.size
    }
    @SuppressLint("CommitPrefEdits")
    override fun onBindViewHolder(holder: ToysViewHolder, position: Int) {
        when {
            position < otherExchanges.size -> {
                val exchange = otherExchanges[position]
                holder.itemView.setOnClickListener {
                    onClickListener.onClick(exchange)
                }
                holder.bind(exchange)
            }
            position - otherExchanges.size < acceptedExchanges.size -> {
                val exchange = acceptedExchanges[position - otherExchanges.size]
                holder.itemView.setOnClickListener {
                    onClickListener.onClick(exchange)
                }
                holder.bind(exchange)
            }
            else -> {
                val exchange = declinedExchanges[position - otherExchanges.size - acceptedExchanges.size]
                holder.bind(exchange)
            }
        }
        if (exchanges.any { it.status != "accepted"}) {
            val sharedPreferences =
                context.getSharedPreferences("notif", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("notif", "new")
            Log.i("notif added new",editor.toString())


        }
        else{
            val sharedPreferences =
                context.getSharedPreferences("notif", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("notif", "old")
            Log.i("notif added old","")


        }

    }


    class OnClickListener(val clickListener: (exchange: Exchange) -> Unit) {
        fun onClick(exchange: Exchange) = clickListener(exchange)
    }




}