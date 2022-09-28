// write your data class here
data class Student(val name: String, var money: Int) {
    override fun equals(other: Any?): Boolean {
        other as Student
        if (other.name != name) return false
        if (other.money >= 1500) return false
        return true
    }
}