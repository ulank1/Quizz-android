package kg.mvvmdordoi.ui.university

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kg.mvvmdordoi.App
import kg.mvvmdordoi.R
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.model.get.Game
import kg.mvvmdordoi.model.get.University
import kg.mvvmdordoi.model.get.User
import kotlinx.android.synthetic.main.activity_test.*

class UniversityListActivity : AppCompatActivity() {

    lateinit var mAdView : AdView
    private lateinit var viewModel: UniversityViewModel
    private lateinit var adapter: UniversityRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.university)
        App.activity = this

        MobileAds.initialize(this,"ca-app-pub-7649587179327452~9914538335")
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(UniversityViewModel::class.java)
        setupRv()

        viewModel.getUniversity()
        viewModel.university.observe(this, Observer { adapter.swapData(it as ArrayList<University>) })
       // viewModel.users.observe(this, Observer { adapter.swapData(it as ArrayList<Game>) })
    }

    private fun setupRv(){
        val layoutManager = GridLayoutManager(this,1)
        rv.layoutManager = layoutManager
        adapter = UniversityRvAdapter(this)
        rv.adapter = adapter
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId){
            android.R.id.home-> finish()
        }

        return true
    }
}
