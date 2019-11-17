package kg.mvvmdordoi.ui.game.category

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
import kg.mvvmdordoi.model.get.User
import kotlinx.android.synthetic.main.activity_test.*

class CategoryActivity : AppCompatActivity() {

    private lateinit var viewModel: CategoryViewModel
    private lateinit var adapter: CategotryRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.choose_part)
        App.activity = this
        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(CategoryViewModel::class.java)
        setupRv()

        viewModel.getUsers()
        viewModel.users.observe(this, Observer { adapter.swapData(it as ArrayList<Game>) })
    }

    private fun setupRv(){
        val layoutManager = GridLayoutManager(this,2)
        rv.layoutManager = layoutManager
        adapter = CategotryRvAdapter(this)
        rv.adapter = adapter
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId){
            android.R.id.home-> finish()
        }

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode==Activity.RESULT_OK&&requestCode==101){
            setResult(Activity.RESULT_OK)
            finish()
        }

    }
}
