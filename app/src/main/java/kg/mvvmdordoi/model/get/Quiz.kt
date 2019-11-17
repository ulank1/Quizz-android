package kg.mvvmdordoi.model.get

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Quiz (

	@SerializedName("id") val id : Int,
	@SerializedName("question") val question : String,
	@SerializedName("answer_a") val answer_a : String,
	@SerializedName("answer_b") val answer_b : String,
	@SerializedName("answer_c") val answer_c : String,
	@SerializedName("answer_d") val answer_d : String,
	@SerializedName("answer_e") val answer_e : String,
	@SerializedName("true_answer") val true_answer : Int,
	@SerializedName("test") val test : Int,
	@SerializedName("duration") val duration : Int,

	var choosenPosition:Int?

):Serializable