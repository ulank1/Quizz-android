package kg.mvvmdordoi.ui.university

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kg.mvvmdordoi.App
import kg.mvvmdordoi.R
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.model.get.Game
import kg.mvvmdordoi.model.get.Info
import kg.mvvmdordoi.model.get.University
import kg.mvvmdordoi.model.get.User
import kg.mvvmdordoi.ui.info.InfoViewModel
import kg.mvvmdordoi.utils.URL1
import kotlinx.android.synthetic.main.activity_uni.*

class UniversityInfoActivity : AppCompatActivity() {

    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uni)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.info)

        MobileAds.initialize(this,"ca-app-pub-7649587179327452~9914538335")
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        App.activity = this
        val info = intent.getSerializableExtra("info") as University
        desc.loadData(
            "<html><body>" + correctImage(info.desc) + "</body></html>",
            "text/html; charset=utf-8",
            "UTF-8"
        )

        name.text = info.name

        Glide
            .with(this)
            .load(info.avatar)
            .into(image)

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

        when(item!!.itemId){
            android.R.id.home-> finish()
        }

        return true
    }
}
