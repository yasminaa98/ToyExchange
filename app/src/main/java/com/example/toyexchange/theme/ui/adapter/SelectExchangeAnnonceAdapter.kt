package com.example.toyexchange.theme.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toyexchange.Common.PicturesConverter
import com.example.toyexchange.Domain.model.Annonce
import com.example.toyexchange.databinding.ExchangeAnnonceItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SelectExchangeAnnonceAdapter (private var annonces:List<Annonce>,
                                    private val onClickListener: OnClickListener,
                                    private val parentLifecycleScope: CoroutineScope
) : RecyclerView.Adapter<SelectExchangeAnnonceAdapter.ToysViewHolder>(){

    private var selectedItem = -1
    inner class ToysViewHolder(private val binding: ExchangeAnnonceItemBinding) : RecyclerView.ViewHolder(binding.root){
        //var toy_image=itemView.findViewById<ImageView>(R.id.toy_image)

        fun bind(annonce: Annonce, isSelected: Boolean) {
            binding.annonceName.text = annonce.name
            binding.radioButton.isChecked = isSelected
            binding.radioButton.isEnabled=false
            //binding.toyDescription.text = toy.description
            parentLifecycleScope.launch {
            binding.annonceImage.setImageBitmap(PicturesConverter.base64ToBitmap(annonce.picturePath))}



        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToysViewHolder {
        val binding= ExchangeAnnonceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToysViewHolder(binding)
  }
    override fun getItemCount(): Int {
        return annonces.size
    }
    override fun onBindViewHolder(holder: ToysViewHolder, @SuppressLint("RecyclerView") position: Int) {

        val annonce=annonces[position]
        val isSelected = selectedItem == position
        holder.itemView.setOnClickListener {
            selectedItem = position
            notifyDataSetChanged()
            onClickListener.onClick(annonce)
        }
        holder.bind(annonce,isSelected)

    }
    class OnClickListener(val clickListener: (annonce: Annonce) -> Unit) {
        fun onClick(annonce: Annonce) = clickListener(annonce)
    }
}