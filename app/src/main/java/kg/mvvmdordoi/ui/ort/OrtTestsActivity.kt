package kg.mvvmdordoi.ui.ort

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import kg.mvvmdordoi.R
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.ui.test.TestRvAdapter
import kotlinx.android.synthetic.main.activity_test.*

class OrtTestsActivity : AppCompatActivity() {
    private lateinit var viewModel: OrtQuestionViewModel
    private lateinit var adapter: OrtTestRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ort_tests)
        viewModel =
            ViewModelProviders.of(this, ViewModelFactory()).get(OrtQuestionViewModel::class.java)
        setupRv()
    }

    private fun setupRv(){
        val layoutManager = GridLayoutManager(this,1)
        rv.layoutManager = layoutManager
        adapter = OrtTestRvAdapter(this)
        rv.adapter = adapter
    }
}
