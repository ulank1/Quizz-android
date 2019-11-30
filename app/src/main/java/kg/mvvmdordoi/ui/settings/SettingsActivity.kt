package kg.mvvmdordoi.ui.settings

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kg.mvvmdordoi.R
import kg.mvvmdordoi.network.Lang
import kotlinx.android.synthetic.main.activity_settings.*
import android.R.string
import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.res.Configuration
import android.view.MenuItem
import kg.mvvmdordoi.ui.notification.NotificationViewModel
import java.util.*
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.ui.auth.redactorProfile.RedactorProfileActivity


class SettingsActivity : AppCompatActivity() {
    private lateinit var viewModel: NotificationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.setttings)
        val lang = Lang.get(this)
        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(NotificationViewModel::class.java)

        if (lang == "1"){
            kg.isChecked = true
        }else{
            ru.isChecked = true
        }

        btn_save.setOnClickListener {

            if (kg.isChecked){
                val locale = Locale("ky")
                Locale.setDefault(locale)
                val configuration = Configuration()
                configuration.locale = locale
                baseContext.resources.updateConfiguration(configuration, null)
                setTitle(R.string.app_name)
                Lang.save("1",this)

            }else{
                val locale = Locale("ru")
                Locale.setDefault(locale)
                val configuration = Configuration()
                configuration.locale = locale
                baseContext.resources.updateConfiguration(configuration, null)
                setTitle(R.string.app_name)

                Lang.save("2",this)

            }

            viewModel.putNot(switch_notification.isChecked)
            setResult(Activity.RESULT_OK)
            finish()


        }

        viewModel.getUser()
        viewModel.user.observe(this,android.arch.lifecycle.Observer {

            if (it != null) {
                switch_notification.isChecked = it.is_notification
            }

        })

        btn_edit_profile.setOnClickListener { startActivity(Intent(this,RedactorProfileActivity::class.java)) }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        finish()

        return true
    }

}
