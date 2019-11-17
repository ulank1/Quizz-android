package kg.mvvmdordoi.ui.test.test_detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import kg.mvvmdordoi.R
import kg.mvvmdordoi.utils.URL1
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.activity_content.content

class ContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        supportActionBar?.title = "Текст"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        content.loadData(
            "<html><body>" + correctImage(intent.getStringExtra("desc")) + "</body></html>",
            "text/html; charset=utf-8",
            "UTF-8"
        )
    }

    private fun correctImage(data: String): String {

        var true_data = data.replace(
            "/media/django-summernote",
            URL1 +"/media/django-summernote"
        )

        Log.e("TRUEDATA", true_data)

        return true_data
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        finish()

        return true
    }

}
