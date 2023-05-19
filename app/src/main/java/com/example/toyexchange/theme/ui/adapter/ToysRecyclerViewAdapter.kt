package com.example.trypostrequest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toyexchange.Common.PicturesConverter
import com.example.toyexchange.Domain.model.Toy
import com.example.toyexchange.databinding.ToyItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class ToysRecyclerViewAdapter(
    private var toys:List<Toy>,
    private val onClickListener: OnClickListener,
    private val parentLifecycleScope:CoroutineScope
) : RecyclerView.Adapter<ToysRecyclerViewAdapter.ToysViewHolder>(){

    private var filteredToys: List<Toy> = toys.filter {it.estArchive==false}
    inner class ToysViewHolder(private val binding: ToyItemBinding) : RecyclerView.ViewHolder(binding.root){
        //var toy_image=itemView.findViewById<ImageView>(R.id.toy_image)

        fun bind(toy: Toy) {
            binding.toyName.text = toy.name
            //binding.toyDescription.text = toy.description
            parentLifecycleScope.launch {
                val image=PicturesConverter.base64ToBitmap(toy.picturePath)
                binding.toyImage.setImageBitmap(PicturesConverter.getRoundedBitmap(image!!,300))}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToysViewHolder {
        val binding=ToyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToysViewHolder(binding)
        //traja3li view lkol, nemchi lil view holder lfou9 w declari jme3a
    }
    override fun getItemCount(): Int {
        return filteredToys.size
    }
    override fun onBindViewHolder(holder: ToysViewHolder, position: Int) {
        // position de chaque item
        // fun hethi bch yjibli item eli cliquet alih
        //te5ou mil viewholder nafsou objet meno , ya nadi bin toul ou
        val toy=filteredToys[position]
        holder.itemView.setOnClickListener {
            onClickListener.onClick(toy)
        }
        holder.bind(toy)
        //filteredToys.sortedBy { it.id }

        //holder.toy_name ..

    }
    class OnClickListener(val clickListener: (toy: Toy) -> Unit) {
        fun onClick(toy: Toy) = clickListener(toy)
    }


}