package indigo

val RANKS = listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
val SUITS = listOf("♦", "♥", "♠", "♣")



fun main() {
    println(RANKS.joinToString(" ") + "\n\n" +
            SUITS.joinToString(" ") + "\n\n" +
            cartesianProduct(SUITS.slice(listOf(2, 1, 0, 3)), RANKS)
                .joinToString(" ") { pair -> pair.second + pair.first })
}

fun <T, U> cartesianProduct(c1: Collection<T>, c2: Collection<U>): List<Pair<T, U>> {
    return c1.flatMap { lhsElem -> c2.map { rhsElem -> lhsElem to rhsElem } }
}
