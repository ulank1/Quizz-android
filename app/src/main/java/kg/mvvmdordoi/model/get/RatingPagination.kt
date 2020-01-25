package kg.mvvmdordoi.model.get

import com.google.gson.annotations.SerializedName

data class RatingPagination (

	@SerializedName("next") val next : String?,
	@SerializedName("count") val count : Int,
	@SerializedName("results") val user : ArrayList<RatingWithUser>
)