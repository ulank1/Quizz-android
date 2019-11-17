package kg.mvvmdordoi.ui.main

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kg.mvvmdordoi.App
import kg.mvvmdordoi.R
import kg.mvvmdordoi.base.BaseViewModel
import kg.mvvmdordoi.model.ApiResponse
import kg.mvvmdordoi.model.Product
import kg.mvvmdordoi.model.get.Category
import kg.mvvmdordoi.network.Lang
import kg.mvvmdordoi.network.PostApi
import kg.mvvmdordoi.network.UserToken
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class MainViewModel() : BaseViewModel() {

    @Inject
    lateinit var postApi: PostApi
    var postList: ArrayList<Product> = ArrayList()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val mainCategory: MutableLiveData<List<Category>> = MutableLiveData()
    val category: MutableLiveData<List<Category>> = MutableLiveData()
    val size: MutableLiveData<Int> = MutableLiveData()

    private var subscription: CompositeDisposable = CompositeDisposable()

    /* init {
         loadPosts()
     }*/

    override fun onCleared() {
        super.onCleared()
        subscription = CompositeDisposable()
    }

    fun getMainCategoryList() {

        subscription.add(
            postApi.getMainCategory(Lang.get(App.activity!!).toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("EWW",result.body().toString())
                        if (result.isSuccessful) {
                            mainCategory.value = result.body()
                        } else {
                            var error = result.errorBody()!!.string()
                            Log.e("Error",error)

                        }
                    },
                    { hideProgress()
                        Log.e("Error",it.toString())
                    }
                )
        )
    }

    fun getCategoryList() {

        subscription.add(
            postApi.getCategory(Lang.get(App.activity!!).toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("EWW",result.body().toString())
                        if (result.isSuccessful) {
                            category.value = result.body()
                        } else {
                            var error = result.errorBody()!!.string()
                            Log.e("Error",error)

                        }
                    },
                    { hideProgress()
                        Log.e("Error",it.toString())
                    }
                )
        )
    }


    fun getNotCount() {

        subscription.add(
            postApi.getNotificationCount(UserToken.getToken(App.activity!!).toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("EWW",result.body().toString())
                        if (result.isSuccessful) {
                            size.value = result.body()?.size
                        } else {
                            var error = result.errorBody()!!.string()
                            Log.e("Error",error)

                        }
                    },
                    { hideProgress()
                        Log.e("Error",it.toString())
                    }
                )
        )
    }


}