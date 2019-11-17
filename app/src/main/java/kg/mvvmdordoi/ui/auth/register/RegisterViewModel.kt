package kg.mvvmdordoi.ui.auth.register

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kg.mvvmdordoi.App
import kg.mvvmdordoi.base.BaseViewModel
import kg.mvvmdordoi.fcm.FCMTokenUtils
import kg.mvvmdordoi.model.Product
import kg.mvvmdordoi.model.get.User
import kg.mvvmdordoi.network.PostApi
import kg.mvvmdordoi.network.UserToken
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class RegisterViewModel() : BaseViewModel() {

    @Inject
    lateinit var postApi: PostApi
    var postList: ArrayList<Product> = ArrayList()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val isExist: MutableLiveData<Boolean> = MutableLiveData()
    val user: MutableLiveData<User> = MutableLiveData()

    private var subscription: CompositeDisposable = CompositeDisposable()

    /* init {
         loadPosts()
     }*/

    override fun onCleared() {
        super.onCleared()
        subscription = CompositeDisposable()
    }

    fun isExistLogin(login: String, name: String) {

        subscription.add(
            postApi.isExistUserReg(login, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result ->
                        hideProgress()
                        Log.e("EWW", result.toString())
                        isExist.value = result.body()?.success

                    },
                    {
                        hideProgress()
                        Log.e("Error", it.toString())
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
                    { result ->
                        hideProgress()
                        Log.e("EWW", result.toString())
                        user.value = result.body()

                    },
                    {
                        hideProgress()
                        Log.e("Error", it.toString())
                    }
                )
        )
    }

    fun register(
        login: RequestBody,
        password: RequestBody,
        name: RequestBody,
        image: MultipartBody.Part,
        birthDate: RequestBody,
        regions: RequestBody,
        is_kg: RequestBody,
        is_ru: RequestBody
    ) {

        subscription.add(
            postApi.addUser(login, name, password, birthDate, regions, is_kg, is_ru, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result ->
                        hideProgress()

                        Log.e("RESULT", result.toString())
                        UserToken.saveToken(result.id.toString(), App.activity!!)
                        postDevice(result.id)
                    },
                    {
                        hideProgress()
                        Log.e("Error", it.toString())
                    }
                )
        )
    }

    fun register(
        login: RequestBody,
        password: RequestBody,
        name: RequestBody,
        birthDate: RequestBody,
        regions: RequestBody,
        is_kg: RequestBody,
        is_ru: RequestBody
    ) {

        subscription.add(
            postApi.addUser(login, name, password, birthDate, regions, is_kg, is_ru)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result ->
                        hideProgress()

                        Log.e("RESULT", result.toString())
                        UserToken.saveToken(result.id.toString(), App.activity!!)
                        postDevice(result.id)
                    },
                    {
                        hideProgress()
                        Log.e("Error", it.toString())
                    }
                )
        )
    }

    private fun postDevice(user_id: Int) {
        FCMTokenUtils.deleteToken(App.activity!!)
        subscription.add(
            postApi.postDevice(FCMTokenUtils.getTokenFromPrefs(App.activity!!), user_id, "android")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result ->
                        hideProgress()
                        Log.e("EWW", result.toString())
                        App.activity!!.setResult(Activity.RESULT_OK)
                        App.activity!!.finish()
                    },
                    {
                        hideProgress()
                        Log.e("Error", it.toString())
                    }
                )
        )
    }

    fun putProfile(
        login: RequestBody,
        name: RequestBody,
        image: MultipartBody.Part,
        birthDate: RequestBody,
        regions: RequestBody,
        is_kg: RequestBody,
        is_ru: RequestBody
    ) {

        subscription.add(
            postApi.putUser(
                UserToken.getToken(App.activity!!).toString(),
                login,
                name,
                birthDate,
                regions,
                is_kg,
                is_ru,
                image
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result ->
                        hideProgress()

                        Log.e("RESULT", result.toString())
                        App.activity?.finish()
                    },
                    {
                        hideProgress()
                        Log.e("Error", it.toString())
                    }
                )
        )
    }

    fun putProfile(
        login: RequestBody,
        name: RequestBody,
        birthDate: RequestBody,
        regions: RequestBody,
        is_kg: RequestBody,
        is_ru: RequestBody
    ) {

        subscription.add(
            postApi.putUser(
                UserToken.getToken(App.activity!!).toString(),
                login,
                name,
                birthDate,
                regions,
                is_kg,
                is_ru
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result ->
                        hideProgress()

                        Log.e("RESULT", result.toString())
                        App.activity?.finish()
                    },
                    {
                        hideProgress()
                        Log.e("Error", it.toString())
                    }
                )
        )
    }

}