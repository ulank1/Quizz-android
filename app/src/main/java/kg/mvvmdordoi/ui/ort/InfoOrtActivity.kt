package kg.mvvmdordoi.ui.ort

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.MenuItem
import com.google.android.gms.ads.AdView
import kg.mvvmdordoi.App
import kg.mvvmdordoi.R
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.utils.URL1
import kg.mvvmdordoi.utils.extension.gone
import kg.mvvmdordoi.utils.extension.visible
import kotlinx.android.synthetic.main.activity_info_ort.*

class InfoOrtActivity : AppCompatActivity() {
    lateinit var mAdView: AdView
    private lateinit var viewModel: OrtQuestionViewModel
    var timer:CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_ort)
        supportActionBar!!.title = getString(R.string.info)
        App.activity = this

        viewModel =
            ViewModelProviders.of(this, ViewModelFactory()).get(OrtQuestionViewModel::class.java)
        if (Ort.typeOfTest == -1) {
//            viewModel.putPay(intent.getIntExtra("payID",0))
            viewModel.getInfo(5)
        } else {
            viewModel.getInfo(Ort.typeOfTest)
        }

        if (Ort.typeOfTest==-1||Ort.typeOfTest==0){
            time.gone()
        }else{
            time.visible()
            var a = Ort.restTimes[Ort.typeOfTest]*60
            timer = object: CountDownTimer((Ort.restTimes[Ort.typeOfTest]*60*1000).toLong(), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    a--
                    String.format("%02d:%02d", a/60, a%60)
                    time.text = String.format("%02d:%02d", a/60, a%60)
                }

                override fun onFinish() {

                    startActivity(Intent(this@InfoOrtActivity,CountActivity::class.java))
                    finish()
                }
            }
            timer!!.start()
        }

        viewModel.info.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                desc.loadData(
                    "<html><body>" + correctImage(it[0].desc) + "</body></html>",
                    "text/html; charset=utf-8",
                    "UTF-8"
                )

                name.text = it[0].title
            }
        })
        next.setOnClickListener {
            if (Ort.typeOfTest == -1) {
                Ort.typeOfTest = 0
                startActivity(Intent(this, InfoOrtActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, CountActivity::class.java))
                finish()
            }
        }
    }

    private fun correctImage(data: String): String {

        var true_data = data.replace(
            "/media/django-summernote",
            URL1 + "/media/django-summernote"
        )

        Log.e("TRUEDATA", true_data)

        return true_data
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
//            android.R.id.home -> finish()
        }

        return true
    }

    override fun onBackPressed() {

    }

    override fun onDestroy() {
        if (timer!=null){
            timer!!.cancel()
            timer = null
        }
        super.onDestroy()

    }

    override fun onResume() {
        super.onResume()
        App.activity = this

    }
}
