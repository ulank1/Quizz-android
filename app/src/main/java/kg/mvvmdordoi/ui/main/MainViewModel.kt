package kg.mvvmdordoi.ui.main

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kg.mvvmdordoi.App
import kg.mvvmdordoi.base.BaseViewModel
import kg.mvvmdordoi.model.Product
import kg.mvvmdordoi.model.get.Category
import kg.mvvmdordoi.model.get.Rating
import kg.mvvmdordoi.network.Lang
import kg.mvvmdordoi.network.PostApi
import kg.mvvmdordoi.network.UserToken
import kg.mvvmdordoi.ui.main.profile.Shared
import kg.mvvmdordoi.utils.extension.getDateDot
import kg.mvvmdordoi.utils.extension.getDateDotDate
import kg.mvvmdordoi.utils.extension.getTodayDateDot
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

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

    fun getRating() {

        subscription.add(
            postApi.getRating(UserToken.getToken(App.activity!!).toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> hideProgress()
                        Log.e("EWW",result.body().toString())
                        if (result.isSuccessful) {
                            val rating:ArrayList<Rating> = (result.body() as ArrayList<Rating>?)!!

                            for ((i,rat)in rating.withIndex()){
                                if (rat.created_at=="all"){
                                    Shared.rating_all = rat.rating
                                    rating.removeAt(i)
                                    break
                                }
                            }
                            var created_at = rating[rating.size-1].created_at
                            if (created_at!= getTodayDateDot()){
                                var bool = true
                                var day = 0
                                while (bool){
                                    day++
                                    var date = getDateDotDate(day,Calendar.DAY_OF_YEAR,created_at)
                                    if (date!= getTodayDateDot()) {
                                        addRating(date, 0, 0, 0)
                                    }else{
                                        bool = false
                                    }
                                }
                            }

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


    private fun addRating(date: String, sum: Int, trueAnswer: Int, falseAnswer: Int) {

        subscription.add(
            postApi.addRating(
                sum,
                UserToken.getToken(App.activity!!)!!,
                date,
                trueAnswer,
                falseAnswer
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        hideProgress()
                        Log.e("EWW", result.body().toString())
                        if (result.isSuccessful) {

                        } else {
                            var error = result.errorBody()!!.string()
                            Log.e("Erroradd", error)

                        }
                    },
                    {
                        hideProgress()
                        Log.e("Erroradd", it.toString())
                    }
                )
        )
    }

}