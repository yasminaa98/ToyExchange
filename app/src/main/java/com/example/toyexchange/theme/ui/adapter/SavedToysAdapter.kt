package com.example.toyexchange.theme.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.toyexchange.Common.Constants
import com.example.toyexchange.Domain.model.Toy
import com.example.toyexchange.Domain.model.ToysInformation
import com.example.toyexchange.databinding.ToyItemBinding
import com.example.trypostrequest.ui.adapter.ToysRecyclerViewAdapter
import kotlinx.coroutines.CoroutineScope

class SavedToysAdapter (
    private var toys:List<ToysInformation>,
    private val onClickListener: OnClickListener,
    private val parentLifecycleScope: CoroutineScope
) : RecyclerView.Adapter<SavedToysAdapter.ToysViewHolder>(){


class ToysViewHolder(private val binding: ToyItemBinding) : RecyclerView.ViewHolder(binding.root){
        //var toy_image=itemView.findViewById<ImageView>(R.id.toy_image)
        fun bind(toy: ToysInformation) {
            binding.toyName.text = toy.name
            Glide.with(itemView)
                .load(Constants.IMAGE_URL +toy.picturePath)
                .apply(RequestOptions.circleCropTransform()) // Apply circular crop transformation
                .into(binding.toyImage)
           /* Glide.with(itemView)
                .load(Constants.IMAGE_URL +toy.picturePath)
                .apply(RequestOptions.circleCropTransform()) // Apply circular crop transformation
                .into(binding.toyImage)*/

            /* parentLifecycleScope.launch {
                 val image=PicturesConverter.base64ToBitmap(toy.picturePath)
                 binding.toyImage.setImageBitmap(PicturesConverter.getRoundedBitmap(image!!,300))}*/
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToysViewHolder {
        val binding= ToyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToysViewHolder(binding)
        //traja3li view lkol, nemchi lil view holder lfou9 w declari jme3a
    }
    override fun getItemCount(): Int {
        return toys.size
    }
    override fun onBindViewHolder(holder: ToysViewHolder, position: Int) {
        // position de chaque item
        // fun hethi bch yjibli item eli cliquet alih
        //te5ou mil viewholder nafsou objet meno , ya nadi bin toul ou
        val toy=toys[position]
        holder.itemView.setOnClickListener {
            onClickListener.onClick(toy)
        }
        holder.bind(toy)
        //filteredToys.sortedBy { it.id }

        //holder.toy_name ..

    }
    class OnClickListener(val clickListener: (toy: ToysInformation) -> Unit) {
        fun onClick(toy: ToysInformation) = clickListener(toy)
    }


}