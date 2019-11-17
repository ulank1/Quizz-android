package kg.mvvmdordoi.ui.test.test_detail

import android.app.Activity.RESULT_OK
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
import kg.mvvmdordoi.model.get.Quiz
import kg.mvvmdordoi.model.get.Test
import kg.mvvmdordoi.network.Lang
import kg.mvvmdordoi.network.PostApi
import kg.mvvmdordoi.network.UserToken
import kg.mvvmdordoi.ui.game.users.Shared
import kg.mvvmdordoi.utils.extension.getTodayDate
import kg.mvvmdordoi.utils.extension.getTodayDateDot
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class QuestionViewModel() : BaseViewModel() {

    @Inject
    lateinit var postApi: PostApi
    var postList: ArrayList<Product> = ArrayList()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val tests: MutableLiveData<List<Quiz>> = MutableLiveData()

    private var subscription: CompositeDisposable = CompositeDisposable()

    /* init {
         loadPosts()
     }*/

    override fun onCleared() {
        super.onCleared()
        subscription = CompositeDisposable()
    }

    fun getTests(id: Int) {

        subscription.add(
            postApi.getQuiz(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result ->
                        hideProgress()
                        Log.e("EWW", result.body().toString())
                        if (result.isSuccessful) {
                            tests.value = result.body()
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

    fun getTestsGame(id: Int) {

        subscription.add(
            postApi.getQuizGame(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result ->
                        hideProgress()
                        Log.e("EWW", result.toString())
                        if (result.isSuccessful) {
                            tests.value = result.body()
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
    fun getTestsGameInvite(quiz: String) {

        subscription.add(
            postApi.getQuizGameInvite(quiz)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result ->
                        hideProgress()
                        Log.e("EWW", result.toString())
                        if (result.isSuccessful) {
                            tests.value = result.body()
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



    fun getTestsDay() {

        subscription.add(
            postApi.getQuizDay(getTodayDateDot(), Lang.get(App.activity!!).toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result ->
                        hideProgress()
                        Log.e("EWW", result.toString())
                        if (result.isSuccessful) {
                            tests.value = result.body()
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


    fun postGameCache(trues:Int,ids:String,type:Int,category:String) {
        Log.e("TOKEN",UserToken.getToken(App.activity!!)!!)
        subscription.add(
            postApi.postGameOuter(UserToken.getToken(App.activity!!)!!,Shared.id,trues,-1,category,ids)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result ->
                        hideProgress()
                        Log.e("EWW", result.toString())
                        if (result.isSuccessful) {

                            if (type==1){
                                App.activity!!.setResult(RESULT_OK)
                                App.activity!!.finish()
                            }

                        } else {
                            var error = result.errorBody()!!.string()
                            Log.e("ErrorPOSTCACHE", error)

                        }
                    },
                    {
                        hideProgress()
                        Log.e("ErrorPOSTCACHE", it.toString())
                    }
                )
        )
    }

    fun putGameCache(trues:Int,id: Int) {

        subscription.add(
            postApi.putGameOuter(id,trues)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result ->
                        hideProgress()
                        Log.e("EWWPUTGAME", result.toString())
                        if (result.isSuccessful) {
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

    private fun addRating(date: String, sum: Int, trueAnswer: Int, falseAnswer: Int,type: Int) {

        subscription.add(
            postApi.addRating(
                sum,
                UserToken.getToken(App.activity!!)!!.toInt(),
                date,
                trueAnswer,
                falseAnswer
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
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

    private fun updateRating(date: String, sum: Int, id: Int, trueAnswer: Int, falseAnswer: Int,type:Int) {
        Log.e("FFFFF",date+" "+sum+" "+id+" "+trueAnswer+" "+falseAnswer)
        subscription.add(
            postApi.updateRating(
                id,
                sum,
                trueAnswer,
                falseAnswer
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result ->
                        hideProgress()
                        Log.e("EWW", result.toString())
                        if (result.isSuccessful) {

                        } else {
                            var error = result.errorBody()!!.string()
                            Log.e("Errorput", error)

                        }
                    },
                    {
                        hideProgress()
                        Log.e("Errorput", it.toString())
                    }
                )
        )
    }

    fun getRating(sum: Int, trueAnswer: Int, falseAnswer: Int) {
        val user_id = UserToken.getToken(App.activity!!)
        if (user_id != "empty") {
            subscription.add(
                postApi.getRatingByDate(getTodayDateDot(), user_id.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { showProgress() }
                    .doOnTerminate { hideProgress() }
                    .subscribe(
                        { result ->
                            hideProgress()
                            Log.e("EWW", result.body().toString())
                            if (result.isSuccessful) {

                                getRatingAll(sum,trueAnswer,falseAnswer)
                                if (result.body() != null) {
                                    if (result.body()!!.isNotEmpty()) {
                                        updateRating(
                                            getTodayDateDot(),
                                            sum + result.body()!![0].rating,
                                            result.body()!![0].id,
                                            result.body()!![0].true_answer + trueAnswer,
                                            result.body()!![0].false_answer + falseAnswer,
                                            2
                                        )
                                    } else {
                                        addRating(
                                            getTodayDateDot(),
                                            sum,
                                            trueAnswer,
                                            falseAnswer,
                                            2
                                        )
                                    }
                                } else {
                                    addRating(
                                        getTodayDateDot(),
                                        sum,
                                         trueAnswer,
                                        falseAnswer,
                                        2
                                    )
                                }
                            } else {
                                var error = result.errorBody()!!.string()
                                Log.e("Errorget", error)

                            }
                        },
                        {
                            hideProgress()
                            Log.e("Errorget", it.toString())
                        }
                    )
            )
        } else {
            App.activity!!.finish()
        }
    }


    fun getRatingAll(sum: Int,trueAnswer: Int,falseAnswer: Int) {
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
                            Log.e("EWW", result.body().toString())
                            if (result.isSuccessful) {
                                if (result.body() != null) {
                                    if (result.body()!!.isNotEmpty()) {
                                        updateRating(
                                            "all",
                                            result.body()!![0].rating+sum,
                                            result.body()!![0].id,
                                            result.body()!![0].true_answer+trueAnswer,
                                            result.body()!![0].false_answer+falseAnswer,
                                            1
                                        )
                                    } else {
                                        addRating(
                                            "all",
                                            sum,
                                            trueAnswer,
                                            falseAnswer,
                                            1
                                        )
                                    }
                                } else {
                                    addRating(
                                        "all",
                                        sum,
                                       trueAnswer,
                                        falseAnswer,
                                        1
                                    )
                                }
                            } else {
                                var error = result.errorBody()!!.string()
                                Log.e("Errorgetall", error)

                            }
                        },
                        {
                            hideProgress()
                            Log.e("Errorgetall", it.toString())
                        }
                    )
            )
        } else {
            App.activity!!.finish()
        }
    }

}