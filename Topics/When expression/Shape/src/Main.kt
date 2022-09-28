const val PRE = "You have chosen a "
const val ID_SQUARE = 1.0
const val ID_CIRCLE = 2.0
const val ID_TRIANGLE = 3.0
const val ID_RHOMBUS = 4.0

fun main(args: Array<String>) {
    println(
        when (readln().toDouble()) {
            ID_SQUARE -> PRE + "square"
            ID_CIRCLE -> PRE + "circle"
            ID_TRIANGLE -> PRE + "triangle"
            ID_RHOMBUS -> PRE + "rhombus"
            else -> "There is no such shape!"
        }
    )
}