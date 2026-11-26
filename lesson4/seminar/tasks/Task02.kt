package seminar4

fun parseSeats(raw: Any?): Int? {
    val seats = when (raw) {
        is Int -> raw
        is String -> raw.trim().toIntOrNull() ?: return null
        else -> return null
    }
    return if (seats > 0) seats else null
}

fun main() {
    println(parseSeats(" 2 "))
}
