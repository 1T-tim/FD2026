package seminar4

<<<<<<< HEAD
=======
/**
 * 3. Проверь обязательный email. Убери пробелы по краям.
 * Для семинара формат такой: ровно один @, с непустым текстом слева и справа,
 * без пробелов и других пробельных символов внутри. Регистр не меняй.
 * Если email не задан или не подходит, верни null.
 * Примеры: "  Anna@demo.test " → "Anna@demo.test", "a@b" → "a@b", "anna" → null, null → null.
 * Подсказка: начать можно с `val email = raw?.trim() ?: return null`.
 */
>>>>>>> 061c056 (Опубликовать решения задач 1–4 и 9 по Kotlin)
fun normalizeEmail(raw: String?): String? {
    val email = raw?.trim() ?: return null
    if (email.any { it.isWhitespace() }) return null
    val at = email.indexOf('@')
    if (at <= 0 || at == email.lastIndex || at != email.lastIndexOf('@')) return null
    return email
}

fun main() {
    println(normalizeEmail("  anya@example.test  "))
}
