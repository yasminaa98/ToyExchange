package com.example.toyexchange.theme.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toyexchange.Domain.model.Annonce
import com.example.toyexchange.Domain.model.Exchange
import com.example.toyexchange.databinding.MyAnnonceItemBinding
import com.example.toyexchange.databinding.NotificationItemBinding

class NotificationAdapter (
    private var exchanges:List<Exchange>,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<NotificationAdapter.ToysViewHolder>(){


    class ToysViewHolder(private val binding: NotificationItemBinding) : RecyclerView.ViewHolder(binding.root){
        //var toy_image=itemView.findViewById<ImageView>(R.id.toy_image)

        fun bind(exchange: Exchange) {
            binding.hisAnnonce.text = exchange.id_HisAnnonce.toString()
            binding.annonceToExchange.text = exchange.id_AnnonceToExchange.toString()


        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToysViewHolder {
        val binding= NotificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToysViewHolder(binding)
        //traja3li view lkol, nemchi lil view holder lfou9 w declari jme3a
    }
    override fun getItemCount(): Int {
        return exchanges.size
    }
    override fun onBindViewHolder(holder: ToysViewHolder, position: Int) {
        // position de chaque item
        // fun hethi bch yjibli item eli cliquet alih
        //te5ou mil viewholder nafsou objet meno , ya nadi bin toul ou
        val exchange=exchanges[position]
        holder.itemView.setOnClickListener {
            onClickListener.onClick(exchange)
        }
        holder.bind(exchange)

        //holder.toy_name ..

    }
    class OnClickListener(val clickListener: (exchange: Exchange) -> Unit) {
        fun onClick(exchange: Exchange) = clickListener(exchange)
    }




}