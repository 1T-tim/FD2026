package seminar4

sealed interface BookingResult {
    data class Booked(val remaining: Int) : BookingResult
    data object SoldOut : BookingResult
}

fun bookTicket(available: Int): BookingResult =
    if (available > 0) BookingResult.Booked(available - 1) else BookingResult.SoldOut

fun main() {
    println(bookTicket(1))
}
