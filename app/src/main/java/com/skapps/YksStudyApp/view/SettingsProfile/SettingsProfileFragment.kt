package com.skapps.YksStudyApp.view.SettingsProfile

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.skapps.YksStudyApp.R
import com.skapps.YksStudyApp.databinding.FragmentAddNickNameBinding
import com.skapps.YksStudyApp.databinding.FragmentSettingsProfileBinding

class SettingsProfileFragment :Fragment() {

    private lateinit var  _binding: FragmentSettingsProfileBinding
    private val binding get() = _binding
    private lateinit var viewModel: SettingsProfileViewModel
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var selectedBitmap: Bitmap?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding =FragmentSettingsProfileBinding.inflate(inflater, container, false)
        binding.settingsImageButton.setOnClickListener {

        }
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
    private fun registerLauncher(){
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ result ->
            if(result){
                val MediaSelect = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(MediaSelect)
                Toast.makeText(context, "Media gönderildi", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "Permission Neeeded!", Toast.LENGTH_SHORT).show()
                println("faker")
            }
        }
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK){
                val intentFromResult = result.data
                if (intentFromResult != null){
                    val imageData = intentFromResult.data
                    if(imageData != null) {
                        try {
                            if(Build.VERSION.SDK_INT >= 28){
                                var source = ImageDecoder.createSource(requireActivity().contentResolver , imageData)
                                selectedBitmap = ImageDecoder.decodeBitmap(source)
                                Toast.makeText(context, "decode1", Toast.LENGTH_SHORT).show()
                              //  binding!!.selectImage.setImageBitmap(selectedBitmap)
                            }else{
                                selectedBitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,imageData)
                             //   binding!!.selectImage.setImageBitmap(selectedBitmap)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }


    fun selectImage(view : View){
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            //rationale
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

            }else{
                // request permission
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }else{
            val intentToGallery=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGallery)
        }
    }

}