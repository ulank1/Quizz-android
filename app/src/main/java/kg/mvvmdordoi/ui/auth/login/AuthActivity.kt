package kg.mvvmdordoi.ui.auth.login

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kg.mvvmdordoi.App.Companion.context
import kg.mvvmdordoi.R
import kg.mvvmdordoi.ui.auth.register.RegisterActivity
import kg.mvvmdordoi.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        signIn.setOnClickListener { startActivityForResult(Intent(this, LoginActivity::class.java),102) }
        register.setOnClickListener { startActivityForResult(Intent(this, RegisterActivity::class.java),101) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode== Activity.RESULT_OK){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

    }

}
