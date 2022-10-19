package indigo

val RANKS = listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
val SUITS = listOf("♦", "♥", "♠", "♣")
val DEFAULT = cartesianProduct(SUITS.slice(listOf(2, 1, 0, 3)), RANKS).map { it.second + it.first }.toMutableSet()

class VirtualCardDeck(var cards: MutableSet<String> = DEFAULT ) {

    val size: Int
        get() = cards.size

    init {
        shuffle()
    }

    fun get(i: Int): MutableSet<String> = cards.toMutableList().slice(0 until i).toMutableSet().also { cards.removeAll(it) }

    private fun shuffle() = cards.shuffled().toMutableSet().also{ cards = it }
}

class History(val firstPlayed: String) {
    var lastWon = firstPlayed
    val playersWins = mutableSetOf<String>()
    val computersWins = mutableSetOf<String>()

    val allCardsHaveBeenPlayed: Boolean
        get() = playersWins.size + computersWins.size == 52

    val summery: String
        get() {
            return "Score: Player $playersScore - Computer $computersScore" + "\n" +
                    "Cards: Player ${playersWins.size} - Computer ${computersWins.size}"
        }

    private val playersScore: Int
        get() {
            return score(playersWins) +
                    if (allCardsHaveBeenPlayed && (playersWins.size > computersWins.size ||
                                (playersWins.size == computersWins.size && firstPlayed == "player"))) { 3 } else { 0 }
        }

    private val computersScore: Int
        get() {
            return score(computersWins) +
                    if (allCardsHaveBeenPlayed && (playersWins.size < computersWins.size ||
                                (playersWins.size == computersWins.size && firstPlayed == "computer"))) { 3 } else { 0 }
        }

    private fun score(set: MutableSet<String>): Int {
        return set
            .map { if (it.substring(0..it.length - 2) in mutableSetOf("A", "10", "J", "Q", "K")) 1 else 0 }
            .sum()
    }
}


fun main() {
    println("Indigo Card Game")

    // decide who starts
    var isPlayersTurn: Boolean
    while (true) {
        println("Play first?")
        isPlayersTurn = when (readln().lowercase()) {
            "yes" -> true
            "no" -> false
            else -> continue
        }
        break
    }

    // initializing core units
    val history = History(firstPlayed = if (isPlayersTurn) "player" else "computer")
    val deck = VirtualCardDeck()
    val table = mutableSetOf<String>()
    val player = mutableSetOf<String>()
    val computer = mutableSetOf<String>()

    // preparing table
    table.addAll(deck.get(4))
    println("Initial cards on the table: ${table.joinToString(" ")}")

    // actual game
    loop@while (true) {

        println(
            "\n" +
            if (table.isNotEmpty()) {
                "${table.size} cards on the table, and the top card is ${table.last()}"
            } else {
                "No cards on the table"
            }
        )

        // handing the players new cards from the deck if theirs are empty
        if (player.size == 0 && computer.size == 0) {
            if (deck.size >= 12) {
                player.addAll(deck.get(6))
                computer.addAll(deck.get(6))
            } else {
                if (history.lastWon == "player") {
                    history.playersWins.addAll(table)
                } else {
                    history.computersWins.addAll(table)
                }
                println(history.summery)
                break@loop
            }
        }

        //
        if (isPlayersTurn) {

            println("Cards in hand: ${player.mapIndexed { idx, el -> "${idx + 1})$el" }.joinToString(" ")}")

            while (true) {
                println("Choose a card to play (1-${player.size}):")
                val s = readln()
                if (s == "exit") {
                    break@loop
                } else if (!s.contains("[1-9][0-9]*".toRegex()) || s.toInt() !in 1..player.size) {
                    continue
                }

                toss(from = player, to = table, idx = s.toInt() - 1)
                break
            }
        } else /* computers turn */ {

            println(computer.joinToString(" "))
            val candidates = getCandidates(computer = computer, table = table)
            val chosen = if (candidates.isNotEmpty()) { candidates.random() } else { computer.random() }
            println("Computer plays ${toss(from = computer, to = table, idx = computer.indexOf(chosen))}")
        }


        // checking if that on the table is a win
        if (table.size > 1 && isAWin(table.last(), table.elementAt(table.size - 2))) {
            if (isPlayersTurn) {
                history.playersWins.addAll(table)
                println("Player wins cards")
                history.lastWon = "player"
            } else {
                history.computersWins.addAll(table)
                println("Computer wins cards")
                history.lastWon = "computer"
            }
            table.clear()
            println(history.summery)
        }

        // break if it's over
        if (table.size >= 52 || history.allCardsHaveBeenPlayed) {
            break@loop
        }

        // prepare for next round
        isPlayersTurn = !isPlayersTurn
    }
    println("Game Over")
}





fun getCandidates(computer: MutableSet<String>, table: MutableSet<String>): MutableSet<String> {

    val candidates = mutableSetOf<String>()

    if (table.isNotEmpty()) {
        candidates.addAll(computer.filter { isAWin(it, table.last()) }.toMutableSet())
    }

    if (candidates.size >= 2) {
        val temp = mutableSetOf<String>()
        temp.addAll(candidates)
        candidates.clear()
        candidates.addAll(cardsWithTokenOccuringMultiple(temp) {
            it.last().toString() /* selector for suit */
        })

        if (candidates.isEmpty()) {
            candidates.addAll(cardsWithTokenOccuringMultiple(temp) {
                it.substring(0 ..it.length - 2) /* selector for rank */
            })
        }

        if (candidates.isEmpty()) {
            candidates.addAll(temp)
        }
    }


    if (candidates.isEmpty()) {
        candidates.addAll(cardsWithTokenOccuringMultiple(computer) {
            it.last().toString() /* selector for suit */
        })
    }

    if (candidates.isEmpty()) {
        candidates.addAll(cardsWithTokenOccuringMultiple(computer) {
            it.substring(0 ..it.length - 2) /* selector for rank */
        })
    }

    return candidates
}








fun cardsWithTokenOccuringMultiple(cards: Set<String>, tokenSelector: (String) -> String): Set<String> {

    val hist = mutableMapOf<String, Int>()
    for (card in cards) {
        val token = tokenSelector(card)
        if (token !in hist) {
            hist[token] = 1
        } else {
            hist[token] = hist[token]!! + 1
        }
    }
    val tokenOccuringMultiple: Set<String> =
        hist.filter { it.value > 1 }.map { it.key }.toSet()

    return cards.filter { tokenSelector(it) in tokenOccuringMultiple }.toSet()
}









/**
 * determines if two given cards represent a win, namely if the ranks or the suits are the same
 */
fun isAWin(card1: String, card2: String): Boolean =
    card1.last() == card2.last() /* suits */ ||
        card1.substring(0..card1.length - 2) == card2.substring(0..card1.length - 2)  /* ranks */

/**
 * produces a cartesian product with the elements being paired
 * (source: https://gist.github.com/kiwiandroiddev/fef957a69f91fa64a46790977d98862b)
 */
fun <T, U> cartesianProduct(c1: Collection<T>, c2: Collection<U>): List<Pair<T, U>> =
    c1.flatMap { lhsElem -> c2.map { rhsElem -> lhsElem to rhsElem } }


/**
 * tosses a single element, from collection 'from' to collection 'to' and returns that element for noticing;
 * idx out of range results in choosing randomly
 */
fun <T> toss(from: MutableCollection<T>, to: MutableCollection<T>, idx: Int = 0) : T =
    from.elementAtOrElse(idx) { from.random() }.also { from -= it; to += it }