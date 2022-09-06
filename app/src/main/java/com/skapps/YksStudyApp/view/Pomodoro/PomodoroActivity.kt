package com.skapps.YksStudyApp.view.Pomodoro
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.skapps.YksStudyApp.R
import com.skapps.YksStudyApp.Service.PomodoroService
import com.skapps.YksStudyApp.databinding.ActivityMainBinding
import com.skapps.YksStudyApp.databinding.ActivityPomodoroBinding

class PomodoroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPomodoroBinding

    private var isStopwatchRunning = false

    private lateinit var statusReceiver: BroadcastReceiver
    private lateinit var timeReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPomodoroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toggleButton.setOnClickListener {
            if (isStopwatchRunning) pauseStopwatch() else startStopwatch()
        }

        binding.resetImageView.setOnClickListener {
            resetStopwatch()
        }
    }

    override fun onStart() {
        super.onStart()
        // Moving the service to background when the app is visible
        moveToBackground()
    }

    override fun onResume() {
        super.onResume()
        getStopwatchStatus()
        // Receiving stopwatch status from service
        val statusFilter = IntentFilter()
        statusFilter.addAction(PomodoroService.STOPWATCH_STATUS)
        statusReceiver = object : BroadcastReceiver() {
            @SuppressLint("SetTextI18n")
            override fun onReceive(p0: Context?, p1: Intent?) {
                val isRunning = p1?.getBooleanExtra(PomodoroService.KRONOMETRE_CALISIYOR, false)!!
                isStopwatchRunning = isRunning
                val timeElapsed = p1.getIntExtra(PomodoroService.GECEN_SURE, 0)

                updateLayout(isStopwatchRunning)
                updateStopwatchValue(timeElapsed)
            }
        }
        registerReceiver(statusReceiver, statusFilter)

        // Receiving time values from service
        val timeFilter = IntentFilter()
        timeFilter.addAction(PomodoroService.STOPWATCH_TICK)
        timeReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val timeElapsed = p1?.getIntExtra(PomodoroService.GECEN_SURE, 0)!!
                updateStopwatchValue(timeElapsed)
            }
        }
        registerReceiver(timeReceiver, timeFilter)
    }

    override fun onPause() {
        super.onPause()

        unregisterReceiver(statusReceiver)
        unregisterReceiver(timeReceiver)

        // Moving the service to foreground when the app is in background / not visible
        moveToForeground()
    }



    @SuppressLint("SetTextI18n")
    private fun updateStopwatchValue(timeElapsed: Int) {
        val hours: Int = (timeElapsed / 60) / 60
        val minutes: Int = timeElapsed / 60
        val seconds: Int = timeElapsed % 60
        binding.stopwatchValueTextView.text =
            "${"%02d".format(hours)}:${"%02d".format(minutes)}:${"%02d".format(seconds)}"
    }

    private fun updateLayout(isStopwatchRunning: Boolean) {
        if (isStopwatchRunning) {
            binding.toggleButton.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_pause)
          binding.resetImageView.visibility = View.INVISIBLE
        } else {
            binding.toggleButton.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_play)
            binding.resetImageView.visibility = View.VISIBLE
        }
    }

    private fun getStopwatchStatus() {
        val pomodoroService = Intent(this, PomodoroService::class.java)
        pomodoroService.putExtra(PomodoroService.IZLEME_DURDUR , PomodoroService.DURUM_AL)
        startService(pomodoroService)
    }

    private fun startStopwatch() {
        val pomodoroService = Intent(this, PomodoroService::class.java)
        pomodoroService.putExtra(PomodoroService.IZLEME_DURDUR , PomodoroService.START)
        startService(pomodoroService)
    }

    private fun pauseStopwatch() {
        val pomodoroService = Intent(this, PomodoroService::class.java)
        pomodoroService.putExtra(PomodoroService.IZLEME_DURDUR , PomodoroService.PAUSE)
        startService(pomodoroService)
    }

    private fun resetStopwatch() {
        val pomodoroService = Intent(this, PomodoroService::class.java)
        pomodoroService.putExtra(PomodoroService.IZLEME_DURDUR , PomodoroService.RESET)
        startService(pomodoroService)
    }

    private fun moveToForeground() {
        val pomodoroService = Intent(this, PomodoroService::class.java)
        pomodoroService.putExtra(
            PomodoroService.IZLEME_DURDUR ,
            PomodoroService.ONE_CIKAR
        )
        startService(pomodoroService)
    }

    private fun moveToBackground() {
        val pomodoroService = Intent(this, PomodoroService::class.java)
        pomodoroService.putExtra(
            PomodoroService.IZLEME_DURDUR ,
            PomodoroService.ARKA_PALANA_GEC
        )
        startService(pomodoroService)
    }

}