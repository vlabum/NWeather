package ru.vlabum.android.apps.nweather.MediaPlayer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import ru.vlabum.android.apps.nweather.App
import ru.vlabum.android.apps.nweather.R

class AudioPlayerFragment : Fragment() {

    var bPlay: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_audio_player, container, false)
    }

    override fun onResume() {
        super.onResume()
        bPlay = view?.findViewById(R.id.fragment_audio_player_play) as Button
        bPlay?.setOnClickListener {
            val aplayer = App.getInstance().audioPlayer
            if (aplayer.isPlaying()) {
                bPlay?.text = "Play"
                aplayer.pause()
            } else {
                bPlay?.text = "Pause"
                aplayer.start()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
