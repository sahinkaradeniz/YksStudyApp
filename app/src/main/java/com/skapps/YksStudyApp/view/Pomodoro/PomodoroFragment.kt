package com.skapps.YksStudyApp.view.Pomodoro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.skapps.YksStudyApp.databinding.FragmentPomodoroBinding
import com.skapps.YksStudyApp.database.LocalDatabase


class PomodoroFragment : Fragment(){
    private var _binding:FragmentPomodoroBinding?=null
    private val binding  get() = _binding
    private lateinit var viewModel:PomodoroViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this).get(PomodoroViewModel::class.java)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentPomodoroBinding.inflate(inflater,container,false)

        return binding?.root
    }







}