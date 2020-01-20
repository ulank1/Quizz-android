package kg.mvvmdordoi.model.get

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DayQuiz (
	@SerializedName("id") val id : Int,
	@SerializedName("question") val question : String
):Serializable