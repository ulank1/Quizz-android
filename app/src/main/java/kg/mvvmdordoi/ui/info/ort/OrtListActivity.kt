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
import kg.mvvmdordoi.ui.info.InfoViewModel
import kg.mvvmdordoi.ui.university.UniversityRvAdapter
import kotlinx.android.synthetic.main.activity_info.*
import kotlinx.android.synthetic.main.activity_test.*

class OrtListActivity : AppCompatActivity() {
    lateinit var mAdView : AdView

    private lateinit var viewModel: InfoViewModel
    lateinit var adapter:OrtRvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.ort)
        App.activity = this
        MobileAds.initialize(this)
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        setupRv()

        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(InfoViewModel::class.java)

        viewModel.getOrtDesc(intent.getIntExtra("id",1))
        viewModel.info.observe(this, Observer {

            if (it != null) {
                adapter.swapData(it as ArrayList<Info>)
            }

        })
    }

    private fun setupRv(){
        val layoutManager = GridLayoutManager(this,1)
        rv.layoutManager = layoutManager
        adapter = OrtRvAdapter(this)
        rv.adapter = adapter
        rv.setHasFixedSize(false)

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId){
            android.R.id.home-> finish()
        }

        return true
    }
}
