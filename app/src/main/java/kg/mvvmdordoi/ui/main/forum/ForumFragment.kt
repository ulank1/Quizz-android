package kg.mvvmdordoi.ui.main.forum

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kg.mvvmdordoi.App

import kg.mvvmdordoi.R
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.ui.main.dayli_game.CommentRvAdapter
import kotlinx.android.synthetic.main.activity_question_day.*

class ForumFragment : Fragment() {

    lateinit var adapter:ForumRvAdapter

    private lateinit var viewModel: ForumViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forum_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,ViewModelFactory()).get(ForumViewModel::class.java)
        setupRv()
        viewModel.getForum()
        viewModel.forum.observe(this, Observer {

            if (it != null) {
                adapter.swapData(it)
            }

        })
    }

    private fun setupRv() {
        val layoutManager = GridLayoutManager(context, 2)
        rv.layoutManager = layoutManager
        adapter = ForumRvAdapter(App.activity!!)
        rv.adapter = adapter
    }


}
