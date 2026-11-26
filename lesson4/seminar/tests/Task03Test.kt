package seminar4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class Task03Test {
    @Test fun `trims a valid email and keeps its case`() {
        assertEquals("Anna@Example.test", normalizeEmail("  Anna@Example.test  "))
    }

    @Test fun `returns null for missing or blank email`() {
        assertNull(normalizeEmail(null))
        assertNull(normalizeEmail("  "))
    }

    @Test fun `needs exactly one at sign`() {
        assertNull(normalizeEmail("anna.example.test"))
        assertNull(normalizeEmail("anna@@example.test"))
    }

    @Test fun `needs text on both sides`() {
        assertNull(normalizeEmail("@example.test"))
        assertNull(normalizeEmail("anna@"))
    }

    @Test fun `does not allow whitespace inside the email`() {
        assertNull(normalizeEmail("anna smith@example.test"))
        assertNull(normalizeEmail("anna@ex\tample.test"))
    }
}
