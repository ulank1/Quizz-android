package kg.mvvmdordoi.ui.main.rating_all

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kg.mvvmdordoi.R
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.model.get.RatingWithUser
import kg.mvvmdordoi.ui.game.PaginationScrollListener
import kg.mvvmdordoi.ui.main.profile.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_rating_all.*
import kotlinx.android.synthetic.main.fragment_rating_all.rv

class RatingFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var adapter: RatingRvAdapter
    private var myPosition = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rating_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(ProfileViewModel::class.java)

        setupRv()

        viewModel.ratingAll.observe(this, Observer {
            if (it != null) {

                var ratings = it.first

                if (ratings.size==1){
                    name1.text = ratings[0].user.name
                }else if (ratings.size==2){
                    name1.text = ratings[0].user.name
                    name2.text = ratings[1].user.name
                }else if (ratings.size>2){
                    name1.text = ratings[0].user.name
                    name2.text = ratings[1].user.name
                    name3.text = ratings[2].user.name
                }

                var my_rating = it.me[0]
                Log.e("Rating",my_rating.toString())
                rating.text = (it.size).toString()
                myPosition = it.size
                point.text = my_rating.rating.toString()
                name.text = my_rating.user.name
            }
        })
        viewModel.ratingAllOf.observe(this, Observer {
            if (it != null) {
                isLastPage = it.next.isNullOrEmpty()
                page = it.next.toString()
                isLoading = false
                adapter.swapData(it.user,myPosition)
            }

        })
        viewModel.getRatingAll()
        isLoading = true
        viewModel.getRatingAllOf(page)
    }

    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    var page="1"
    private fun setupRv(){
        val layoutManager = GridLayoutManager(context,1)
        rv.layoutManager = layoutManager
        adapter = RatingRvAdapter(context!!)
        rv.adapter = adapter
        rv.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                //you have to call loadmore items to get more data
                isLoading = true
                viewModel.getRatingAllOf(page)
            }
        })
    }
}