package kg.mvvmdordoi.model.get

import com.google.gson.annotations.SerializedName

data class UserDuel (

	@SerializedName("next") val next : String,
	@SerializedName("count") val count : Int,
	@SerializedName("results") val user : ArrayList<User>
)