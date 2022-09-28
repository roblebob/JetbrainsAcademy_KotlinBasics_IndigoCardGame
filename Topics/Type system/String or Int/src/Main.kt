import java.lang.NumberFormatException

fun isNumber(input: String): Any {
    return try {
        input.toInt()
    } catch (e: NumberFormatException) {
        input
    }
}