package kg.mvvmdordoi.ui.ort

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kg.mvvmdordoi.R
import kg.mvvmdordoi.ui.ort.Ort.ortPoints
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        true1.text = "${ortPoints[0]}/30"
        true2.text = "${ortPoints[1]}/30"
        true3.text = "${ortPoints[2]}/30"
        true4.text = "${ortPoints[3]}/30"
        true5.text = "${ortPoints[4]}/30"

        false1.text = "${30-ortPoints[0]}/30"
        false2.text = "${30-ortPoints[1]}/30"
        false3.text = "${30-ortPoints[2]}/30"
        false4.text = "${30-ortPoints[3]}/30"
        false5.text = "${30-ortPoints[4]}/30"


    }
}
