package seminar4

fun readCapacity(raw: String?): Int {
    val text = raw?.trim() ?: missingCapacity()
    if (text.isEmpty()) missingCapacity()
    val capacity = try {
        text.toInt()
    } catch (e: NumberFormatException) {
        throw EventConfigException("Лимит не является целым числом: $text", e)
    }
    if (capacity <= 0) throw EventConfigException("Лимит должен быть положительным")

    return capacity
}

class EventConfigException(message: String, cause: Throwable? = null) : IllegalStateException(message, cause)

fun missingCapacity(): Nothing = throw EventConfigException("Лимит мест не указан")

fun checkedCapacity(raw: String?, onFinish: () -> Unit): Int {
    try {
        return readCapacity(raw)
    } finally {
        onFinish()
    }
}

fun main() {
    val capacity = checkedCapacity("25") { println("Проверка завершена") }
    println(capacity)
}
