package kg.mvvmdordoi.model.get

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Info (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("desc") val desc : String,
	@SerializedName("image") val image : String,
	@SerializedName("main_category") val main_category : Int

):Serializable