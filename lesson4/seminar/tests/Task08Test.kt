package seminar4

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class Task08Test {
    private class Scanner(val response: () -> String?) : TicketReader {
        var reads = 0; var closes = 0
        override fun readCode(): String? { reads++; check(closes == 0); return response() }
        override fun close() { closes++ }
        fun verify() { assertEquals(1, reads); assertEquals(1, closes) }
    }
    @Test fun `своё исключение сохраняет сообщение и тот же cause`() {
        val cause = ReaderException("связь"); val e = TicketCheckException("ошибка", cause)
        assertEquals("ошибка", e.message); assertSame(cause, e.cause)
    }
    @Test fun `билет без крайних пробелов и сканер закрыт`() {
        val r = Scanner { "  A-17 " }; assertEquals("A-17", TicketInspector().inspect(r)); r.verify()
    }
    @Test fun `нет кода но сканер закрыт`() {
        for (code in listOf(null, "", " \t ")) { val r = Scanner { code }; assertNull(TicketInspector().inspect(r)); r.verify() }
    }
    @Test fun `поломка сканера сохраняет причину и закрывает устройство`() {
        val cause = ReaderException("обрыв"); val r = Scanner { throw cause }
        val e = assertThrows(TicketCheckException::class.java) { TicketInspector().inspect(r) }
        assertSame(cause, e.cause); r.verify()
    }
    @Test fun `посторонняя ошибка выходит без обёртки и сканер закрыт`() {
        val cause = IllegalStateException("сбой"); val r = Scanner { throw cause }
        assertSame(cause, assertThrows(IllegalStateException::class.java) { TicketInspector().inspect(r) }); r.verify()
    }
}
