class City(val name: String) {
    var degrees: Int = 0
        set(value: Int) {
            field = if (value < -92 || value > 57) {
                when (name) {
                    "Moscow" -> 5
                    "Hanoi" -> 20
                    "Dubai" -> 30
                    else -> 0
                }
            } else value
        }
}

fun main() {
    val first = readLine()!!.toInt()
    val second = readLine()!!.toInt()
    val third = readLine()!!.toInt()
    val firstCity = City("Dubai")
    firstCity.degrees = first
    val secondCity = City("Moscow")
    secondCity.degrees = second
    val thirdCity = City("Hanoi")
    thirdCity.degrees = third

    //implement comparing here
    val list = listOf(firstCity.degrees, secondCity.degrees, thirdCity.degrees)
    val coldestTemp = list.minOrNull()!!
    println( if (list.count { it == coldestTemp } > 1) {
        "neither"
    } else {
        listOf(firstCity, secondCity, thirdCity)[list.indexOf(coldestTemp)].name
    })
}