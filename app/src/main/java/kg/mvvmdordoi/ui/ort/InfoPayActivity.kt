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

class InfoPayActivity : AppCompatActivity() {
    lateinit var mAdView: AdView
    private lateinit var viewModel: OrtQuestionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_pay)
        supportActionBar!!.title = getString(R.string.info)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        App.activity = this

        viewModel =
            ViewModelProviders.of(this, ViewModelFactory()).get(OrtQuestionViewModel::class.java)


        viewModel.getInfoPay()
        viewModel.infoPay.observe(this, Observer {
            if (it!=null) {
                desc.loadData(
                    "<html><body>" + correctImage(it[0].desc) + "</body></html>",
                    "text/html; charset=utf-8",
                    "UTF-8"
                )

            }
        })

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
            android.R.id.home -> finish()
        }

        return true
    }

    override fun onResume() {
        super.onResume()
        App.activity = this

    }
}
