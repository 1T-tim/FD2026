package homework

class EmptyWateringCanException : IllegalStateException("Лейка пуста")

class WateringCan(private val capacity: Int = 2) {
    init { TODO("Задание 2") }
    fun pour(): Unit = TODO("Задание 2")
    fun refill(): Unit = TODO("Задание 2")
}
