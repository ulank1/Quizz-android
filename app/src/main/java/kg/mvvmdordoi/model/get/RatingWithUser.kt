package kg.mvvmdordoi.model.get

import com.google.gson.annotations.SerializedName

data class RatingWithUser (

	@SerializedName("id") val id : Int,
	@SerializedName("rating") val rating : Int,
	@SerializedName("true_answer") val true_answer : Int,
	@SerializedName("false_answer") val false_answer : Int,
	@SerializedName("user") val user : User,
	@SerializedName("created_at") val created_at : String
)