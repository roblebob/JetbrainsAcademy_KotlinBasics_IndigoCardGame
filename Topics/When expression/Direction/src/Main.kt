const val S = "move"

fun main() {
    println(
        when (readln()) {
            "0" -> "do not $S"
            "1" -> "$S up"
            "2" -> "$S down"
            "3" -> "$S left"
            "4" -> "$S right"
            else -> "error!"
        }
    )
}