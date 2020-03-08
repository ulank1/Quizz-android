package kg.mvvmdordoi.model.get

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Topic (
	@SerializedName("id") val id : Int,
	@SerializedName("title") val title : String,
	@SerializedName("image") val image : String,
	@SerializedName("description") val description : String,
	@SerializedName("comment_count") val comment_count : Int,
	@SerializedName("user") val user : User
):Serializable