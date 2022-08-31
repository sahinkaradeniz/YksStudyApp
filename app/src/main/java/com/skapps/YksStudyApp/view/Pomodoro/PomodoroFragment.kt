package com.skapps.YksStudyApp.view.Pomodoro
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.skapps.YksStudyApp.databinding.FragmentPomodoroBinding
import com.skapps.YksStudyApp.util.CustomSharedPreferences
import kotlinx.coroutines.launch


class PomodoroFragment : Fragment() {
    private var _binding:FragmentPomodoroBinding?=null
    private val binding  get() = _binding
    private lateinit var viewModel: PomodoroViewModel
    private var customSharedPreferences= CustomSharedPreferences()
    private var destroyTime:Int = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding= FragmentPomodoroBinding.inflate(inflater,container,false)
        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(this).get(PomodoroViewModel::class.java)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted{
            viewModel.viewModelScope.launch {
                viewModel.data.collect{
               //     binding!!.choronometre.text=it.toString()
                    destroyTime=it
                }
            }
        }

        binding!!.startChoronometre.setOnClickListener {

        //    viewModel.setSharedPreference(requireContext(),"test","01.01")
        }
        binding!!.appCompatImageButton3.setOnClickListener {
           val time= viewModel.getSharedPreference(requireContext(),"test","0")
            binding!!.choronometre.text=time

        }


    }


    fun yasin():String{
        return "yasin"
    }

}