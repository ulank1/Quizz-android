package kg.mvvmdordoi.ui.notification

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import kg.mvvmdordoi.App
import kg.mvvmdordoi.R
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.model.get.Game
import kg.mvvmdordoi.model.get.Notification
import kg.mvvmdordoi.model.get.User
import kotlinx.android.synthetic.main.activity_test.*

class NotificationActivity : AppCompatActivity() {

    private lateinit var viewModel: NotificationViewModel
    private lateinit var adapter: NotificationRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Уведомления"
        App.activity = this
        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(NotificationViewModel::class.java)
        setupRv()

        viewModel.getNotifications()
        viewModel.notifications.observe(this, Observer { adapter.swapData(it as ArrayList<Notification>) })
    }

    private fun setupRv(){
        val layoutManager = GridLayoutManager(this,1)
        rv.layoutManager = layoutManager
        adapter = NotificationRvAdapter(this)
        rv.adapter = adapter
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId){
            android.R.id.home-> finish()
        }

        return true
    }

}
