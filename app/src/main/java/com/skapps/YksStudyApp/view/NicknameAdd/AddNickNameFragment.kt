package com.skapps.YksStudyApp.view.NicknameAdd

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.skapps.YksStudyApp.R

class AddNickNameFragment:DialogFragment() {

    companion object {
        fun newInstance() = AddNickNameFragment()
    }

    private lateinit var viewModel: AddNickNameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_nick_name, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddNickNameViewModel::class.java)
        // TODO: Use the ViewModel
    }

}