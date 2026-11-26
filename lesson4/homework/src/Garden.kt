package homework

sealed interface HarvestResult {
    data class Picked(val tomato: Tomato) : HarvestResult
    data object NotRipe : HarvestResult
}

fun tryHarvest(bush: TomatoBush): HarvestResult = TODO("Задание 4")

fun showHarvest(bush: TomatoBush): Unit = TODO("Задание 5")

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
