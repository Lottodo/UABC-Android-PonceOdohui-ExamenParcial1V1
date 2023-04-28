package uabc.iec.examen1

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.*


class scoresActivity : AppCompatActivity() {

    lateinit var scores: ArrayList<Score>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)

        var string: String = ""

        /*try {
            val inputStream = assets.open("names.txt")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            string = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }*/

        string = readFromFile(this.applicationContext)


        // ...
        // Lookup the recyclerview in activity layout
        val rvScores = findViewById<View>(R.id.rvScores) as RecyclerView
        // Initialize contacts
        Log.d("DEBUGGEANDO","string:"+string)
        scores = Score.createScoresList(string)
        // Create adapter passing in the sample user data
        val adapter = ScoresAdapter(scores)
        // Attach the adapter to the recyclerview to populate items
        rvScores.adapter = adapter
        // Set layout manager to position the items
        rvScores.layoutManager = LinearLayoutManager(this)
        // That's all!
    }


    fun irMenu(view: View) {
        playCartaCSFX()
        finish()
    }

    private fun readFromFile(context: Context): String {
        var ret = ""
        try {
            val inputStream: InputStream = context.openFileInput("names.txt")
            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                var receiveString: String? = ""
                val stringBuilder = StringBuilder()
                while (bufferedReader.readLine().also { receiveString = it } != null) {
                    stringBuilder.append("\n").append(receiveString)
                }
                inputStream.close()
                ret = stringBuilder.toString()
            }
        } catch (e: FileNotFoundException) {
            Log.e("login activity", "File not found: $e")
        } catch (e: IOException) {
            Log.e("login activity", "Can not read file: $e")
        }
        return ret
    }

    fun playCartaCSFX(){
        val mp: MediaPlayer = MediaPlayer.create(this, R.raw.cartac)
        mp.start()
        mp.setOnCompletionListener {
            mp.release()
        }
    }
}