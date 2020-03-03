package kg.mvvmdordoi.injection

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import kg.mvvmdordoi.post.PostListViewModel
import kg.mvvmdordoi.product.ProductListViewModel
import kg.mvvmdordoi.ui.auth.confirm_code.ConfirmCodeViewModel
import kg.mvvmdordoi.ui.auth.login.LoginViewModel
import kg.mvvmdordoi.ui.auth.register.RegisterViewModel
import kg.mvvmdordoi.ui.game.category.CategoryViewModel
import kg.mvvmdordoi.ui.game.users.UserViewModel
import kg.mvvmdordoi.ui.info.InfoViewModel
import kg.mvvmdordoi.ui.main.MainViewModel
import kg.mvvmdordoi.ui.main.dayli_game.QuestionDayViewModel
import kg.mvvmdordoi.ui.main.forum.ForumViewModel
import kg.mvvmdordoi.ui.main.game.GameViewModel
import kg.mvvmdordoi.ui.main.profile.ProfileViewModel
import kg.mvvmdordoi.ui.notification.NotificationViewModel
import kg.mvvmdordoi.ui.test.TestViewModel
import kg.mvvmdordoi.ui.test.test_detail.QuestionViewModel
import kg.mvvmdordoi.ui.university.UniversityViewModel

class ViewModelFactory(): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostListViewModel() as T
        }
        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel() as T
        }
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel() as T
        }
        if (modelClass.isAssignableFrom(TestViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TestViewModel() as T
        }
        if (modelClass.isAssignableFrom(QuestionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuestionViewModel() as T
        }
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel() as T
        }
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel() as T
        }
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel() as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel() as T
        }
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel() as T
        }
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel() as T
        }
        if (modelClass.isAssignableFrom(InfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InfoViewModel() as T
        }
        if (modelClass.isAssignableFrom(QuestionDayViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuestionDayViewModel() as T
        }
        if (modelClass.isAssignableFrom(UniversityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UniversityViewModel() as T
        }
        if (modelClass.isAssignableFrom(NotificationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotificationViewModel() as T
        }
        if (modelClass.isAssignableFrom(ConfirmCodeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConfirmCodeViewModel() as T
        }

        if (modelClass.isAssignableFrom(ForumViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ForumViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}