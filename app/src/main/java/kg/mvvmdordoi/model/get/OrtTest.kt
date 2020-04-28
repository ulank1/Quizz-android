package kg.mvvmdordoi.model.get

import com.google.gson.annotations.SerializedName

data class OrtTest (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("text1") val text1 : String,
	@SerializedName("text2") val text2 : String,
	@SerializedName("text3") val text3 : String
)