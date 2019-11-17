package kg.mvvmdordoi.product

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kg.mvvmdordoi.R
import kg.mvvmdordoi.base.BaseViewModel
import kg.mvvmdordoi.model.ApiResponse
import kg.mvvmdordoi.model.Product
import kg.mvvmdordoi.network.PostApi
import retrofit2.Response
import javax.inject.Inject

class ProductListViewModel() : BaseViewModel() {

    @Inject
    lateinit var postApi: PostApi
    val postListAdapter: ProductListAdapter = ProductListAdapter()
    var postList: ArrayList<Product> = ArrayList()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPosts(1) }

    private var subscription: CompositeDisposable = CompositeDisposable()

    /* init {
         loadPosts()
     }*/

    override fun onCleared() {
        super.onCleared()
        subscription = CompositeDisposable()
    }

    fun loadPosts(page: Int) {
        if (page == 1) {
            postList = ArrayList()
            postListAdapter.updatePostList(this.postList)
        }
        subscription.add(
            postApi.getProducts("", page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrievePostListStart() }
                .doOnTerminate { onRetrievePostListFinish() }
                .subscribe(
                    { result -> onRetrievePostListSuccess(result.body()!!.results!!) },
                    { onRetrievePostListError() }
                )
        )
    }

    private fun onRetrievePostListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePostListSuccess(postList: ArrayList<Product>) {
        postListAdapter.addList(postList)
    }

    private fun onRetrievePostListError() {
        errorMessage.value = R.string.post_error
    }
}