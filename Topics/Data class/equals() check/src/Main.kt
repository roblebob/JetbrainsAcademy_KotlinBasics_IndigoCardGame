data class Client(val name: String, val age: Int, val balance: Int) {
    override fun equals(other: Any?): Boolean {
        other as Client
        if (name != other.name) return false
        if (age != other.age) return false
        return true
    }
}

fun main() {
    // implement your code here
    val client1 = Client(readln(), readln().toInt(), readln().toInt())
    val client2 = Client(readln(), readln().toInt(), readln().toInt())
    println(client1 == client2)
}