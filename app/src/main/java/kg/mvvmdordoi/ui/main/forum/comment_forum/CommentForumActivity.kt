package kg.mvvmdordoi.ui.main.forum.comment_forum

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kg.mvvmdordoi.App
import kg.mvvmdordoi.App.Companion.activity
import kg.mvvmdordoi.R
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.model.get.Comment
import kg.mvvmdordoi.model.get.Topic
import kg.mvvmdordoi.ui.main.dayli_game.CommentClickListener
import kg.mvvmdordoi.ui.main.dayli_game.CommentRvAdapter
import kg.mvvmdordoi.ui.main.forum.ForumViewModel
import kg.mvvmdordoi.utils.extension.gone
import kg.mvvmdordoi.utils.extension.visible
import kotlinx.android.synthetic.main.activity_comment_forum.*

class CommentForumActivity : AppCompatActivity(),CommentClickListener {

    private lateinit var viewModel: ForumViewModel
    private var idComment = 0
    private var name = ""
    lateinit var adapter: CommentRvAdapter
    lateinit var mAdView : AdView

    override fun likeClick(comment: Int, type: Int) {
        viewModel.sendLike(comment,type)
    }

    override fun likeAnswerClick(comment: Int, type: Int) {
        viewModel.sendLikeAnswer(comment,type)
    }

    override fun commentClick(name: String?, comment: Int) {
        idComment = comment
        user.text = "$name,"
        user.visible()
        if (name != null) {
            this.name = name
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_forum)
        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(ForumViewModel::class.java)
        var topic = intent.getSerializableExtra("topic1") as Topic
        title1.text = topic.title
        desc.text = topic.description
        Glide.with(this).load(topic.image).into(image)
        setupRv()
        MobileAds.initialize(activity!!)
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = intent.getStringExtra("title")

        user.setOnClickListener { user.gone() }

        viewModel.getComments(intent.getIntExtra("topic",-1))

        btn_send.setOnClickListener {

            if (user.visibility== View.GONE){
                if (message1.text.toString().isNotEmpty()) {
                    viewModel.sendComment(message1.text.toString(),intent.getIntExtra("topic",-1))
                }
            }else{
                if (message1.text.toString().isNotEmpty()) {
                    viewModel.sendAnswer(message1.text.toString(),idComment,user.text.toString())
                }
            }
            user.gone()
            message1.setText("")
        }
        viewModel.isSuccess.observe(this, Observer { viewModel.getComments(intent.getIntExtra("topic",-1)) })
        viewModel.comments.observe(this, Observer { adapter.swapData(it as ArrayList<Comment>) })

    }

    private fun setupRv() {
        val layoutManager = GridLayoutManager(this, 1)
        rv.layoutManager = layoutManager
        adapter = CommentRvAdapter(App.activity!!,this)
        rv.adapter = adapter
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

}
