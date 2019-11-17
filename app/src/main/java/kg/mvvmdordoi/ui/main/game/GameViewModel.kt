package kg.mvvmdordoi.ui.main.game

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
import kg.mvvmdordoi.model.get.Game
import kg.mvvmdordoi.model.get.GameOuter
import kg.mvvmdordoi.model.get.Test
import kg.mvvmdordoi.model.get.User
import kg.mvvmdordoi.network.PostApi
import kg.mvvmdordoi.network.UserToken
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class GameViewModel() : BaseViewModel() {

    @Inject
    lateinit var postApi: PostApi
    var postList: ArrayList<Product> = ArrayList()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val users: MutableLiveData<List<GameOuter>> = MutableLiveData()

    private var subscription: CompositeDisposable = CompositeDisposable()

    /* init {
         loadPosts()
     }*/

    override fun onCleared() {
        super.onCleared()
        subscription = CompositeDisposable()
    }

    fun getGame() {

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
                            users.value = result.body()
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