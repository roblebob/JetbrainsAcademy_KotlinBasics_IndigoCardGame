fun printIfPrime(number: Int) {
    // write here
    var flag = true
    for (i in number / 2 downTo 2) {
        if (number % i == 0) {
            flag = false
            break
        }
    }
    println(if (flag) { "$number is a prime number." } else { "$number is not a prime number." })
}

fun main(args: Array<String>) {
    // write here
    val number = readln().toInt()
    printIfPrime(number)
}