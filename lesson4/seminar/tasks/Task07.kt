package seminar4

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
