package kg.mvvmdordoi.model.get

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Forum (
	@SerializedName("id") val id : Int,
	@SerializedName("title") val title : String,
	@SerializedName("image") val image : String
):Serializable