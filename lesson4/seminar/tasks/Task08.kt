package seminar4

<<<<<<< HEAD
=======
/*
 * 8. Сканер билетов · 20 минут
 *
 * У входа сканируют билет. Если код не виден, билет можно показать ещё раз. Если устройство сломалось, сотрудник должен узнать о поломке. Не превращай эти два случая в один `null`.
 *
 * 1. Допиши свой класс исключения `TicketCheckException`: он наследуется от `RuntimeException` и сохраняет переданные `message` и `cause`. После создания исключения `cause` должен указывать на исходный объект, а не на новую ошибку с тем же текстом.
 * 2. Допиши `TicketInspector.inspect(reader)`. Прочитай код один раз. Убери пробелы по краям. Верни непустой код. Если сканер вернул `null` или строка пуста после обрезки, верни `null`.
 * 3. Если `readCode()` бросил `ReaderException`, брось `TicketCheckException` с понятным сообщением и исходной причиной. Другие исключения пропускай без изменений.
 * 4. После попытки чтения вызови `close()` ровно один раз: и при успехе, и при любом исключении. Считай, что `close()` не бросает исключений.
 *
 * | Ответ сканера | Результат проверки |
 * |---|---|
 * | `"  A-17 "` | `"A-17"` |
 * | `null`, `""` или `"   "` | `null` |
 * | `ReaderException` | `TicketCheckException` с исходной причиной |
 * | `IllegalStateException` | То же исключение без обёртки |
 *
 * Обсудим: что увидит сотрудник, если вместо обработки поломки написать `catch (e: Exception) { return null }`?
 */

>>>>>>> 061c056 (Опубликовать решения задач 1–4 и 9 по Kotlin)
interface TicketReader {
    fun readCode(): String?
    fun close()
}

class ReaderException(message: String) : RuntimeException(message)

<<<<<<< HEAD
class TicketCheckException(message: String, cause: Throwable) : RuntimeException(message, cause)

=======
// Шаг 1: допиши своё исключение. Сейчас оно теряет сообщение и причину.
class TicketCheckException(message: String, cause: Throwable) : RuntimeException()

// Шаг 2: реализуй проверку. Сканер после чтения всегда нужно закрыть.
>>>>>>> 061c056 (Опубликовать решения задач 1–4 и 9 по Kotlin)
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
