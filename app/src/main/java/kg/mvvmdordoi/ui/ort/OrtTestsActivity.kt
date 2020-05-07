package kg.mvvmdordoi.ui.ort

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import kg.mvvmdordoi.App
import kg.mvvmdordoi.R
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.model.get.OrtTest
import kg.mvvmdordoi.ui.test.TestRvAdapter
import kotlinx.android.synthetic.main.activity_ort_tests.*
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.android.synthetic.main.activity_test.rv

class OrtTestsActivity : AppCompatActivity() {
    private lateinit var viewModel: OrtQuestionViewModel
    private lateinit var adapter: OrtTestRvAdapter
    private var hasActive = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ort_tests)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Пробный тест"
        App.activity = this

        viewModel =
            ViewModelProviders.of(this, ViewModelFactory()).get(OrtQuestionViewModel::class.java)
        setupRv()
        viewModel.getOrt()
        viewModel.categories.observe(this, Observer {

            adapter.swapData(it as ArrayList<OrtTest>)

        })

        viewModel.getPay()

        viewModel.pay.observe(this, Observer {

            if (it != null) {
                hasActive = it.isNotEmpty()
                if (hasActive) {
                    adapter.hasActive(hasActive, it[0].id)
                }else{
                    adapter.hasActive(hasActive, 0)
                }
                text.text = "Оплаченные тесты: "+it.size
            }

        })
    }

    private fun setupRv(){
        val layoutManager = GridLayoutManager(this,1)
        rv.layoutManager = layoutManager
        adapter = OrtTestRvAdapter(this)
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
