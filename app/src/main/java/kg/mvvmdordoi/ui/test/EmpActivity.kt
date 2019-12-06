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
import java.security.NoSuchAlgorithmException
import android.provider.Settings


class EmpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emp)
        setAd()
    }

    private lateinit var mInterstitialAd: InterstitialAd

    fun setAd() {
        MobileAds.initialize(
            this,
            getString(R.string.app_id_ad_mob)
        )
//        6B635D398D472375ADFAF82341AB573B
        val android_id = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        val deviceId = md5(android_id).toUpperCase()
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = getString(R.string.ad_video_id)
        mInterstitialAd.loadAd(AdRequest.Builder().addTestDevice("6B635D398D472375ADFAF82341AB573B").build())
        Log.e("device id=",deviceId);
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
                Log.e("Status", errorCode.toString())
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
    fun md5(s: String): String {
        try {
            // Create MD5 Hash
            val digest = java.security.MessageDigest.getInstance("MD5")
            digest.update(s.toByteArray())
            val messageDigest = digest.digest()

            // Create Hex String
            val hexString = StringBuffer()
            for (i in messageDigest.indices)
                hexString.append(Integer.toHexString(0xFF and messageDigest[i].toInt()))
            return hexString.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }
}
