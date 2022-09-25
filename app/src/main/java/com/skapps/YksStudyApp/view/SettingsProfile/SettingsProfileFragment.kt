package com.skapps.YksStudyApp.view.SettingsProfile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.skapps.YksStudyApp.R
import com.skapps.YksStudyApp.databinding.FragmentAddNickNameBinding
import com.skapps.YksStudyApp.databinding.FragmentSettingsProfileBinding

class SettingsProfileFragment :Fragment() {

    private lateinit var  _binding: FragmentSettingsProfileBinding
    private val binding get() = _binding
    private lateinit var viewModel: SettingsProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding =FragmentSettingsProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    /*
    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val xbinding = FragmentSettingsProfileBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(xbinding.root)
        val dialog = builder.create()
        dialog?.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }*/

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsProfileViewModel::class.java)

    }

}