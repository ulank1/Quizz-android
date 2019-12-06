package kg.mvvmdordoi.model.get
import com.google.gson.annotations.SerializedName

data class User (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("is_kg") val is_kg : Boolean,
	@SerializedName("is_ru") val is_ru : Boolean,
	@SerializedName("login") val login : String,
	@SerializedName("birth_date") val birth_date : String,
	@SerializedName("place") val place : String,
	@SerializedName("password") val password : String,
	@SerializedName("avatar") val avatar : String,
	@SerializedName("is_notification") val is_notification : Boolean
)