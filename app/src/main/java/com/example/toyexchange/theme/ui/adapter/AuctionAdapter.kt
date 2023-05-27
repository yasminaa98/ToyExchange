package com.example.toyexchange.theme.ui.adapter

import android.os.CountDownTimer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toyexchange.Domain.model.AuctionResponse
import com.example.toyexchange.databinding.AuctionItemBinding
import java.text.SimpleDateFormat
import java.util.*

class AuctionAdapter(
    private var auctionResponses:List<AuctionResponse>,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<AuctionAdapter.ToysViewHolder>(){

    class ToysViewHolder(private val binding: AuctionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val handler = Handler()
        private var isAnimationRunning = false
        fun startAnimation() {
            binding.liveImage.animate().apply {
                duration = 1000
                scaleX(1.25f).scaleY(1.25f)
            }.withEndAction {
                binding.liveImage.animate().apply {
                    duration = 1000
                    scaleX(1f).scaleY(1f)
                }
            }
        }
        fun scheduleAnimation() {
            if (!isAnimationRunning) {
                isAnimationRunning = true
                handler.postDelayed(object : Runnable {
                    override fun run() {
                        startAnimation()
                        handler.postDelayed(this, 2000)
                    }
                }, 2000)
            }
        }
        //var toy_image=itemView.findViewById<ImageView>(R.id.toy_image)

        fun bind(auctionResponse: AuctionResponse) {
            binding.auctionName.text = auctionResponse.name
                binding.auctionPrice.text = auctionResponse.initial_price
                binding.auctionEndDateTime.text = auctionResponse.end_dateTime
            binding.auctionName.text=auctionResponse.description
                val timer = auctionResponse.end_dateTime.toString()
                //countdown
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val futureDate = dateFormat.parse(timer) // replace with your future date

                val countDownTimer =
                    object : CountDownTimer(futureDate.time - System.currentTimeMillis(), 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            val seconds = millisUntilFinished / 1000
                            val minutes = seconds / 60
                            val hours = minutes / 60
                            val days = hours / 24

                            val timeString = String.format(
                                Locale.getDefault(),
                                "%02d:%02d:%02d:%02d",
                                days,
                                hours % 24,
                                minutes % 60,
                                seconds % 60
                            )
                            // Update UI with the remaining time string
                            binding.auctionEndDateTime.text = timeString
                        }

                        override fun onFinish() {
                            // Do something when the countdown finishes
                            binding.auctionEndDateTime.text = "Closed"
                            binding.liveImage.visibility=View.GONE
                            binding.liveText.visibility=View.GONE

                        }
                    }

                countDownTimer.start()

                //binding.toyDescription.text = toy.description

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToysViewHolder {
            val binding =
                AuctionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ToysViewHolder(binding)
            //traja3li view lkol, nemchi lil view holder lfou9 w declari jme3a
        }


        override fun getItemCount(): Int {
            return auctionResponses.size
        }

        override fun onBindViewHolder(holder: ToysViewHolder, position: Int) {
            // position de chaque item
            // fun hethi bch yjibli item eli cliquet alih
            //te5ou mil viewholder nafsou objet meno , ya nadi bin toul ou
            val auction = auctionResponses[position]
            holder.itemView.setOnClickListener {
                onClickListener.onClick(auction)
            }
            holder.bind(auction)

            holder.startAnimation()
            holder.scheduleAnimation()


            //holder.toy_name ..

        }

        class OnClickListener(val clickListener: (auctionResponse: AuctionResponse) -> Unit) {
            fun onClick(auctionResponse: AuctionResponse) = clickListener(auctionResponse)
        }


    }