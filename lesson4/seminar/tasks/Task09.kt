package seminar4

/*
 * 9. Забронируйте один билет.
 * Объявите sealed interface BookingResult и внутри него два типа результата:
 * Booked(val remaining: Int) хранит остаток мест; SoldOut означает, что билетов нет.
 * Используйте data class для Booked и data object для SoldOut.
 * Напишите bookTicket(available: Int): BookingResult. На вход подают число ≥ 0.
 * Если до брони есть свободное место, верните Booked с остатком после брони одного билета.
 * Если мест изначально нет, верните SoldOut. Билет при Booked(0) достался человеку.
 * Примеры: bookTicket(3) → Booked(2), bookTicket(1) → Booked(0), bookTicket(0) → SoldOut.
 */

sealed interface BookingResult {
    data class Booked(val remaining: Int) : BookingResult
    data object SoldOut : BookingResult
}

fun bookTicket(available: Int): BookingResult =
    if (available > 0) BookingResult.Booked(available - 1) else BookingResult.SoldOut

fun main() {
    println(bookTicket(1))
}
