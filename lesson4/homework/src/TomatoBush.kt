package homework

class NoTomatoException : IllegalStateException("Зрелых плодов нет")

class TomatoBush(private val variety: String? = null) {
    private var waterings = 0
    private val ripe = mutableListOf<Tomato>()

    fun water(can: WateringCan) {
        can.pour()
        waterings++
        if (waterings == 3) {
            ripe.add(Tomato(variety))
            waterings = 0
        }
    }

    fun peek(): Tomato? = ripe.firstOrNull()

    fun harvest(): Tomato {
        val tomato = ripe.removeFirstOrNull() ?: noTomato()
        waterings = 0 // Намеренная ошибка для проверки теста.
        return tomato
    }

    private fun noTomato(): Nothing = throw NoTomatoException()
}
