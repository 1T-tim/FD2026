package seminar4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Task05Test {
    @Test fun `rejects an invalid seat count`() {
        assertEquals("Неверное количество мест", registrationStatus(null, 3))
    }

    @Test fun `rejects when there are not enough seats`() {
        assertEquals("Мест не хватает", registrationStatus(4, 3))
        assertEquals("Мест не хватает", registrationStatus(1, 0))
    }

    @Test fun `registers when the seats fit`() {
        assertEquals("Зарегистрирован", registrationStatus(2, 3))
    }

    @Test fun `accepts an exact fit`() {
        assertEquals("Зарегистрирован", registrationStatus(3, 3))
    }
}
