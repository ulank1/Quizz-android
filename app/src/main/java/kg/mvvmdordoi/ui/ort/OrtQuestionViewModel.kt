package kg.mvvmdordoi.ui.ort

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
import kg.mvvmdordoi.model.get.*
import kg.mvvmdordoi.network.Lang
import kg.mvvmdordoi.network.PostApi
import kg.mvvmdordoi.network.UserToken
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class OrtQuestionViewModel() : BaseViewModel() {

    @Inject
    lateinit var postApi: PostApi
    var postList: ArrayList<Product> = ArrayList()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val tests: MutableLiveData<List<Quiz>> = MutableLiveData()
    val categories: MutableLiveData<List<OrtTest>> = MutableLiveData()
    val pay: MutableLiveData<List<Pay>> = MutableLiveData()
    val info: MutableLiveData<List<DescOrt>> = MutableLiveData()

    private var subscription: CompositeDisposable = CompositeDisposable()

    /* init {
         loadPosts()
     }*/

    override fun onCleared() {
        super.onCleared()
        subscription = CompositeDisposable()
    }

    fun getTests(type:Int,category:Int) {

        subscription.add(
            postApi.getQuizOrt(type,category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("EWW",result.toString())
                        if (result.isSuccessful) {
                            tests.value = result.body()
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

    fun getOrt() {

        subscription.add(
            postApi.getOrtCategory(Lang.get(App.activity!!).toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("EWW",result.toString())
                        if (result.isSuccessful) {
                            categories.value = result.body()
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

    fun getPay() {

        subscription.add(
            postApi.getPay(false,UserToken.getToken(App.activity!!).toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("EWW",result.toString())
                        if (result.isSuccessful) {
                            pay.value = result.body()
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
    fun getInfo(category: Int) {

        subscription.add(
            postApi.getDesc(Lang.get(App.activity!!).toString(),category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("INFO",result.body().toString())
                        if (result.isSuccessful) {
                            info.value = result.body()
                        } else {
                            var error = result.errorBody()!!.string()
                            Log.e("ErrorINFO",error)

                        }
                    },
                    { hideProgress()
                    Log.e("ErrorINFOD",it.toString())
                    }
                )
        )
    }

}