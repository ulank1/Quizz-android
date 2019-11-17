package kg.mvvmdordoi.model.get

import android.graphics.drawable.Drawable
import com.google.gson.annotations.SerializedName

data class Category (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("desc") val desc : String,
	@SerializedName("main_category") val main_category : Int,
	@SerializedName("image") val image : String?,
	var res : Int?

)