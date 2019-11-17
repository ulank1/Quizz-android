package kg.mvvmdordoi.ui.auth.confirm_code.new_password

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import kg.mvvmdordoi.App
import kg.mvvmdordoi.R
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.ui.auth.login.LoginViewModel
import kg.mvvmdordoi.utils.extension.toast
import kotlinx.android.synthetic.main.activity_new_password.*
class NewPasswordActivity : AppCompatActivity() {

     var imagePath:String?= null
     var id:Int = 0

    private lateinit var viewModel: LoginViewModel
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_password)

        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Смена пароля"

        App.activity = this
        viewModel =
            ViewModelProviders.of(this, ViewModelFactory()).get(LoginViewModel::class.java)

        viewModel.isExist.observe(this, Observer {
            Log.e("Boolean",it.toString())
            if (it != null) {
                when {
                    it.isEmpty() -> this.toast("Такого аккаунта не существует")
                    else -> {
                        viewModel.putPassword(it[0].id,password.text.toString())
                    }
                }
            }
        })
        setOnClickLiteners()
    }


    private fun setOnClickLiteners() {
        btn_ok.setOnClickListener {
            if (isValidate()) {
                viewModel.isExistLogin(intent.getStringExtra("phone"))
            }
        }
    }

    private fun isValidate(): Boolean {
        var boolean = true

        if (password.text.toString().isEmpty()) {
            boolean = false
            password.error = "Пароль не может быть пустым"
        }

        if (password.text.toString() != repeat_password.text.toString()) {
            boolean = false
            repeat_password.error = "Пароли не совпадают"
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
