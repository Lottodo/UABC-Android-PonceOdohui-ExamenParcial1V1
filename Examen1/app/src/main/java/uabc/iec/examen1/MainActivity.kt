package uabc.iec.examen1

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import java.io.*


class MainActivity : AppCompatActivity() {

    lateinit var music: ImageButton
    private var isPlaying: Boolean = false

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startService(Intent(this,BackgroundMusicService::class.java))

        music = findViewById(R.id.musicButton)
        if(isMyServiceRunning(BackgroundMusicService::class.java)) {
            music.setImageResource(R.drawable.music)
            isPlaying = true
        }
        else {
            music.setImageResource(R.drawable.musicoff)
            isPlaying = false
        }
        music.setOnClickListener{
            playCartaASFX()
            if(isPlaying){
              stopService(Intent(this,BackgroundMusicService::class.java))
              isPlaying = false
              music.setImageResource(R.drawable.musicoff)
          }
          else{
              startService(Intent(this,BackgroundMusicService::class.java))
              isPlaying = true
              music.setImageResource(R.drawable.music)
          }
        }
    }

    override fun onResume(){
        super.onResume()
        if(isMyServiceRunning(BackgroundMusicService::class.java)) {
            music.setImageResource(R.drawable.music)
            isPlaying = true
        }
        else {
            music.setImageResource(R.drawable.musicoff)
            isPlaying = false
        }
    }

    fun irGame(view: View){
        playCartaBSFX()
        val intentExplicito = Intent(this, gameActivity::class.java)
        startActivity(intentExplicito)
    }

    fun irHowToPlay(view: View){
        playCartaBSFX()
        val intentExplicito = Intent(this, howToPlayActivity::class.java)
        startActivity(intentExplicito)
    }

    fun irScores(view: View){
        playCartaBSFX()
        val intentExplicito = Intent(this, scoresActivity::class.java)
        startActivity(intentExplicito)
        /*try {
            val inputStream: InputStream = this.applicationContext.openFileInput("names.txt")
            if (inputStream != null) {

            }
        } catch (e: FileNotFoundException) {
            Log.e("login activity", "File not found: $e")
        } catch (e: IOException) {
            Log.e("login activity", "Can not read file: $e")
        }
        if(file.exists())
        //Do something
        else
// Do something else.*/

    }

    fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    fun playCartaASFX(){
        val mp: MediaPlayer = MediaPlayer.create(this, R.raw.cartaa)
        mp.start()
        mp.setOnCompletionListener {
            mp.release()
        }
    }

    fun playCartaBSFX(){
        val mp: MediaPlayer = MediaPlayer.create(this, R.raw.cartab)
        mp.start()
        mp.setOnCompletionListener {
            mp.release()
        }
    }

    fun playCartaCSFX(){
        val mp: MediaPlayer = MediaPlayer.create(this, R.raw.cartac)
        mp.start()
        mp.setOnCompletionListener {
            mp.release()
        }
    }

}