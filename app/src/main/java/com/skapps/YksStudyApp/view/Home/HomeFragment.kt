package com.skapps.YksStudyApp.view.Home

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.skapps.YksStudyApp.R
import com.skapps.YksStudyApp.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding?=null
    private val binding get() = _binding
    private lateinit var countDownTimer:CountDownTimer
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding=FragmentHomeBinding.inflate(inflater,container,false)
        printDifferenceDateForHours()
        binding!!.pomodoro.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_pomodoroFragment)
        }
        return binding?.root
    }

    fun printDifferenceDateForHours() {
        val currentTime = Calendar.getInstance().time
        val endDateDay = "29/09/2022 00:00:00"
        val format1 = SimpleDateFormat("dd/MM/yyyy hh:mm:ss",Locale.getDefault())
        val endDate = format1.parse(endDateDay)
        //milliseconds
        var different = endDate.time - currentTime.time
        countDownTimer = object : CountDownTimer(different, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                var diff = millisUntilFinished
                val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60
                val daysInMilli = hoursInMilli * 24

                val elapsedDays = diff / daysInMilli
                diff %= daysInMilli

                val elapsedHours = diff / hoursInMilli
                diff %= hoursInMilli

                val elapsedMinutes = diff / minutesInMilli
                diff %= minutesInMilli

                val elapsedSeconds = diff / secondsInMilli

                binding?.dateYks?.text = "$elapsedDays Gün $elapsedHours Saat $elapsedMinutes Dakika $elapsedSeconds sn. kaldı."
            }

            override fun onFinish() {
                binding?.dateYks?.text = "Bugün Sınav"
            }
        }.start()
    }


    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

}