package kg.mvvmdordoi.network

import android.content.ClipDescription
import io.reactivex.Observable
import kg.mvvmdordoi.model.ApiResponse
import kg.mvvmdordoi.model.Product
import kg.mvvmdordoi.model.Post
import kg.mvvmdordoi.model.get.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import javax.annotation.PostConstruct

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

    @GET("users_duel/")
    fun getUsersDuel(@Query("page") page:String,@Query("user")user:Int): Observable<Response<UserDuel>>

    @GET("users/")
    fun getUsersSearch(@Query("search")text:String,@Query("user")user:Int): Observable<Response<List<User>>>

    @GET("search_users/")
    fun getUsersSearch1(@Query("search")text:String,@Query("user")user:Int): Observable<Response<List<User>>>

    @GET("my_friend/")
    fun getFriend(@Query("user")user:Int): Observable<Response<List<Friend>>>

    @FormUrlEncoded
    @POST("friend/")
    fun addFriend(
        @Field("user")user:Int,
        @Field("friend")friend:Int,
        @Field("is_active")is_active:Boolean

    ): Observable<Response<Any>>

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
        @Part avatar: MultipartBody.Part,
        @Part("is_notification") is_notification : RequestBody
    ): Observable<User>

    @Multipart
    @POST("topic_create/")
    fun addTopic(
        @Part("forum") forum: RequestBody,
        @Part("title") login: RequestBody,
        @Part avatar: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("user") user: RequestBody
    ): Observable<Response<Any>>

    @Multipart
    @POST("topic_create/")
    fun addTopic(
        @Part("forum") forum: RequestBody,
        @Part("title") login: RequestBody,
        @Part("description") description: RequestBody,
        @Part("user") user: RequestBody
    ): Observable<Response<Any>>

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
        @Part("is_notification") is_notification : RequestBody


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

    @GET("category_ort/")
    fun getOrtCategory(@Query("lang") lang: String,@Query("user_id") user_id: String): Observable<Response<List<OrtTest>>>

    @GET("point_ort_get/")
    fun getHistoryOrt(@Query("user") user_id: String): Observable<Response<ArrayList<HistoryOrt>>>

    @GET("desc_ort/")
    fun getDesc(@Query("lang") lang: String,
                @Query("category") category:Int): Observable<Response<List<DescOrt>>>
    @FormUrlEncoded
    @PATCH("pay/{id}/")
    fun putPay(@Path("id") id: Int,
               @Field("is_used") boolean: Boolean): Observable<Response<Any>>

    @FormUrlEncoded
    @POST("point_ort/")
    fun createHistory(@Field("user") id: Int,
                      @Field("category") category: Int,
                      @Field("point") point: Int,
                      @Field("math1") math1: Int,
                      @Field("math2") math2: Int,
                      @Field("analog") analog: Int,
                      @Field("understand") understand: Int,
                      @Field("grammar") grammar: Int
    ): Observable<Response<Any>>

    @GET("pay/")
    fun getPay(@Query("is_used") is_used: Boolean,
               @Query("user")user:String): Observable<Response<List<Pay>>>

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

    @GET("math1/")
    fun getQuizOrt(@Query("type_of_test") type: Int,
                   @Query("category")category: Int): Observable<Response<List<Quiz>>>

    @GET("pay_info/")
    fun getInfoPay(@Query("lang")lang: String): Observable<Response<ArrayList<InfoPay>>>

    @GET("quote/")
    fun getQuote(@Query("lang")lang: String): Observable<Response<Quote>>

    @GET("forum/")
    fun getForum(@Query("lang")lang: String): Observable<Response<ArrayList<Forum>>>

    @GET("topic/")
    fun getTopic(@Query("forum")forum: Int): Observable<Response<ArrayList<Topic>>>

    @GET("topic/")
    fun getMyTopic(@Query("user")user: String): Observable<Response<ArrayList<Topic>>>

    @DELETE("topic/{id}/")
    fun deleteMyTopic(@Path("id")id: Int): Observable<Response<ArrayList<Topic>>>

    @GET("topic/")
    fun getTopicSearch(@Query("forum")forum: Int,@Query("search") text: String): Observable<Response<ArrayList<Topic>>>

    @GET("day_quiz/")
    fun getQuizDay(@Query("date") category: String,@Query("lang") lang: String): Observable<Response<List<DayQuiz>>>

    @GET("game_quiz/")
    fun getQuizGame(@Query("test") category: Int): Observable<Response<List<Quiz>>>

    @GET("comment_quiz/")
    fun getCommentQuiz(@Query("quiz") id: Int,@Query("user_id")user_id: Int): Observable<Response<List<Comment>>>

    @GET("comment_forum/")
    fun getCommentForum(@Query("topic") id: Int,@Query("user_id")user_id: Int): Observable<Response<List<Comment>>>

    @FormUrlEncoded
    @POST("comment_quiz_create/")
    fun postComment(@Field("quiz") quiz_id: Int,
                    @Field("message") message:String,
                    @Field("user") user_id: Int): Observable<Response<Any>>

    @FormUrlEncoded
    @POST("comment_forum_create/")
    fun postCommentForum(@Field("topic") topic: Int,
                    @Field("message") message:String,
                    @Field("user") user_id: Int): Observable<Response<Any>>

    @FormUrlEncoded
    @POST("answer_quiz/")
    fun postAnswer(@Field("quiz") quiz_id: Int,
                    @Field("message") message:String,
                    @Field("user") user_id: String,
                   @Field("comment")comment_id:Int,
                   @Field("name") name: String
                   ): Observable<Response<Any>>

    @FormUrlEncoded
    @POST("like_quiz/")
    fun postLike(@Field("user") user_id: Int,
                   @Field("comment") idComment:Int,
                   @Field("like") like: Int
    ): Observable<Response<Any>>


    @FormUrlEncoded
    @POST("like_answer_quiz/")
    fun postLikeAnswer(@Field("user") user_id: Int,
                   @Field("answer") answer:Int,
                   @Field("like") like: Int
    ): Observable<Response<Any>>






    @FormUrlEncoded
    @POST("answer_forum/")
    fun postAnswerForum(
                   @Field("message") message:String,
                   @Field("user") user_id: String,
                   @Field("comment")comment_id:Int,
                   @Field("name") name: String
    ): Observable<Response<Any>>

    @FormUrlEncoded
    @POST("like_forum/")
    fun postLikeForum(@Field("user") user_id: Int,
                 @Field("comment") idComment:Int,
                 @Field("like") like: Int
    ): Observable<Response<Any>>


    @FormUrlEncoded
    @POST("like_answer_forum/")
    fun postLikeAnswerForum(@Field("user") user_id: Int,
                       @Field("answer") answer:Int,
                       @Field("like") like: Int
    ): Observable<Response<Any>>







    @GET("game_invite/")
    fun getQuizGameInvite(@Query("quiz") category: String): Observable<Response<List<Quiz>>>

    @GET("main_category/")
    fun getMainCategory(@Query("lang") lang: String): Observable<Response<List<Category>>>

    @GET("category/")
    fun getCategory(@Query("lang") lang: String): Observable<Response<List<Category>>>

    @FormUrlEncoded
    @POST("rating/")
    fun addRating(
        @Field("rating") rating: Int,
        @Field("user") user: String,
        @Field("created_at") create_at: String,
        @Field("true_answer") true_answer: Int,
        @Field("false_answer") false_answer: Int
    ): Observable<Response<Rating>>

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

    @GET("rating_first/")
    fun getRatingAll(
        @Query("user_id") user_id: String
    ): Observable<Response<RatingAll>>

    @GET("rating_all_of_pagination/")
    fun getRatingAllOf(
            @Query("page") page:String
    ): Observable<Response<RatingPagination>>



}