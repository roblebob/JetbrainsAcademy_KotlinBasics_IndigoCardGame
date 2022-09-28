// do not change this data class
data class Box(val height: Int, val length: Int, val width: Int)

fun main() {
    // implement your code here
    val v1 = readln().toInt()
    val v2 = readln().toInt()
    val v3 = readln().toInt()
    val v4 = readln().toInt()


    val box1 = Box(v1, v2, v4)
    val box2 = box1.copy(length = v3)

    println("$box1\n$box2")
}