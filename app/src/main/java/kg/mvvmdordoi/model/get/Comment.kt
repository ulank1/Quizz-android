package kg.mvvmdordoi.model.get

import com.google.gson.annotations.SerializedName

data class Comment (

	@SerializedName("id") val id : Int,
	@SerializedName("message") val message : String,
	@SerializedName("answer") var answers : ArrayList<Comment>?,
	@SerializedName("user") val user : User
)