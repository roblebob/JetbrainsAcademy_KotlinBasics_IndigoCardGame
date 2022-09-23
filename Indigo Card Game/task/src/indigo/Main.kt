package indigo

val RANKS = listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
val SUITS = listOf("♦", "♥", "♠", "♣")

class VirtualCardDeck(var deck: MutableSet<String> = mutableSetOf() ) {

    init {
        deck = default()
    }

    fun get(i: Int): MutableSet<String> = deck.toMutableList().slice(0 until i).toMutableSet().also { deck.removeAll(it) }

    fun shuffle() = deck.shuffled().toMutableSet().also{ deck = it }

    fun reset() = default().also { deck = it }


    fun print() = println(deck)

    private fun default() = cartesianProduct(SUITS.slice(listOf(2, 1, 0, 3)), RANKS).map { it.second + it.first }.toMutableSet()
}




fun main() {
    val deck = VirtualCardDeck()

    while (true) {
        println("Choose an action (reset, shuffle, get, exit):")
        when (readln()) {
            "reset" -> {
                deck.reset()
                println("Card deck is reset.")
            }
            "shuffle" -> {
                deck.shuffle()
                println("Card deck is shuffled.")
            }
            "get" -> {
                println("Number of cards:")
                val s = readln()
                if (!s.contains("[1-9][0..9]*".toRegex())  ||  s.toInt() > 52) {
                    println("Invalid number of cards.")
                    continue
                }

                val i = s.toInt()

                if (i > deck.deck.size) {
                    println("The remaining cards are insufficient to meet the request.")
                    continue
                }

                val hand = deck.get(i)

                println(hand.joinToString(" "))
            }
            "exit" -> {
                println("Bye")
                return
            }
            else -> {
                println("Wrong action.")
                continue
            }
        }
    }
}





fun <T, U> cartesianProduct(c1: Collection<T>, c2: Collection<U>): List<Pair<T, U>> {
    return c1.flatMap { lhsElem -> c2.map { rhsElem -> lhsElem to rhsElem } }
}
