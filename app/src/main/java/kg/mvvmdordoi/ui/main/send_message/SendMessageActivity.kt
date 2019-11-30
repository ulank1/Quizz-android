package kg.mvvmdordoi.ui.main.send_message

import android.content.ActivityNotFoundException
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kg.mvvmdordoi.App
import kg.mvvmdordoi.R
import kotlinx.android.synthetic.main.activity_send_message.*
import android.widget.Toast
import android.content.Intent
import android.net.Uri
import android.util.Log
import kg.mvvmdordoi.ui.main.MainActivity
import kg.mvvmdordoi.utils.extension.toast





class SendMessageActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_message)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.svyaz)
        App.activity = this



        btn_send.setOnClickListener {
            val send = Intent(Intent.ACTION_SENDTO)
            val uriText = "mailto:" + Uri.encode("email@gmail.com") +
                    "?subject=" + Uri.encode("the subject") +
                    "&body=" + Uri.encode("the body of the message")
            val uri = Uri.parse(uriText)

            send.data = uri
            startActivity(Intent.createChooser(send, "Send mail..."))


        }
        btn_send_facebook.setOnClickListener { launchFacebook(this) }
        btn_send_instagram.setOnClickListener { launchInstagram(this,"ulankkarimov") }


    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
            android.R.id.home -> finish()
        }

        return true
    }

    private fun launchFacebook(context: Context) {
        context.startActivity(getOpenFacebookIntent(context))
    }

    private fun getOpenFacebookIntent(context: Context): Intent {
        return Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/100005190183416"))

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
