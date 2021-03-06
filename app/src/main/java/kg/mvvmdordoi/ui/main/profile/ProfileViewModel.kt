package kg.mvvmdordoi.ui.main.profile

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kg.mvvmdordoi.App
import kg.mvvmdordoi.base.BaseViewModel
import kg.mvvmdordoi.model.Product
import kg.mvvmdordoi.model.get.*
import kg.mvvmdordoi.network.PostApi
import kg.mvvmdordoi.network.UserToken
import javax.inject.Inject
import kotlin.collections.ArrayList

class ProfileViewModel() : BaseViewModel() {

    @Inject
    lateinit var postApi: PostApi
    var postList: ArrayList<Product> = ArrayList()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val ratings: MutableLiveData<List<Rating>> = MutableLiveData()
    val user: MutableLiveData<User> = MutableLiveData()
    val game: MutableLiveData<List<GameOuter>> = MutableLiveData()
    val ratingAll: MutableLiveData<RatingAll> = MutableLiveData()
    val ratingAllOf: MutableLiveData<RatingPagination> = MutableLiveData()

    private var subscription: CompositeDisposable = CompositeDisposable()

    /* init {
         loadPosts()
     }*/

    override fun onCleared() {
        super.onCleared()
        subscription = CompositeDisposable()
    }

    fun getRating() {

        subscription.add(
            postApi.getRating(UserToken.getToken(App.activity!!).toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        if (result.isSuccessful) {
                            Log.e("RATINGS",result.body().toString())

                            val rating:ArrayList<Rating> = (result.body() as ArrayList<Rating>?)!!

                            for ((i,rat)in rating.withIndex()){
                                if (rat.created_at=="all"){
                                    Shared.rating_all = rat.rating
                                    rating.removeAt(i)
                                    break
                                }
                            }

                            Log.e("RRRR",rating.toString())
//                            rating.reverse()
                            ratings.value = rating
                        }else{
                            Log.e("ERROR", result.errorBody()?.string())
                        }
                    },
                    { hideProgress()
                        Log.e("Errdasdasor",it.toString())
                    }
                )
        )
    }

    fun getRating(id:String) {

        subscription.add(
            postApi.getRating(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        if (result.isSuccessful) {
                            Log.e("RATINGS",result.body().toString())

                            val rating:ArrayList<Rating> = (result.body() as ArrayList<Rating>?)!!

                            for ((i,rat)in rating.withIndex()){
                                if (rat.created_at=="all"){
                                    Shared.rating_all = rat.rating
                                    rating.removeAt(i)
                                    break
                                }
                            }

                            Log.e("RRRR",rating.toString())
//                            rating.reverse()
                            ratings.value = rating
                        }else{
                            Log.e("ERROR", result.errorBody()?.string())
                        }
                    },
                    { hideProgress()
                        Log.e("Errdasdasor",it.toString())
                    }
                )
        )
    }


    fun getRatingAll() {

        subscription.add(
            postApi.getRatingAll(UserToken.getToken(App.activity!!).toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        if (result.isSuccessful) {
                            Log.e("RATINGS",result.body().toString())
                            ratingAll.value = result.body()!!
                        }else{
                            Log.e("ERROR", result.errorBody()?.string())
                        }
                    },
                    { hideProgress()
                        Log.e("Errdasdasor",it.toString())
                    }
                )
        )
    }

    fun getRatingAllOf(page: String) {

        subscription.add(
            postApi.getRatingAllOf(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        if (result.isSuccessful) {
                            Log.e("RATINGSGHJKL",result.body().toString())
                            ratingAllOf.value = result.body()!!
                        }else{
                            Log.e("ERROR", result.errorBody()?.string())
                        }
                    },
                    { hideProgress()
                        Log.e("Errdasdasor",it.toString())
                    }
                )
        )
    }


    fun getUser() {

        subscription.add(
            postApi.getUser(UserToken.getToken(App.activity!!).toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        if (result.isSuccessful) {
                            Log.e("RATINGS",result.body().toString())
                            user.value = result.body()!!
                        }else{
                            Log.e("ERROR", result.errorBody()?.string())
                        }
                    },
                    { hideProgress()
                        Log.e("Errdasdasor",it.toString())
                    }
                )
        )
    }

    fun getUser(id:String) {

        subscription.add(
            postApi.getUser(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        if (result.isSuccessful) {
                            Log.e("RATINGS",result.body().toString())
                            user.value = result.body()!!
                        }else{
                            Log.e("ERROR", result.errorBody()?.string())
                        }
                    },
                    { hideProgress()
                        Log.e("Errdasdasor",it.toString())
                    }
                )
        )
    }

    fun getGameOuter() {

        subscription.add(
            postApi.getGameOuter(UserToken.getToken(App.activity!!)!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("EWW",result.body().toString())
                        if (result.isSuccessful) {
                            game.value = result.body()
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