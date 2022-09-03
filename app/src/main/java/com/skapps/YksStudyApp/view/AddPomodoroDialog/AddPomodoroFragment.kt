package com.skapps.YksStudyApp.view.AddPomodoroDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.skapps.YksStudyApp.Adapter.HistoryPomodoroAdapter
import com.skapps.YksStudyApp.Model.HistoryPomodoro
import com.skapps.YksStudyApp.R
import com.skapps.YksStudyApp.databinding.FragmentAddPomodoroBinding
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams


class AddPomodoroFragment : BottomSheetDialogFragment() {

    private var _binding:FragmentAddPomodoroBinding?=null
    private val binding get() = _binding
    private lateinit var viewModel: AddPomodoroViewModel
    private  var time:Int=0
    private val pomodoroAdapter=HistoryPomodoroAdapter(arrayListOf())
    private var addhistory=HistoryPomodoro("Diğer",25)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewModel= ViewModelProvider(this).get(AddPomodoroViewModel::class.java)
        viewModel.refreshData()
        _binding=FragmentAddPomodoroBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,R.style.AppBottomSheetDialogTheme)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.myRecyclerView?.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding?.myRecyclerView?.adapter=pomodoroAdapter

        observeLivedata()

        binding!!.seekBar.setOnSeekChangeListener(object : OnSeekChangeListener {
            override fun onSeeking(seekParams: SeekParams) {
                time=seekParams.progress
            }
            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar) {}
            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar) {
            }
        })
        binding!!.radioGroup.setOnCheckedChangeListener { group, checkedId ->
        addhistory.activity=viewModel.onRadioButtonClicked(checkedId,requireContext())
        }
        binding!!.button.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("time",time)
            addhistory.time=time.toLong()
           viewModel.store(addhistory)
            findNavController().navigate(R.id.action_addPomodoroFragment_to_pomodoroFragment,bundle)
        }


        super.onViewCreated(view, savedInstanceState)
    }

    private fun observeLivedata(){
        viewModel.pomodoro.observe(viewLifecycleOwner){
            pomodoroAdapter.updatePomodoro(it)
        }
    }







}