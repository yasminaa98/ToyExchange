package com.example.trypostrequest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toyexchange.Domain.model.Toy
import com.example.toyexchange.databinding.ToyItemBinding


class ToysRecyclerViewAdapter(
    private var toys:List<Toy>,
    private val onClickListener: OnClickListener
  ) : RecyclerView.Adapter<ToysRecyclerViewAdapter.ToysViewHolder>(){




    class ToysViewHolder(private val binding: ToyItemBinding) : RecyclerView.ViewHolder(binding.root){
        //var toy_image=itemView.findViewById<ImageView>(R.id.toy_image)

            fun bind(toy: Toy) {
                binding.toyName.text = toy.name
                //binding.toyDescription.text = toy.description
                Glide.with(itemView)
                    .load(toy.image_url)
                    .into(binding.toyImage)
            }
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToysViewHolder {
        val binding=ToyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

        //holder.toy_name ..

    }
    class OnClickListener(val clickListener: (toy: Toy) -> Unit) {
        fun onClick(toy: Toy) = clickListener(toy)
    }


}