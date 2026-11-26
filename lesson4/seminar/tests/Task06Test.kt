package seminar4

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class Task06Test {
    @Test fun `null берёт лимит площадки`() { assertEquals(30, VenueSettings(" 30 ").capacityFor(null)) }
    @Test fun `свой лимит не требует запасного`() {
        val place = VenueSettings("abc")
        assertEquals(12, place.capacityFor(12)); assertEquals(8, place.capacityFor(" 8 "))
    }
    @Test fun `пустая строка и ноль не заменяются запасным`() {
        for (input in listOf("", "  ", 0, -1)) assertThrows(EventConfigException::class.java) { VenueSettings("30").capacityFor(input) }
    }
    @Test fun `ошибка разбора сохраняет причину`() {
        val e = assertThrows(EventConfigException::class.java) { VenueSettings("30").capacityFor("abc") }
        assertTrue(e.cause is NumberFormatException)
    }
    @Test fun `Long и отсутствие обоих лимитов отклоняются`() {
        assertThrows(EventConfigException::class.java) { VenueSettings("30").capacityFor(12L) }
        assertThrows(EventConfigException::class.java) { VenueSettings(null).capacityFor(null) }
    }
}
