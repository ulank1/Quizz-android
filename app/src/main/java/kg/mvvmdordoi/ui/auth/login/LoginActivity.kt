package kg.mvvmdordoi.ui.auth.login

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import kg.mvvmdordoi.App
import kg.mvvmdordoi.R
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.network.UserToken
import kg.mvvmdordoi.ui.auth.confirm_code.PhoneActivity
import kg.mvvmdordoi.utils.ImagePickerHelper
import kg.mvvmdordoi.utils.extension.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

     var imagePath:String?= null

    private lateinit var viewModel: LoginViewModel
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.lgin)

        App.activity = this
        viewModel =
            ViewModelProviders.of(this, ViewModelFactory()).get(LoginViewModel::class.java)

        viewModel.isExist.observe(this, Observer {
            Log.e("Boolean",it.toString())
            if (it != null) {
                when {
                    it.isEmpty() -> login.error = "Такого аккаунта не существует"
                    it[0].password.trim()!=password.text.toString().trim() -> password.error = "Пароль неверный"
                    else -> {
                        UserToken.saveToken(it[0].id.toString(), this)
                        viewModel.postDevice(it[0].id)
                    }
                }
            }
        })

        forgot_password.setOnClickListener { startActivity(Intent(this,PhoneActivity::class.java)) }

        setOnClickLiteners()
    }


    private fun setOnClickLiteners() {
        btn_login.setOnClickListener {
            if (isValidate()) {
                viewModel.isExistLogin(login.text.toString())
            }
        }
    }

    private fun isValidate(): Boolean {
        var boolean = true

        if (login.text.toString().length < 5) {
            boolean = false
            login.error = "Номер введен неверно"
        }

        if (password.text.toString().isEmpty()) {
            boolean = false
            password.error = "Пароль не может быть пустым"
        }

        return boolean
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId){
            android.R.id.home-> finish()
        }

        return true
    }

}
