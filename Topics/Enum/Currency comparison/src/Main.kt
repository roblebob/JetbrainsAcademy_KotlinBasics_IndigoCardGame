enum class Dictionary(val currency: String) {
    Germany("Euro"),
    Mali("CFA franc"),
    Dominica("Eastern Caribbean dollar"),
    Canada("Canadian dollar"),
    Spain("Euro"),
    Australia("Australian dollar"),
    Brazil("Brazilian real"),
    Senegal("CFA franc"),
    France("Euro"),
    Grenada("Eastern Caribbean dollar"),
    Kiribati("Australian dollar")
}

fun main() {
    // put your code here
    val countries = readLine()!!.split(" ")

    println(findCurrency(countries[0]) == findCurrency(countries[1]))
}

fun findCurrency(country: String): String {
    for (enum in Dictionary.values()) {
        if (country == enum.name) {
            return enum.currency
        }
    }
    return ""
}
