package com.skapps.YksStudyApp.view.SignUp

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.skapps.YksStudyApp.Model.UserProfile

class SignUpViewModel : ViewModel() {
    private lateinit var auth: FirebaseAuth
    val db= Firebase.firestore

    fun saveUserDatabase(){
        val user=UserProfile("null","null","null","null")
        user.id=auth.currentUser!!.uid
        db.collection("users").document(auth.currentUser!!.uid).set(user).addOnSuccessListener { documentReference ->
            Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference}")
        }.addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Error adding document", e)
        }
    }
}