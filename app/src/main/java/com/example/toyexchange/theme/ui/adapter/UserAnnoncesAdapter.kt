package com.example.toyexchange.theme.ui.adapter

import com.example.toyexchange.Domain.model.Annonce

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toyexchange.Domain.model.Toy
import com.example.toyexchange.databinding.MyAnnonceItemBinding
import com.example.toyexchange.databinding.ToyItemBinding


class UserAnnoncesAdapter(
    private var annonces:List<Annonce>,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<UserAnnoncesAdapter.ToysViewHolder>(){




    class ToysViewHolder(private val binding: MyAnnonceItemBinding) : RecyclerView.ViewHolder(binding.root){
        //var toy_image=itemView.findViewById<ImageView>(R.id.toy_image)

        fun bind(annonce: Annonce) {
            binding.annonceName.text = annonce.name
            //binding.toyDescription.text = toy.description
            Glide.with(itemView)
                .load(annonce.image_url)
                .into(binding.annonceImage)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToysViewHolder {
        val binding=MyAnnonceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToysViewHolder(binding)
        //traja3li view lkol, nemchi lil view holder lfou9 w declari jme3a
    }
    override fun getItemCount(): Int {
        return annonces.size
    }
    override fun onBindViewHolder(holder: ToysViewHolder, position: Int) {
        // position de chaque item
        // fun hethi bch yjibli item eli cliquet alih
        //te5ou mil viewholder nafsou objet meno , ya nadi bin toul ou
        val annonce=annonces[position]
        holder.itemView.setOnClickListener {
            onClickListener.onClick(annonce)
        }
        holder.bind(annonce)

        //holder.toy_name ..

    }
    class OnClickListener(val clickListener: (annonce: Annonce) -> Unit) {
        fun onClick(annonce: Annonce) = clickListener(annonce)
    }


}