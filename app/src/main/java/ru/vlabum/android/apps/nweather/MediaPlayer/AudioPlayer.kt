package ru.vlabum.android.apps.nweather.MediaPlayer

import android.content.Context
import android.content.Context.AUDIO_SERVICE
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log

class AudioPlayer {

    private val LOG_CLASS_NAME = this.javaClass.name
    lateinit var audioManager: AudioManager
    lateinit var mediaPlayer: MediaPlayer

    fun prepareAudio(context: Context, fileName: String) {
        try {
            val assets = context.assets.openFd(fileName)
            audioManager = context.getSystemService(AUDIO_SERVICE) as AudioManager
            mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(assets.fileDescriptor, assets.startOffset, assets.length)
            mediaPlayer.isLooping = true
            mediaPlayer.prepare()

        } catch (e: Exception) {
            Log.d(LOG_CLASS_NAME, e.toString())
        }
    }

    fun start() {
        mediaPlayer.start()
    }

    fun isPlaying(): Boolean {
        return mediaPlayer.isPlaying
    }

    fun pause() {
        mediaPlayer.pause()
    }

    fun release() {
        mediaPlayer.stop()
        mediaPlayer.release()
    }

}