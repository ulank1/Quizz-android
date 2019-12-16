package kg.mvvmdordoi.ui.game.users

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import kg.mvvmdordoi.R
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.model.get.User
import com.ferfalk.simplesearchview.SimpleSearchView
import kg.mvvmdordoi.App
import kg.mvvmdordoi.ui.game.category.CategoryActivity
import android.view.*
import kg.mvvmdordoi.network.UserToken
import kg.mvvmdordoi.utils.extension.hideKeyboard
import kg.mvvmdordoi.utils.extension.hideKeyboardFrom
import kotlinx.android.synthetic.main.activity_users1.*


class UsersActivity : AppCompatActivity(),TextWatcher {
    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        viewModel.getUsers(s.toString())
    }

    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserRvAdapter
    private  lateinit var users:ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users1)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.choose_user)



        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(UserViewModel::class.java)
        setupRv()

        viewModel.getUsers()
        viewModel.users.observe(this, Observer {
            Log.e("sdf",it.toString())
            if (it != null) {

                users = it as ArrayList<User>

                for ((i,user) in users.withIndex()){

                    if (user.id.toString()==UserToken.getToken(this)){
                        users.removeAt(i)
                        break
                    }

                }
                    adapter.swapData(users)
            }
        }
        )

        search.addTextChangedListener(this)

        share.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody = "https://play.google.com/store/apps/details?id=kg.mvvmdordoi"
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here")
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sharingIntent, "Share via")) }

        random.setOnClickListener {

            var rand = Math.random()*Math.min(users.size,100)

            var item = users[rand.toInt()]

            Shared.id = item.id
            Shared.name_outer = item.name
            Shared.avatar_outer = item.avatar
            val intent = Intent(App.activity, CategoryActivity::class.java)
            intent.putExtra("id",item.id)
            App.activity!!.startActivity(intent)

        }
        hideKeyboardFrom(this,search)
    }
    private fun setupRv(){
        Log.e("USER_ACTIVITY","setupRv")
        val layoutManager = GridLayoutManager(this,1)
        rv.layoutManager = layoutManager
        adapter = UserRvAdapter(this!!)
        rv.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        App.activity = this
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId){
            android.R.id.home-> finish()
        }

        return true
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== Activity.RESULT_OK&&requestCode==101){
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}
