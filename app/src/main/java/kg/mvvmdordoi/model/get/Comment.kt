package kg.mvvmdordoi.model.get

import com.google.gson.annotations.SerializedName

data class Comment (

	@SerializedName("id") val id : Int,
	@SerializedName("like_count") var like_count : Int=0,
	@SerializedName("is_my_like") var is_my_like : Int=0,
	@SerializedName("un_like_count") var un_like_count : Int=0,
	@SerializedName("message") val message : String,
	@SerializedName("created_at") val created_at : String,
	@SerializedName("name") val name : String,
	@SerializedName("answer") var answers : ArrayList<Comment>?,
	@SerializedName("user") val user : User
)