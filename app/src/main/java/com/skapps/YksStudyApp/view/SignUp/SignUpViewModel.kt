package com.skapps.YksStudyApp.view.SignUp

import android.app.Application
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import com.skapps.YksStudyApp.Base.BaseViewModel
import com.skapps.YksStudyApp.Model.UserProfile
import com.skapps.YksStudyApp.util.infoToast
import com.skapps.YksStudyApp.util.warningToast
import kotlinx.coroutines.launch
import java.util.regex.Pattern


class SignUpViewModel(application: Application) : BaseViewModel(application) {
    private  var auth: FirebaseAuth
    var mailList=MutableLiveData<ArrayList<String>>()
    val db= Firebase.firestore
    init {
        auth=Firebase.auth
    }
    fun saveUserDatabase(){
        val user=UserProfile("null","null","null","null","null")
        user.id=auth.currentUser!!.uid
        db.collection("users").document(auth.currentUser!!.uid).set(user).addOnSuccessListener { documentReference ->
            Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference}")
        }.addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Error adding document", e)
        }
    }
/**
    fun profileUpdates(){
        val user=Firebase.auth.currentUser
        val profileUpdates= userProfileChangeRequest {
            displayName=binding!!.registerName1.editText?.text.toString()
        }
        user!!.updateProfile(profileUpdates).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(activity,"Create Profile", Toast.LENGTH_SHORT).show()
            }
        }
    } **/

    fun userRegister(email:String,password:String,context: Context){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                 //   profileUpdates()
                    saveUserDatabase()
                    Log.d(TAG, "createUserWithEmail:success")
                } else {
                    Toast.makeText(context,email,Toast.LENGTH_SHORT).show()
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    context.warningToast(task.exception?.message.toString())
                }
            }
    }
    fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null) {
            false
        } else {
            !Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }
    fun isValidPassword(s: String?): Boolean {
        val PASSWORD_PATTERN: Pattern = Pattern.compile(
            "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}"
        )
        return TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s.toString()).matches()
    }
    /**
    fun getEmailList(context: Context){
            db.collection("users").get().addOnSuccessListener { result ->
                for (document in result) {
                    mailList.value?.add(document.get("email").toString())
                    Log.e(TAG, " documents: ${document.get("email") }")
                }
            }.addOnFailureListener { exception ->
                context.warningToast(exception.toString())
                Log.e(TAG, "Error getting documents: ", exception)
            }
    }
    fun emailControl(listMail:ArrayList<String>,em:String,context: Context){
                for (i in listMail) {
                    Log.e(TAG, "Mailler => ${i}")
                    if (i==em){
                        Log.e(TAG, "Duplicate => ${em}")
                        context.warningToast("Bu emaile kayıtlı zaten bir hesap var.")
                    }
                }
                context.warningToast("çıktı")
            } */

    }

