package homework

sealed interface HarvestResult {
    data class Picked(val tomato: Tomato) : HarvestResult
    data object NotRipe : HarvestResult
}

fun tryHarvest(bush: TomatoBush): HarvestResult = try {
    HarvestResult.Picked(bush.harvest())
} catch (e: NoTomatoException) {
    HarvestResult.NotRipe
}

fun showHarvest(bush: TomatoBush) {
    try {
        when (val result = tryHarvest(bush)) {
            is HarvestResult.Picked -> println("Собрали: ${result.tomato.label()}")
            HarvestResult.NotRipe -> println("Собирать пока рано")
        }
    } finally {
        println("Осмотр закончен") // Намеренная ошибка для проверки тестов.
    }
}

fun main() {
    val bush = TomatoBush("  Черри  ")
    val can = WateringCan()
    repeat(2) { bush.water(can) }
    println(bush.peek()?.label() ?: "Плода пока нет")
    showHarvest(bush)
    try {
        bush.water(can)
    } catch (e: EmptyWateringCanException) {
        println("Лейка пуста, доливаем воду")
        can.refill()
        bush.water(can)
    }
    showHarvest(bush)
    check(bush.peek() == null)
}
