package kg.mvvmdordoi.base

import android.app.ProgressDialog
import android.arch.lifecycle.ViewModel
import kg.mvvmdordoi.App
import kg.mvvmdordoi.injection.component.DaggerViewModelInjector
import kg.mvvmdordoi.injection.component.ViewModelInjector
import kg.mvvmdordoi.injection.module.NetworkModule
import kg.mvvmdordoi.post.PostListViewModel
import kg.mvvmdordoi.post.PostViewModel
import kg.mvvmdordoi.product.ProductListViewModel
import kg.mvvmdordoi.product.ProductViewModel
import kg.mvvmdordoi.ui.auth.confirm_code.ConfirmCodeActivity
import kg.mvvmdordoi.ui.auth.confirm_code.ConfirmCodeViewModel
import kg.mvvmdordoi.ui.auth.login.LoginViewModel
import kg.mvvmdordoi.ui.auth.register.RegisterViewModel
import kg.mvvmdordoi.ui.game.category.CategoryViewModel
import kg.mvvmdordoi.ui.game.users.UserViewModel
import kg.mvvmdordoi.ui.info.InfoViewModel
import kg.mvvmdordoi.ui.main.MainViewModel
import kg.mvvmdordoi.ui.main.dayli_game.QuestionDayViewModel
import kg.mvvmdordoi.ui.main.game.GameViewModel
import kg.mvvmdordoi.ui.main.profile.ProfileViewModel
import kg.mvvmdordoi.ui.notification.NotificationViewModel
import kg.mvvmdordoi.ui.test.TestViewModel
import kg.mvvmdordoi.ui.test.test_detail.QuestionViewModel
import kg.mvvmdordoi.ui.university.UniversityViewModel

abstract class BaseViewModel:ViewModel(){
    private val injector: ViewModelInjector = DaggerViewModelInjector
            .builder()
            .networkModule(NetworkModule)
            .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is PostListViewModel -> injector.inject(this)
            is PostViewModel -> injector.inject(this)

            is ProductListViewModel -> injector.inject(this)
            is ProductViewModel -> injector.inject(this)
            is RegisterViewModel -> injector.inject(this)
            is TestViewModel -> injector.inject(this)
            is QuestionViewModel -> injector.inject(this)
            is UserViewModel -> injector.inject(this)
            is CategoryViewModel -> injector.inject(this)
            is GameViewModel -> injector.inject(this)
            is LoginViewModel -> injector.inject(this)
            is ProfileViewModel -> injector.inject(this)
            is InfoViewModel -> injector.inject(this)
            is MainViewModel -> injector.inject(this)
            is QuestionDayViewModel -> injector.inject(this)
            is UniversityViewModel -> injector.inject(this)
            is NotificationViewModel -> injector.inject(this)
            is ConfirmCodeViewModel -> injector.inject(this)
        }
    }

    private var progressBar: ProgressDialog? = null



    fun showProgress() {
        if (progressBar!=null){
            progressBar!!.dismiss()
            progressBar = null
        }
        progressBar = ProgressDialog(App.activity)
        progressBar!!.show()
    }

    fun hideProgress() {
        if (progressBar != null /*&& progressBar!!.isShowing*/) {
            progressBar!!.dismiss()
        }
        progressBar = null
    }
}