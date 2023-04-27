package com.example.toyexchange.Common

import android.os.CountDownTimer
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class CountDownManager {

    companion object {
        fun startCountDown(endDateTime: String, textView: TextView, onFinishCallback: () -> Unit) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val futureDate = dateFormat.parse(endDateTime) // replace with your future date

            val countDownTimer = object : CountDownTimer(futureDate.time - System.currentTimeMillis(), 1000) {
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
                    textView.text = timeString
                }

                override fun onFinish() {
                    // Do something when the countdown finishes
                   onFinishCallback()
                }
            }

            countDownTimer.start()
        }
    }
}
