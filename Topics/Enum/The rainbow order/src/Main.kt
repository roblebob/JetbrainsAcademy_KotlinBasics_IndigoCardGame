enum class Rainbow {
    RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET
}

fun main() {
    val color = readln()
    // put your code here
    for (enum in Rainbow.values()) {
        if (color.uppercase() == enum.name) {
            println(enum.ordinal + 1)
            return
        }
    }
}