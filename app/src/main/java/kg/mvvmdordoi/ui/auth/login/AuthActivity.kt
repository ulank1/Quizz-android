package kg.mvvmdordoi.ui.auth.login

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kg.mvvmdordoi.App.Companion.context
import kg.mvvmdordoi.R
import kg.mvvmdordoi.ui.auth.register.RegisterActivity
import kg.mvvmdordoi.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_auth.*
import java.util.*
import kg.mvvmdordoi.network.Lang

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        signIn.setOnClickListener {
            startActivityForResult(
                Intent(this, LoginActivity::class.java),
                102
            )
        }
        register.setOnClickListener {
            startActivityForResult(
                Intent(
                    this,
                    RegisterActivity::class.java
                ), 101
            )
        }

        kg.setOnClickListener {
            val locale = Locale("ky")
            Locale.setDefault(locale)
            val configuration = Configuration()
            configuration.locale = locale
            baseContext.resources.updateConfiguration(configuration, null)
            Lang.save("1", this)
            setTextLang()
        }
        ru.setOnClickListener {

            val locale = Locale("ru")
            Locale.setDefault(locale)
            val configuration = Configuration()
            configuration.locale = locale
            baseContext.resources.updateConfiguration(configuration, null)
            Lang.save("2", this)
            setTextLang()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }


    fun setTextLang(){
        register.text = getString(R.string.i_need_to_register)
        signIn.setText(getString(R.string.sign_in))
    }
}
