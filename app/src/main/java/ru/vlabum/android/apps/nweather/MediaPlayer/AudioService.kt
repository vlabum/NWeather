package ru.vlabum.android.apps.nweather.MediaPlayer

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.support.annotation.RequiresApi
import ru.vlabum.android.apps.nweather.App
import ru.vlabum.android.apps.nweather.MainActivity

class AudioService : Service() {

    lateinit var audioPlayer: AudioPlayer

    override fun onCreate() {
        super.onCreate()
        audioPlayer = AudioPlayer()
        audioPlayer.prepareAudio(App.getInstance(), "music.mp3")
        App.getInstance().audioPlayer = audioPlayer
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            showNotification("NWeather audio Player")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return Service.START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        audioPlayer.release()
        App.getInstance().audioPlayer = null
    }

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun showNotification(text: String) {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val notification = Notification.Builder(this, "NWeatherPlay")
            .setContentTitle("Hello")
            .setContentText(text)
            .setContentIntent(pendingIntent)
            .setCategory(Notification.CATEGORY_TRANSPORT)
            .setWhen(System.currentTimeMillis())
            .build()
        startForeground(12345, notification)
    }

}