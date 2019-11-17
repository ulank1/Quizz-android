package kg.mvvmdordoi.model.get

import com.google.gson.annotations.SerializedName


data class GameOuter (

	@SerializedName("id") val id : Int,
	@SerializedName("quiz") val quiz : String,
	@SerializedName("category") val category : String?,
	@SerializedName("owner_point") val owner_point : Int,
	@SerializedName("outer_point") val outer_point : Int,
	@SerializedName("user_owner") val user_owner : User,
	@SerializedName("user_outer") val user_outer : User
)