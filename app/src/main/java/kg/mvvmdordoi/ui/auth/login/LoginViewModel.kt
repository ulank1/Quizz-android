package kg.mvvmdordoi.ui.auth.login

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
import kg.mvvmdordoi.fcm.FCMTokenUtils
import kg.mvvmdordoi.model.ApiResponse
import kg.mvvmdordoi.model.Product
import kg.mvvmdordoi.model.get.User
import kg.mvvmdordoi.network.PostApi
import kg.mvvmdordoi.network.UserToken
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class LoginViewModel() : BaseViewModel() {

    @Inject
    lateinit var postApi: PostApi
    var postList: ArrayList<Product> = ArrayList()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val isExist: MutableLiveData<List<User>> = MutableLiveData()

    private var subscription: CompositeDisposable = CompositeDisposable()

    /* init {
         loadPosts()
     }*/

    override fun onCleared() {
        super.onCleared()
        subscription = CompositeDisposable()
    }

    fun isExistLogin(login:String) {

        subscription.add(
            postApi.isExistUser(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("LOGIN",result.body().toString())
                        if (result.isSuccessful) {
                            isExist.value = result.body()
                        }

                    },
                    { hideProgress()
                    Log.e("ErrorLOGIN",it.toString())
                    }
                )
        )
    }

    fun register(login:RequestBody,password:RequestBody,name:RequestBody,image:MultipartBody.Part) {

      /*  subscription.add(
            postApi.addUser(login,name,password,image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()

                        Log.e("RESULT",result.toString())
                        UserToken.saveToken(result.id.toString(),App.activity!!)
                        App.activity!!.finish()
                    },
                    { hideProgress()
                        Log.e("Error",it.toString())
                    }
                )
        )*/
    }
    fun postDevice(user_id:Int) {
        FCMTokenUtils.deleteToken(App.activity!!)
        subscription.add(
            postApi.postDevice(FCMTokenUtils.getTokenFromPrefs(App.activity!!),user_id,"android")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("EWWDEVICE",result.toString())
                        App.activity!!.setResult(RESULT_OK)
                        App.activity!!.finish()
                    },
                    { hideProgress()
                        Log.e("ErrorDEVICE",it.toString())
                    }
                )
        )
    }

    fun putPassword(user_id:Int,password:String) {

        Log.e("DDSFGSDSDSDDS", "$password $user_id")

        subscription.add(
            postApi.putUser(user_id,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("EWWPassword",result.toString())
                        App.activity!!.setResult(RESULT_OK)
                        App.activity!!.finish()
                    },
                    { hideProgress()
                        Log.e("ErrorDEVICE",it.toString())
                    }
                )
        )
    }

}