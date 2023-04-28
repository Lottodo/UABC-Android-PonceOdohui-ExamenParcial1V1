package uabc.iec.examen1

import android.util.Log


class Score(val ganador: String, val cartas: String, val fecha: String, val hora: String, val win: Boolean) {


    companion object {
        private var lastContactId = 0
        fun createScoresList(scoreRaw: String): ArrayList<Score> {
            val scoreNames: Array<String>
            val scores = ArrayList<Score>()


            if (scoreRaw != null) {

                scoreNames = scoreRaw.split("\n").toTypedArray()

                for (score in scoreNames) {
                    Log.d("DEBUGEANDO","score:"+score)
                    val scoreData = score.split(",").toTypedArray()
                    val winner: Boolean// = scoreData.get(4).toBoolean()
                    Log.d("DEBUGEANDO","scoreData.size:"+scoreData.size)
                    if(scoreData.size==5) {
                        if (scoreData.get(4).toInt() == 1)
                            winner = true
                        else
                            winner = false
                        scores.add(
                            Score(scoreData.get(0), scoreData.get(1),
                                scoreData.get(2), scoreData.get(3), winner))
                    }
                }
            }
            return scores
        }
    }
}