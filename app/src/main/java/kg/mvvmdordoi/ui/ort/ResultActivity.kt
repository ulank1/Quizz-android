package kg.mvvmdordoi.ui.ort

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kg.mvvmdordoi.R
import kg.mvvmdordoi.ui.ort.Ort.ortPoints
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.activity_result.image_status
import kotlinx.android.synthetic.main.activity_result.result
import kotlinx.android.synthetic.main.activity_result.text_status
import kotlin.math.roundToInt

class ResultActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        close.setOnClickListener { finish() }

        true1.text = "${ortPoints[0]}/30"
        true2.text = "${ortPoints[1]}/30"
        true3.text = "${ortPoints[2]}/30"
        true4.text = "${ortPoints[3]}/30"
        true5.text = "${ortPoints[4]}/30"

        var allPoints:Double = (ortPoints[0]+ortPoints[1]+ortPoints[2]+ortPoints[3]+ortPoints[4]).toDouble()*1.63
        var points:Int = allPoints.roundToInt()
        result.text = points.toString()
        if (points>220){
            text_status.text = getString(R.string.more220)
            image_status.setImageResource(R.drawable.happy)
        }else if (points in 200..220){
            text_status.text = getString(R.string.more200)
            image_status.setImageResource(R.drawable.happy)
        }else if (points in 150..199){
            text_status.text = getString(R.string.more150)
            image_status.setImageResource(R.drawable.wink)
        }else if (points in 110..149){
            text_status.text = getString(R.string.more110)
            image_status.setImageResource(R.drawable.sad)
        }else if (points in 0..109){
            text_status.text = getString(R.string.more0)
            image_status.setImageResource(R.drawable.crying)
        }

        share.setOnClickListener {

            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody = text_status.text.toString()+"\n Пройти тест \n https://play.google.com/store/apps/details?id=kg.mvvmdordoi"
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here")
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sharingIntent, "Share via"))

        }
    }
}