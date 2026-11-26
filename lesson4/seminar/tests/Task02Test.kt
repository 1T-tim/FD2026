package seminar4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class Task02Test {
    @Test
    fun `accepts positive Int values through the maximum`() {
        assertEquals(1, parseSeats(1))
        assertEquals(Int.MAX_VALUE, parseSeats(Int.MAX_VALUE))
    }

    @Test
    fun `trims and parses positive decimal strings`() {
        assertEquals(3, parseSeats(" \t+003\n"))
        assertEquals(1, parseSeats("1"))
        assertEquals(Int.MAX_VALUE, parseSeats("2147483647"))
    }

    @Test
    fun `rejects zero and negative values both as Int and as String`() {
        for (raw in listOf(0, -1, Int.MIN_VALUE, "0", " -1 ", "-2147483648")) {
            assertNull(parseSeats(raw), "Expected no seat count for [$raw]")
        }
    }

    @Test
    fun `rejects blank malformed and overflowing strings`() {
        for (raw in listOf("", " \t\n", "3.0", "1 2", "1_000", "0x10", "2147483648", "-2147483649")) {
            assertNull(parseSeats(raw), "Expected no seat count for [$raw]")
        }
    }

    @Test
    fun `rejects all other types even when they represent a positive number`() {
        val inputs = listOf<Any?>(null, 2L, 2.toShort(), 2.toByte(), 2.0, true, StringBuilder("2"))

        inputs.forEachIndexed { index, raw ->
            assertNull(parseSeats(raw), "Unexpected seat count for input $index: [$raw]")
        }
    }
}
