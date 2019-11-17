package kg.mvvmdordoi.injection.component

import dagger.Component
import kg.mvvmdordoi.injection.module.NetworkModule
import kg.mvvmdordoi.post.PostListViewModel
import kg.mvvmdordoi.post.PostViewModel
import kg.mvvmdordoi.product.ProductListViewModel
import kg.mvvmdordoi.product.ProductViewModel
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
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified PostListViewModel.
     * @param postListViewModel PostListViewModel in which to inject the dependencies
     */
    fun inject(postListViewModel: PostListViewModel)
    fun inject(productListViewModel: ProductListViewModel)
    /**
     * Injects required dependencies into the specified PostViewModel.
     * @param postViewModel PostViewModel in which to inject the dependencies
     */
    fun inject(postViewModel: PostViewModel)
    fun inject(productViewModel: ProductViewModel)
    fun inject(registerViewModel: RegisterViewModel)
    fun inject(testViewModel: TestViewModel)
    fun inject(userViewModel: UserViewModel)
    fun inject(questionViewModel: QuestionViewModel)
    fun inject(CategoryViewModel: CategoryViewModel)
    fun inject(GameViewModel: GameViewModel)
    fun inject(LoginViewModel: LoginViewModel)
    fun inject(ProfileViewModel: ProfileViewModel)
    fun inject(MainViewModel: MainViewModel)
    fun inject(InfoViewModel: InfoViewModel)
    fun inject(QuestionDayViewModel: QuestionDayViewModel)
    fun inject(UniversityViewModel: UniversityViewModel)
    fun inject(NotificationViewModel: NotificationViewModel)
    fun inject(ConfirmCodeViewModel: ConfirmCodeViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}