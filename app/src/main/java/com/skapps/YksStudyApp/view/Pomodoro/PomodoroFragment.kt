package com.skapps.YksStudyApp.view.Pomodoro

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.skapps.YksStudyApp.R
import com.skapps.YksStudyApp.databinding.FragmentPomodoroBinding
import com.skapps.YksStudyApp.util.LocalDatabase
import com.skapps.YksStudyApp.util.safeNavigate
import java.util.*


class PomodoroFragment : Fragment(){
    private var _binding:FragmentPomodoroBinding?=null
    private val binding  get() = _binding
    private lateinit var viewModel: PomodoroViewModel
    private var pauseTime:Long =0L
    private lateinit var localDatabase: LocalDatabase
    private  var addTime:Long = 0L
    private lateinit var calendar: Calendar


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        _binding= FragmentPomodoroBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        localDatabase= LocalDatabase(requireContext())

        viewModel= ViewModelProvider(this).get(PomodoroViewModel::class.java)
        pauseTime= localDatabase.getSharedPreference(requireContext(),"pauseTime",0L)

        observeLiveData()

        if (pauseTime != 0L){
            viewModel.startTimer(pauseTime,requireContext().applicationContext)
        }


        binding!!.startChoronometre.setOnClickListener {
                viewModel.cancelTimer()
                viewModel.startTimer(6000,requireContext().applicationContext)
        }

        binding!!.pauseButton.setOnClickListener {
            viewModel.cancelTimer()
        }
        binding!!.addPomodoro.setOnClickListener {
            findNavController().safeNavigate(PomodoroFragmentDirections.actionPomodoroFragmentToAddPomodoroFragment())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        localDatabase.setSharedPreference(requireContext().applicationContext,"pauseTime", time = pauseTime)
    }

    private fun observeLiveData(){
        viewModel.time.observe(viewLifecycleOwner) {
            it.let {
                pauseTime=it
                val seconds = (it / 1000).toInt() % 60
                val minutes = (it / (1000 * 60) % 60).toInt()
                binding!!.choronometre.text = "$minutes : $seconds"
            }
        }
    }

    override fun onResume() {
        viewModel= ViewModelProvider(this).get(PomodoroViewModel::class.java)
        arguments?.getInt("time").let {
            it?.let { it1 -> viewModel.startTimer(it*60*1000L,requireContext()) }
        }
        super.onResume()
    }

    @SuppressLint("Range")
    fun timepic(){
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setMinute(10)
            .setTitleText("Select Appointment time")
            .build()
        picker.show(requireActivity().supportFragmentManager, picker.toString())

        picker.addOnPositiveButtonClickListener {
            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] =0
            val hour=picker.hour
            var total=hour*60*1000*1000*60
            val minute=picker.minute
            total=total+minute*1000*60
            viewModel.startTimer(total.toLong(),requireContext())

        }

    }


}