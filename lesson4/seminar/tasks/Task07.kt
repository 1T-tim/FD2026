package seminar4

<<<<<<< HEAD
=======
/*
 * 7. Черновик заявки · 20–25 минут
 *
 * Маша начала заполнять заявку и нажала «Отправить» без email. Она должна иметь возможность исправить тот же черновик. Но уже отправленную заявку менять нельзя.
 *
 * Допиши `RegistrationDraft`. Каждый экземпляр хранит свои имя, email и признак отправки. В начале оба поля пусты, черновик не отправлен.
 *
 * - `editName` и `editEmail` запоминают последний ввод, даже если это `null` или пока некорректная строка. Во время редактирования не проверяй формат.
 * - `submit()` получает имя через `displayName`, а email через `normalizeEmail`. Имя можно не указывать: получится «Гость». Если email не годится, брось `IllegalArgumentException` и оставь черновик доступным для исправления.
 * - При успехе верни готовый `Registration`. С этого момента все три метода должны бросать `IllegalStateException`.
 *
 * Проверь одну историю: введи имя `"  Маша  "` → попробуй отправить без email → получи ошибку → введи `" m@b "` → отправь и получи `Registration("Маша", "m@b")` → попробуй ещё раз изменить имя: теперь это ошибка состояния.
 *
 * Обсудим: в какой момент можно менять признак «отправлено» и почему не раньше?
 */

>>>>>>> 061c056 (Опубликовать решения задач 1–4 и 9 по Kotlin)
data class Registration(val name: String, val email: String)

class RegistrationDraft {
    private var name: String? = null
    private var email: String? = null
    private var submitted = false

    fun editName(value: String?) {
        checkEditable()
        name = value
    }

    fun editEmail(value: String?) {
        checkEditable()
        email = value
    }

    fun submit(): Registration {
        checkEditable()
        val validEmail = requireNotNull(normalizeEmail(email)) { "Укажи корректный email" }
        val registration = Registration(displayName(name), validEmail)
        submitted = true
        return registration
    }

    private fun checkEditable() {
        check(!submitted) { "Заявка уже отправлена" }
    }
}

fun main() {
    val draft = RegistrationDraft()
    draft.editName("  Аня  ")
    draft.editEmail(" anya@example.test ")
    println(draft.submit())
}
