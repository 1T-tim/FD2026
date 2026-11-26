package seminar4

interface TicketReader {
    fun readCode(): String?
    fun close()
}

class ReaderException(message: String) : RuntimeException(message)

class TicketCheckException(message: String, cause: Throwable) : RuntimeException(message, cause)

class TicketInspector {
    fun inspect(reader: TicketReader): String? = try {
        reader.readCode()?.trim()?.takeIf { it.isNotEmpty() }
    } catch (e: ReaderException) {
        throw TicketCheckException("Не удалось прочитать билет", e)
    } finally {
        reader.close()
    }
}

fun main() {
    val reader = object : TicketReader {
        override fun readCode(): String = "  A-17  "
        override fun close() {}
    }
    println(TicketInspector().inspect(reader))
}
