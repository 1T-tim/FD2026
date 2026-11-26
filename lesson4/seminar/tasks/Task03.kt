package seminar4

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
