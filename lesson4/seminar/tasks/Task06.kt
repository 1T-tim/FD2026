package seminar4

<<<<<<< HEAD
=======
/*
 * 6. Настройки площадки · 10–15 минут
 *
 * У зала есть обычный лимит мест, но организатор может задать свой. Допиши метод `VenueSettings.capacityFor`. Он возвращает положительный `Int`.
 *
 * 1. Если организатор передал `null`, используй `defaultCapacity` из конструктора.
 * 2. Если он передал строку, используй её. Если передал `Int`, преобразуй его в строку.
 *    Проверь выбранный лимит через `readCapacity` из Task04. Исключение не перехватывай.
 * 3. Для остальных типов, включая `Long`, бросай `EventConfigException`. Не проверяй запасной лимит, если он не нужен.
 *
 * | Лимит зала | Ввод организатора | Результат |
 * |---|---|---|
 * | `"30"` | `null` | `30` |
 * | `"30"` | `""` | Ошибка: пустую строку передали явно |
 * | `"abc"` | `12` | `12`: запасной лимит не используем |
 * | `"30"` | `"abc"` | `EventConfigException`, причина: `NumberFormatException` |
 *
 * Обсудим: почему нельзя проверять лимит зала сразу в конструкторе?
 */

>>>>>>> 061c056 (Опубликовать решения задач 1–4 и 9 по Kotlin)
class VenueSettings(private val defaultCapacity: String?) {
    fun capacityFor(organizerLimit: Any?): Int {
        val limit = when (organizerLimit) {
            null -> defaultCapacity
            is String -> organizerLimit
            is Int -> organizerLimit.toString()
            else -> throw EventConfigException("Лимит должен быть строкой или целым числом")
        }
        return readCapacity(limit)
    }
}

fun main() {
    println(VenueSettings("30").capacityFor(null))
}
