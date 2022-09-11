package com.skapps.YksStudyApp.view.Login

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.skapps.YksStudyApp.Base.BaseViewModel
import com.skapps.YksStudyApp.Model.Pomodoro
import com.skapps.YksStudyApp.R

class LoginViewModel(application: Application):BaseViewModel(application) {

    var loginsuccesful= MutableLiveData<Boolean>()
    private var auth:FirebaseAuth = Firebase.auth
    fun loginUser(password:String,email:String,context: Context){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    loginsuccesful.value=true
                    // Sign in success, update UI with the signed-in user's information
                    Log.e(ContentValues.TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.e(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}