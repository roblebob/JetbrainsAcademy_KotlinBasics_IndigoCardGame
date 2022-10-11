fun main() {
    // write your code here
    val input = readln().split(" ")
    val s = input[0]
    val n = input[1].toInt()
    println(
        if (n !in 1..s.length) {
            s
        } else {
            s.substring(n until s.length) + s.substring(0 until n)
        }
    )
}