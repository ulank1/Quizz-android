package kg.mvvmdordoi.ui.info.ort

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.MenuItem
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kg.mvvmdordoi.App
import kg.mvvmdordoi.R
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.model.get.Game
import kg.mvvmdordoi.model.get.Info
import kg.mvvmdordoi.model.get.User
import kg.mvvmdordoi.utils.URL1
import kotlinx.android.synthetic.main.activity_info.*

class OrtInfoActivity : AppCompatActivity() {

    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.ort)
        App.activity = this
        MobileAds.initialize(this)
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        var it = intent.getSerializableExtra("info") as Info


        desc.loadDataWithBaseURL(
            null,
            "<html><body>" + correctImage(it.desc) + "</body></html>",
            "text/html; charset=utf-8",
            "UTF-8",
            null
        )

        name.text = it.name


    }

    private fun correctImage(data: String): String {

        var true_data = data.replace(
            "/media/django-summernote",
            "$URL1/media/django-summernote"
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
}
