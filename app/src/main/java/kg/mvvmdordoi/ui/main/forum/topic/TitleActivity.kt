package kg.mvvmdordoi.ui.main.forum.topic

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kg.mvvmdordoi.R
import kotlinx.android.synthetic.main.activity_title.*

class TitleActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.topic_create)

        btn_back.setOnClickListener {

            var intent = Intent()
            intent.putExtra("title",title1.text.toString())
            intent.putExtra("description",desc.text.toString())
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var intent = Intent()
        intent.putExtra("title",title1.text.toString())
        intent.putExtra("description",desc.text.toString())
        setResult(Activity.RESULT_OK,intent)
        finish()
        return super.onOptionsItemSelected(item)
    }
}
