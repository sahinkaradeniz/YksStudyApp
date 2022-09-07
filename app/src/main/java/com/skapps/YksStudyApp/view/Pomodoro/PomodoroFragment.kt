package com.skapps.YksStudyApp.view.Pomodoro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.skapps.YksStudyApp.R
import com.skapps.YksStudyApp.databinding.FragmentPomodoroBinding
import com.skapps.YksStudyApp.database.LocalDatabase


class PomodoroFragment : Fragment(){
    private var _binding:FragmentPomodoroBinding?=null
    private val binding  get() = _binding
    private lateinit var viewModel:PomodoroViewModel
    private var pauseTime:Long =0L
    private  var localDatabase= LocalDatabase()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this).get(PomodoroViewModel::class.java)
        val p=  localDatabase.getSharedPreference(requireContext(),"pause",0)
        viewModel.onCreatePause(requireContext(),p.toInt())
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentPomodoroBinding.inflate(inflater,container,false)
        observeLiveData()
       /** binding!!.startChoronometre.setOnClickListener {
            viewModel.cancelTimer()
            if(pauseTime<3L){
                viewModel.startTimer(25*1000*60,requireContext())
            }else{
                viewModel.startTimer(pauseTime,requireContext())
            }
        } */
        arguments?.getInt("time").let {
            it?.let {
                    it1 -> viewModel.onCreatePause(requireContext(),it*60*1000) }
        }
        binding!!.backPomodoro.setOnClickListener {

            findNavController().navigate(PomodoroFragmentDirections.actionPomodoroFragmentToHomeFragment())
        }
       /* binding!!.pauseButton.setOnClickListener {
            viewModel.cancelTimer()
        }*/
        binding!!.addPomodoro.setOnClickListener {
            viewModel.cancelTimer()
           findNavController().navigate(R.id.addPomodoroFragment)
        }
        return binding?.root
    }


    override fun onDestroy() {
        super.onDestroy()
  //      localDatabase.setSharedPreference(requireContext().applicationContext,"pause", time = pauseTime)
    }
    private fun observeLiveData(){
        viewModel.time.observe(viewLifecycleOwner) {
            it.let {
                pauseTime=it
                val seconds = (it / 1000).toInt() % 60
                val minutes = (it / (1000 * 60) % 60).toInt()
              //  binding!!.choronometre.text = "$minutes : $seconds"
            }
        }
    }




}