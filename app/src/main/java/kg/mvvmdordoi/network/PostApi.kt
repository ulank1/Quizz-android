package kg.mvvmdordoi.network

import io.reactivex.Observable
import kg.mvvmdordoi.App
import kg.mvvmdordoi.model.ApiResponse
import kg.mvvmdordoi.model.Product
import kg.mvvmdordoi.model.Post
import kg.mvvmdordoi.model.get.*
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

/**
 * The interface which provides methods to get result of webservices
 */
interface PostApi {
    /**
     * Get the list of the pots from the API
     */
    @GET("/posts")
    fun getPosts(): Observable<List<Post>>

    @GET("index/products/last")
    fun getProducts(@Query("category_id") category_id: String, @Query("page") page: Int): Observable<Response<ApiResponse<Product>>>

    @GET("users/")
    fun isExistUser(@Query("login") login: String): Observable<Response<List<User>>>

    @GET("is_exist/")
    fun isExistUserReg(@Query("login") login: String,@Query("name") name: String): Observable<Response<IsExist>>

    @GET("users/")
    fun getUsers(): Observable<Response<List<User>>>

    @GET("users/")
    fun getUsersSearch(@Query("search")text:String): Observable<Response<List<User>>>

    @GET("users/{id}")
    fun getUser(@Path("id") id:String): Observable<Response<User>>

    @Multipart
    @POST("users/")
    fun addUser(
        @Part("login") login: RequestBody,
        @Part("name") name: RequestBody,
        @Part("password") password: RequestBody,
        @Part("birth_date") birth_date: RequestBody,
        @Part("place") region: RequestBody,
        @Part("is_kg") is_kg: RequestBody,
        @Part("is_ru") is_ru: RequestBody,
        @Part avatar: MultipartBody.Part

    ): Observable<User>
    @Multipart
    @POST("users/")
    fun addUser(
        @Part("login") login: RequestBody,
        @Part("name") name: RequestBody,
        @Part("password") password: RequestBody,
        @Part("birth_date") birth_date: RequestBody,
        @Part("place") region: RequestBody,
        @Part("is_kg") is_kg: RequestBody,
        @Part("is_ru") is_ru: RequestBody

    ): Observable<User>


    @Multipart
    @PATCH("users/{id}/")
    fun putUser(
        @Path("id") id:String,
        @Part("login") login: RequestBody,
        @Part("name") name: RequestBody,
        @Part("birth_date") birth_date: RequestBody,
        @Part("place") region: RequestBody,
        @Part("is_kg") is_kg: RequestBody,
        @Part("is_ru") is_ru: RequestBody

    ): Observable<User>

    @Multipart
    @PATCH("users/{id}/")
    fun putUser(
        @Path("id") id:String,
        @Part("login") login: RequestBody,
        @Part("name") name: RequestBody,
        @Part("birth_date") birth_date: RequestBody,
        @Part("place") region: RequestBody,
        @Part("is_kg") is_kg: RequestBody,
        @Part("is_ru") is_ru: RequestBody,
        @Part avatar: MultipartBody.Part

    ): Observable<User>


    @GET("tests/")
    fun getTest(@Query("category") category: Int,@Query("lang") lang: String): Observable<Response<List<Test>>>

    @GET("game_lang/")
    fun getGames(@Query("lang") lang: String): Observable<Response<List<Game>>>

    @GET("notification/")
    fun getNotification(@Query("user") user: String): Observable<Response<List<Notification>>>

    @GET("notification_count/")
    fun getNotificationCount(@Query("user") user: String): Observable<Response<NotificationCount>>

    @GET("university/")
    fun getUniversty(@Query("lang") lang: String): Observable<Response<List<University>>>

    @GET("info/")
    fun getInfo(@Query("main_category") main_category:Int,@Query("lang") lang: String): Observable<Response<List<Info>>>

    @GET("news/")
    fun getNews(@Query("lang") lang: String): Observable<Response<List<Info>>>

    @GET("ort_desc/")
    fun getOrtDesc(@Query("ort") main_category:Int): Observable<Response<List<Info>>>

    @GET("ort/")
    fun getOrt(@Query("lang") lang: String): Observable<Response<List<Info>>>

    @GET("game_all/")
    fun getGameOuter(@Query("user_id")id:String): Observable<Response<List<GameOuter>>>

    @FormUrlEncoded
    @POST("game_cache/")
    fun postGameOuter(
        @Field("user_owner") user_owner:String,
        @Field("user_outer") user_outer:Int,
        @Field("owner_point") owner_point:Int,
        @Field("outer_point") outer_point:Int,
        @Field("category") category:String,
        @Field("quiz") quiz:String

    ): Observable<Response<Any>>

    @FormUrlEncoded
    @PATCH("users/{id}/")
    fun putUser(
        @Path("id") id: Int,
        @Field("password") password:String

    ): Observable<Response<Any>>

    @FormUrlEncoded
    @PATCH("users/{id}/")
    fun putNotification(
        @Path("id") id: String,
        @Field("is_notification") password:Boolean

    ): Observable<Response<Any>>


    @FormUrlEncoded
    @POST("device")
    fun postDevice(
        @Field("registration_id") reg:String,
        @Field("users") user_id:Int,
        @Field("type") type:String

    ): Observable<Response<Any>>

    @FormUrlEncoded
    @PATCH("game_cache/{id}/")
    fun putGameOuter(
        @Path("id") id: Int,
        @Field("outer_point") outer_point:Int

    ): Observable<Response<Any>>

    @GET("quiz/")
    fun getQuiz(@Query("test") category: Int): Observable<Response<List<Quiz>>>

    @GET("quote/")
    fun getQuote(): Observable<Response<Quote>>

    @GET("day_quiz/")
    fun getQuizDay(@Query("date") category: String,@Query("lang") lang: String): Observable<Response<List<Quiz>>>

    @GET("game_quiz/")
    fun getQuizGame(@Query("test") category: Int): Observable<Response<List<Quiz>>>

    @GET("game_invite/")
    fun getQuizGameInvite(@Query("quiz") category: String): Observable<Response<List<Quiz>>>

    @GET("main_category/")
    fun getMainCategory(@Query("lang") lang: String): Observable<Response<List<Category>>>

    @GET("category_lang/")
    fun getCategory(@Query("lang") lang: String): Observable<Response<List<Category>>>

    @FormUrlEncoded
    @POST("rating/")
    fun addRating(
        @Field("rating") rating: Int,
        @Field("user") user: Int,
        @Field("created_at") create_at: String,
        @Field("true_answer") true_answer: Int,
        @Field("false_answer") false_answer: Int
    ): Observable<Response<Any>>

    @FormUrlEncoded
    @PATCH("rating/{id}/")
    fun updateRating(
        @Path("id") id: Int,
        @Field("rating") rating: Int,
        @Field("true_answer") true_answer: Int,
        @Field("false_answer") false_answer: Int
    ): Observable<Response<Any>>

    @GET("rating/")
    fun getRatingByDate(
        @Query("created_at") create_at: String,
        @Query("user") user_id: String
        ): Observable<Response<List<Rating>>>

    @GET("rating/")
    fun getRating(
        @Query("user") user_id: String
    ): Observable<Response<List<Rating>>>

    @GET("rating_al/")
    fun getRatingAll(
        @Query("user_id") user_id: String
    ): Observable<Response<RatingAll>>



}