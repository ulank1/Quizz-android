package kg.mvvmdordoi.ui.ort

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import kg.mvvmdordoi.R
import kotlinx.android.synthetic.main.activity_count.*

class CountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count)
        var a = 5
        val timer = object: CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                a--
                count.text = a.toString()

            }

            override fun onFinish() {

                startActivity(Intent(this@CountActivity,OrtQuestionActivity::class.java))
                finish()
            }
        }
        timer.start()
    }


}
