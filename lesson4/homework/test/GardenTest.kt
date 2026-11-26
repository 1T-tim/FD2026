package homework

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.opentest4j.AssertionFailedError
import java.io.ByteArrayOutputStream
import java.io.PrintStream

// Keep feedback short in both IntelliJ's JUnit runner and Gradle.
private fun checkGarden(id: String, scenario: String, test: () -> Unit) {
    try {
        test()
    } catch (error: Throwable) {
        if (error !is AssertionError && error !is Exception && error !is NotImplementedError) throw error
        val assertion = error as? AssertionFailedError
        val expected = assertion?.takeIf { it.isExpectedDefined }?.expected?.stringRepresentation ?: scenario
        val actual = when {
            generateSequence(error) { it.cause }.any { it is NotImplementedError } -> "результата нет (TODO)"
            assertion != null && assertion.isActualDefined -> assertion.actual.stringRepresentation
            error is AssertionError -> "условие не выполнилось"
            else -> error.javaClass.simpleName
        }
        throw AssertionError("Тест $id не прошёл.\nОжидали: $expected\nПолучили: $actual")
            .apply { stackTrace = emptyArray() }
    }
}

class TomatoTest {
    @Test fun `null uses default label`() = checkGarden("S1", "название по умолчанию") { assertEquals("Без сорта", Tomato().label()) }
    @Test fun `variety is trimmed`() = checkGarden("S2", "пробелы по краям названия") { assertEquals("Черри", Tomato("  Черри  ").label()) }
    @Test fun `blank stays empty`() = checkGarden("S3", "пустое название") {
        assertEquals("", Tomato("").label())
        assertEquals("", Tomato("   ").label())
    }
}

class WateringCanTest {
    @Test fun `capacity must be positive`() = checkGarden("S4", "объём лейки должен быть положительным") {
        assertThrows(IllegalArgumentException::class.java) { WateringCan(0) }
        assertThrows(IllegalArgumentException::class.java) { WateringCan(-1) }
    }
    @Test fun `starts full and refill restores capacity`() = checkGarden("S5", "расход воды и пополнение лейки") {
        val can = WateringCan()
        can.pour(); can.pour()
        assertThrows(EmptyWateringCanException::class.java) { can.pour() }
        can.refill(); can.pour(); can.pour()
        assertThrows(EmptyWateringCanException::class.java) { can.pour() }
    }
    @Test fun `refill does not increase capacity`() = checkGarden("S6", "пополнение не меняет объём лейки") {
        val can = WateringCan(1)
        can.refill(); can.pour()
        assertThrows(EmptyWateringCanException::class.java) { can.pour() }
    }
}

class TomatoBushTest {
    @Test fun `failed third watering does not advance growth`() = checkGarden("S7", "неудачный полив не ускоряет рост") {
        val bush = TomatoBush("Черри"); val can = WateringCan()
        bush.water(can); bush.water(can)
        assertNull(bush.peek())
        assertThrows(EmptyWateringCanException::class.java) { bush.water(can) }
        assertNull(bush.peek())
        can.refill(); bush.water(can)
        assertEquals("Черри", bush.harvest().label())
        assertNull(bush.peek())
    }
    @Test fun `fruits accumulate and harvest in order`() = checkGarden("S8", "порядок сбора помидоров") {
        val bush = TomatoBush(" Черри "); val can = WateringCan(6)
        repeat(6) { bush.water(can) }
        val first = bush.peek()!!
        assertSame(first, bush.harvest())
        val second = bush.peek()!!
        assertNotSame(first, second)
        assertEquals(" Черри ", second.variety)
        assertSame(second, bush.harvest())
        assertNull(bush.peek())
        assertThrows(NoTomatoException::class.java) { bush.harvest() }
    }
    @Test fun `harvest does not reset partial growth`() = checkGarden("S9", "сбор не сбрасывает рост") {
        val bush = TomatoBush(); val can = WateringCan(6)
        repeat(5) { bush.water(can) }
        assertEquals("Без сорта", bush.harvest().label())
        assertNull(bush.peek())
        bush.water(can)
        assertNotNull(bush.peek())
    }
    @Test fun `bushes keep their own variety and growth when using one can`() = checkGarden("S10", "кусты растут независимо") {
        val cherry = TomatoBush("Черри")
        val pink = TomatoBush("Розовый")
        val can = WateringCan(6)
        repeat(2) { cherry.water(can); pink.water(can) }
        assertNull(cherry.peek())
        assertNull(pink.peek())
        cherry.water(can)
        assertNull(pink.peek())
        pink.water(can)
        val first = cherry.peek()!!
        assertSame(first, cherry.peek())
        assertSame(first, cherry.harvest())
        assertEquals("Черри", first.variety)
        assertEquals("Розовый", pink.harvest().variety)
    }
    @Test fun `new bush has no fruit`() = checkGarden("S11", "на новом кусте нет плодов") {
        val bush = TomatoBush()
        assertNull(bush.peek())
        assertThrows(NoTomatoException::class.java) { bush.harvest() }
    }
}

class HarvestResultTest {
    @Test fun `not ripe and picked states`() = checkGarden("S12", "результат сбора спелого и неспелого плода") {
        val bush = TomatoBush(" Черри "); val can = WateringCan(3)
        assertEquals(HarvestResult.NotRipe, tryHarvest(bush))
        repeat(3) { bush.water(can) }
        val tomato = bush.peek()!!
        val result = tryHarvest(bush)
        assertTrue(result is HarvestResult.Picked)
        assertSame(tomato, (result as HarvestResult.Picked).tomato)
        assertEquals(HarvestResult.NotRipe, tryHarvest(bush))
    }
}

class GardenScenarioTest {
    @Test fun `show prints both outcomes and finally`() = checkGarden("S13", "сообщения при осмотре куста") {
        val bush = TomatoBush("  Черри  "); val can = WateringCan(3)
        val bytes = ByteArrayOutputStream(); val previous = System.out
        try {
            System.setOut(PrintStream(bytes, true, Charsets.UTF_8))
            showHarvest(bush)
            repeat(3) { bush.water(can) }
            showHarvest(bush)
        } finally { System.setOut(previous) }
        assertEquals(listOf("Собирать пока рано", "Осмотр завершён", "Собрали: Черри", "Осмотр завершён").joinToString(System.lineSeparator(), postfix = System.lineSeparator()), bytes.toString(Charsets.UTF_8))
    }
    @Test fun `main prints complete scenario`() = checkGarden("S14", "вывод полного сценария") {
        val bytes = ByteArrayOutputStream(); val previous = System.out
        try { System.setOut(PrintStream(bytes, true, Charsets.UTF_8)); main() }
        finally { System.setOut(previous) }
        assertEquals(listOf("Плода пока нет", "Собирать пока рано", "Осмотр завершён", "Лейка пуста, доливаем воду", "Собрали: Черри", "Осмотр завершён").joinToString(System.lineSeparator(), postfix = System.lineSeparator()), bytes.toString(Charsets.UTF_8))
    }
}
