package kg.mvvmdordoi.ui.test

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kg.mvvmdordoi.R
import kotlinx.android.synthetic.main.activity_emp.*

class EmpActivity : AppCompatActivity() {
    private lateinit var mInterstitialAd: InterstitialAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emp)
        MobileAds.initialize(
            this,
            "ca-app-pub-3940256099942544~3347511713"
        )
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/8691691433"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        btn_rek.setOnClickListener {
            if (mInterstitialAd.isLoaded) {
                mInterstitialAd.show()
            } else {
                Log.e("TAG", "The interstitial wasn't loaded yet.")
            }
        }

        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdLoaded() {
                Log.e("Status", "onAdLoaded")
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                Log.e("Status", "onAdFailedToLoad")
            }

            override fun onAdOpened() {
                Log.e("Status", "onAdOpened")
            }

            override fun onAdClicked() {
                Log.e("Status", "onAdClicked")
            }

            override fun onAdLeftApplication() {
                Log.e("Status", "onAdLeftApplication")
            }

            override fun onAdClosed() {
                Log.e("Status", "onAdClosed")
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }
    }


}
