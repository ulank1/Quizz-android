package kg.mvvmdordoi.ui.main

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager

import java.util.Locale

import kg.mvvmdordoi.R
import kg.mvvmdordoi.network.Lang
import kg.mvvmdordoi.network.UserToken
import kg.mvvmdordoi.ui.auth.login.AuthActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      
        setContentView(R.layout.activity_splash_screen)

        val lang = Lang.get(this)!!

        if (lang == "1") {
            val locale = Locale("ky")
            Locale.setDefault(locale)
            val configuration = Configuration()
            configuration.locale = locale
            baseContext.resources.updateConfiguration(configuration, null)
        } else {
            val locale = Locale("ru")
            Locale.setDefault(locale)
            val configuration = Configuration()
            configuration.locale = locale
            baseContext.resources.updateConfiguration(configuration, null)
        }
        val i: Intent
        if (!UserToken.hasToken(this)) {
            i = Intent(this, AuthActivity::class.java)

        } else {
            i = Intent(this, MainActivity::class.java)

        }

        val timer = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    startActivity(i)
                    finish()
                }
            }
        }
        timer.start()
    }
}
