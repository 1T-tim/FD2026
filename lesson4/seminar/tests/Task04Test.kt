package seminar4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class Task04Test {
    @Test
    fun `missingCapacity throws an EventConfigException`() {
        assertThrows(EventConfigException::class.java) { missingCapacity() }
    }

    @Test
    fun `configuration exception is an illegal state and preserves a supplied message and cause`() {
        val cause = NumberFormatException("bad number")
        val failure: Throwable = EventConfigException("bad capacity", cause)

        assertTrue(failure is IllegalStateException)
        assertEquals("bad capacity", failure.message)
        assertSame(cause, failure.cause)
        assertNull(EventConfigException("bad capacity").cause)
    }

    @Test
    fun `reads trimmed positive capacities`() {
        assertEquals(4, readCapacity(" \t+004\n"))
        assertEquals(1, readCapacity("1"))
        assertEquals(Int.MAX_VALUE, readCapacity("2147483647"))
    }

    @Test
    fun `missing and blank capacities fail without a cause`() {
        for (raw in listOf(null, "", " \t\n")) {
            val failure = assertThrows(EventConfigException::class.java, { readCapacity(raw) }, "Capacity [$raw]")
            assertNull(failure.cause, "Capacity [$raw]")
        }
    }

    @Test
    fun `nonpositive parsed capacities fail without a cause`() {
        for (raw in listOf("0", " -1 ", "-2147483648")) {
            val failure = assertThrows(EventConfigException::class.java, { readCapacity(raw) }, "Capacity [$raw]")
            assertNull(failure.cause, "Capacity [$raw]")
        }
    }

    @Test
    fun `malformed and overflowing capacities retain a NumberFormatException cause`() {
        for (raw in listOf(" \tthree\n", "1 2", " 2147483648 ", "-2147483649")) {
            val parseFailure = assertThrows(NumberFormatException::class.java) { raw.trim().toInt() }
            val failure = assertThrows(EventConfigException::class.java, { readCapacity(raw) }, "Capacity [$raw]")
            val cause = failure.cause

            assertTrue(cause is NumberFormatException, "Capacity [$raw] should retain the parsing exception")
            assertEquals(parseFailure.message, cause?.message, "The cause should come from parsing the trimmed input")
        }
    }
}
