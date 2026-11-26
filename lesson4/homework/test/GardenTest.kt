package homework

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class TomatoTest {
    @Test fun `null uses default label`() { assertEquals("Без сорта", Tomato().label()) }
    @Test fun `variety is trimmed`() { assertEquals("Черри", Tomato("  Черри  ").label()) }
    @Test fun `blank stays empty`() {
        assertEquals("", Tomato("").label())
        assertEquals("", Tomato("   ").label())
    }
}

class WateringCanTest {
    @Test fun `capacity must be positive`() {
        assertThrows(IllegalArgumentException::class.java) { WateringCan(0) }
        assertThrows(IllegalArgumentException::class.java) { WateringCan(-1) }
    }
    @Test fun `starts full and refill restores capacity`() {
        val can = WateringCan()
        can.pour(); can.pour()
        assertThrows(EmptyWateringCanException::class.java) { can.pour() }
        can.refill(); can.pour(); can.pour()
        assertThrows(EmptyWateringCanException::class.java) { can.pour() }
    }
    @Test fun `refill does not increase capacity`() {
        val can = WateringCan(1)
        can.refill(); can.pour()
        assertThrows(EmptyWateringCanException::class.java) { can.pour() }
    }
}

class TomatoBushTest {
    @Test fun `failed third watering does not advance growth`() {
        val bush = TomatoBush("Черри"); val can = WateringCan()
        bush.water(can); bush.water(can)
        assertNull(bush.peek())
        assertThrows(EmptyWateringCanException::class.java) { bush.water(can) }
        assertNull(bush.peek())
        can.refill(); bush.water(can)
        assertEquals("Черри", bush.harvest().label())
        assertNull(bush.peek())
    }
    @Test fun `fruits accumulate and harvest in order`() {
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
    @Test fun `harvest does not reset partial growth`() {
        val bush = TomatoBush(); val can = WateringCan(6)
        repeat(5) { bush.water(can) }
        assertEquals("Без сорта", bush.harvest().label())
        assertNull(bush.peek())
        bush.water(can)
        assertNotNull(bush.peek())
    }
    @Test fun `bushes keep their own variety and growth when using one can`() {
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
    @Test fun `new bush has no fruit`() {
        val bush = TomatoBush()
        assertNull(bush.peek())
        assertThrows(NoTomatoException::class.java) { bush.harvest() }
    }
}

class HarvestResultTest {
    @Test fun `not ripe and picked states`() {
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
    @Test fun `show prints both outcomes and finally`() {
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
    @Test fun `main prints complete scenario`() {
        val bytes = ByteArrayOutputStream(); val previous = System.out
        try { System.setOut(PrintStream(bytes, true, Charsets.UTF_8)); main() }
        finally { System.setOut(previous) }
        assertEquals(listOf("Плода пока нет", "Собирать пока рано", "Осмотр завершён", "Лейка пуста, доливаем воду", "Собрали: Черри", "Осмотр завершён").joinToString(System.lineSeparator(), postfix = System.lineSeparator()), bytes.toString(Charsets.UTF_8))
    }
}
