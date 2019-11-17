package kg.mvvmdordoi.ui.test

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import kg.mvvmdordoi.App
import kg.mvvmdordoi.R
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.model.get.Test
import kotlinx.android.synthetic.main.activity_test.*

class GameTestActivity : AppCompatActivity() {

    private lateinit var viewModel: TestViewModel
    private lateinit var adapter: TestRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Орт Математика"
        App.activity = this
        viewModel =
            ViewModelProviders.of(this, ViewModelFactory()).get(TestViewModel::class.java)
        viewModel.getTests(intent.getIntExtra("category",1))
        setupRv()
        viewModel.tests.observe(this, Observer { adapter.swapData(it as ArrayList<Test>) })
    }

    private fun setupRv(){
        val layoutManager = GridLayoutManager(this,1)
        rv.layoutManager = layoutManager
        adapter = TestRvAdapter(this,"")
        rv.adapter = adapter
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId){
            android.R.id.home-> finish()
        }

        return true
    }
}
