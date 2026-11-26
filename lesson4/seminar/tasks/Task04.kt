package seminar4

<<<<<<< HEAD
=======
/**
 * 4. Прочитай обязательный лимит мест. Убери пробелы по краям.
 * Если вход равен null или строка пуста после обрезки, вызови готовую missingCapacity().
 * Разбери число через toInt(). Поймай NumberFormatException и брось
 * EventConfigException, передав исходное исключение вторым аргументом (cause).
 * Если число <= 0, брось EventConfigException без cause.
 * Примеры: " 5 " → 5, null → EventConfigException, "abc" → EventConfigException с cause.
 * Вопрос: почему missingCapacity() возвращает Nothing, а не Unit?
 */
>>>>>>> 061c056 (Опубликовать решения задач 1–4 и 9 по Kotlin)
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

<<<<<<< HEAD
=======
// Готовые вспомогательные объявления для задачи 4. Менять их не нужно.
>>>>>>> 061c056 (Опубликовать решения задач 1–4 и 9 по Kotlin)
class EventConfigException(message: String, cause: Throwable? = null) : IllegalStateException(message, cause)

fun missingCapacity(): Nothing = throw EventConfigException("Лимит мест не указан")

fun checkedCapacity(raw: String?, onFinish: () -> Unit): Int {
    try {
        return readCapacity(raw)
    } finally {
        onFinish()
    }
}

// Решение 4б: убираем return из finally, а onFinish() оставляем.
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
