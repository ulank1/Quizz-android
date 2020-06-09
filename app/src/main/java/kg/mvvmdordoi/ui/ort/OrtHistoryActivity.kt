package kg.mvvmdordoi.ui.ort

import android.annotation.SuppressLint
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
import kg.mvvmdordoi.model.get.OrtTest
import kg.mvvmdordoi.ui.test.TestRvAdapter
import kg.mvvmdordoi.utils.extension.gone
import kotlinx.android.synthetic.main.activity_ort_tests.*
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.android.synthetic.main.activity_test.rv

class OrtHistoryActivity : AppCompatActivity() {
    private lateinit var viewModel: OrtQuestionViewModel
    private lateinit var adapter: OrtHistoryRvAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ort_tests)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "История"
        App.activity = this

        viewModel =
            ViewModelProviders.of(this, ViewModelFactory()).get(OrtQuestionViewModel::class.java)
        setupRv()
        viewModel.getHitoryOrt()
        viewModel.history.observe(this, Observer {

            if (it != null) {
                adapter.swapData(it)
            }

        })

        link1.gone()
        history.gone()
    }

    private fun setupRv(){
        val layoutManager = GridLayoutManager(this,1)
        rv.layoutManager = layoutManager
        adapter = OrtHistoryRvAdapter(this)
        rv.adapter = adapter
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
