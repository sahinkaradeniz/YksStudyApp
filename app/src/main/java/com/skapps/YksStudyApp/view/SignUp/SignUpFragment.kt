package com.skapps.YksStudyApp.view.SignUp

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.skapps.YksStudyApp.R
import com.skapps.YksStudyApp.databinding.FragmentSignUpBinding
import com.skapps.YksStudyApp.util.infoToast
import com.skapps.YksStudyApp.util.toast
import com.skapps.YksStudyApp.util.warningToast
import com.skapps.YksStudyApp.view.NicknameAdd.AddNickNameFragment
import es.dmoral.toasty.Toasty
import es.dmoral.toasty.Toasty.info

class SignUpFragment : Fragment() {
    companion object {
        fun newInstance() = SignUpFragment()
    }
    private lateinit var viewModel: SignUpViewModel
    private lateinit var _binding:FragmentSignUpBinding
    private val binding get() = _binding
    private var emailList=ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        observeLiveData()
        binding.sinupButton.setOnClickListener {
            val password = binding.signpasswordText.editText?.text.toString()
            val email = binding.signemailtext.editText?.text.toString()
            val name =binding.signupName.editText?.text.toString()
            if (name.isEmpty()){
               requireContext().warningToast("Lütfen isminizi yazınız")
            }else if (password.length<7){
               requireContext().warningToast("Şifre uzunluğu 6 karakterden kısa olamaz")
            }else if(viewModel.isValidEmail(email)){
               requireContext().warningToast("Email biçimi hatalı")
            }else{
              //  viewModel.emailControl(emailList,email,requireContext())
              //  requireContext().warningToast("Bu email başka bir hesap tarafından kullanılmakta")
              viewModel.userRegister(email,password,requireContext())
                val dialog =AddNickNameFragment()
                dialog.setStyle(DialogFragment.STYLE_NORMAL,R.style.custom_alert_dialog)
                dialog.show(requireActivity().supportFragmentManager,"addNickNameFragment")
            }
        }
    }

   private fun observeLiveData(){

   }
}