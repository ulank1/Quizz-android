package kg.mvvmdordoi.model.get

import com.google.gson.annotations.SerializedName

data class Game (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("image") val image : String
)