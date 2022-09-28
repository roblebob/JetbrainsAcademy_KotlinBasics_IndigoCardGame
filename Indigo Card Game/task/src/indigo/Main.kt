package indigo

val RANKS = listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
val SUITS = listOf("♦", "♥", "♠", "♣")
val DEFAULT = cartesianProduct(SUITS.slice(listOf(2, 1, 0, 3)), RANKS).map { it.second + it.first }.toMutableSet()

class VirtualCardDeck(var deck: MutableSet<String> = DEFAULT ) {

    init {
        // shuffle()
    }

    fun get(i: Int): MutableSet<String> = deck.toMutableList().slice(0 until i).toMutableSet().also { deck.removeAll(it) }

    fun shuffle() = deck.shuffled().toMutableSet().also{ deck = it }

    fun reset() = DEFAULT.also { deck = it }
}



data class Cards(val my: MutableSet<String>, val computer: MutableSet<String>, val table: MutableSet<String>)



fun main() {
    println("Indigo Card Game")

    val deck = VirtualCardDeck()

    val cards = Cards(my = deck.get(6), computer = deck.get(6), table = deck.get(4))


    // determine who starts
    var isMyTurn = false
    while (true) {
        println("Play first?")
        val s = readln().lowercase()
        isMyTurn = if (s == "yes") {
            true
        } else if (s == "no") {
            false
        } else {
            continue
        }
        break
    }


    println("Initial cards on the table: ${cards.table.joinToString(" ")}")

    // actual game
    loop@while (true) {
        println("\n${cards.table.size} cards on the table, and the top card is ${cards.table.last()}")
        if (cards.table.size >= 52) {
            break
        }
        if (cards.my.size == 0 && cards.computer.size == 0) {
            cards.my.addAll(deck.get(6))
            cards.computer.addAll(deck.get(6))
        }

        if (isMyTurn) {
            // println(cards.my.joinToString(" "))
            print("Cards in hand:")
            for (i in cards.my.toMutableList().indices) {
                print(" ${i+1})${cards.my.toMutableList()[i]}" )
            }
            print("\n")
            while (true) {
                println("\nChoose a card to play (1-${cards.my.size}):")
                val s = readln()
                if (s == "exit") {
                    break@loop
                } else if (!s.contains("[1-9][0-9]*".toRegex()) || s.toInt() !in 1..cards.my.size) {
                    continue
                }

                val i = s.toInt() - 1
                val el = cards.my.toMutableList()[i]
                cards.table += el
                cards.my.remove(el)
                break
            }
        } else {
            val i = 0
            val el = cards.computer.toMutableList()[i]
            cards.table += el
            cards.computer.remove(el)
            println("Computer plays $el")
        }

        isMyTurn = !isMyTurn
    }
    println("Game Over")

//    while (true) {
//        println("Choose an action (reset, shuffle, get, exit):")
//        when (readln()) {
//            "reset" -> {
//                deck.reset()
//                println("Card deck is reset.")
//            }
//            "shuffle" -> {
//                deck.shuffle()
//                println("Card deck is shuffled.")
//            }
//            "get" -> {
//                println("Number of cards:")
//                val s = readln()
//                if (!s.contains("[1-9][0-9]*".toRegex())  ||  s.toInt() > 52) {
//                    println("Invalid number of cards.")
//                    continue
//                }
//
//                val i = s.toInt()
//
//                if (i > deck.deck.size) {
//                    println("The remaining cards are insufficient to meet the request.")
//                    continue
//                }
//
//                val hand = deck.get(i)
//
//                println(hand.joinToString(" "))
//            }
//            "exit" -> {
//                println("Bye")
//                return
//            }
//            else -> {
//                println("Wrong action.")
//                continue
//            }
//        }
//    }
}





// https://gist.github.com/kiwiandroiddev/fef957a69f91fa64a46790977d98862b
fun <T, U> cartesianProduct(c1: Collection<T>, c2: Collection<U>): List<Pair<T, U>> {
    return c1.flatMap { lhsElem -> c2.map { rhsElem -> lhsElem to rhsElem } }
}
