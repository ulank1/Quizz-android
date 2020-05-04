package kg.mvvmdordoi.model.get

data class Pay(
    val created_at: String,
    val id: Int,
    val is_used: Boolean,
    val used_time: Any,
    val user: Int
)