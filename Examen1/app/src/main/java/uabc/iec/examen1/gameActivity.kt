package uabc.iec.examen1


import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class gameActivity : AppCompatActivity() {

    lateinit var music: ImageButton
    lateinit var mostrar: Button
    lateinit var tomar: Button
    var isPlaying: Boolean = false


    private lateinit var juego: Juego
    private lateinit var cartasCPU: ArrayList<ImageView>
    private lateinit var puntosCPU: TextView
    private lateinit var cartasPLY: ArrayList<ImageView>
    private lateinit var puntosPLY: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_game)

        mostrar = findViewById(R.id.mostrarButton)
        tomar = findViewById(R.id.tomarButton)

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

        nameDialog()
        }

    /*
    LÓGICA DE UI DEL JUEGO
     */

    fun iniciarJuego(nombreJugador: String, puntosMax: Int) {
        juego = Juego(nombreJugador, puntosMax)

        cartasCPU = ArrayList<ImageView>()
        cartasCPU.add(findViewById(R.id.cartaCPU1))
        cartasCPU.add(findViewById(R.id.cartaCPU2))
        cartasCPU.add(findViewById(R.id.cartaCPU3))
        cartasCPU.add(findViewById(R.id.cartaCPU4))
        cartasCPU.add(findViewById(R.id.cartaCPU5))
        cartasPLY = ArrayList<ImageView>()
        cartasPLY.add(findViewById(R.id.carta1))
        cartasPLY.add(findViewById(R.id.carta2))
        cartasPLY.add(findViewById(R.id.carta3))
        cartasPLY.add(findViewById(R.id.carta4))
        cartasPLY.add(findViewById(R.id.carta5))

        puntosCPU = findViewById(R.id.puntosCPUView)
        puntosPLY = findViewById(R.id.puntosPLYView)

        for (carta in cartasCPU) {
            carta.setVisibility(View.INVISIBLE)
        }
        for (carta in cartasPLY) {
            carta.setVisibility(View.INVISIBLE)
        }

        cartasCPU.get(0).setVisibility(View.VISIBLE)
        cartasCPU.get(1).setVisibility(View.VISIBLE)
        cartasCPU.get(0).setImageResource(getCartaImage(Juego.getCartasJugadorCPU().get(0).getValor(),Juego.getCartasJugadorCPU().get(0).getMano()))
        cartasPLY.get(0).setVisibility(View.VISIBLE)
        cartasPLY.get(1).setVisibility(View.VISIBLE)
        cartasPLY.get(0).setImageResource(getCartaImage(Juego.getCartasJugadorPLY().get(0).getValor(),Juego.getCartasJugadorPLY().get(0).getMano()))

        puntosCPU.text = "  " + juego.jugadorCPU.puntosParciales.toString() + " + ?  "
        puntosPLY.text = juego.jugadorPLY.puntos.toString()

        if(juego.jugadorPLY.puntos == juego.maxPuntos)
            tomar.visibility = View.INVISIBLE
    }

    fun tomarCarta(view: View) {
        playCartaASFX()

        //Revelar carta escondida
        val sizeCartasCPU = Juego.getCartasJugadorCPU().size
        var cartaUltima: Int = Juego.getCartasJugadorPLY().size - 1
        cartasPLY.get(cartaUltima).setImageResource(
            getCartaImage(Juego.getCartasJugadorPLY().get(cartaUltima).getValor(),
                Juego.getCartasJugadorPLY().get(cartaUltima).getMano()))

        juego.darCartaJugadorPLY()
        cartaUltima = Juego.getCartasJugadorPLY().size - 1
        cartasPLY.get(cartaUltima).visibility = View.VISIBLE
        puntosPLY.text = juego.jugadorPLY.puntos.toString()

        tomar.visibility = View.INVISIBLE
        mostrar.visibility = View.INVISIBLE
        val timer = object: CountDownTimer(500, 500) {
            override fun onTick(millisUntilFinished: Long) { }

            override fun onFinish() {
                if(sizeCartasCPU!=Juego.getCartasJugadorCPU().size){
                    playCartaASFX()
                    var cartaUltima: Int = Juego.getCartasJugadorCPU().size - 2
                    cartasCPU.get(cartaUltima).setImageResource(
                        getCartaImage(Juego.getCartasJugadorCPU().get(cartaUltima).getValor(),
                            Juego.getCartasJugadorCPU().get(cartaUltima).getMano()))

                    cartaUltima = Juego.getCartasJugadorPLY().size - 1
                    cartasCPU.get(cartaUltima).visibility = View.VISIBLE
                    puntosCPU.text = "  " + juego.jugadorCPU.puntosParciales.toString() + " + ?  "
                }
                mostrar.visibility = View.VISIBLE
                if(Juego.getCartasJugadorPLY().size<5 && juego.jugadorPLY.puntos<=juego.maxPuntos) {
                    tomar.visibility = View.VISIBLE
                }
            }
        }
        timer.start()
    }

    fun mostrarCartas(view: View) {
        tomar.visibility = View.INVISIBLE
        mostrar.visibility = View.INVISIBLE

        for (i in 0..Juego.getCartasJugadorCPU().size-1) {
            cartasCPU.get(i).setVisibility(View.VISIBLE)
            cartasCPU.get(i).setImageResource(getCartaImage(
                    Juego.getCartasJugadorCPU().get(i).getValor(),
                        Juego.getCartasJugadorCPU().get(i).getMano()))
        }
        puntosCPU.text = "  "+juego.jugadorCPU.puntos.toString()+"  "

        val cartaUltima: Int = Juego.getCartasJugadorPLY().size - 1
        cartasPLY.get(cartaUltima).setImageResource(
            getCartaImage(Juego.getCartasJugadorPLY().get(cartaUltima).getValor(),
                Juego.getCartasJugadorPLY().get(cartaUltima).getMano()))

        val context: Context = this.applicationContext
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val c: Calendar = Calendar.getInstance()
        val date: String = sdf.format(c.getTime())

        val hour24hrs: Int = c.get(Calendar.HOUR_OF_DAY)
        val minutes: Int = c.get(Calendar.MINUTE)
        val time:String = "$hour24hrs:$minutes"
        if(juego.verificarGanador()==1) {
            val c1:ImageView = findViewById(R.id.carta1)
            val c2:ImageView = findViewById(R.id.carta2)
            val c3:ImageView = findViewById(R.id.carta3)
            val c4:ImageView = findViewById(R.id.carta4)
            val c5:ImageView = findViewById(R.id.carta5)
            val cartas: String = c1.tag.toString() +" "+ c2.tag.toString() +" "+ c3.tag.toString() +" "+ c4.tag.toString() +" "+ c5.tag.toString()

            val a:String = juego.jugadorPLY.nombre+","+cartas+","+date+","+time+",1"
            Log.d("DEBUGEANDO",a)
            writeToFile(a,context)
        }
        else if(juego.verificarGanador()==(-1)) {
            val c1:ImageView = findViewById(R.id.cartaCPU1)
            val c2:ImageView = findViewById(R.id.cartaCPU2)
            val c3:ImageView = findViewById(R.id.cartaCPU3)
            val c4:ImageView = findViewById(R.id.cartaCPU4)
            val c5:ImageView = findViewById(R.id.cartaCPU5)
            val cartas: String = c1.tag.toString() +" "+ c2.tag.toString() +" "+ c3.tag.toString() +" "+ c4.tag.toString() +" "+ c5.tag.toString()

            val a:String = "Courier (CPU)"+","+cartas+","+date+","+time+",0"
            Log.d("DEBUGEANDO",a)
            writeToFile(a,context)
        }

        val timer = object: CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) { }

            override fun onFinish() {
                when (juego.verificarGanador()) {
                    1 -> mostrarDialog("¡GANASTE!")
                    0 -> mostrarDialog("EMPATE")
                    -1 -> mostrarDialog("PERDISTE")
                    -2 -> mostrarDialog("ERROR")
                }
            }
        }
        timer.start()

    }

    //shameful
    fun getCartaImage(valor: Int, mano: Int): Int {
        when(valor) {
            1 -> {
                when(mano) {
                    1 -> return R.drawable.ac
                    2 -> return R.drawable.ad
                    3 -> return R.drawable.ae
                    4 -> return R.drawable.at
                }
            }
            2 -> {
                when(mano) {
                    1 -> return R.drawable.dosc
                    2 -> return R.drawable.dosd
                    3 -> return R.drawable.dose
                    4 -> return R.drawable.dost
                }
            }
            3 -> {
                when(mano) {
                    1 -> return R.drawable.tresc
                    2 -> return R.drawable.tresd
                    3 -> return R.drawable.trese
                    4 -> return R.drawable.trest
                }
            }
            4 -> {
                when(mano) {
                    1 -> return R.drawable.cuatroc
                    2 -> return R.drawable.cuatrod
                    3 -> return R.drawable.cuatroe
                    4 -> return R.drawable.cuatrot
                }
            }
            5 -> {
                when(mano) {
                    1 -> return R.drawable.cincoc
                    2 -> return R.drawable.cincod
                    3 -> return R.drawable.cincoe
                    4 -> return R.drawable.cincot
                }
            }
            6 -> {
                when(mano) {
                    1 -> return R.drawable.seisc
                    2 -> return R.drawable.seisd
                    3 -> return R.drawable.seise
                    4 -> return R.drawable.seist
                }
            }
            7 -> {
                when(mano) {
                    1 -> return R.drawable.sietec
                    2 -> return R.drawable.sieted
                    3 -> return R.drawable.sietee
                    4 -> return R.drawable.sietet
                }
            }
            8 -> {
                when(mano) {
                    1 -> return R.drawable.ochoc
                    2 -> return R.drawable.ochod
                    3 -> return R.drawable.ochoe
                    4 -> return R.drawable.ochot
                }
            }
            9 -> {
                when(mano) {
                    1 -> return R.drawable.nuevec
                    2 -> return R.drawable.nueved
                    3 -> return R.drawable.nuevee
                    4 -> return R.drawable.nuevet
                }
            }
            10 -> {
                when(mano) {
                    1 -> return R.drawable.diezc
                    2 -> return R.drawable.diezd
                    3 -> return R.drawable.dieze
                    4 -> return R.drawable.diezt
                }
            }
            11 -> {
                when(mano) {
                    1 -> return R.drawable.jc
                    2 -> return R.drawable.jd
                    3 -> return R.drawable.je
                    4 -> return R.drawable.jt
                }
            }
            12 -> {
                when(mano) {
                    1 -> return R.drawable.qc
                    2 -> return R.drawable.qd
                    3 -> return R.drawable.qe
                    4 -> return R.drawable.qt
                }
            }
            13 -> {
                when(mano) {
                    1 -> return R.drawable.kc
                    2 -> return R.drawable.kd
                    3 -> return R.drawable.ke
                    4 -> return R.drawable.kt
                }
            }
        }
        return R.drawable.cardback
    }




    /*
    TODOS LOS MÉTODOS QUE TENGAN QUE VER CON CAJAS DE DIALOGO O MENÚS
     */
    fun openCardDialog(view: View) {
        playCartaBSFX()
        var i: Int = -1
        if(cartasPLY.contains(view))
            i = cartasPLY.indexOf(view)
        Log.d("myTag","CARTA SELECCIONADA: "+i.toString())

        if(i!=-1) {
            cardDialog(getCartaImage(Juego.getCartasJugadorPLY().get(i).getValor(),Juego.getCartasJugadorPLY().get(i).getMano()))
        }
    }

    fun cardDialog(cartaId: Int) {

        //Declarar AlertDialog
        val alertCustomDialog: View = LayoutInflater.from(this).inflate(R.layout.custom_dialog_card,null)
        var alert: AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setView(alertCustomDialog)

        //Declarar botones
        val carta: ImageView = alertCustomDialog.findViewById(R.id.cardView)
        carta.setImageResource(cartaId)


        //Mostrar AlertDialog
        val dialog: AlertDialog = alert.create()
        val drawableStuff: ColorDrawable = ColorDrawable(Color.TRANSPARENT)
        dialog.window?.setBackgroundDrawable(drawableStuff)
        dialog.show()

        //Declarar onClick de los botones
        //val mp: MediaPlayer = MediaPlayer.create(this, R.raw.cartac)
        carta.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?){
                dialog.dismiss()
                playCartaCSFX()
                //mp.start()
            } })

    }

    fun mostrarDialog(victoria: String) {
        //Declarar AlertDialog
        val alertCustomDialog: View = LayoutInflater.from(this).inflate(R.layout.custom_dialog_gameover,null)
        var alert: AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setView(alertCustomDialog)

        //Declarar botones
        val jugar: Button = alertCustomDialog.findViewById(R.id.jugarButton)
        val salir: Button = alertCustomDialog.findViewById(R.id.salirButton)
        val gameover: TextView = alertCustomDialog.findViewById(R.id.gameoverView)


        //Mostrar AlertDialog
        val dialog: AlertDialog = alert.create()
        val drawableStuff: ColorDrawable = ColorDrawable(Color.TRANSPARENT)
        dialog.window?.setBackgroundDrawable(drawableStuff)
        dialog.show()

        gameover.text = victoria



        //Declarar onClick de los botones
        jugar.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = intent
                finish()
                startActivity(intent)
                dialog.dismiss()
            }
        })
        salir.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })
    }

    override fun onBackPressed(){
        openDialogMenu()
    }

    fun openPauseMenu(view: View){
        openDialogMenu()
    }

    fun openDialogMenu() {
        //Declarar AlertDialog
        val alertCustomDialog: View = LayoutInflater.from(this).inflate(R.layout.custom_dialog,null)
        var alert: AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setView(alertCustomDialog)



        //Declarar botones
        val volver: Button = alertCustomDialog.findViewById(R.id.volverButton)
        val salir: Button = alertCustomDialog.findViewById(R.id.salirButton)
        val reiniciar: Button = alertCustomDialog.findViewById(R.id.reiniciarButton)


        //Mostrar AlertDialog
        val dialog: AlertDialog = alert.create()
        val drawableStuff: ColorDrawable = ColorDrawable(Color.TRANSPARENT)
        dialog.window?.setBackgroundDrawable(drawableStuff)
        dialog.show()

        //Declarar onClick de los botones
        volver.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                dialog.dismiss()
            }
        })
        reiniciar.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = intent
                finish()
                startActivity(intent)
                dialog.dismiss()
            }
        })
        salir.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })
    }

    @SuppressLint("MissingInflatedId")
    fun nameDialog() {
        var nombreJugador: String = "Jugador"
        var nameView: TextView = findViewById(R.id.nameView)

        //Declarar AlertDialog
        val alertCustomDialog: View = LayoutInflater.from(this).inflate(R.layout.custom_dialog_player_name,null)
        var alert: AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setView(alertCustomDialog)

        //Declarar botones
        val play: Button = alertCustomDialog.findViewById(R.id.playButton)
        val name: EditText = alertCustomDialog.findViewById(R.id.playerName)
        val puntosMax: EditText = alertCustomDialog.findViewById(R.id.puntosMax)


        //Mostrar AlertDialog
        val dialog: AlertDialog = alert.create()
        val drawableStuff: ColorDrawable = ColorDrawable(Color.TRANSPARENT)
        dialog.window?.setBackgroundDrawable(drawableStuff)
        dialog.show()

        //Declarar onClick de los botones
        play.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if(name.text.isNotEmpty()) {
                    nombreJugador = name.text.toString()
                    if(puntosMax.text.toString().toIntOrNull()!=null){
                        nameView.text = nombreJugador+"\n"+puntosMax.text.toString()
                        iniciarJuego(nombreJugador, puntosMax.text.toString().toInt())
                    }
                    else {
                        nameView.text = nombreJugador+"\n21"
                        iniciarJuego(nombreJugador, 21)
                    }

                }
                else {
                    nombreJugador = "Jugador"
                    if(puntosMax.text.toString().toIntOrNull()!=null){
                        nameView.text = nombreJugador+"\n"+puntosMax.text.toString()
                        iniciarJuego(nombreJugador, puntosMax.text.toString().toInt())
                    }
                    else {
                        nameView.text = nombreJugador+"\n21"
                        iniciarJuego(nombreJugador, 21)
                    }
                }
                dialog.dismiss()
            }
        })


    }

    val a: String = "a"
    //writeToFile(a, this.getApplicationContext())





    /*
    EXTRAS
     */
    fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    fun writeToFile(data: String, context: Context) {
        Log.d("DEBUGWRITE",readFromFile(context))
        val dataWrite = data + "\n" + readFromFile(context)
        try {
            val outputStreamWriter =
                OutputStreamWriter(context.openFileOutput("names.txt", MODE_PRIVATE))
            outputStreamWriter.append(dataWrite)
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: $e")
        }
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