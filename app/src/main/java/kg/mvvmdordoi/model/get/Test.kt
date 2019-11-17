package kg.mvvmdordoi.model.get

import com.google.gson.annotations.SerializedName

data class Test (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("desc") val desc : String,
	@SerializedName("category") val category : Int
)