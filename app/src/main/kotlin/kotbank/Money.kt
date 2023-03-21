package kotbank

data class Money private constructor(
        val value: Int
) {
        companion object {
                fun Int.toMoney(): Money = Money(this)
        }
}
