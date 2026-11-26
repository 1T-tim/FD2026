package homework

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class GardenTest {
    // Проверяем через публичное поведение, не привязываясь к именам полей.
    private fun failsAs(type: Class<*>, action: () -> Unit) {
        val actual = assertThrows(IllegalStateException::class.java) { action() }
        assertEquals(type, actual.javaClass)
    }
    @Test fun `новой лейки хватает ровно на два полива`() {
        val can = WateringCan(); repeat(2) { can.pour() }
        repeat(2) { failsAs(EmptyWateringCanException::class.java) { can.pour() } }
    }
    @Test fun `долив восстанавливает две порции и не переполняет лейку`() {
        val can = WateringCan(); can.pour(); repeat(2) { can.refill() }
        repeat(2) { can.pour() }; failsAs(EmptyWateringCanException::class.java) { can.pour() }
    }
    @Test fun `у лейки и куста есть свои ошибки`() {
        val can = WateringCan(); repeat(2) { can.pour() }
        failsAs(EmptyWateringCanException::class.java) { can.pour() }
        val bush = TomatoBush(); assertNull(bush.peek()); failsAs(NoRipeTomatoException::class.java) { bush.harvest() }
    }
    @Test fun `осмотр не срывает плод а сбор возвращает увиденный помидор`() {
        val bush = TomatoBush(); val can = WateringCan()
        repeat(2) { bush.water(can); assertNull(bush.peek()) }; can.refill(); bush.water(can)
        val tomato = bush.peek(); assertNotNull(tomato); assertSame(tomato, bush.peek()); assertSame(tomato, bush.harvest())
        assertNull(bush.peek()); failsAs(NoRipeTomatoException::class.java) { bush.harvest() }
    }
    @Test fun `ранний сбор и пустая лейка не портят рост`() {
        val bush = TomatoBush(); val can = WateringCan(); repeat(2) { bush.water(can) }
        failsAs(NoRipeTomatoException::class.java) { bush.harvest() }
        failsAs(EmptyWateringCanException::class.java) { bush.water(can) }; assertNull(bush.peek())
        can.refill(); bush.water(can); assertNotNull(bush.harvest())
    }
    @Test fun `лишний полив тратит воду но не переносится на следующий урожай`() {
        val bush = TomatoBush(); val can = WateringCan(); repeat(2) { bush.water(can) }; can.refill()
        repeat(2) { bush.water(can) }; failsAs(EmptyWateringCanException::class.java) { can.pour() }
        val first = bush.harvest(); can.refill(); repeat(2) { bush.water(can) }; assertNull(bush.peek())
        can.refill(); bush.water(can); assertNotSame(first, bush.harvest())
    }
    @Test fun `кусты растут независимо а вода в общей лейке заканчивается`() {
        val a = TomatoBush(); val b = TomatoBush(); val can = WateringCan()
        a.water(can); b.water(can); failsAs(EmptyWateringCanException::class.java) { a.water(can) }
        can.refill(); repeat(2) { a.water(can) }; assertNotNull(a.peek()); assertNull(b.peek())
    }
    @Test fun `новая лейка не зависит от пустой`() {
        val a = WateringCan(); repeat(2) { a.pour() }; WateringCan().pour()
        failsAs(EmptyWateringCanException::class.java) { a.pour() }
    }
}
