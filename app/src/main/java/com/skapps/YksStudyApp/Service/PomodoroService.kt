package com.skapps.YksStudyApp.Service

import android.app.*
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.skapps.YksStudyApp.Model.DateClass
import com.skapps.YksStudyApp.Model.LogPomodoro
import com.skapps.YksStudyApp.R
import com.skapps.YksStudyApp.Statistics.Pomodoro.RatingPomodoro
import com.skapps.YksStudyApp.database.LocalDatabase
import com.skapps.YksStudyApp.database.LogPomDatabase
import com.skapps.YksStudyApp.database.PomodoroDatabase
import com.skapps.YksStudyApp.view.Pomodoro.PomodoroActivity
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.math.log

class PomodoroService : Service() {
    companion object {
        // Channel ID for notifications
        const val CHANNEL_ID = "Stopwatch_Notifications"

        // Service Actions
        const val START = "START"
        const val PAUSE = "PAUSE"
        const val RESET = "RESET"
        const val DURUM_AL = "GET_STATUS"
        const val ONE_CIKAR = "MOVE_TO_FOREGROUND"
        const val ARKA_PALANA_GEC = "MOVE_TO_BACKGROUND"

        // Intent Extras
        const val IZLEME_DURDUR = "STOPWATCH_ACTION"
        const val GECEN_SURE = "TIME_ELAPSED"
        const val KRONOMETRE_CALISIYOR = "IS_STOPWATCH_RUNNING"

        // Intent Actions
        const val STOPWATCH_TICK = "STOPWATCH_TICK"
        const val STOPWATCH_STATUS = "STOPWATCH_STATUS"
    }

    private var timeElapsed: Int = 25*60*1000
    private var isStopWatchRunning = false
    private var isStartControl=true
    var cTimer: CountDownTimer? = null
    private var updateTimer = Timer()
    private var timePause:Long=timeElapsed.toLong()
    private var date:DateClass?=null
    val calender = Calendar.getInstance()
    @OptIn(DelicateCoroutinesApi::class)
    private val rating= RatingPomodoro()
    private var logPomodoro= LogPomodoro(activity = "Di??er",time = 25,calender.get(Calendar.YEAR).toString(),
        SimpleDateFormat("MM",Locale.getDefault()).format(Date()).toString(),calender.get(Calendar.DAY_OF_MONTH).toString(),calender.get(Calendar.WEEK_OF_YEAR).toString(),SimpleDateFormat("HH",Locale.getDefault()).format(Date()).toString(),SimpleDateFormat("mm",Locale.getDefault()).format(Date()).toString())

    // Getting access to the NotificationManager
    private lateinit var notificationManager: NotificationManager

    /**
     * Sistem, yaln??zca ilk istemci ba??land??????nda IBinder'?? almak i??in onBind() y??ntemini ??a????r??r.
     * Sistem daha sonra ayn?? IBinder'?? herhangi bir ek istemciye iletir.
     * onBind()'i tekrar ??a????rmadan.
     * */
    override fun onBind(p0: Intent?): IBinder? {

        Log.d("Stopwatch", "Stopwatch onBind")
        return null
    }

    /**
     * onStartCommand(), bir istemci hizmeti her ba??latt??????nda ??a??r??l??r
     * startService kullanarak (Niyet amac??)
     * Bu hizmetin hangi eylem i??in ??a??r??ld??????n?? kontrol edece??iz ve ard??ndan
     * buna g??re hareket edin. Eylem, ba??latmak i??in kullan??lan niyetten ????kar??l??r.
     * bu servis.
     * */

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createChannel()
        getNotificationManager()

        val action = intent?.getStringExtra(IZLEME_DURDUR)!!
        Log.d("Stopwatch", "onStartCommand Action: $action")
        when (action) {
            START ->{
                if (isStartControl){
                    val custompomodoro =intent.getIntExtra("custompomodoro",31)
                    logPomodoro.activity= intent.getStringExtra("logactivity")
                    logPomodoro.time=custompomodoro
                    Log.e("Stopwatch", "test: ${custompomodoro}")
                    Log.e("Stopwatch", "test: ${logPomodoro.activity}")
                    timePause=custompomodoro*1000*60L
                    timeElapsed=timePause.toInt()
                    startStopwatch()
                    isStartControl=false
                }else{
                    startStopwatch()
                }
            }
            PAUSE -> pauseStopwatch()
            RESET -> resetStopwatch()
            DURUM_AL -> sendStatus()
            ONE_CIKAR -> moveToForeground()
            ARKA_PALANA_GEC -> moveToBackground()
        }

        return START_STICKY
    }

    /*
     * Bu i??lev, uygulama art??k kullan??c?? taraf??ndan g??r??lmedi??inde tetiklenir
     * Kronometrenin ??al??????p ??al????mad??????n?? kontrol eder, ??al??????yorsa ??n plan servisini ba??lat??r.
     * bildirim ile.
     * Bildirimi her saniye g??ncellemek i??in ba??ka bir zamanlay??c?? ??al????t??r??yoruz.
     * */
    private fun moveToForeground() {

        if (isStopWatchRunning) {
            startForeground(1, buildNotification())

            updateTimer = Timer()

            updateTimer.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    updateNotification()

                }
            }, 0, 1000)
        }
    }

    /*
      * Bu i??lev, uygulama kullan??c??ya tekrar g??r??n??r oldu??unda tetiklenir
      * Bildirimi her saniye g??ncelleyen zamanlay??c??y?? iptal eder
      * Ayr??ca ??n plan hizmetini durdurur ve bildirimi kald??r??r
      * */
    private fun moveToBackground() {
        updateTimer.cancel()
        stopForeground(true)
    }

    /*
     * Bu i??lev kronometreyi ba??lat??r
     * Kronometrenin ??al????ma durumunu true olarak ayarlar
     * Bir Zamanlay??c?? ba??lat??yoruz ve ge??en s??reyi her saniye 1 art??r??yoruz ve de??eri yay??nl??yoruz
     * STOPWATCH_TICK eylemiyle.
     * Ge??en s??reye eri??mek i??in bu yay??n?? MainActivity'de alaca????z.
     * */
    @OptIn(DelicateCoroutinesApi::class)
    private fun startStopwatch() {
        isStopWatchRunning = true
        sendStatus()
       // stopwatchTimer = Timer()
      /*
        stopwatchTimer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val stopwatchIntent = Intent()
                stopwatchIntent.action = STOPWATCH_TICK

                timeElapsed--
                stopwatchIntent.putExtra(GECEN_SURE, timeElapsed)
                sendBroadcast(stopwatchIntent)
            }
        }, 0, 1000)
        */
        //timePause
         cTimer = object : CountDownTimer(timePause, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val stopwatchIntent = Intent()
                stopwatchIntent.action = STOPWATCH_TICK
                timePause=millisUntilFinished
                timeElapsed =millisUntilFinished.toInt()
                stopwatchIntent.putExtra(GECEN_SURE, timeElapsed)
                sendBroadcast(stopwatchIntent)
            }
             override fun onFinish() {
                 pauseStopwatch()
                 GlobalScope.launch{
               //      logPomodoro.date= DateClass(calender.get(Calendar.YEAR),calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH),calender.get(Calendar.WEEK_OF_YEAR),calender.get(Calendar.HOUR_OF_DAY),calender.get(Calendar.MINUTE))
                     saveRoom(logPomodoro)
                     Log.e("PomodoroService", "Savetest succes: ${logPomodoro.activity}")
                     rating.updateTimes(logPomodoro.time!!)
                 }
             }
         }
        (cTimer as CountDownTimer).start()
    }

    /*
     * Bu i??lev kronometreyi duraklat??r
     * Kronometrenin mevcut durumunun bir g??ncellemesini g??nderir
     * */
    private suspend fun saveRoom(logPomodoro: LogPomodoro){
        Log.e("save Room",logPomodoro.activity.toString())
        val dao =LogPomDatabase(getApplication()).logPomDao()
        dao.insert(logPomodoro)
    }
    private fun pauseStopwatch() {
        // stopwatchTimer.cancel()
        cTimer?.cancel()
        isStopWatchRunning = false
        sendStatus()
    }

    /*
     * Bu i??lev kronometreyi s??f??rlar
     * Kronometrenin mevcut durumunun bir g??ncellemesini g??nderir
     * */
    private fun resetStopwatch() {
        pauseStopwatch()
        timeElapsed = 0
        sendStatus()
        isStartControl=true
    }

    /*
      * Bu i??lev, kronometrenin durumunu yay??nlamaktan sorumludur.
      * Kronometre ??al??????yorsa ve ge??en s??reyi de yay??nlar
      * */
    private fun sendStatus() {
        val statusIntent = Intent()
        statusIntent.action = STOPWATCH_STATUS
        statusIntent.putExtra(KRONOMETRE_CALISIYOR, isStopWatchRunning)
        statusIntent.putExtra(GECEN_SURE, timeElapsed)
        sendBroadcast(statusIntent)
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "Stopwatch",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.setSound(null, null)
            notificationChannel.setShowBadge(true)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun getNotificationManager() {
        notificationManager = ContextCompat.getSystemService(
            this,
            NotificationManager::class.java
        ) as NotificationManager
    }

    /** * Bu i??lev, Kronometrenin mevcut * durumunu ve Ge??en s??reyi i??eren bir Bildirim olu??turmak ve geri g??ndermekten sorumludur * */
    private fun buildNotification(): Notification {
        val title = if (isStopWatchRunning) {
            "Pomodoro"
        } else {
            "Pomodoro Tamamland??!"
        }
        val seconds = (timeElapsed/ 1000) % 60
        val minutes = (timeElapsed / (1000 * 60) % 60)
        //  binding!!.choronometre.text = "$minutes : $seconds"


        val intent = Intent(this, PomodoroActivity::class.java)
       //val pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        var pIntent : PendingIntent? = null
        pIntent  = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(this, 0,intent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(this, 0,intent, PendingIntent.FLAG_ONE_SHOT)
        }
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle( "${"%02d".format(minutes)}:${
                "%02d".format(
                    seconds
                )
            }")
            .setOngoing(true)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setColorized(true)
            .setSubText(title)
            .setColor(Color.parseColor("#2196F3"))
            .setSmallIcon(R.drawable.ic_clock)
            .setOnlyAlertOnce(true)
            .setContentIntent(pIntent)
            .setAutoCancel(true)
            .build()
    }


    /** Bu i??lev, mevcut bildirimi yeni bildirimle g??ncellemek i??in bildirim Y??neticisini kullan??r **/
    private fun updateNotification() {
        notificationManager.notify(
            1,
            buildNotification()
        )
    }
}