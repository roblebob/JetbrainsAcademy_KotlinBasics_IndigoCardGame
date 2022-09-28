fun main() {
    val s1 = readln()
    val op = readln()
    val s2 = readln()
    print(
        when (op) {
            "equals" -> s1 == s2
            "plus" -> s1 + s2
            "endsWith" -> s1.endsWith(s2)
            else -> "Unknown operation"
        }
    )
}