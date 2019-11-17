package kg.mvvmdordoi.model.get

import com.google.gson.annotations.SerializedName

data class Notification (

	@SerializedName("id") val id : Int,
	@SerializedName("is_view") val is_view : Boolean,
	@SerializedName("title") val title : String,
	@SerializedName("body") val body : String,
	@SerializedName("news") val news : String,
	@SerializedName("game") val game : Int,
	@SerializedName("created_at") val created_at : String,
	@SerializedName("user") val user : Int
)