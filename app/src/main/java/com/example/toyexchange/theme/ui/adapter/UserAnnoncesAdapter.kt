package com.example.toyexchange.theme.ui.adapter

import android.util.Log
import com.example.toyexchange.Domain.model.Annonce

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.toyexchange.Common.Constants
import com.example.toyexchange.Common.Constants.IMAGE_URL
import com.example.toyexchange.Common.PicturesConverter
import com.example.toyexchange.databinding.MyAnnonceItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class UserAnnoncesAdapter(
    private var annonces:List<Annonce>,
    private val onClickListener: OnClickListener,
    private val parentLifecycleScope: CoroutineScope
) : RecyclerView.Adapter<UserAnnoncesAdapter.ToysViewHolder>(){


    inner class ToysViewHolder(private val binding: MyAnnonceItemBinding) : RecyclerView.ViewHolder(binding.root){
        //var toy_image=itemView.findViewById<ImageView>(R.id.toy_image)

        fun bind(annonce: Annonce) {
            binding.annonceName.text = annonce.name
            Glide.with(itemView)
                .load(IMAGE_URL +annonce.picturePath)
                .apply(RequestOptions.circleCropTransform()) // Apply circular crop transformation
                .into(binding.annonceImage)
            //binding.toyDescription.text = toy.description

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