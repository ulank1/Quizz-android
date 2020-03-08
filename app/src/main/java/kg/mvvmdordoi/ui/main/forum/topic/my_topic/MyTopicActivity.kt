package kg.mvvmdordoi.ui.main.forum.topic.my_topic

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import com.bumptech.glide.Glide
import kg.mvvmdordoi.App
import kg.mvvmdordoi.R
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.ui.main.forum.ForumViewModel
import kg.mvvmdordoi.utils.ImagePickerHelper
import kotlinx.android.synthetic.main.activity_topic.*
import kotlinx.android.synthetic.main.activity_topic.avatar
import kotlinx.android.synthetic.main.activity_topic.name
import okhttp3.MediaType
import okhttp3.RequestBody
import kg.mvvmdordoi.network.UserToken
import kg.mvvmdordoi.ui.main.forum.topic.TitleActivity
import kg.mvvmdordoi.ui.main.forum.topic.TopicRvAdapter
import kg.mvvmdordoi.utils.extension.hideKeyboardFrom
import kotlinx.android.synthetic.main.activity_topic.rv
import kotlinx.android.synthetic.main.activity_topic.search
import okhttp3.MultipartBody
import java.io.File

class MyTopicActivity : ImagePickerHelper(),TextWatcher, MyTopicRvAdapter.OnClickItem {
    override fun onClickItemDelete(id:Int) {
        viewModel.deleteMyTopic(id)
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s != null) {
            if (!s.isEmpty()) {
                viewModel.getTopicSearch(intent.getIntExtra("forum",-1),s.toString())
            }else{
                viewModel.getTopic(intent.getIntExtra("forum",-1))
            }
        }
    }

    override fun openGallery(mItemId: String) {

    }

    override fun setImagePath(imgpath: Uri) {

        imagePath = imgpath.path
        add_photo.setImageURI(imgpath)

    }
    var imagePath:String?= null
    var textTitle:String?= null
    var textDescription :String?= null

    private lateinit var viewModel: ForumViewModel
    lateinit var adapter: MyTopicRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_topic)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.my_topics)

        App.activity = this
        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(ForumViewModel::class.java)
        setupRv()
        viewModel.getMyTopic()
        viewModel.topic.observe(this, Observer {

            if (it != null) {
                adapter.swapData(it)
            }

        })


    }

    private fun setupRv() {
        val layoutManager = GridLayoutManager(this, 1)
        rv.layoutManager = layoutManager
        adapter = MyTopicRvAdapter(App.activity!!)
        rv.adapter = adapter
        adapter.setListener1(this)
    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        App.activity = this
    }

}
