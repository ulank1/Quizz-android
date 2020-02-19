package kg.mvvmdordoi.ui.game.users

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.MenuItem
import kg.mvvmdordoi.R
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.model.get.Friend
import kg.mvvmdordoi.ui.game.PaginationScrollListener
import kotlinx.android.synthetic.main.activity_users1.*

class MyFriendsActivity : AppCompatActivity(),FriendListener {

    override fun onClickAddFriend(user_id:Int) {
        viewModel.postFriend(user_id)
    }

    private lateinit var viewModel: UserViewModel
    lateinit var adapter:FriendRvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_friends)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.friends)

        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(UserViewModel::class.java)
        setupRv()
        viewModel.getFriends()
        viewModel.friends.observe(this, Observer {

            adapter.swapData(it as ArrayList<Friend>)

        })

    }

    private fun setupRv() {
        val layoutManager = GridLayoutManager(this, 1)
        rv.layoutManager = layoutManager
        adapter = FriendRvAdapter(this,this)
        rv.adapter = adapter

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}
