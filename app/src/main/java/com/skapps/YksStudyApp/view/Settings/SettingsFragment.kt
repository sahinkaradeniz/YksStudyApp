package com.skapps.YksStudyApp.view.Settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.skapps.YksStudyApp.R
import com.skapps.YksStudyApp.databinding.FragmentSettingsBinding
import com.skapps.YksStudyApp.databinding.FragmentSettingsProfileBinding

class SettingsFragment : Fragment() {

    private lateinit var  _binding:FragmentSettingsBinding
    private val binding get() = _binding
    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding=FragmentSettingsBinding.inflate(inflater,container,false)
        binding.let {
            binding.editProfile.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_settingsProfileFragment2)
            }
        }
        binding.let {
            binding.backsettingsToHome.setOnClickListener {

            }
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

    }

}