package homework

class Tomato // Готовый тип плода. Менять не нужно.

// Пока это обычные классы. Сделайте их исключениями согласно условию.
class EmptyWateringCanException
class NoRipeTomatoException

class WateringCan {
    fun pour(): Unit = TODO()
    fun refill(): Unit = TODO()
}

class TomatoBush {
    fun water(can: WateringCan): Unit = TODO()
    fun peek(): Tomato? = TODO()
    fun harvest(): Tomato = TODO()
}

fun main() {
    // Покажите один игровой сценарий из README.
}
