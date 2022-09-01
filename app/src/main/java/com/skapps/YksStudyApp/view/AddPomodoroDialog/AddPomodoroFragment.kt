package com.skapps.YksStudyApp.view.AddPomodoroDialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.skapps.YksStudyApp.databinding.FragmentAddPomodoroBinding
import com.skapps.YksStudyApp.view.Pomodoro.PomodoroViewModel


class AddPomodoroFragment : BottomSheetDialogFragment() {

    private var _binding:FragmentAddPomodoroBinding?=null
    private val binding get() = _binding
    private lateinit var viewModel: AddPomodoroViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewModel= ViewModelProvider(this).get(AddPomodoroViewModel::class.java)
        _binding=FragmentAddPomodoroBinding.inflate(inflater,container,false)
        return binding?.root
    }

}