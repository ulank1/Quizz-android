package kg.mvvmdordoi.model.get

import com.google.gson.annotations.SerializedName
import io.reactivex.internal.operators.maybe.MaybeDoAfterSuccess

data class IsExist (

	@SerializedName("success") val success : Boolean
)