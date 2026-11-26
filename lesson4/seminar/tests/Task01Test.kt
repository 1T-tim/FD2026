package seminar4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Task01Test {
    @Test fun `trims a name but keeps the spaces inside`() {
        assertEquals("Аня Петрова", displayName("  Аня Петрова  "))
    }

    @Test fun `uses guest when the name is missing`() {
        assertEquals("Гость", displayName(null))
    }

    @Test fun `uses guest when the name is empty or blank`() {
        assertEquals("Гость", displayName(""))
        assertEquals("Гость", displayName(" \t "))
    }
}
