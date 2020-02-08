package kg.mvvmdordoi.model.get

import com.google.gson.annotations.SerializedName

data class Friend (

	@SerializedName("id") val id : Int,
	@SerializedName("friend") val friend : User,
	@SerializedName("user") val user : Int
)