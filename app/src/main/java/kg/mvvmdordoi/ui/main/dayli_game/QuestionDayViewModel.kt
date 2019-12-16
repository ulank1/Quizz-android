package kg.mvvmdordoi.ui.main.dayli_game

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kg.mvvmdordoi.App
import kg.mvvmdordoi.base.BaseViewModel
import kg.mvvmdordoi.model.Product
import kg.mvvmdordoi.model.get.Quiz
import kg.mvvmdordoi.model.get.Quote
import kg.mvvmdordoi.network.Lang
import kg.mvvmdordoi.network.PostApi
import kg.mvvmdordoi.network.UserToken
import kg.mvvmdordoi.utils.extension.getTodayDateDot
import javax.inject.Inject
import kotlin.collections.ArrayList

class QuestionDayViewModel() : BaseViewModel() {

    @Inject
    lateinit var postApi: PostApi
    var postList: ArrayList<Product> = ArrayList()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val tests: MutableLiveData<List<Quiz>> = MutableLiveData()
    val quote: MutableLiveData<Quote> = MutableLiveData()

    private var subscription: CompositeDisposable = CompositeDisposable()

    /* init {
         loadPosts()
     }*/

    override fun onCleared() {
        super.onCleared()
        subscription = CompositeDisposable()
    }

    fun getTests(id:Int) {

        subscription.add(
            postApi.getQuiz(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("EWW",result.body().toString())
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

    fun getQuote() {

        subscription.add(
            postApi.getQuote(Lang.get(App.activity!!).toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("EWW",result.body().toString())
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

    fun getTestsGame(id:Int) {

        subscription.add(
            postApi.getQuizGame(id)
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

    fun getTestsDay() {

        subscription.add(
            postApi.getQuizDay(getTodayDateDot(), Lang.get(App.activity!!).toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("EWW",result.body().toString())
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

    fun addRating(date:String, sum:Int,trueAnswer: Int,falseAnswer: Int) {

        subscription.add(
            postApi.addRating(sum,UserToken.getToken(App.activity!!)!!, date,trueAnswer,falseAnswer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("EWW",result.toString())
                        if (result.isSuccessful) {
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

    private fun updateRating(date:String,sum:Int, id: Int,trueAnswer:Int,falseAnswer:Int) {

        subscription.add(
            postApi.updateRating(id,sum,trueAnswer,falseAnswer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("EWW",result.toString())
                        if (result.isSuccessful) {
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

    fun getRating(sum:Int) {

        subscription.add(
            postApi.getRatingByDate(getTodayDateDot(),UserToken.getToken(App.activity!!).toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("EWW",result.toString())
                        if (result.isSuccessful) {
                           if (result.body()!=null){
                               if (result.body()!!.isNotEmpty()) {
                                   updateRating(
                                       getTodayDateDot(),sum + result.body()!![0].rating,result.body()!![0].id,
                                       result.body()!![0].true_answer, result.body()!![0].false_answer)
                               }else{
                                   addRating(getTodayDateDot(),sum,0,0)
                               }
                           }else{
                                addRating(getTodayDateDot(),sum,0,0)
                           }
                            getRatingAll(sum)
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

    fun getRatingAll(sum:Int) {
        val user_id = UserToken.getToken(App.activity!!)
        if (user_id != "empty") {
            subscription.add(
                postApi.getRatingByDate("all", user_id.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { showProgress() }
                    .doOnTerminate { hideProgress() }
                    .subscribe(
                        { result ->
                            hideProgress()
                            Log.e("EWW", result.toString())
                            if (result.isSuccessful) {
                                if (result.body() != null) {
                                    if (result.body()!!.isNotEmpty()) {
                                        updateRating(
                                            "all",
                                            sum + result.body()!![0].rating,
                                            result.body()!![0].id,
                                            result.body()!![0].true_answer,
                                            result.body()!![0].false_answer
                                        )
                                    } else {
                                        addRating("all", sum, 0, 0)
                                    }
                                } else {
                                    addRating("all", sum, 0, 0)
                                }
                            } else {
                                var error = result.errorBody()!!.string()
                                Log.e("Error", error)

                            }
                        },
                        {
                            hideProgress()
                            Log.e("Error", it.toString())
                        }
                    )
            )
        }
    }



}