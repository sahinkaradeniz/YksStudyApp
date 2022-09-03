package com.skapps.YksStudyApp.Base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application):AndroidViewModel(application),CoroutineScope{
    private val job= Job() //iş
    override val coroutineContext: CoroutineContext
         get() = job+Dispatchers.Main // arka planda işini yap main therad dön

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}