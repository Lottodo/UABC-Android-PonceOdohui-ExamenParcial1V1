package uabc.iec.examen1

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class howToPlayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_to_play)
    }



    fun irMenu(view: View) {
        finish()
        playCartaCSFX()
    }

    fun playCartaCSFX(){
        val mp: MediaPlayer = MediaPlayer.create(this, R.raw.cartac)
        mp.start()
        mp.setOnCompletionListener {
            mp.release()
        }
    }
}