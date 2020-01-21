package kg.mvvmdordoi.ui.main.dayli_game

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kg.mvvmdordoi.App
import kg.mvvmdordoi.base.BaseViewModel
import kg.mvvmdordoi.model.Product
import kg.mvvmdordoi.model.get.Comment
import kg.mvvmdordoi.model.get.DayQuiz
import kg.mvvmdordoi.model.get.Quiz
import kg.mvvmdordoi.model.get.Quote
import kg.mvvmdordoi.network.Lang
import kg.mvvmdordoi.network.PostApi
import kg.mvvmdordoi.network.UserToken
import kg.mvvmdordoi.utils.extension.getTodayDate
import kg.mvvmdordoi.utils.extension.getTodayDateDot
import javax.inject.Inject
import kotlin.collections.ArrayList

class QuestionDayViewModel() : BaseViewModel() {

    @Inject
    lateinit var postApi: PostApi
    var postList: ArrayList<Product> = ArrayList()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val tests: MutableLiveData<List<DayQuiz>> = MutableLiveData()
    val comments: MutableLiveData<List<Comment>> = MutableLiveData()
    val quote: MutableLiveData<Quote> = MutableLiveData()
    val isSuccess: MutableLiveData<Boolean> = MutableLiveData()


    private var subscription: CompositeDisposable = CompositeDisposable()

    /* init {
         loadPosts()
     }*/

    override fun onCleared() {
        super.onCleared()
        subscription = CompositeDisposable()
    }



    fun getQuote() {

        subscription.add(
            postApi.getQuote(Lang.get(App.activity!!).toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("EWW33",result.body().toString())
                        if (result.isSuccessful) {
                            quote.value = result.body()
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

    fun getTestsDay() {

        subscription.add(
            postApi.getQuizDay("2020-01-18", Lang.get(App.activity!!).toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("EWW333",result.body().toString())
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
    fun getComments(id:Int) {

        subscription.add(
            postApi.getCommentQuiz(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("EWW",result.body().toString())
                        if (result.isSuccessful) {
                            comments.value = result.body()
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
    fun sendComment(message:String,quiz_id:Int) {
        Log.e("User",UserToken.getToken(App.activity!!).toString())
        subscription.add(
            postApi.postComment(quiz_id,message,UserToken.getToken(App.activity!!)!!.toInt())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("Post Comment",result.body().toString())
                        if (result.isSuccessful) {
                            isSuccess.value = true
                        } else {
                            var error = result.errorBody()!!.string()
                            Log.e("Error",error)

                        }
                    },
                    { hideProgress()
                        Log.e("ASError",it.toString())
                    }
                )
        )
    }

    fun sendAnswer(message:String,quiz_id:Int,comment_id:Int,name:String) {

        subscription.add(
            postApi.postAnswer(quiz_id,message,UserToken.getToken(App.activity!!).toString(),comment_id,name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("Post Comment",result.body().toString())
                        if (result.isSuccessful) {
                            isSuccess.value = true
                        } else {
                            var error = result.errorBody()!!.string()
                            Log.e("FFError",error)

                        }
                    },
                    { hideProgress()
                        Log.e("Error",it.toString())
                    }
                )
        )
    }

}