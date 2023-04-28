package uabc.iec.examen1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScoresAdapter (private val mScores: List<Score>) : RecyclerView.Adapter<ScoresAdapter.ViewHolder>()
{
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val name = itemView.findViewById<TextView>(R.id.playersName)
        val hour = itemView.findViewById<TextView>(R.id.hourView)
        val date = itemView.findViewById<TextView>(R.id.dateView)
        val layout = itemView.findViewById<LinearLayout>(R.id.layoutMain)
        val carta1 = itemView.findViewById<ImageView>(R.id.carta1)
        val carta2 = itemView.findViewById<ImageView>(R.id.carta2)
        val carta3 = itemView.findViewById<ImageView>(R.id.carta3)
        val carta4 = itemView.findViewById<ImageView>(R.id.carta4)
        val carta5 = itemView.findViewById<ImageView>(R.id.carta5)
        //
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val scoreView = inflater.inflate(R.layout.item_score, parent, false)
        // Return a new holder instance
        return ViewHolder(scoreView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: ScoresAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val score: Score = mScores.get(position)
        // Set item views based on your views and data model


        val nameView = viewHolder.name
        nameView.setText(score.ganador)
        val hourView = viewHolder.hour
        hourView.setText(score.hora)
        val dateView = viewHolder.date
        dateView.setText(score.fecha)
        val layoutView = viewHolder.layout
        if(score.win)
            layoutView.setBackgroundResource(R.drawable.rounded_corner_view_blue)
        else
            layoutView.setBackgroundResource(R.drawable.rounded_corner_view_red)
        val cartasImg = score.cartas.split(" ").toTypedArray()
        val carta1 = viewHolder.carta1
        carta1.setImageResource(getImageCard(cartasImg.get(0)))
        val carta2 = viewHolder.carta2
        carta2.setImageResource(getImageCard(cartasImg.get(1)))
        val carta3 = viewHolder.carta3
        carta3.setImageResource(getImageCard(cartasImg.get(2)))
        val carta4 = viewHolder.carta4
        carta4.setImageResource(getImageCard(cartasImg.get(3)))
        val carta5 = viewHolder.carta5
        carta5.setImageResource(getImageCard(cartasImg.get(4)))
    }

    fun getImageCard(card: String): Int {
        when(card) {
            "ac" -> return R.drawable.ac
            "at" -> return R.drawable.at
            "ad" -> return R.drawable.ad
            "ae" -> return R.drawable.ae
            "dosc" -> return R.drawable.dosc
            "dost" -> return R.drawable.dost
            "dosd" -> return R.drawable.dosd
            "dose" -> return R.drawable.dose
            "tresc" -> return R.drawable.tresc
            "trest" -> return R.drawable.trest
            "tresd" -> return R.drawable.tresd
            "trese" -> return R.drawable.trese
            "cuatroc" -> return R.drawable.cuatroc
            "cuatrot" -> return R.drawable.cuatrot
            "cuatrod" -> return R.drawable.cuatrod
            "cuatroe" -> return R.drawable.cuatroe
            "cincoc" -> return R.drawable.cincoc
            "cincot" -> return R.drawable.cincot
            "cincod" -> return R.drawable.cincod
            "cincoe" -> return R.drawable.cincoe
            "seisc" -> return R.drawable.seisc
            "seist" -> return R.drawable.seist
            "seisd" -> return R.drawable.seisd
            "seise" -> return R.drawable.seise
            "sietec" -> return R.drawable.sietec
            "sietet" -> return R.drawable.sietet
            "sieted" -> return R.drawable.sieted
            "sietee" -> return R.drawable.sietee
            "ochoc" -> return R.drawable.ochoc
            "ochot" -> return R.drawable.ochot
            "ochod" -> return R.drawable.ochod
            "ochoe" -> return R.drawable.ochoe
            "nuevec" -> return R.drawable.nuevec
            "nuevet" -> return R.drawable.nuevet
            "nueved" -> return R.drawable.nueved
            "nuevee" -> return R.drawable.nuevee
            "diezc" -> return R.drawable.diezc
            "diezt" -> return R.drawable.diezt
            "diezd" -> return R.drawable.diezd
            "dieze" -> return R.drawable.dieze
            "jc" -> return R.drawable.jc
            "jt" -> return R.drawable.jt
            "jd" -> return R.drawable.jd
            "je" -> return R.drawable.je
            "qc" -> return R.drawable.qc
            "qt" -> return R.drawable.qt
            "qd" -> return R.drawable.qd
            "qe" -> return R.drawable.qe
            "kc" -> return R.drawable.kc
            "kt" -> return R.drawable.kt
            "kd" -> return R.drawable.kd
            "ke" -> return R.drawable.ke
        }
        return R.drawable.blankcard
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return mScores.size
    }
}