package kg.mvvmdordoi.ui.main.about_us

import android.content.ActivityNotFoundException
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kg.mvvmdordoi.R
import kotlinx.android.synthetic.main.activity_about_us.*
import android.content.Intent
import android.support.v4.app.SupportActivity
import android.support.v4.app.SupportActivity.ExtraData
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri
import android.util.Log
import android.view.MenuItem
import kg.mvvmdordoi.utils.extension.toast
import kotlinx.android.synthetic.main.activity_about_us.btn_send_facebook
import kotlinx.android.synthetic.main.activity_about_us.btn_send_instagram
import kotlinx.android.synthetic.main.activity_send_message.*


class AboutUsActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        rate_us.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.google.android.youtube")
                )
            ) }
        btn_send_facebook.setOnClickListener { launchFacebook(this) }
        btn_send_instagram.setOnClickListener { launchInstagram(this,"synak_time") }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    private fun launchFacebook(context: Context) {
        context.startActivity(getOpenFacebookIntent(context))
    }

    private fun getOpenFacebookIntent(context: Context): Intent {
        return Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/synaktimeapp"))

    }

    private fun launchInstagram(context: Context, instagramId: String) {
        Log.e("insta",instagramId)
        if (!instagramId.isNullOrEmpty()) {
            val uri = Uri.parse("http://instagram.com/_u/$instagramId")
            val intent = Intent(Intent.ACTION_VIEW, uri)

            intent.setPackage("com.instagram.android")

            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com/$instagramId")
                    )
                )
            }
        } else {
            this.toast(getString(R.string.no_instagram))
        }
    }
}
