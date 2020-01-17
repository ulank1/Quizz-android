package kg.mvvmdordoi.model.get

import com.google.gson.annotations.SerializedName

data class RatingAll (

	@SerializedName("first") val first : ArrayList<RatingWithUser>,
	@SerializedName("me") val me : ArrayList<RatingWithUser>,
	@SerializedName("size") val size : Int

)