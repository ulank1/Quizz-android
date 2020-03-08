package kg.mvvmdordoi.ui.main.forum

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kg.mvvmdordoi.App
import kg.mvvmdordoi.base.BaseViewModel
import kg.mvvmdordoi.model.Product
import kg.mvvmdordoi.model.get.*
import kg.mvvmdordoi.network.Lang
import kg.mvvmdordoi.network.PostApi
import kg.mvvmdordoi.network.UserToken
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import kotlin.collections.ArrayList

class ForumViewModel() : BaseViewModel() {

    @Inject
    lateinit var postApi: PostApi
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val forum: MutableLiveData<ArrayList<Forum>> = MutableLiveData()
    val topic: MutableLiveData<ArrayList<Topic>> = MutableLiveData()
    val user: MutableLiveData<User> = MutableLiveData()

    val comments: MutableLiveData<List<Comment>> = MutableLiveData()
    val isSuccess: MutableLiveData<Boolean> = MutableLiveData()

    private var subscription: CompositeDisposable = CompositeDisposable()

    /* init {
         loadPosts()
     }*/

    override fun onCleared() {
        super.onCleared()
        subscription = CompositeDisposable()
    }



    fun getForum() {

        subscription.add(
            postApi.getForum(Lang.get(App.activity!!).toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("EWW33",result.body().toString())
                        if (result.isSuccessful) {
                            forum.value = result.body()
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

    fun getTopic(id:Int) {

        subscription.add(
            postApi.getTopic(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("EWW33",result.body().toString())
                        if (result.isSuccessful) {
                            topic.value = result.body()
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

    fun getMyTopic() {

        subscription.add(
            postApi.getMyTopic(UserToken.getToken(App.activity!!).toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("EWW33",result.body().toString())
                        if (result.isSuccessful) {
                            topic.value = result.body()
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

    fun deleteMyTopic(id:Int) {

        subscription.add(
            postApi.deleteMyTopic(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("Delete",result.body().toString())
                        if (result.isSuccessful) {
                            getMyTopic()
                        } else {
                            var error = result.errorBody()!!.string()
                            Log.e("Error",error)

                        }
                    },
                    { hideProgress()
                        Log.e("DeleteError",it.toString())
                    }
                )
        )
    }

    fun getTopicSearch(id:Int,text:String) {

        subscription.add(
            postApi.getTopicSearch(id,text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("EWW3dfsd3",result.body().toString())
                        if (result.isSuccessful) {
                            topic.value = result.body()
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

    fun addTopic(forum:RequestBody,title:RequestBody,avatar:MultipartBody.Part,description:RequestBody,user:RequestBody,id:Int) {

        subscription.add(
            postApi.addTopic(forum,title,avatar,description,user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        if (result.isSuccessful) {
                            Log.e("RATINGS",result.body().toString())
                            getTopic(id)
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


    fun addTopic(forum:RequestBody,title:RequestBody,description:RequestBody,user:RequestBody,id:Int) {

        subscription.add(
            postApi.addTopic(forum,title,description,user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        if (result.isSuccessful) {
                            Log.e("RATINGS",result.body().toString())
                            getTopic(id)
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

    fun getComments(id:Int) {

        subscription.add(
            postApi.getCommentForum(id, UserToken.getToken(App.activity!!)!!.toInt())
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
            postApi.postCommentForum(quiz_id,message,UserToken.getToken(App.activity!!)!!.toInt())
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

    fun sendAnswer(message:String,comment_id:Int,name:String) {

        subscription.add(
            postApi.postAnswerForum(message,UserToken.getToken(App.activity!!).toString(),comment_id,name)
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
    fun sendLike(idComment:Int,type:Int) {

        subscription.add(
            postApi.postLikeForum(UserToken.getToken(App.activity!!)!!.toInt(),idComment,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("Post Comment",result.body().toString())
                        if (result.isSuccessful) {
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
    fun sendLikeAnswer(idAnswer:Int,type:Int) {

        subscription.add(
            postApi.postLikeAnswerForum(UserToken.getToken(App.activity!!)!!.toInt(),idAnswer,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnTerminate { hideProgress() }
                .subscribe(
                    { result -> hideProgress()
                        Log.e("Post Comment",result.body().toString())
                        if (result.isSuccessful) {
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