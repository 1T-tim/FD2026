package homework

class NoTomatoException : IllegalStateException("Зрелых плодов нет")

class TomatoBush(private val variety: String? = null) {
    fun water(can: WateringCan): Unit = TODO("Задание 3")
    fun peek(): Tomato? = TODO("Задание 3")
    fun harvest(): Tomato = TODO("Задание 3")
    private fun noTomato(): Nothing = TODO("Задание 3")
}
