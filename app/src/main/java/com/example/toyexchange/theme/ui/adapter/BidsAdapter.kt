package com.example.toyexchange.theme.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toyexchange.Common.PicturesConverter
import com.example.toyexchange.Domain.model.Bid
import com.example.toyexchange.Domain.model.Toy
import com.example.toyexchange.R
import com.example.toyexchange.databinding.BidsItemBinding
import com.example.toyexchange.databinding.ToyItemBinding
import com.example.trypostrequest.ui.adapter.ToysRecyclerViewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class BidsAdapter (
    private var bids:List<Bid>,
    private val onClickListener: OnClickListener,
    private val parentLifecycleScope: CoroutineScope
) : RecyclerView.Adapter<BidsAdapter.ToysViewHolder>(){

    inner class ToysViewHolder(private val binding: BidsItemBinding) : RecyclerView.ViewHolder(binding.root){
        //var toy_image=itemView.findViewById<ImageView>(R.id.toy_image)

        fun bind(bid:Bid) {
            binding.priceProposed.text = bid.price_proposed
            binding.userName.text=bid.username

            parentLifecycleScope.launch {
                Log.i("bidder image in adapter","bidder image in adapter")
                val image=PicturesConverter.base64ToBitmap(bid.profile_picture_path)
                binding.bidderImage.setImageBitmap(PicturesConverter.getRoundedBitmap(image!!,100))
           }
            if (adapterPosition == 0) {
                binding.winner.setImageResource(R.drawable.winner)
                Log.i("winner icon","winner icon")
            }
        }
    }
    private val sortedItems = bids.sortedByDescending { it.price_proposed.toInt()}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToysViewHolder {
        val binding= BidsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToysViewHolder(binding)
        //traja3li view lkol, nemchi lil view holder lfou9 w declari jme3a
    }
    override fun getItemCount(): Int {
        return sortedItems.size
    }
    override fun onBindViewHolder(holder: ToysViewHolder, position: Int) {
        // position de chaque item
        // fun hethi bch yjibli item eli cliquet alih
        //te5ou mil viewholder nafsou objet meno , ya nadi bin toul ou
        val bid=sortedItems[position]

        holder.bind(bid)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(bid)
        }
        if (position == 0) {
            Log.i("BidsAdapter", "First item: ${bid.username}")
        }

        //holder.toy_name ..

    }
    class OnClickListener(val clickListener: (bid: Bid) -> Unit) {
        fun onClick(bid: Bid) = clickListener(bid)
    }


}