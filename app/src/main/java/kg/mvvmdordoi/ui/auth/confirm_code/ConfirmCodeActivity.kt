package kg.mvvmdordoi.ui.auth.confirm_code

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kg.mvvmdordoi.App
import kg.mvvmdordoi.R
import kg.mvvmdordoi.injection.ViewModelFactory
import kotlinx.android.synthetic.main.activity_confirm_code.*

class ConfirmCodeActivity : AppCompatActivity() {

    private lateinit var viewModel: ConfirmCodeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar!!.title = getString(R.string.confirm_code)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_confirm_code)


        App.activity = this


        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(ConfirmCodeViewModel::class.java)


        viewModel.getSmsCode(this,intent.getStringExtra("phone"),1)
        btn_ok.setOnClickListener { viewModel.sendCode(code.text.toString()) }
    }

    override fun onResume() {
        super.onResume()
        App.activity = this
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        finish()

        return true
    }

}
