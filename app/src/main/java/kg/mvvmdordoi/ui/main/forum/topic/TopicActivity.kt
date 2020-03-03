package kg.mvvmdordoi.ui.main.forum.topic

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
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
import okhttp3.MultipartBody
import java.io.File

class TopicActivity : ImagePickerHelper() {
    override fun openGallery(mItemId: String) {

    }

    override fun setImagePath(imgpath: Uri) {

        imagePath = imgpath.path
        avatar.setImageURI(imgpath)

    }
    var imagePath:String?= null
    var textTitle:String?= null
    var textDescription :String?= null

    private lateinit var viewModel: ForumViewModel
    lateinit var adapter:TopicRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic)
        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(ForumViewModel::class.java)
        setupRv()
        viewModel.getTopic(intent.getIntExtra("forum",-1))
        viewModel.topic.observe(this, Observer {

            if (it != null) {
                adapter.swapData(it)
            }

        })
        viewModel.getUser()
        viewModel.user.observe(this, Observer {

            if (it!=null){
                Glide.with(this).load(it.avatar).into(avatar)
                name.text = it.name
            }

        })

        add_photo.setOnClickListener {

            showPickImageDialog()

        }

        btn_add.setOnClickListener {

            if (isValidate()){
                val forum = RequestBody.create(MediaType.parse("text/plain"), intent.getIntExtra("forum",-1).toString())
                val title = RequestBody.create(MediaType.parse("text/plain"), textTitle)
                val description = RequestBody.create(MediaType.parse("text/plain"), textDescription)
                val user = RequestBody.create(MediaType.parse("text/plain"), UserToken.getToken(App.activity!!).toString())

                if (imagePath!=null) {
                    var imageFile = File(imagePath)
                    val file = RequestBody.create(MediaType.parse("image/*"), imageFile)
                    val fileToUpload =
                        MultipartBody.Part.createFormData("image", imageFile!!.name, file)
                    viewModel.addTopic(forum,title,fileToUpload,description,user,intent.getIntExtra("forum",-1))
                }else{
                    viewModel.addTopic(forum,title,description,user,intent.getIntExtra("forum",-1))
                }
            }
        }

        new_topic.setOnClickListener { startActivityForResult(Intent(this,TitleActivity::class.java),101) }

    }

    private fun setupRv() {
        val layoutManager = GridLayoutManager(this, 1)
        rv.layoutManager = layoutManager
        adapter = TopicRvAdapter(App.activity!!)
        rv.adapter = adapter
    }

    fun isValidate():Boolean{
        var bool = true

        if (textTitle==null){
            bool = false
            new_topic.error = "Нажмите сюда, чтобы заполнить тему"
        }else if(textDescription==null){
            new_topic.error = "Нажмите сюда, чтобы заполнить тему"
            bool = false
        }

        return bool
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode== Activity.RESULT_OK){
            if (data != null) {
                textDescription = data.getStringExtra("description")
                textTitle = data.getStringExtra("title")

                new_topic.text = textTitle
            }
        }

    }

}
