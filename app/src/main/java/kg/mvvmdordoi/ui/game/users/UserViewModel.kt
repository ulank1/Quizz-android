package kg.mvvmdordoi.ui.game.users

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
import kg.mvvmdordoi.model.get.Friend
import kg.mvvmdordoi.model.get.Test
import kg.mvvmdordoi.model.get.User
import kg.mvvmdordoi.model.get.UserDuel
import kg.mvvmdordoi.network.PostApi
import kg.mvvmdordoi.network.UserToken
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class UserViewModel() : BaseViewModel() {

    @Inject
    lateinit var postApi: PostApi
    var postList: ArrayList<Product> = ArrayList()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val users: MutableLiveData<List<User>> = MutableLiveData()
    val friends: MutableLiveData<List<Friend>> = MutableLiveData()
    val usersDuel: MutableLiveData<UserDuel> = MutableLiveData()

    private var subscription: CompositeDisposable = CompositeDisposable()

    /* init {
         loadPosts()
     }*/

    override fun onCleared() {
        super.onCleared()
        subscription = CompositeDisposable()
    }

    fun getUsers() {

        subscription.add(
            postApi.getUsersDuel(UsersActivity.page, UserToken.getToken(App.activity!!)!!.toInt())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result ->
                        hideProgress()
                        Log.e("EWW", result.toString())
                        if (result.isSuccessful) {
                            usersDuel.value = result.body()
                        } else {
                            var error = result.errorBody()!!.string()
                            Log.e("Error4", error)

                        }
                    },
                    {
                        hideProgress()
                        Log.e("Error5", it.toString())
                    }
                )
        )
    }

    fun getUsers(text: String) {

        subscription.add(
            postApi.getUsersSearch1(text, UserToken.getToken(App.activity!!)!!.toInt())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result ->
                        hideProgress()
                        Log.e("EWW", result.toString())
                        if (result.isSuccessful) {
                            users.value = result.body()
                        } else {
                            var error = result.errorBody()!!.string()
                            Log.e("Error14", error)

                        }
                    },
                    {
                        hideProgress()
                        Log.e("Error51", it.toString())
                    }
                )
        )
    }

    fun postFriend(userId: Int) {
        Log.e("IDDDD", UserToken.getToken(App.activity!!)!!)
        subscription.add(
            postApi.addFriend(UserToken.getToken(App.activity!!)!!.toInt(), userId, true)
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
                            Log.e("Error14", error)

                        }
                    },
                    {
                        hideProgress()
                        Log.e("Error5e", it.toString())
                    }
                )
        )
    }

    fun getFriends() {
        subscription.add(
            postApi.getFriend(UserToken.getToken(App.activity!!)!!.toInt())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result ->
                        hideProgress()
                        Log.e("EWW", result.toString())
                        if (result.isSuccessful) {
                            var results = result.body()
                            if (results != null) {

                                for (r in results) {
                                    r.friend.friend = true
                                }
                                friends.value = results

                            }

                        } else {
                            var error = result.errorBody()!!.string()
                            Log.e("Error14", error)

                        }
                    },
                    {
                        hideProgress()
                        Log.e("Error51", it.toString())
                    }
                )
        )
    }

}