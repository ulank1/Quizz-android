package kg.mvvmdordoi.ui.auth.confirm_code

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kg.mvvmdordoi.R
import kotlinx.android.synthetic.main.activity_confirm_code.*
import kotlinx.android.synthetic.main.activity_confirm_code.btn_ok
import kotlinx.android.synthetic.main.activity_confirm_code.code
import kotlinx.android.synthetic.main.activity_phone.*

class PhoneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone)

        supportActionBar!!.title = "Востановить пароль"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        btn_ok.setOnClickListener { startActivity(Intent(this,ConfirmCodeActivity::class.java).putExtra("phone",code.text.toString()))
        finish()}
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        finish()

        return true
    }

}
