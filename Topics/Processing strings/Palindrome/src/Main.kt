fun main() {
    // write your code here
    val s = readln()

    val w1 = s.substring(0 until s.length / 2)

    val w2 = if (s.length % 2 == 0) {
        s.substring(s.length / 2 until s.length)
    } else {
        s.substring(s.length / 2 + 1 until s.length)
    }

    println(if (w1 == w2.reversed()) { "yes" } else { "no" })
}