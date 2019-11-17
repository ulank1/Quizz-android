package kg.mvvmdordoi.model.get

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class University (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("desc") val desc : String,
	@SerializedName("avatar") val avatar : String


):Serializable