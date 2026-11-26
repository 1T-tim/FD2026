package seminar4

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class Task07Test {
    @Test fun `после ошибки тот же черновик можно исправить`() {
        val d = RegistrationDraft(); d.editName("  Маша  "); d.editEmail("не email")
        assertThrows(IllegalArgumentException::class.java) { d.submit() }
        d.editEmail("  Masha@example.test "); assertEquals(Registration("Маша", "Masha@example.test"), d.submit())
    }
    @Test fun `без email отправить нельзя а без имени можно`() {
        val d = RegistrationDraft(); assertThrows(IllegalArgumentException::class.java) { d.submit() }
        d.editEmail("a@b"); assertEquals(Registration("Гость", "a@b"), d.submit())
    }
    @Test fun `после отправки нельзя повторить или редактировать`() {
        val d = RegistrationDraft(); d.editEmail("a@b"); d.submit()
        assertThrows(IllegalStateException::class.java) { d.submit() }
        assertThrows(IllegalStateException::class.java) { d.editEmail(null) }
        assertThrows(IllegalStateException::class.java) { d.editName("Аня") }
    }
    @Test fun `два черновика независимы`() {
        val a = RegistrationDraft(); val b = RegistrationDraft(); a.editEmail("a@b"); a.submit()
        b.editEmail("c@d"); assertEquals("c@d", b.submit().email)
    }
}
